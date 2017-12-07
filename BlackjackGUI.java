package Deber_Blackjack;

import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;

public class BlackjackGUI {

    /**
     * Este programa permite al usuario jugar Blackjack. La computadora actúa
     * como el distribuidor. El usuario tiene una apuesta de $ 100, y hace una
     * apuesta en cada juego. El usuario puede irse en cualquier momento, o será
     * expulsado cuando pierda todo el dinero. Reglas de la casa: el repartidor
     * golpea a un total de 16 o menos y se ubica en un total de 17 o más. El
     * distribuidor gana lazos. Se usa una nueva baraja de cartas para cada
     * juego.
     */
    private static Object TextIO;

    public static void main(String[] args) {
        int dinero;          // Cantidad de dinero que tiene el usuario. 
        int apuesta;            // Cantidad de apuestas de usuario en un juego. 
        boolean userWins;   // ¿El usuario ganó el juego?

        JOptionPane.showMessageDialog(null, "Bienvenido al juego de blackjack");

        dinero = 100;

        while (true) {
            JOptionPane.showMessageDialog(null, "Tiene " + dinero + " Dolares");

            do {
                apuesta = Integer.parseInt(JOptionPane.showInputDialog(null, "¿Cuántos dólares quieres apostar? "
                        + "(Ingresa 0 para finalizar)?"));
                if (apuesta < 0 || apuesta > dinero) {
                    JOptionPane.showMessageDialog(null, "Su respuesta debe estar entre 0 y" + dinero + '.');
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
                JOptionPane.showMessageDialog(null, "Parece que te has quedado sin dinero");
                break;
            }
        }

        System.out.println();
        System.out.println("Sales con $" + dinero + '.');
        JOptionPane.showMessageDialog(null, "Sales con $" + dinero + '.');

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

        /* Verifica si uno de los jugadores tiene Blackjack (dos cartas que suman un total de 21). 
         El jugador con Blackjack gana el juego. El distribuidor gana lazos. 
         */
        if (dealerHand.getBlackjackValue() == 21) {
            JOptionPane.showMessageDialog(null, "El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ". \n"
                    + "El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".\n"
                    + "El distribuidor tiene Blackjack. El distribuidor gana");
            return false;
        }

        if (userHand.getBlackjackValue() == 21) {

            JOptionPane.showMessageDialog(null, "El distribuidor tiene la " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".\n"
                    + "El usuario tiene la " + userHand.getCard(0)
                    + " y la " + userHand.getCard(1) + ".\n"
                    + "Tienes Blackjack. Tú ganas");
            return true;
        }

        /* Si ninguno de los jugadores tiene Blackjack, juega el juego. Primero, el usuario 
         tiene la oportunidad de robar cartas (es decir, "golpear"). 
         El ciclo while finaliza cuando el usuario elige "Stand". Si el usuario supera los 21,
         el usuario pierde inmediatamente. 
         */
        while (true) {
            //Mostrar tarjetas de usuario, y dejar que el usuario decida Hit o Stand. 
            JOptionPane.showMessageDialog(null, "Tus cartas son:");
            for (int i = 0; i < userHand.getCardCount(); i++) {
                JOptionPane.showMessageDialog(null, "    " + userHand.getCard(i));

            }
            JOptionPane.showMessageDialog(null, "Su total es " + userHand.getBlackjackValue());
            JOptionPane.showMessageDialog(null, "El concesionario muestra la " + dealerHand.getCard(0));

            String opcion = JOptionPane.showInputDialog(null, "golpear (H) or seguir (S)? ");
            System.out.print("golpear (H) or seguir (S)? ");

            char userAction;  // respuesta del usuario 'H' o 'S'.
            do {
                userAction = Character.toUpperCase(opcion.charAt(0));
                if (userAction != 'H' && userAction != 'S') {
                    JOptionPane.showMessageDialog(null, "Porfavor responder H o S:  ");
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
                JOptionPane.showMessageDialog(null, "El usuario acerta\n"
                        + "Su tarjeta es la " + newCard
                        + "\n su total es ahora " + userHand.getBlackjackValue());
                if (userHand.getBlackjackValue() > 21) {

                    JOptionPane.showMessageDialog(null, "Has fallado al pasar de 21. Pierdes\n"
                            + "Otra tarjeta del distribuidor fue el "
                            + dealerHand.getCard(1));
                    return false;
                }
            }

        } // end while 

        /* Si llegamos a este punto, el usuario tiene Stood con 21 o menos. Ahora es
         la oportunidad del distribuidor para dibujar. El distribuidor roba cartas hasta que el crupier
         el total es> 16. Si el crupier supera los 21, el crupier pierde.
         */
        JOptionPane.showMessageDialog(null, "usuario parado\n"
                + "Las tarjetas del distribuidor son\n"
                + "    " + dealerHand.getCard(0)
                + "\n    " + dealerHand.getCard(1));
        while (dealerHand.getBlackjackValue() <= 16) {
            Card newCard = deck.dealCard();
            JOptionPane.showMessageDialog(null, "El distribuidor golpea y obtiene la " + newCard);
            dealerHand.addCard(newCard);
            if (dealerHand.getBlackjackValue() > 21) {
                System.out.println();
                JOptionPane.showMessageDialog(null, "Distribuidor detenido por pasar de 21. Usted gana.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "El total del distribuidor es " + dealerHand.getBlackjackValue());

        /* Si llegamos a este punto, ambos jugadores tienen 21 o menos. Nosotros
         puede determinar el ganador comparando los valores de sus manos. */
        System.out.println();
        if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
            JOptionPane.showMessageDialog(null, "El distribuidor gana en un empate. Pierdes");
            return false;
        } else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
            JOptionPane.showMessageDialog(null, "distribuidor gana, " + dealerHand.getBlackjackValue()
                    + " puntos a " + userHand.getBlackjackValue() + ".");
            return false;
        } else {
            JOptionPane.showMessageDialog(null, "Tú ganas, " + userHand.getBlackjackValue()
                    + " puntos a " + dealerHand.getBlackjackValue() + ".");
            return true;
        }

    }  // end playBlackjack()
}
