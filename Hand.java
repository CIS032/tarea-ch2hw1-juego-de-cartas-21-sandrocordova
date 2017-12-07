package Deber_Blackjack;

/**
 * Un objeto de tipo Mano representa una mano de cartas. los las tarjetas
 * pertenecen a la clase Tarjeta. Una mano está vacía cuando se crea y se puede
 * agregar cualquier cantidad de tarjetas.
 */
import java.util.ArrayList;

import java.util.ArrayList;

public class Hand {

    public ArrayList<Card> hand;   // The cards in the hand.

    /**
     * Crea una mano que está inicialmente vacía.
     */
    public Hand() {
        hand = new ArrayList<Card>();
    }

    /**
     * Retire todas las cartas de la mano, dejándolas vacías.
     */
    public void clear() {
        hand.clear();
    }

    /**
     * Agrega una carta a la mano. Se agrega al final de la mano actual.
     *
     * @param c la tarjeta no nula que se agregará.
     * @throws NullPointerException si el parámetro c es nulo.
     */
    public void addCard(Card c) {
        if (c == null) {
            throw new NullPointerException("Can't add a null card to a hand.");
        }
        hand.add(c);
    }

    /**
     * Retire una tarjeta de la mano, si está presente.
     *
     * @param c la tarjeta que se eliminará. Si c es nulo o si la tarjeta no
     * está en la mano, entonces no se hace nada.
     */
    public void removeCard(Card c) {
        hand.remove(c);
    }

    /**
     * Retire la tarjeta en una posición específica de la mano.
     *
     * @param posicionar la posición de la tarjeta que se va a eliminar, donde
     * las posiciones comienzan desde cero.
     * @throws IllegalArgumentException si el puesto no existe en la mano, eso
     * es si la posición es menor que 0 o mayor que o igual a la cantidad de
     * cartas en la mano.
     */
    public void removeCard(int position) {
        if (position < 0 || position >= hand.size()) {
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        }
        hand.remove(position);
    }

    /**
     * Devuelve la cantidad de cartas en la mano.
     */
    public int getCardCount() {
        return hand.size();
    }

    /**
     * Obtiene la carta en una posición específica en la mano. (Tenga en cuenta
     * que esta tarjeta no se elimina de la mano!)
     *
     * @param posicionar la posición de la tarjeta que se va a devolver
     * @throws IllegalArgumentException si la posición no existe en la mano
     */
    public Card getCard(int position) {
        if (position < 0 || position >= hand.size()) {
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        }
        return hand.get(position);
    }

    /**
     * Clasifica las cartas en la mano para que las cartas del mismo palo sean
     * agrupados, y dentro de un palo, las tarjetas se ordenan por valor. Tenga
     * en cuenta que se considera que los ases tienen el valor más bajo, 1.
     */
    public void sortBySuit() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if (c1.getSuit() < c.getSuit()
                        || (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue())) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }

    /**
     * Clasifica las cartas en la mano para que las cartas del mismo valor sean
     * agrupados. Las cartas con el mismo valor se clasifican por palo. Tenga en
     * cuenta que se considera que los ases tienen el valor más bajo, 1.
     */
    public void sortByValue() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if (c1.getValue() < c.getValue()
                        || (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit())) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }

}
