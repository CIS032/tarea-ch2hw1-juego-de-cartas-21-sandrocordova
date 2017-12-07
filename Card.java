package Deber_Blackjack;

/**
 * Un objeto de tipo Carta representa un naipe de una baraja de póquer estándar,
 * incluidos los comodines. La tarjeta tiene un traje, que puede ser espadas,
 * corazones, diamantes, palos o bromista. Una pala, corazón, diamante o palo
 * tiene uno de los 13 valores: as, 2, 3, 4, 5, 6, 7, 8, 9, 10, jota, reina o
 * rey. Tenga en cuenta que "as" se considera que es el valor más pequeño. Un
 * comodín también puede tener un valor asociado; este valor puede ser cualquier
 * cosa y se puede utilizar para realizar un seguimiento de varios comodines
 * diferentes.
 */
public class Card {

    public final static int SPADES = 0;   // Códigos para los 4 palos, más Joker. 
    public final static int HEARTS = 1;
    public final static int DIAMONDS = 2;
    public final static int CLUBS = 3;
    public final static int JOKER = 4;

    public final static int ACE = 1;      // Códigos para las tarjetas no numéricas. 
    public final static int JACK = 11;    //   Las tarjetas 2 a 10 tienen su 
    public final static int QUEEN = 12;   //   valores numéricos para sus códigos.
    public final static int KING = 13;

    /**
     * El juego de esta carta, una de las constantes SPADES, HEARTS, DIAMONDS,
     * CLUBS, o JOKER. La demanda no puede ser cambiada después de que la
     * tarjeta es construida.
     */
    private final int suit;

    /*
     * El valor de la tarjeta. Para una carta normal, este es uno de los valores 
     * 1 a 13, donde 1 representa ACE. Para un JOKER, el valor 
     * puede ser cualquier cosa. El valor no se puede cambiar después de que 
     se construye la tarjeta *. 
     */
    private final int value;

    /* 
     * Crea un Joker, con 1 como el valor asociado. (Tenga en cuenta que 
     * "nueva Tarjeta ()" es equivalente a "nueva Tarjeta (1, Tarjeta.CAJA)".) 
     */
    public Card() {
        suit = JOKER;
        value = 1;
    }

    /**
     * Crea una carta con un palo y valor especificados.
     *
     * @param theValue el valor de la nueva tarjeta. Para una tarjeta regular
     * (no bromista), el valor debe estar en el rango de 1 a 13, donde 1
     * representa un As. Puede usar las constantes Card.ACE, Card.JACK,
     * Card.QUEEN y Card.KING. Para un Joker, el valor puede ser cualquier cosa.
     * @param theSuit el traje de la nueva tarjeta. Este debe ser uno de los
     * valores Card.SPADES, Card.HEARTS, Card.DIAMONDS, Card.CLUBS o Card.JOKER.
     * @throws IllegalArgumentException si los valores de los parámetros no
     * están en los rangos * permisibles
     */
    public Card(int theValue, int theSuit) {
        if (theSuit != SPADES && theSuit != HEARTS && theSuit != DIAMONDS
                && theSuit != CLUBS && theSuit != JOKER) {
            throw new IllegalArgumentException("Traje de naipes ilegal");
        }
        if (theSuit != JOKER && (theValue < 1 || theValue > 13)) {
            throw new IllegalArgumentException("valor de naipes ilegal");
        }
        value = theValue;
        suit = theSuit;
    }

    /**
     * Devuelve la demanda de esta tarjeta.
     *
     * @ devuelve la demanda, que es una de las constantes Card.SPADES,
     * Card.HEARTS, Card.DIAMONDS, Card.CLUBS, o Card.JOKER
     */
    public int getSuit() {
        return suit;
    }

    /* 
     * Devuelve el valor de esta tarjeta.
     * @return el valor, que es uno de los números del 1 al 13, inclusive para 
     * una tarjeta regular, y que puede ser cualquier valor para un Joker. 
     */
    public int getValue() {
        return value;
    }

    /**
     * Devuelve una representación de Cadena del palo de la carta.
     *
     * @return una de las cadenas "Picas", "Corazones", "Diamantes", "Clubs" o
     * "Joker".
     */
    public String getSuitAsString() {
        switch (suit) {
            case SPADES:
                return "Spades";
            case HEARTS:
                return "Hearts";
            case DIAMONDS:
                return "Diamonds";
            case CLUBS:
                return "Clubs";
            default:
                return "Joker";
        }
    }

    /* 
     * Devuelve una representación de cadena del valor de la tarjeta. 
     * @return para una carta regular, una de las cadenas "Ace", "2", 
     * "3", ..., "10", "Jack", "Queen" o "King". Para un Joker, la 
     * cadena siempre es numérica. 
     */
    public String getValueAsString() {
        if (suit == JOKER) {
            return "" + value;
        } else {
            switch (value) {
                case 1:
                    return "Ace";
                case 2:
                    return "2";
                case 3:
                    return "3";
                case 4:
                    return "4";
                case 5:
                    return "5";
                case 6:
                    return "6";
                case 7:
                    return "7";
                case 8:
                    return "8";
                case 9:
                    return "9";
                case 10:
                    return "10";
                case 11:
                    return "Jack";
                case 12:
                    return "Queen";
                default:
                    return "King";
            }
        }
    }

    /**
     * Devuelve una representación de cadena de esta tarjeta, incluyendo tanto
     * su palo como su valor (excepto que para un Joker con valor 1, el valor de
     * retorno es solo "Joker"). Los valores de muestra de muestra son: "Joker",
     * "Joker # 2"
     */
    public String toString() {
        if (suit == JOKER) {
            if (value == 1) {
                return "Joker";
            } else {
                return "Joker #" + value;
            }
        } else {
            return getValueAsString() + " of " + getSuitAsString();
        }
    }

} // end class Card
