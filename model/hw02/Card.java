package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a Card in the Pyramid Solitaire game.
 */
public class Card implements Comparable<Card> {

  // Getters for the private fields

  public int getValue() {
    return value;
  }

  public boolean isExposed() {
    return exposed;
  }

  public Suit getSuit() {
    return suit;
  }

  private final int value;
  private final boolean exposed;
  private final Suit suit;



  /**
   * Constructs a new card given only the value and suit.
   *
   * @param value The value of the card
   * @param suit The suit of the card
   */
  public Card(int value, Suit suit) {
    if (value > 0 && value < 14) {
      this.value = value;
      this.suit = suit;
      this.exposed = false;
    } else {
      throw new IllegalArgumentException("Value must be between 1 and 13.");
    }
  }

  /**
   * Constructs a new card given value, suit, and whether it is exposed.
   *
   * @param value the value of the card
   * @param exposed whether the card is exposed or not
   * @param suit the suit of the card
   */
  public Card(int value, boolean exposed, Suit suit) {
    if (value > 0 && value < 14) {
      this.value = value;
      this.suit = suit;
      this.exposed = exposed;
    } else {
      throw new IllegalArgumentException("Value must be between 1 and 13.");
    }

  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return value == card.value &&
            exposed == card.exposed &&
            suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, exposed, suit);
  }

  @Override
  public String toString() {
    switch (this.suit) {
      case Hearts:
        return String.format("%s", this.valueToString()) + "♥";
      case Clubs:
        return String.format("%s", this.valueToString()) + "♣";
      case Spades:
        return String.format("%s", this.valueToString()) + "♠";
      case Diamonds:
        return String.format("%s", this.valueToString()) + "♦";
      default:
        return "";
    }
  }

  /**
   * Returns the String representation of the numerical value of the card.
   *
   * @return the String that represents the value of the card (A, J, Q, K, 1 - 10)
   */
  private String valueToString() {
    if (this.value <= 10 && this.value != 1) {
      return Integer.toString(this.value);
    } else if (this.value > 10) {
      switch (this.value) {
        case 11:
          return "J";
        case 12:
          return "Q";
        case 13:
          return "K";
        default:
          return "you missed up, idiot";
      }
    } else {
      return "A";
    }
  }

  public Card copy() {
    return new Card(this.value, this.exposed, this.suit);
  }

  @Override
  public int compareTo(Card o) {
    if (this.suitToInt() == o.suitToInt()) {
      return this.getValue() - o.getValue();
    } else {
      return this.suitToInt() - o.suitToInt();
    }
  }

  private int suitToInt() {
    switch (this.getSuit()) {
      case Hearts:
        return 1;
      case Clubs:
        return 2;
      case Spades:
        return 3;
      case Diamonds:
        return 4;
      default:
        return -1;
    }
  }
}
