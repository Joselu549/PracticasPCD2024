# Prácticas PCD curso 2023-2024
Ejercicios de prácticas de PCD curso 2023/2024.

# Ejercicio 1
Tenemos 10 procesos consumidores, un proceso generador de números y operaciones (producto,  suma y resta) y un proceso sumador.
El proceso generador introduce de forma alternativa un número y una operación, en bloques de 6 números y 5 operaciones, en un array de 110 elementos de tamaño (para ello deberás codificar las operaciones como enteros, por ejemplo puedes decir que 1 = suma, 2 = resta y 3 = multiplicación).  Cada proceso consumidor tiene que leer 11 posiciones consecutivas del array y calcular el resultado final de realizar las operaciones indicadas. 
Por ejemplo, si en las 5 primeras posiciones un proceso se encuentra con la siguiente secuencia '3*5+2' el resultado sería '17'. Una vez calculado el resultado de procesar las 11 posiciones, lo volcará en otro array e imprimirá por pantalla su ID y el resultado que ha calculado. Una vez que todos los procesos hayan calculado su parte y volcando la misma en el vector de resultados, el proceso sumador deberá calcular la suma de todos los resultados y mostrarla en pantalla. 
Resolver el ejercicio en Java utilizando ReentrantLock.  

# Ejercicio 2
Imagina un cruce con semáforos para peatones y vehículos. El cruce tiene dos semáforos de vehículos (Norte-Sur y Este-Oeste) y un semáforo de peatones.
Los semáforos de vehículos cambian de rojo a verde cada 10 segundos, permitiendo 5 segundos para el paso de los vehículos en la dirección correspondiente. El semáforo de peatones permite el paso igualmente cada 10 segundos durante 5 segundos. En cada instante solamente puede estar en verde uno de los tres semáforos, rotándose secuencialmente el turno de paso cada 5 segundos.
Un vehículo tarda medio segundo en cruzar el cruce, mientras que un peatón tarda 3 segundos. La calzada tiene capacidad para 4 vehículos simultáneos en cada dirección y el paso de peatones admite 10 peatones al mismo tiempo. Además, cada vehículo que cruza el cruce en una dirección, 7 segundos más tarde intenta cruzarlo en la otra dirección. Y cada peatón que cruza el cruce de peatones, a los 8 segundos vuelve a intentar cruzarlo. Para evitar accidentes, sólo pueden estar cruzando elementos del mismo (peatones, vehículos en dirección Norte-Sur o vehículos en dirección Este-Oeste), por lo que el cruce debe estar vacío antes de que se empiece a cruzar en un nuevo turno (a pesar de estar en verde).
Implementa un programa en Java utilizando semáforos para coordinar el paso continuo de 50 vehículos y 100 peatones en el cruce de manera sincronizada. Todos los vehículos empiezan en dirección Norte-Sur (e irán continuamente alternando su dirección con el paso de los turnos). Imprime por pantalla el semáforo que está en verde (turno) y el seguimiento de quién está cruzando. 

# Ejercicio 3
En este ejercicio, vamos a representar mediante un programa concurrente la actividad en un banco al que van los clientes a ser atendidos para realizar gestiones. Para ser atendido el banco dispone de 4 mesas para atender a la gente simultáneamente. Vamos a suponer que un cliente entra en el banco, tiene que ir a una máquina y elegir el servicio que necesita (esta acción tarda X milisegundos en realizarla). El banco tiene un total de 3 máquinas, el cliente se tendrá que poner en la primera que encuentre libre, una vez seleccione el servicio que necesita se dirige a la cola de la mesa con menor número clientes esperando para ser atendido. Cuando un cliente está siendo atendido tarda Y milisegundos en la mesa. Usando monitores para la sincronización, haremos una simulación donde habrá 50 hilos clientes con sus valores X, Y inicializados de forma aleatoria.
La impresión en pantalla por parte de los hilos clientes justo antes de ponerse en la cola de la mesa elegida debe tener el siguiente formato:

--------------------------------------------------------------
Cliente id ha solicitado su servicio en la máquina:
----- Tiempo en solicitar el servicio: X Será atendido en la mesa:
----- Tiempo en la mesa = Y Número de personas esperando en la mesa1=----, mesa2=-----, mesa3=-----, mesa4=-----
--------------------------------------------------------------

donde id, -----, X, Y, son valores particulares para cada hilo. Los valores de mesa 1, mesa 2, mesa 3 y mesa 4 son los valores que le llevan al cliente a tomar la decisión de a qué mesa dirigirse, es decir, sin incluirse él mismo. Los tiempos X e Y se reflejarán mediante el método Sleep() de Java. Desarrollar un programa concurrente en Java para resolver el problema anterior usando monitores como mecanismo para la sincronización.

# Ejercicio 4
Consideremos un conjunto de 30 personas que compran y tiene que pagar las compra. Para pagar hay dos cajas, caja A y B siendo la caja A más rápida que la B. Cada persona tiene que usar una caja para pagar durante un tiempo estimado (valor aleatorio entre 1 y 10) por la persona que controla las cajas. El controlador de cajas, cuando recibe llega una nueva persona a pagar estima el tiempo de pago y a aquellas personas cuyo tiempo estimado sea mayor o igual a 5 se les asignará la caja más rápida, es decir, la A. A las demás personas se les asignará la caja B.  Cada persona comprará y pagará 5 veces, es decir, el hilo repite 5 veces la siguiente secuencia de acciones: 1 Realiza la compra (representado con una llamada Thread.sleep(tiempoAleatorio)), 2 solicita ponerse en una caja, 3 Realiza el pago en la caja, 4 libera la caja  y 5 imprime en pantalla la información que se indica a continuación:

--------------------------------------------------------------
“Persona  id ha usado la caja X
Tiempo de pago= T
Thread.sleep(T)
Persona id liberando la caja X”
--------------------------------------------------------------

donde X es la caja asignada y T el tiempo asignado al trabajo que quiere imprimir el hilo.
Desarrollar un programa concurrente en Java para resolver el problema anterior usando como mecanismo para la sincronización el paso de mensajes asíncrono. 
