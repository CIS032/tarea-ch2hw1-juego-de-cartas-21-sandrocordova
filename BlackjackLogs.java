package Deber_Blackjack;

import java.util.Scanner;

/**
 * Este programa permite al usuario jugar Blackjack. La computadora actúa como
 * el distribuidor. El usuario tiene una apuesta de $ 100, y hace una apuesta en
 * cada juego. El usuario puede irse en cualquier momento, o será expulsado
 * cuando pierda todo el dinero. Reglas de la casa: el repartidor golpea a un
 * total de 16 o menos y se ubica en un total de 17 o más. El distribuidor gana
 * lazos. Se usa una nueva baraja de cartas para cada juego.
 */
import java.io.*;

public class BlackjackLogs {

    public static void main(String[] args) throws IOException {
        String ruta = "logs.txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if (archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("El fichero de texto ya estaba creado.");
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Acabo de crear el fichero de texto.");
        }

        String log = "";
        int dinero;          // Cantidad de dinero que tiene el usuario. 
        int apuesta;            // Cantidad de apuestas de usuario en un juego. 
        boolean userWins;   // ¿El usuario ganó el juego?

        System.out.println("Bienvenido al juego de blackjack");
        System.out.println();
        log += "Bienvenido al juego de blackjack \n";
        dinero = 100;
        log = log + "Dinero = 100\n";
        while (true) {
            System.out.println("Tiene " + dinero + " Dolares");
            log += "Tiene " + dinero + " Dolares\n";
            do {
                Scanner scan = new Scanner(System.in);
                System.out.println("¿Cuántos dólares quieres apostar? (Ingresa 0 para finalizar)");
                log += "¿Cuántos dólares quieres apostar? (Ingresa 0 para finalizar)\n";
                System.out.print("? ");
                log += scan + "\n";
                apuesta = scan.nextInt();
                if (apuesta < 0 || apuesta > dinero) {
                    System.out.println("Su respuesta debe estar entre 0 y" + dinero + '.');
                }
                log += "Su respuesta debe estar entre 0 y" + dinero + ".\n";
            } while (apuesta < 0 || apuesta > dinero);
            if (apuesta == 0) {
                break;
            }
            userWins = playBlackjack();
            log += playBlackjack() + "\n";

            if (userWins) {
                dinero = dinero + apuesta;
                log += dinero + "\n";
            } else {
                dinero = dinero - apuesta;
            }
            log += dinero + "\n";
            System.out.println();
            if (dinero == 0) {
                System.out.println("Parece que te has quedado sin dinero");
                log += "Parece que te has quedado sin dinero\n";
                break;
            }
        }

        System.out.println();
        System.out.println("Sales con $" + dinero + '.');
        log += "Sales con $" + dinero + '.' + "\n";
        bw.write(log);
        bw.close();
    } // end main()

    /**
     * Deje que el usuario juegue un juego de Blackjack, con la computadora como
     * dealer.
     *
     * @return true si el usuario gana el juego, falso si el usuario pierde.
     */
    static boolean playBlackjack() {
        String logdos = "\n";

        Deck deck;                  // Una baraja de cartas. Una nueva baraja para cada juego. 
        BlackjackHand dealerHand;   // La mano del crupier. 
        BlackjackHand userHand;     // La mano del usuario. 

        deck = new Deck();
        dealerHand = new BlackjackHand();
        userHand = new BlackjackHand();

       // Baraja el mazo, reparte dos cartas a cada jugador. 
        deck.shuffle();
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        userHand.addCard(deck.dealCard());
        userHand.addCard(deck.dealCard());

        System.out.println();
        System.out.println();

        /* Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21). 
         El jugador con Blackjack gana el juego. El distribuidor gana lazos. 
         */
        if (dealerHand.getBlackjackValue() == 21) {
            System.out.println("El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".");

            logdos += "El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".\n";

            System.out.println("El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".");

            logdos += "El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".\n";
            System.out.println();
            System.out.println("El distribuidor tiene Blackjack. El distribuidor gana");
            logdos += "El distribuidor tiene Blackjack. El distribuidor gana\n";
            return false;
        }

        if (userHand.getBlackjackValue() == 21) {
            System.out.println("El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".");
            logdos += "El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".\n";
            System.out.println("El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".");
            logdos += "El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".\n";
            System.out.println();
            System.out.println("Tienes Blackjack. Tú ganas");
            logdos += "Tienes Blackjack. Tú ganas\n";
            return true;
        }

        /* Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero, el usuario 
         tiene la oportunidad de robar cartas (es decir, "golpear"). 
         El ciclo while finaliza cuando el usuario elige "Stand". Si el usuario supera los 21,
         el usuario pierde inmediatamente. 
         */
        while (true) {
            //Mostrar tarjetas de usuario, y dejar que el usuario decida Hit o Stand. 

            System.out.println();
            System.out.println();
            System.out.println("Tus cartas son:");
            logdos += "Tus cartas son:\n";
            for (int i = 0; i < userHand.getCardCount(); i++) {
                System.out.println("    " + userHand.getCard(i));
            }
            Scanner aux = new Scanner(System.in);
            System.out.println("Su total es " + userHand.getBlackjackValue());
            logdos += "Su total es " + userHand.getBlackjackValue() + "\n";
            System.out.println();
            System.out.println("El concesionario muestra la " + dealerHand.getCard(0));
            logdos += "El concesionario muestra la " + dealerHand.getCard(0) + "\n";
            System.out.println();
            System.out.print("golpear (H) or seguir (S)? ");
            logdos += "golpear (H) or seguir (S)? \n";
            String opcion = aux.nextLine();

            char userAction;  // respuesta del usuario 'H' o 'S'.
            do {
                userAction = Character.toUpperCase(opcion.charAt(0));
                if (userAction != 'H' && userAction != 'S') {
                    System.out.print("Porfavor responder H o S:  ");
                }
                logdos += "Porfavor responder H o S:  \n";
            } while (userAction != 'H' && userAction != 'S');

            /* Si el usuario tiene éxito, el usuario obtiene una tarjeta. Si el usuario se para, 
             el ciclo termina (y es el turno del crupier de robar cartas). 
             */
            if (userAction == 'S') {
                // Loop ends; el usuario ha terminado de tomar cartas.
                break;
            } else {  // accion es 'H'. Dale una tarjeta al usuario.  
                // Si el usuario supera los 21, el usuario pierde.
                Card newCard = deck.dealCard();
                userHand.addCard(newCard);
                System.out.println();
                System.out.println("El usuario acerta");
                logdos += "El usuario acerta\n";
                System.out.println("Su tarjeta es la " + newCard);
                logdos += "Su tarjeta es la " + newCard + "\n";
                System.out.println("su total es ahora " + userHand.getBlackjackValue());
                logdos += "su total es ahora " + userHand.getBlackjackValue() + "\n";
                if (userHand.getBlackjackValue() > 21) {
                    System.out.println();
                    System.out.println("Has fallado al pasar de 21. Pierdes");
                    logdos += "Has fallado al pasar de 21. Pierdes" + "\n";
                    System.out.println("Otra tarjeta del distribuidor fue el "
                            + dealerHand.getCard(1));
                    logdos += "Otra tarjeta del distribuidor fue el "
                            + dealerHand.getCard(1) + "\n";
                    return false;
                }
            }

        } // end while 

        /* Si llegamos a este punto, el usuario tiene Stood con 21 o menos. Ahora es
         la oportunidad del distribuidor para dibujar. El distribuidor roba cartas hasta que el crupier
         el total es> 16. Si el crupier supera los 21, el crupier pierde.
         */
        System.out.println();
        System.out.println("usuario parado");
        System.out.println("Las tarjetas del distribuidor son");
        System.out.println("    " + dealerHand.getCard(0));
        System.out.println("    " + dealerHand.getCard(1));
        logdos += "usuario parado" + "\n"
                + "Las tarjetas del distribuidor son" + "\n"
                + "    " + dealerHand.getCard(0) + "\n"
                + "    " + dealerHand.getCard(1) + "\n";
        while (dealerHand.getBlackjackValue() <= 16) {
            Card newCard = deck.dealCard();
            System.out.println("El distribuidor golpea y obtiene la " + newCard);
            dealerHand.addCard(newCard);
            logdos += "El distribuidor golpea y obtiene la " + newCard + "\n";
            if (dealerHand.getBlackjackValue() > 21) {
                System.out.println();
                System.out.println("Distribuidor detenido por pasar de 21. Usted gana.");
                logdos += "Distribuidor detenido por pasar de 21. Usted gana." + "\n";
                return true;
            }
        }
        System.out.println("El total del distribuidor es " + dealerHand.getBlackjackValue());
        logdos += "El total del distribuidor es " + dealerHand.getBlackjackValue() + "\n";

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos. */
        System.out.println();
        if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
            System.out.println("El distribuidor gana en un empate. Pierdes");
            logdos += "El distribuidor gana en un empate. Pierdes" + "\n";
            return false;
        } else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
            System.out.println("distribuidor gana, " + dealerHand.getBlackjackValue()
                    + " puntos a " + userHand.getBlackjackValue() + ".");
            logdos += "distribuidor gana, " + dealerHand.getBlackjackValue()
                    + " puntos a " + userHand.getBlackjackValue() + "." + "\n";
            return false;
        } else {
            System.out.println("Tú ganas, " + userHand.getBlackjackValue()
                    + " puntos a " + dealerHand.getBlackjackValue() + ".");
            logdos += "Tú ganas, " + userHand.getBlackjackValue()
                    + " puntos a " + dealerHand.getBlackjackValue() + "." + "\n";
            return true;
        }

    }  // end playBlackjack()

}
