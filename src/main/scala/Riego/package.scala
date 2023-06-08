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
  /*
  //Calculando el tiempo de inicio de riego
  def tIR(f:Finca, pi:ProgRiego): TiempoInicioRiego = {

  }

  //Calculando costos
  def costoRiegoTablon(i: Int, f: Finca, pi: ProgRiego): Int = {

  }

  def costoRiegoFinca(f: Finca, pi: ProgRiego): Int = {

  }

  def costoRiegoFincaPar(f: Finca, pi: ProgRiego): Int = {

  }

  def costoMovilidad(f: Finca, pi: ProgRiego, d: Distancia): Int = {

  }

  def costoMovilidadPar(f: Finca, pi: ProgRiego, d: Distancia): Int = {

  }

  //Generando programaciones de riego
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {

  }

  def generarProgramacionesRiegoPar(f: Finca): Vector[ProgRiego] = {

  }

  //Calculando una programación de riego óptimo
  def programacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {

  }

  def programacionRiegoOptimoPar(f: Finca, d: Distancia): (ProgRiego, Int) = {

  }
  */
}
