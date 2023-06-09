import Riego._
import org.scalameter._

val standardConfig = config (
  Key.exec.minWarmupRuns := 20,
  Key.exec.maxWarmupRuns := 40,
  Key.exec.benchRuns := 25,
  Key.verbose := false
) withWarmer (Warmer.Default())

val fincaRandom = fincaAlAzar(1000)
val distanciaRandom = distanciaAlAzar(1000)
val progRandom = progAlAzar(1000)
val finca:Finca = Vector(new Tablon(10,3,4), new Tablon(5,3,3), new Tablon(2,2,1), new Tablon(8,1,1), new Tablon(6,4,2))
val distancia:Distancia = Vector( Vector(0, 2, 2, 4, 4),
                                  Vector(2, 0, 4, 2, 6),
                                  Vector(2, 4, 0, 2, 2),
                                  Vector(4, 2, 2, 0, 4),
                                  Vector(4, 6, 2, 4, 0)
                                )

val programacion1:ProgRiego = Vector(0,1,4,2,3)
val programacion2:ProgRiego = Vector(2,1,4,3,0)

//val costoRiegoFinca = costoRiegoFinca(finca, programacion1)

val costoMovilidadFinca = costoMovilidad(finca, programacion2, distancia)
val costoMovilidadFincaPar = costoMovilidadPar(finca, programacion2, distancia)
val costoMovilidadFincaPar2 = costoMovilidadPar2(finca, programacion2, distancia)

standardConfig measure(costoMovilidad(finca,progRandom,distancia))
standardConfig measure(costoMovilidad2(finca,progRandom,distancia))
//standardConfig measure(costoMovilidadPar(fincaRandom,programacion1,distanciaRandom))
//standardConfig measure(costoMovilidadPar2(fincaRandom,programacion1,distanciaRandom))