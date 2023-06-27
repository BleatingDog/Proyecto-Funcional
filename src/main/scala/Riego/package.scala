import scala.collection.parallel.CollectionConverters._
import scala.util.Random
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.immutable.ParVector

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
    val parejasTablones = for {
      i <- (0 to pi.length - 2).par
    } yield (pi(i), pi(i + 1))
    val distanciaPorPareja = parejasTablones.map { case (x, y) => d(x)(y) }
    distanciaPorPareja.sum
  }

  //Generando programaciones de riego
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {
    val indices = f.indices.toVector

    def generarPermutaciones(indices: Vector[Int]): Vector[ProgRiego] = {
      if (indices.isEmpty) {
        Vector(Vector.empty[Int]) // Caso base: lista vacía, retornar una programación vacía
      } else {
        for {
          i <- indices
          prog <- generarPermutaciones(indices.filterNot(_ == i))
        } yield i +: prog
      }
    }
    generarPermutaciones(indices)
  }

  def generarProgramacionesRiegoPar2(f: Finca): ParVector[ProgRiego] = {
    val indices = f.indices.toVector

    def generarPermutaciones(indices: Vector[Int]): ParVector[ProgRiego] = {
      if (indices.isEmpty) {
        ParVector(Vector.empty[Int]) // Caso base: lista vacía, retornar una programación vacía
      } else {
        indices.par.flatMap{i =>
          generarPermutaciones(indices.filterNot(_ == i)).map(i +: _)
        }
      }
    }
    generarPermutaciones(indices)
  }

  def generarProgramacionesRiegoPar(f: Finca): Vector[ProgRiego] = {

    //Verifica si una programación de riego es válida para una finca.
    def booleanProgramacion(programacion: ProgRiego, finca: Finca): Boolean = {
      val tiemposRiego = tIR(finca, programacion)

      //Tiempos de riego sin duplicado.
      val clausula1 = tiemposRiego.distinct.length == tiemposRiego.length
      //Tiempos de riego para cada elemento de la programación sea mayor o igual al tiempo de riego máximo.
      val clausula2 = tiemposRiego.zip(programacion).foldLeft(0)((acc, pair) => acc + treg(finca, pair._2)) >= tiemposRiego.max

      //Validacion T F.
      clausula1 && clausula2
    }

    //Finca to list
    val indices = f.indices.toList

    def generarTodasProgramaciones(indicesLibres: List[Int]): Vector[ProgRiego] = {
      indicesLibres match {
        //Vacio
        case Nil => Vector.empty
        //1 indice
        case indice :: Nil =>
          val programacion = Vector(indice)
          if (booleanProgramacion(programacion, f)) Vector(programacion) else Vector.empty
        //else
        case _ =>
          //parallel
          indicesLibres.toVector.par.flatMap { indice =>
            val indicesRest = indicesLibres.filterNot(_ == indice)
            val programacionRest = generarTodasProgramaciones(indicesRest)
            programacionRest.map(indice +: _)
          }.filter(booleanProgramacion(_, f)).toVector
      }
    }
    generarTodasProgramaciones(indices)
  }


  //Calculando una programación de riego óptimo

  def programacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val programaciones = generarProgramacionesRiego(f)
    val costosRiego = programaciones.map { prog => (prog, costoMovilidad(f, prog, d) + costoRiegoFinca(f, prog)) }
    costosRiego.minBy(_._2)
  }

  def programacionRiegoOptimoPar(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val programaciones = generarProgramacionesRiegoPar(f)
    val costosRiego = programaciones.par.map { prog =>
      (prog, costoMovilidadPar(f, prog, d) + costoRiegoFincaPar(f, prog))
    }
    costosRiego.minBy(_._2)
  }

}
