import Riego._
import org.scalameter._

val standardConfig = config (
  Key.exec.minWarmupRuns := 20,
  Key.exec.maxWarmupRuns := 40,
  Key.exec.benchRuns := 25,
  Key.verbose := false
) withWarmer (Warmer.Default())

//val fincaRandom = fincaAlAzar(1000)
//val distanciaRandom = distanciaAlAzar(1000)

//Ejemplo 1 del profesor -> Verificar que las funciones sirven :D
val finca:Finca = Vector(new Tablon(10,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(8,1,1), new Tablon(6,4,2))
val distancia:Distancia = Vector( Vector(0, 2, 2, 4, 4),
                                  Vector(2, 0, 4, 2, 6),
                                  Vector(2, 4, 0, 2, 2),
                                  Vector(4, 2, 2, 0, 4),
                                  Vector(4, 6, 2, 4, 0)
                                )
val optima = programacionRiegoOptimo2(finca,distancia)
/*
val programacion1:ProgRiego = Vector(0,1,4,2,3)
val programacion2:ProgRiego = Vector(2,1,4,3,0)

val programaciones = generarProgramacionesRiego(finca)
val programacionesH = generarProgramacionesRiego2(finca)

val optimo = programacionRiegoOptimo2(finca,distancia)

val tiempoRiego = tIR(finca,programacion1)
val costoRiegoFincaTotal = costoRiegoFinca(finca, programacion1)
val costoRiegoFincaTotalPar = costoRiegoFincaPar(finca, programacion1)
//Probando costoMovilidadFinca

val costoMovilidadFinca = costoMovilidad(finca, programacion2, distancia)
val costoMovilidadFincaPar = costoMovilidadPar(finca, programacion2, distancia)
*/

//Pruebas rendimiento
/*
for{
  i <- 1 to 1
  finca = fincaAlAzar(math.pow(10,i).toInt*5)
  distancia = distanciaAlAzar(math.pow(10,i).toInt*5)
  programacion = programacionRiegoOptimo2(finca,distancia)._1
} yield (standardConfig measure(costoMovilidad(finca,programacion,distancia)),
  standardConfig measure(costoMovilidadPar(finca,programacion,distancia)), math.pow(10,i).toInt*5)
*/