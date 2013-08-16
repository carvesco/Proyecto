# Batalla Naval
Juego de Batalla Naval via LAN, hecho en JAVA trabajando con Sockets, desarrollado en Netbeans

## Main
Contiene la mayor parte del juego, la interfáz gráfica y las funciones básicas del juego.

### Métodos clase Main
<table>
  <tr>
    <th>Nombre</th>
    <th>Descripción</th>
  </tr>
  <tr>
    <td>resetAll()</td>
    <td>Vuelve a correr otra instancia del juego, además que cierra la instancia actual</td>
  </tr>
  <tr>
    <td>enemyAttack()</td>
    <td>Se ejecuta el método hasta que el juego termina, reproduce el sonido cuando el enemigo ataca una nave</td>
  </tr>
  <tr>
    <td>startAttack()</td>
    <td>Al momento que los dos jugadores inician el juego, se crea la cuadrícula de ataque e inicia el juego</td>
  </tr>
  <tr>
    <td>getChat()</td>
    <td>Agrega a la ventana del chat todos los mensajes que reciba</td>
  </tr>
  <tr>
    <td>getAttack()</td>
    <td>Obtiene las posiciones de ataque del contrario y llama al método attack con las posiciones guardadas</td>
  </tr>
  <tr>
    <td>resetBombs()</td>
    <td>Al momento que las bombas se acaban, crea un contador de 10 segundos hasta 0 y vuelve a recargar a 10 las bombas</td>
  </tr>
  <tr>
    <td>youWin()</td>
    <td>Termina el juego y envía un mensaje a la pantalla de que el jugador ha ganado</td>
  </tr>
  <tr>
    <td>gameOver()</td>
    <td>Coloca los barcos en las posiciones actuales para evitar que se muevan de ahí mientras el panel no está seleccionado</td>
  </tr>
  <tr>
    <td>fillHealt()</td>
    <td>Establece cada segundo los puntos de vida de cada barco y bombas</td>
  </tr>
  <tr>
    <td>explode()</td>
    <td>Coloca el gif de explosión en la posición del barco y reproduce el sonido de explotar</td>
  </tr>
  <tr>
    <td>attack()</td>
    <td>Realiza la comprobación con cada barco y analiza si esta por hundirse o no</td>
  </tr>
  <tr>
    <td>getSizes()</td>
    <td>Obtiene el tamaño de todos los barcos así como sus posiciones en XY</td>
  </tr>
  <tr>
    <td>fillPos()</td>
    <td>Obtiene las posiciones de cada barco</td>
  </tr>
  <tr>
    <td>connect()</td>
    <td>Empieza la conexión con el servidor</td>
  </tr>
  <tr>
    <td>start()</td>
    <td>Ejecuta los métodos de getNetworks() y connect() para que se inicie la pantalla de conexión al servidor</td>
  </tr>
</table>
