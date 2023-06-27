import scala.collection.parallel.CollectionConverters._
import scala.util.Random
package object Riego {

  // Tipos de datos
  type Tablon = (Int,Int,Int)
  type Finca = Vector[Tablon]
  type Distancia = Vector[Vector[Int]]
  type ProgRiego = Vector[Int]
  type TiempoInicioRiego = Vector[Int]

  // ------- Generación de fincas y distancias al azar  ------- \\
  val random = new Random()

  def fincaAlAzar(long: Int): Finca = {
    val v = Vector.fill(long){
      (
        random.nextInt(long * 2) + 1,
        random.nextInt(long) + 1,
        random.nextInt(4) + 1
      )}
    v
  }

  def distanciaAlAzar(long: Int): Distancia = {
    val v = Vector.fill(long, long){
      random.nextInt(long * 3) + 1
    }
    Vector.tabulate(long, long){
      (i,j) => if (i < j) v(i)(j)
      else if (i==j) 0
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
  def tIR(f:Finca, pi:ProgRiego): TiempoInicioRiego = {
    val tiemposRiego = pi.scanLeft(0)((acc, tablon) => acc + treg(f,tablon)) //tiempos de riego en orden ascendente
    val tiemposConAjuste = tiemposRiego.take(pi.length) //El último elemento de tiemposRiego representa el tiempo final del último tablón, por lo tanto no se toma
    pi.map(x => tiemposConAjuste(pi(x)))  //tiempos de riego por orden de tablón
  }

  //Calculando costos
  def costoRiegoTablon(i: Int, f: Finca, pi: ProgRiego): Int = {
    val tiempoInicioRiego = tIR(f,pi)
    val tiempoSupervivencia = tsup(f,i)
    val tiempoRiego = treg(f,i)
    val prioridadTablon = prio(f,i)

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
    val distanciaPorPareja = parejasTablones.map{case (x, y) => d(x)(y)}
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
    val indices = (0 to f.length - 1).toVector
    def permutaciones(indiceDisponible: Vector[Int], acc: Vector[Int]): Vector[Vector[Int]] = {
      indiceDisponible match {
        case Vector() => Vector(acc)
        case _ => for {
          i <- indiceDisponible
          resto <- permutaciones(indiceDisponible.filterNot(_ == i), acc :+ i)
        } yield resto
      }
    }
    permutaciones(indices, Vector())
  }

  def generarProgramacionesRiego2(f: Finca): Vector[ProgRiego] = {
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

  
  /*
  def generarProgramacionesRiegoPar(f: Finca): Vector[ProgRiego] = {

  }
  */

  //Calculando una programación de riego óptimo

  def programacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val programaciones = generarProgramacionesRiego2(f)
    val costosRiego = programaciones.map{prog => (prog, costoMovilidad(f, prog, d) + costoRiegoFinca(f, prog))}
    costosRiego.minBy(_._2)
    //val costoOptimo = costosRiego.min
    //val programacionOptima = (programaciones(costosRiego.indexOf(costoOptimo)), costoOptimo)
    //programacionOptima
  }


  /*
  def programacionRiegoOptimoPar(f: Finca, d: Distancia): (ProgRiego, Int) = {

  }
  */
}
