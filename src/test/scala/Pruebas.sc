import Riego._
import org.scalameter._

import scala.util.Random

val standardConfig = config (
  Key.exec.minWarmupRuns := 20,
  Key.exec.maxWarmupRuns := 40,
  Key.exec.benchRuns := 25,
  Key.verbose := false
) withWarmer (Warmer.Default())

def programacionAlAzar(n: Int): Vector[Int] = {
  val numeros = (0 until n).toVector
  val numerosAleatorios = Random.shuffle(numeros)

  Vector.tabulate(n)(i => numerosAleatorios(i))
}

/*
val fincaRandom = fincaAlAzar(100)
val distanciaRandom = distanciaAlAzar(100)
val riegoOptimo = programacionRiegoOptimo(fincaRandom,distanciaRandom)
*/

//Ejemplo 1 del profesor -> Verificar que las funciones sirven :D

/*
val finca:Finca = Vector(new Tablon(10,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(8,1,1), new Tablon(6,4,2))
val distancia:Distancia = Vector( Vector(0, 2, 2, 4, 4),
                                  Vector(2, 0, 4, 2, 6),
                                  Vector(2, 4, 0, 2, 2),
                                  Vector(4, 2, 2, 0, 4),
                                  Vector(4, 6, 2, 4, 0)
                                )
val finca2:Finca = Vector(new Tablon(9,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(8,1,1), new Tablon(6,4,2))
val finca3:Finca = Vector(new Tablon(10,3,2), new Tablon(3,5,3), new Tablon(8,4,1), new Tablon(5,3,2),new Tablon(5,1,1))
val distancia3 = Vector(Vector(0, 10, 8, 1, 12), Vector(10, 0, 3, 8, 3), Vector(8, 3, 0, 15, 14), Vector(1, 8, 15, 0, 7), Vector(12,3,14,7,0))
val programacionOptima3 = programacionRiegoOptimo(finca3,distancia3)
val programacionOptima = programacionRiegoOptimo(finca,distancia)
val programacionOptima2 = programacionRiegoOptimo(finca2,distancia)
val programacionOptima2 = programacionRiegoOptimoPar(finca2,distancia)
val prog = Vector(1, 3, 4, 2, 0)
val tiempo = tIR(finca,prog)
val costo = costoRiegoFinca(finca,prog) + costoMovilidad(finca,prog,distancia)
val costoTablon = costoRiegoTablon(0,finca,prog)

val programacion1:ProgRiego = Vector(0,1,4,2,3)
val programacion2:ProgRiego = Vector(2,1,4,3,0)

val programaciones = generarProgramacionesRiego(finca)
val programacionesH = generarProgramacionesRiego(finca)

val optimo = programacionRiegoOptimo(finca,distancia)
val optimoP = programacionRiegoOptimoPar(finca,distancia)

val tiempoRiego = tIR(finca,programacion1)
val costoRiegoFincaTotal = costoRiegoFinca(finca, programacion1)
val costoRiegoFincaTotalPar = costoRiegoFincaPar(finca, programacion1)
//Probando costoMovilidadFinca

val costoMovilidadFinca = costoMovilidad(finca, programacion1, distancia)
val costoMovilidadFincaPar = costoMovilidadPar(finca, programacion1, distancia)
*/

/*
//Testing Programaciones
val generarProgramacionesRiegoTest = generarProgramacionesRiego(finca)
val generarProgramacionesRiegoPar2Test = generarProgramacionesRiegoPar2(finca)
*/

/*
* PRUEBAS DE RENDIMIENTO
* A continuación, se registran las pruebas diseñadas para comparar
* los tiempos de ejecución de las versiones secuenciales y paralelas
* de cada función que se paralelizó, es decir: costoRiegoFinca, costoMovilidad,
* generarProgramacionesRiego y programacionRiegoOptimo.
*
* Se diseñaron 4 tests por función, y cada test se ejecutó 3 veces. Para un total
* de 48 pruebas de rendimiento
*/


//costoRiegoFinca VS costoRiegoFincaPar
/*
// ---- Prueba 1 ---- || ---- 10 a 500 ---- \\
val CRF1 = for{
  i <- 1 to 2
  j <- 1 to 5
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoRiegoFinca(finca,programacion),
  standardConfig measure costoRiegoFincaPar(finca,programacion), math.pow(10,i).toInt * j)
val resultadoCRF1 = CRF1.mkString("\n")

// ---- Prueba 2 ---- || ---- 600 a 1000 ---- \\
val CRF2 = for{
  i <- 2 to 2
  j <- 6 to 10
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoRiegoFinca(finca,programacion),
  standardConfig measure costoRiegoFincaPar(finca,programacion), math.pow(10,i).toInt * j)
val resultadoCRF2 = CRF2.mkString("\n")

// ---- Prueba 3 ---- || ---- 2000 a 5000 ---- \\
val CRF3 = for{
  i <- 3 to 3
  j <- 2 to 5
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoRiegoFinca(finca,programacion),
  standardConfig measure costoRiegoFincaPar(finca,programacion), math.pow(10,i).toInt * j)
val resultadoCRF3 = CRF3.mkString("\n")

// ---- Prueba 4 ---- || ---- 6000 a 8000 ---- \\
val CRF4 = for{
  i <- 3 to 3
  j <- 6 to 8
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoRiegoFinca(finca,programacion),
  standardConfig measure costoRiegoFincaPar(finca,programacion), math.pow(10,i).toInt * j)
val resultadoCRF4 = CRF4.mkString("\n")

//costoMovilidad VS costoMovilidadPar

// ---- Prueba 1 ---- || ---- 10 a 500 ---- \\
val CM1 = for{
  i <- 1 to 2
  j <- 1 to 5
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
  distancia = distanciaAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoMovilidad(finca,programacion, distancia),
  standardConfig measure costoMovilidadPar(finca,programacion, distancia), math.pow(10,i).toInt * j)
val resultadoCM1 = CM1.mkString("\n")


// ---- Prueba 2 ---- || ---- 600 a 1000 ---- \\
val CM2 = for{
  i <- 2 to 2
  j <- 6 to 10
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
  distancia = distanciaAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoMovilidad(finca,programacion, distancia),
  standardConfig measure costoMovilidadPar(finca,programacion, distancia), math.pow(10,i).toInt * j)
val resultadoCM2 = CM2.mkString("\n")



// ---- Prueba 3 ---- || ---- 2000 a 5000 ---- \\
val CM3 = for{
  i <- 3 to 3
  j <- 2 to 4
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
  distancia = distanciaAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoMovilidad(finca,programacion, distancia),
  standardConfig measure costoMovilidadPar(finca,programacion, distancia), math.pow(10,i).toInt * j)
val resultadoCM3 = CM3.mkString("\n")

val CM3 = for{
  i <- 3 to 3
  j <- 5 to 5
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
  distancia = distanciaAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoMovilidad(finca,programacion, distancia),
  standardConfig measure costoMovilidadPar(finca,programacion, distancia), math.pow(10,i).toInt * j)
val resultadoCM3 = CM3.mkString("\n")
*/


// ---- Prueba 4 ---- || ---- 6000 a 10000 ---- \\ -> A partir de +6000, overhead limit exceed
/*
val CM4 = for{
  i <- 3 to 3
  j <- 6 to 6
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
  distancia = distanciaAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoMovilidad(finca,programacion, distancia),
  standardConfig measure costoMovilidadPar(finca,programacion, distancia), math.pow(10,i).toInt * j)
val resultadoCM4 = CM4.mkString("\n")
*/


//generarProgramacionesRiego VS generarProgramacionesRiegoPar
/*
// ---- Prueba 1 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\

val GPR1 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
} yield (standardConfig measure generarProgramacionesRiego(finca),
  standardConfig measure generarProgramacionesRiegoPar(finca), 2 * i)
val resultadoGPR1 = GPR1.mkString("\n")


// ---- Prueba 2 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\

val GPR2 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
} yield (standardConfig measure generarProgramacionesRiego(finca),
  standardConfig measure generarProgramacionesRiegoPar(finca), 2 * i)
val resultadoGPR2 = GPR2.mkString("\n")

// ---- Prueba 3 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\

val GPR3 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
} yield (standardConfig measure generarProgramacionesRiego(finca),
  standardConfig measure generarProgramacionesRiegoPar(finca), 2 * i)
val resultadoGPR3 = GPR3.mkString("\n")
*/


//programacionRiegoOptimo VS programacionRiegoOptimoPar

// ---- Prueba 1 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\

val PRO1 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
  distancia = distanciaAlAzar(2 * i)
} yield (standardConfig measure programacionRiegoOptimo(finca, distancia),
  standardConfig measure programacionRiegoOptimoPar(finca, distancia), 2 * i)
val resultadoPRO1 = PRO1.mkString("\n")

// ---- Prueba 2 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\
/*
val PRO2 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
  distancia = distanciaAlAzar(2 * i)
} yield (standardConfig measure programacionRiegoOptimo(finca, distancia),
  standardConfig measure programacionRiegoOptimoPar(finca, distancia), 2 * i)
val resultadoPRO2 = PRO2.mkString("\n")

// ---- Prueba 3 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\
*/
/*

val PRO3 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
  distancia = distanciaAlAzar(2 * i)
} yield (standardConfig measure programacionRiegoOptimo(finca, distancia),
  standardConfig measure programacionRiegoOptimoPar(finca, distancia), 2 * i)
val resultadoPRO3 = PRO3.mkString("\n")
*/
// ---- Prueba 4 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\
/*
val PRO4 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
  distancia = distanciaAlAzar(2 * i)
} yield (standardConfig measure programacionRiegoOptimo(finca, distancia),
  standardConfig measure programacionRiegoOptimoPar(finca, distancia), 2 * i)
val resultadoPRO1 = PRO4.mkString("\n")

*/

/*
val fincaAlAzar1 = fincaAlAzar(2000)
val distanciaAlAzar1 = distanciaAlAzar(2000)
val programacionAlAzar1 = programacionAlAzar(2000)

standardConfig measure programacionRiegoOptimo(fincaAlAzar1,distanciaAlAzar1)
standardConfig measure programacionRiegoOptimoPar(fincaAlAzar1,distanciaAlAzar1)

val costoMovilidadFinca = costoMovilidad(fincaAlAzar1, programacionAlAzar1, distanciaAlAzar1)
val costoMovilidadFincaPar = costoMovilidadPar(fincaAlAzar1, programacionAlAzar1, distanciaAlAzar1)

standardConfig measure costoMovilidad(fincaAlAzar1,programacionAlAzar1,distanciaAlAzar1)
standardConfig measure costoMovilidadPar(fincaAlAzar1,programacionAlAzar1,distanciaAlAzar1)

standardConfig measure costoRiegoFinca(fincaAlAzar1,programacionAlAzar1)
standardConfig measure costoRiegoFincaPar(fincaAlAzar1,programacionAlAzar1)
*/
