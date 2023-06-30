# Proyecto-Funcional

### Autores:
- Santiago González Gálvez
- Nicolás Garcés Larrahondo
- Wilsón Andrés Mosquera Zapata

### Archivos:
- package.scala (Riego)
- pruebas.sc
- common

### Package.scala (Riego)
En este archivo encontrará todas las funciones definidas para que el programa funciones correctamente.
Las funciones que encontrará son:
- fincaAlAzar
- distanciaAlAzar
- tsup
- treg
- prio
- tIR
- costoRiegoTablon
- costoRiegoFinca
- costoRiegoFincaPar
- costoMovilidad
- costoMovilidadPar
- generarProgramacionesRiego
- generarProgramacionesRiegoPar
- programacionRiegoOptimo
- programacionRiegoOptimoPar

### Pruebas.sc
Este es el archivo ejecutable donde podrá correr todas las pruebas que se diseñaron, con el objetivo de verificar que los resultados que arrojas las funciones son los esperados.
Además, en este archivo se encuentran alojadas las pruebas de rendimiento para comparar los tiempos de ejecución de las versiones secuenciales y paralelas.

### common
Este archivo contiene las funciones que permiten realizar la paralelización de tareas, tales como parallel y task.
### Para correr el programa:

#### Usando un entorno de trabajo
Si está utlizando un IDE como Intellig, asegúrese de crear un proyecto de Scala con un paquete denominado Riego, en él deberá alojar el archivo package.scala, luego en directorio de test genere un worksheet de trabajo y aloje en él el archivo pruebas.sc. Luego de eso, sólo requiere buildear el proyecto y darle a "Run". A partir de ahí comenzará a visualizar los resultados de ejecutar las pruebas ya definidas.

#### Usando consola
Deberá tener configuradod sbt en su computador.
Estando en la consola ingrese el comando sbt console y espere.
Luego deberá copiar el contenido de package.scala y darle enter.
Luego de eso puede empezar a realizar las pruebas que se definieron en pruebas.sc
