import scala.collection.parallel.CollectionConverters._
import scala.util.Random
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable.ParVector
import common._
package object Riego {

  // Tipos de datos
  type Tablon = (Int, Int, Int)
  type Finca = Vector[Tablon]
  type Distancia = Vector[Vector[Int]]
  type ProgRiego = Vector[Int]
  type TiempoInicioRiego = Vector[Int]

  // ------- Generación de fincas y distancias al azar  ------- \\
  val random = new Random()

  def fincaAlAzar(long: Int): Finca = {
    val v = Vector.fill(long) {
      (
        random.nextInt(long * 2) + 1,
        random.nextInt(long) + 1,
        random.nextInt(4) + 1
      )
    }
    v
  }

  def distanciaAlAzar(long: Int): Distancia = {
    val v = Vector.fill(long, long) {
      random.nextInt(long * 3) + 1
    }
    Vector.tabulate(long, long) {
      (i, j) =>
        if (i < j) v(i)(j)
        else if (i == j) 0
        else v(j)(i)
    }
  }

  // ------- Explorar entradas de cada tablón ------- \\
  def tsup(f: Finca, i: Int): Int = {
    f(i)._1
  }

  def treg(f: Finca, i: Int): Int = {
    f(i)._2
  }

  def prio(f: Finca, i: Int): Int = {
    f(i)._3
  }

  // ------- Implementación del problema de riego ------- \\

  //Calculando el tiempo de inicio de riego
  def tIR(f: Finca, pi: ProgRiego): TiempoInicioRiego = {
    def acumulado(v: Vector[Int]): Vector[Int] = {
      v.scanLeft(0)(_ + _).zip(pi).sortBy(_._2).map(x => x._1)
    }

    val tiempoInicioRiego = Vector.tabulate(pi.length - 1)(i => treg(f, pi(i)))
    acumulado(tiempoInicioRiego)
  }

  //Calculando costos
  def costoRiegoTablon(i: Int, f: Finca, pi: ProgRiego): Int = {
    val tiempoInicioRiego = tIR(f, pi)
    val tiempoSupervivencia = tsup(f, i)
    val tiempoRiego = treg(f, i)
    val prioridadTablon = prio(f, i)

    if (tiempoSupervivencia - tiempoRiego >= tiempoInicioRiego(i)) {
      tiempoSupervivencia - (tiempoInicioRiego(i) + tiempoRiego)
    }
    else {
      prioridadTablon * ((tiempoInicioRiego(i) + tiempoRiego) - tiempoSupervivencia)
    }
  }
  def costoRiegoFinca(f: Finca, pi: ProgRiego): Int = {
    val costos = for {
      id_tablon <- pi
    } yield costoRiegoTablon(id_tablon, f, pi)
    costos.sum
  }

  def costoRiegoFincaPar(f: Finca, pi: ProgRiego): Int = {
    val costos = for {
      id_tablon <- pi.par
    } yield costoRiegoTablon(id_tablon, f, pi)
    costos.sum
  }

  def costoMovilidad(f: Finca, pi: ProgRiego, d: Distancia): Int = {
    val parejasTablones = for {
      i <- 0 to pi.length - 2
    } yield (pi(i), pi(i + 1))
    val distanciaPorPareja = parejasTablones.map { case (x, y) => d(x)(y) }
    distanciaPorPareja.sum
  }

  def costoMovilidadPar(f: Finca, pi: ProgRiego, d: Distancia): Int = {

    def costoMovilidadAux(intervalo: (Int,Int)): Int = {
      val tablones = for {
        i <- intervalo._1 until intervalo._2
      } yield (pi(i), pi(i + 1))
      tablones.map { case (x, y) => d(x)(y) }.sum
    }

    val umbral = 800
    if (pi.length <= umbral) {
      costoMovilidad(f, pi, d)
    } else {
      val n = pi.length
      val cuarto = n / 4
      val resto = n % 4
      val divisiones = (0 until 4).toVector.map { i =>
        val inicio = i * cuarto + math.min(i, resto)
        val fin = inicio + cuarto + (if (i < resto) 1 else 0)
        (inicio,fin)
      }
      val resul = parallel(costoMovilidadAux(divisiones(0)),
        costoMovilidadAux(divisiones(1)),
        costoMovilidadAux(divisiones(2)),
        costoMovilidadAux(divisiones(3)._1,divisiones(3)._2 - 1))
      resul._1 + resul._2 + resul._3 + resul._4
    }
  }

  //Generando programaciones de riego
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {
    val indices = f.indices.toVector

    def generarPermutaciones(indices: Vector[Int]): Vector[ProgRiego] = {
      if (indices.isEmpty) {
        Vector(Vector.empty[Int]) // Caso base: lista vacía, retornar una programación vacía
      } else {
        indices.flatMap { i =>
          generarPermutaciones(indices.filterNot(_ == i)).map(i +: _)
        }
      }
    }
    generarPermutaciones(indices)
  }

  def generarProgramacionesRiegoPar(f: Finca): ParVector[ProgRiego] = {
    val indices = f.indices.toVector

    def generarPermutaciones(indices: Vector[Int]): Vector[ProgRiego] = {
      if (indices.isEmpty) {
        Vector(Vector.empty[Int]) // Caso base: lista vacía, retornar una programación vacía
      } else {
        indices.flatMap{i =>
          generarPermutaciones(indices.filterNot(_ == i)).map(i +: _)
        }
      }
    }

    def generarPermutacionesPar(indices: Vector[Int]): ParVector[ProgRiego] = {
      if (indices.isEmpty) {
        ParVector(Vector.empty[Int]) // Caso base: lista vacía, retornar una programación vacía
      } else {
        indices.par.flatMap { i =>
          generarPermutaciones(indices.filterNot(_ == i)).map(i +: _)
        }
      }
    }
    generarPermutacionesPar(indices)
  }

  //Calculando una programación de riego óptimo

  def programacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val programaciones = generarProgramacionesRiego(f)
    val costosRiego = programaciones.map { prog => (prog, costoMovilidad(f, prog, d) + costoRiegoFinca(f, prog)) }
    costosRiego.minBy(_._2)
  }

  def programacionRiegoOptimoPar(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val programaciones = generarProgramacionesRiegoPar(f)
    val costosRiego = programaciones.map { prog =>
      (prog, costoMovilidadPar(f, prog, d) + costoRiegoFincaPar(f, prog))
    }
    costosRiego.minBy(_._2)
  }
}
