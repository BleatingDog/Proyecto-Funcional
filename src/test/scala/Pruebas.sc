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
val finca:Finca = Vector(new Tablon(10,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(20,1,1), new Tablon(6,4,2))

//val finca:Finca = Vector(new Tablon(10,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(20,1,1), new Tablon(6,4,2))
val distancia:Distancia = Vector( Vector(0, 2, 2, 4, 4),
                                  Vector(2, 0, 4, 2, 6),
                                  Vector(2, 4, 0, 2, 2),
                                  Vector(4, 2, 2, 0, 4),
                                  Vector(4, 6, 2, 4, 0)
                                )

val programacionOptima = programacionRiegoOptimo(finca,distancia)
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
//Pruebas rendimiento

/*
for{
  i <- 1 to 1
  finca = fincaAlAzar(math.pow(10,i).toInt)
  distancia = distanciaAlAzar(math.pow(10,i).toInt)
  //programacion = programacionRiegoOptimo(finca,distancia)._1
} yield (standardConfig measure generarProgramacionesRiego(finca),
  standardConfig measure generarProgramacionesRiegoPar(finca), math.pow(10,i))


val fincaAlAzar1 = fincaAlAzar(20)
val distanciaAlAzar1 = distanciaAlAzar(20)
//val programacion = programacionRiegoOptimo(fincaAlAzar1, distanciaAlAzar1)._1
val programacion = Vector(5, 1, 9, 12, 18, 4, 16, 15, 19, 7, 3, 0, 2, 17, 13, 6, 11, 8, 10, 14)
val costoMovilidadFinca = costoMovilidad(fincaAlAzar1, programacion, distanciaAlAzar1)
val costoMovilidadFincaPar = costoMovilidadPar2(fincaAlAzar1, programacion, distanciaAlAzar1)


val finca8 = fincaAlAzar(8)
generarProgramacionesRiego(finca8)(40319)
generarProgramacionesRiegoPar(finca8)(40319)
standardConfig measure generarProgramacionesRiego(finca8)
standardConfig measure generarProgramacionesRiegoPar(finca8)

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
