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

public class Principal {

    private static Object TextIO;

    public static void main(String[] args) {
        int dinero;          // Cantidad de dinero que tiene el usuario. 
        int apuesta;            // Cantidad de apuestas de usuario en un juego. 
        boolean userWins;   // ¿El usuario ganó el juego?

        System.out.println("Bienvenido al juego de blackjack");
        System.out.println();

        dinero = 100;

        while (true) {
            System.out.println("Tiene " + dinero + " Dolares");
            do {
                Scanner scan = new Scanner(System.in);
                System.out.println("¿Cuántos dólares quieres apostar? (Ingresa 0 para finalizar)");
                System.out.print("? ");
                apuesta = scan.nextInt();
                if (apuesta < 0 || apuesta > dinero) {
                    System.out.println("Su respuesta debe estar entre 0 y" + dinero + '.');
                }
            } while (apuesta < 0 || apuesta > dinero);
            if (apuesta == 0) {
                break;
            }
            userWins = playBlackjack();
            if (userWins) {
                dinero = dinero + apuesta;
            } else {
                dinero = dinero - apuesta;
            }
            System.out.println();
            if (dinero == 0) {
                System.out.println("Parece que te has quedado sin dinero");
                break;
            }
        }

        System.out.println();
        System.out.println("Sales con $" + dinero + '.');

    } // end main()

    /**
     * Deje que el usuario juegue un juego de Blackjack, con la computadora como
     * dealer.
     *
     * @return true si el usuario gana el juego, falso si el usuario pierde.
     */
    static boolean playBlackjack() {

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
            System.out.println("El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".");
            System.out.println();
            System.out.println("El distribuidor tiene Blackjack. El distribuidor gana");
            return false;
        }

        if (userHand.getBlackjackValue() == 21) {
            System.out.println("El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".");
            System.out.println("El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".");
            System.out.println();
            System.out.println("Tienes Blackjack. Tú ganas");
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
            for (int i = 0; i < userHand.getCardCount(); i++) {
                System.out.println("    " + userHand.getCard(i));
            }
            Scanner aux = new Scanner(System.in);
            System.out.println("Su total es " + userHand.getBlackjackValue());
            System.out.println();
            System.out.println("El concesionario muestra la " + dealerHand.getCard(0));
            System.out.println();
            System.out.print("golpear (H) or seguir (S)? ");
            String opcion = aux.nextLine();

            char userAction;  // respuesta del usuario 'H' o 'S'.
            do {
                userAction = Character.toUpperCase(opcion.charAt(0));
                if (userAction != 'H' && userAction != 'S') {
                    System.out.print("Porfavor responder H o S:  ");
                }
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
                System.out.println("Su tarjeta es la " + newCard);
                System.out.println("su total es ahora " + userHand.getBlackjackValue());
                if (userHand.getBlackjackValue() > 21) {
                    System.out.println();
                    System.out.println("Has fallado al pasar de 21. Pierdes");
                    System.out.println("Otra tarjeta del distribuidor fue el "
                            + dealerHand.getCard(1));
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
        while (dealerHand.getBlackjackValue() <= 16) {
            Card newCard = deck.dealCard();
            System.out.println("El distribuidor golpea y obtiene la " + newCard);
            dealerHand.addCard(newCard);
            if (dealerHand.getBlackjackValue() > 21) {
                System.out.println();
                System.out.println("Distribuidor detenido por pasar de 21. Usted gana.");
                return true;
            }
        }
        System.out.println("El total del distribuidor es " + dealerHand.getBlackjackValue());

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos. */
        System.out.println();
        if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
            System.out.println("El distribuidor gana en un empate. Pierdes");
            return false;
        } else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
            System.out.println("distribuidor gana, " + dealerHand.getBlackjackValue()
                    + " puntos a " + userHand.getBlackjackValue() + ".");
            return false;
        } else {
            System.out.println("Tú ganas, " + userHand.getBlackjackValue()
                    + " puntos a " + dealerHand.getBlackjackValue() + ".");
            return true;
        }

    }  // end playBlackjack()
}
