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

//Ejemplo 1 pdf
val finca:Finca = Vector(new Tablon(10,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(8,1,1), new Tablon(6,4,2))
val distancia:Distancia = Vector( Vector(0, 2, 2, 4, 4),
                                  Vector(2, 0, 4, 2, 6),
                                  Vector(2, 4, 0, 2, 2),
                                  Vector(4, 2, 2, 0, 4),
                                  Vector(4, 6, 2, 4, 0)
                                )
val finca2:Finca = Vector(new Tablon(9,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(8,1,1), new Tablon(6,4,2))

val programacion1:ProgRiego = Vector(0,1,4,2,3)
val programacion2:ProgRiego = Vector(2,1,4,3,0)

val tiempoRiego = tIR(finca,programacion1) //Coincide con el resultado del pdf
val costoRiegoFincaTotal = costoRiegoFinca(finca, programacion1) //Coincide con el resultado del pdf
val costoRiegoFincaTotalPar = costoRiegoFincaPar(finca, programacion2) //Coincide con el resultado del pdf
val costoMovilidadTotal = costoMovilidad(finca,programacion1,distancia) //Coincide con el resultado del pdf
val costoMovilidadTotal = costoMovilidad(finca,programacion2,distancia) //Coincide con el resultado del pdf

//PRUEBAS PROPIAS
val finca10:Finca = Vector(new Tablon(1,2,3), new Tablon(4,3,2), new Tablon(1,2,4), new Tablon(5,1,4), new Tablon(6,4,2))
val finca11:Finca = Vector(new Tablon(1,5,5), new Tablon(4,2,5), new Tablon(1,4,2), new Tablon(2,4,2), new Tablon(3,1,3))
val finca12:Finca = Vector(new Tablon(2,2,4), new Tablon(3,1,5), new Tablon(3,4,3), new Tablon(1,1,1), new Tablon(2,1,5))
val finca13:Finca = Vector(new Tablon(3,2,4))
val finca14:Finca = Vector(new Tablon(2,4,1), new Tablon(1,4,5))
val programacion10:ProgRiego = Vector(0,1,4,3,2)
val programacion11:ProgRiego = Vector(0,4,1,2,3)
val programacion12:ProgRiego = Vector(0,1,2,4,3)
val programacion13:ProgRiego = Vector(0)
val programacion14:ProgRiego = Vector(0,1)
val distancia1:Distancia = distanciaAlAzar(1)
val distancia2:Distancia = distanciaAlAzar(2)
val distancia5:Distancia = distanciaAlAzar(5)


val fincaCompleja = fincaAlAzar(4000)
val programacionCompleja = programacionAlAzar(4000)
val distanciaCompleja = distanciaAlAzar(4000)

/*
//Pruebas de tIR
val primerCasoR = tIR(finca10, programacion10)
val segundoCasoR = tIR(finca10, programacion11)
val tercerCasoR = tIR(finca11, programacion11)
val cuartoCasoR = tIR(finca13, programacion13)
val quintocasoR = tIR(finca14, programacion14)
*/

/*
//Pruebas de costoRiegoTablon
val primerCasoT = costoRiegoTablon(0,finca10,programacion10)
val segundoCasoT = costoRiegoTablon(1,finca10, programacion11)
val tercerCasoT = costoRiegoTablon(4,finca11, programacion11)
val cuartoCasoT = costoRiegoTablon(0,finca13, programacion13)
val quintoCasoT = costoRiegoTablon(1,finca14,programacion14)
*/

/*
// Pruebas de costoRiegoFinca
val primerCasoF = costoRiegoFinca(finca10, programacion10)
val primerCasoFPar = costoRiegoFincaPar(finca10, programacion10)
val segundoCasoF = costoRiegoFinca(finca10, programacion11)
val segundoCasoFPar = costoRiegoFincaPar(finca10, programacion11)
val tercerCasoF = costoRiegoFinca(finca11, programacion11)
val tercerCasoFPar = costoRiegoFincaPar(finca11, programacion11)
val cuartoCasoF = costoRiegoFinca(finca13, programacion13)
val cuartoCasoFPar = costoRiegoFincaPar(finca13, programacion13)
val quintoCasoF = costoRiegoFinca(finca14 , programacion14)
val quintoCasoFPar = costoRiegoFincaPar(finca14 , programacion14)
*/

/*
// Pruebas de costoMovilidad
val primerCasoM = costoMovilidad(finca10,programacion10, distancia5)
val primerCasoMPar = costoMovilidadPar(finca10,programacion10, distancia5)
val segundoCasoM = costoMovilidad(finca10, programacion11, distancia5)
val segundoCasoMPar = costoMovilidadPar(finca10, programacion11, distancia5)
val tercerCasoM = costoMovilidad(finca11, programacion11, distancia5)
val tercerCasoMPar = costoMovilidadPar(finca11, programacion11, distancia5)
val cuartoCasoM = costoMovilidad(finca13, programacion13, distancia1)
val cuartoCasoMPar = costoMovilidadPar(finca13, programacion13, distancia1)
val quintoCasoM = costoMovilidad(finca14, programacion14, distancia2)
val quintoCasoMPar = costoMovilidadPar(finca14, programacion14, distancia2)
*/

//PRUEBAS COMPLEJAS
/*
val complexR = tIR(fincaCompleja, programacionCompleja)
complexR.length
complexR(programacionCompleja.head) //Se espera 0
val complexT = costoRiegoTablon(3999, fincaCompleja,programacionCompleja)

val complexF = costoRiegoFinca(fincaCompleja,programacionCompleja)
val complexFPar = costoRiegoFincaPar(fincaCompleja,programacionCompleja)

val complexCM = costoMovilidad(fincaCompleja,programacionCompleja,distanciaCompleja)
val complexCMPar = costoMovilidadPar(fincaCompleja,programacionCompleja,distanciaCompleja)
*/

/*
// Pruebas de generarProgramacionesRiego
val primerCasoGP = generarProgramacionesRiego(fincaAlAzar(2))
val primerCasoGPPar = generarProgramacionesRiegoPar(fincaAlAzar(2))
primerCasoGP.length //2! = 2
primerCasoGPPar.length
val segundoCasoGP = generarProgramacionesRiego(fincaAlAzar(4))
val segundoCasoGPPar = generarProgramacionesRiegoPar(fincaAlAzar(4))
segundoCasoGP.length //4! = 24
segundoCasoGPPar.length
val tercerCasoGP = generarProgramacionesRiego(fincaAlAzar(6))
val tercerCasoGPPar = generarProgramacionesRiegoPar(fincaAlAzar(6))
tercerCasoGP.length //6! = 720
tercerCasoGPPar.length
val cuartoCasoGP = generarProgramacionesRiego(fincaAlAzar(8))
val cuartoCasoGPPar = generarProgramacionesRiegoPar(fincaAlAzar(8))
cuartoCasoGP.length //8! = 40320
cuartoCasoGPPar.length
 */

//PRUEBA COMPLEJA
/*
val complexGP = generarProgramacionesRiego(fincaAlAzar(10))
val complexGPGPPar = generarProgramacionesRiegoPar(fincaAlAzar(10))
complexGPGP.length //10! = 3628800
complexGPGPPar.length
*/

/*
// Pruebas de programacionRiegoOptimo
val primerCasoRO = programacionRiegoOptimo(finca10,distancia5)
val primerCasoROPar = programacionRiegoOptimoPar(finca10,distancia5)
val segundoCasoRO = programacionRiegoOptimo(finca11,distancia5)
val segundoCasoROPar = programacionRiegoOptimoPar(finca11,distancia5)
val tercerCasoRO = programacionRiegoOptimo(finca12,distancia5)
val tercerCasoROPar = programacionRiegoOptimoPar(finca12,distancia5)
val cuartoCasoRO = programacionRiegoOptimo(finca13,distancia1)
val cuartoCasoROPar = programacionRiegoOptimoPar(finca13,distancia1)
val quintoCasoRO = programacionRiegoOptimo(finca14,distancia2)
val quintoCasoROPar = programacionRiegoOptimoPar(finca14,distancia2)
*/

//PRUEBA COMPLEJA
/*
val fincaComplex: Vector[Tablon] = Vector(
  new Tablon(10, 3, 4),
  new Tablon(5, 3, 3),
  new Tablon(2, 2, 1),
  new Tablon(8, 1, 1),
  new Tablon(6, 4, 2),
  new Tablon(7, 2, 3),
  new Tablon(9, 5, 4),
  new Tablon(3, 2, 1),
  new Tablon(4, 6, 3),
  new Tablon(1, 4, 2)
)
val distanciaComplex: Distancia= Vector(
  Vector(0, 2, 2, 4, 4, 5, 6, 3, 5, 6),
  Vector(2, 0, 4, 2, 6, 7, 8, 5, 7, 8),
  Vector(2, 4, 0, 2, 2, 3, 4, 3, 2, 3),
  Vector(4, 2, 2, 0, 4, 5, 6, 3, 5, 6),
  Vector(4, 6, 2, 4, 0, 1, 2, 5, 3, 4),
  Vector(5, 7, 3, 5, 1, 0, 1, 6, 4, 5),
  Vector(6, 8, 4, 6, 2, 1, 0, 7, 5, 6),
  Vector(3, 5, 3, 3, 5, 6, 7, 0, 2, 3),
  Vector(5, 7, 2, 5, 3, 4, 5, 2, 0, 1),
  Vector(6, 8, 3, 6, 4, 5, 6, 3, 1, 0)
)


val complexRO = programacionRiegoOptimo(fincaComplex, distanciaComplex)
val complexROPar = programacionRiegoOptimoPar(fincaComplex,distanciaComplex)
val costoMovilidadMinComplex = costoMovilidad(fincaComplex,complexRO._1,distanciaComplex)
val costoRiegoMinComplex = costoRiegoFinca(fincaComplex,complexRO._1)
val costoTotal = costoMovilidadMinComplex + costoRiegoMinComplex
*/

/*
//Comparando tiempos
val fincaComplex2: Vector[Tablon] = Vector(
  new Tablon(10, 3, 4),
  new Tablon(5, 3, 3),
  new Tablon(2, 2, 1),
  new Tablon(8, 1, 1),
  new Tablon(6, 4, 2),
  new Tablon(7, 2, 3),
  new Tablon(9, 5, 4),
  new Tablon(3, 2, 1),
)
val distanciaComplex2: Vector[Vector[Int]] = Vector(
  Vector(0, 2, 3, 5, 4, 6, 7, 2),
  Vector(2, 0, 4, 3, 2, 3, 4, 5),
  Vector(3, 4, 0, 6, 5, 3, 2, 1),
  Vector(5, 3, 6, 0, 2, 4, 5, 3),
  Vector(4, 2, 5, 2, 0, 1, 3, 4),
  Vector(6, 3, 3, 4, 1, 0, 2, 3),
  Vector(7, 4, 2, 5, 3, 2, 0, 1),
  Vector(2, 5, 1, 3, 4, 3, 1, 0)
)
val tamanio = 1000
val progComplex2 = programacionAlAzar(8)
val fincaGrande = fincaAlAzar(tamanio)
val programacionGrande = programacionAlAzar(tamanio)
val distanciaGrande = distanciaAlAzar(tamanio)

//Comparando costoRiegoFinca
val comparacionCRiegoFinca = Vector(standardConfig measure costoRiegoFinca(fincaComplex2,progComplex2),
                                    standardConfig measure costoRiegoFincaPar(fincaComplex2,progComplex2))
comparacionCRiegoFinca.mkString("\n")

val comparacionCRiegoFincaGrande = Vector(standardConfig measure costoRiegoFinca(fincaGrande,programacionGrande),
  standardConfig measure costoRiegoFincaPar(fincaGrande,programacionGrande))
comparacionCRiegoFincaGrande.mkString("\n")

//Comparando costoMovilidad
val comparacionCMovilidad = Vector(standardConfig measure costoMovilidad(fincaComplex2,progComplex2,distanciaComplex2),
                                  standardConfig measure costoMovilidadPar(fincaComplex2,progComplex2,distanciaComplex2))
comparacionCMovilidad.mkString("\n")

val comparacionCMovilidadGrande = Vector(standardConfig measure costoMovilidad(fincaGrande,programacionGrande,distanciaGrande),
  standardConfig measure costoMovilidadPar(fincaGrande,programacionGrande,distanciaGrande))
comparacionCMovilidadGrande.mkString("\n")

//Comparando generarProgramacionRiego
val comparacionGenerarProgRiego = Vector(standardConfig measure generarProgramacionesRiego(fincaComplex2),
                                        standardConfig measure generarProgramacionesRiegoPar(fincaComplex2))
comparacionGenerarProgRiego.mkString("\n")

//Comparando programacionRiegoOptimo
val comparacionProgRiegoOptimo = Vector(standardConfig measure programacionRiegoOptimo(fincaComplex2, distanciaComplex2),
  standardConfig measure programacionRiegoOptimoPar(fincaComplex2, distanciaComplex2))
comparacionProgRiegoOptimo.mkString("\n")
*/

/*
* PRUEBAS DE RENDIMIENTO
* A continuación, se registran las pruebas diseñadas para comparar
* los tiempos de ejecución de las versiones secuenciales y paralelas
* de cada función que se paralelizó, es decir: costoRiegoFinca, costoMovilidad,
* generarProgramacionesRiego y programacionRiegoOptimo.
*
* Se diseñaron 4 tests para las dos primeras funciones y 3 para las dos últimas.
* 1 test para la función costoMovilidad aborta con error OutOfMemory
* Cada test se realizó 3 veces.
* Para un total de 42 pruebas
*/

/*
//costoRiegoFinca VS costoRiegoFincaPar

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

// ---- Prueba 4 ---- || ---- 6000 a 10000 ---- \\ -> A partir de +6000, overhead limit exceed
/*
val CM4 = for{
  i <- 3 to 3
  j <- 6 to 10000
  finca = fincaAlAzar(math.pow(10,i).toInt * j)
  programacion = programacionAlAzar(math.pow(10,i).toInt * j)
  distancia = distanciaAlAzar(math.pow(10,i).toInt * j)
} yield (standardConfig measure costoMovilidad(finca,programacion, distancia),
  standardConfig measure costoMovilidadPar(finca,programacion, distancia), math.pow(10,i).toInt * j)
val resultadoCM4 = CM4.mkString("\n")
*/

//generarProgramacionesRiego VS generarProgramacionesRiegoPar

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

val PRO2 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
  distancia = distanciaAlAzar(2 * i)
} yield (standardConfig measure programacionRiegoOptimo(finca, distancia),
  standardConfig measure programacionRiegoOptimoPar(finca, distancia), 2 * i)
val resultadoPRO2 = PRO2.mkString("\n")

// ---- Prueba 3 ---- || ---- Finca de 2,4,6,8 y 10 tablones ---- \\

val PRO3 = for{
  i <- 1 to 5
  finca = fincaAlAzar(2 * i)
  distancia = distanciaAlAzar(2 * i)
} yield (standardConfig measure programacionRiegoOptimo(finca, distancia),
  standardConfig measure programacionRiegoOptimoPar(finca, distancia), 2 * i)
val resultadoPRO3 = PRO3.mkString("\n")
*/


