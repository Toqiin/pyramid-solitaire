package cs3500.pyramidsolitaire.model.hw04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;

/**
 * Class to represent a basic implementation of PyramidSolitaireModel for
 * the type: Card.
 */
public class TriPeakPyramidSolitaire extends BasicPyramidSolitaire
        implements PyramidSolitaireModel<Card> {


  private int overlapped;


  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
    for (int decks = 0; decks < 2; decks++) {
      for (int i = 0; i <= 3; i++) {
        for (int j = 1; j <= 13; j++) {
          switch (i) {
            case 0:
              deck.add(new Card(j, Suit.Hearts));
              break;
            case 1:
              deck.add(new Card(j, Suit.Clubs));
              break;
            case 2:
              deck.add(new Card(j, Suit.Spades));
              break;
            case 3:
              deck.add(new Card(j, Suit.Diamonds));
              break;
            default:
              break;
          }
        }
      }
    }

    return deck;
  }

  /**
   * Method that assigns this.pyramid to be an ArrayList of ArrayLists of Cards
   * and fills the pyramid downward with the cards pulled from the stock (which,
   * at the time this method is called by startGame, is the same as the deck).
   */
  @Override
  protected void makePyramid() {
    this.pyramid = new ArrayList<>();
    for (int i = 0; i < this.rows; i++) {
      this.pyramid.add(new ArrayList<>());
      if (i < (this.rows - this.overlapped)) {
        for (int peak = 0; peak <= 1; peak++) {
          for (int b = 0; b <= i; b++) {
            this.pyramid.get(i).add(this.stock.poll());
          }
          for (int a = 0; a < (this.rows - this.overlapped) - (i + 1); a++) {
            this.pyramid.get(i).add(null);
          }
        }
        for (int b = 0; b <= i; b++) {
          this.pyramid.get(i).add(this.stock.poll());
        }
      } else {
        for (int j = 0; j <= i + ((this.rows / 2) * 2); j++) {
          if (this.stock.size() >= 1) {
            this.pyramid.get(i).add(this.stock.poll());
          } else {
            this.pyramid.get(i).add(null);
          }

        }
      }

    }
    this.updatePyramid();
  }

  private int cardsInPyr(int rows) {
    int result = 3 * sum(rows);
    result = result - 2 * sum((rows + 1) / 2);
    return result;
  }

  @Override
  public void startGame(List deck, boolean shouldShuffle, int numRows, int numDraw) {
    if (this.validDeck(deck) && (numRows > 0) && (numDraw > 0)
            && numRows <= 8 && (numDraw +
            this.cardsInPyr(numRows) <= 104)) {
      if (shouldShuffle) {
        Collections.shuffle(deck);
      }
      this.started = true;
      this.deck = deck;
      this.stock = new LinkedList<Card>(this.deck);
      this.rows = numRows;
      this.draw = numDraw;
      this.overlapped = (this.rows + 1) / 2;
      this.makePyramid();
      this.genDraw(numDraw);

    } else {
      throw new IllegalArgumentException("At least one argument is not valid.");
    }
  }

  /**
   * Returns if the given deck is valid or not.
   *
   * @param deck the deck to verify
   * @return true if valid deck, false if not
   */
  @Override
  protected boolean validDeck(List deck) {
    if (deck == null || deck.size() != 104) {
      return false;
    }
    boolean result = true;
    List<Card> deckCopy = new ArrayList<>();
    for (Object o : deck) {
      if (!(o instanceof Card)) {
        return false;
      } else {
        deckCopy.add(((Card) o).copy());
      }
    }
    /*
    for (int i = 0; i <= 3; i++) {
      for (int j = 1; j <= 13; j++) {
        switch (i) {
          case 0:
            result = result && deckCopy.contains(new Card(j, Suit.Hearts));
            deckCopy.remove(new Card(j, Suit.Hearts));
            break;
          case 1:
            result = result && deckCopy.contains(new Card(j, Suit.Clubs));
            deckCopy.remove(new Card(j, Suit.Clubs));
            break;
          case 2:
            result = result && deckCopy.contains(new Card(j, Suit.Spades));
            deckCopy.remove(new Card(j, Suit.Spades));
            break;
          case 3:
            result = result && deckCopy.contains(new Card(j, Suit.Diamonds));
            deckCopy.remove(new Card(j, Suit.Diamonds));
            break;
          default:
            break;
        }
      }
    }

     */
    List<Card> valid = Arrays.asList(new Card(1, Suit.Hearts),
            new Card(2, Suit.Hearts),
            new Card(3, Suit.Hearts),
            new Card(4, Suit.Hearts),
            new Card(5, Suit.Hearts),
            new Card(6, Suit.Hearts),
            new Card(7, Suit.Hearts),
            new Card(8, Suit.Hearts),
            new Card(9, Suit.Hearts),
            new Card(10, Suit.Hearts),
            new Card(11, Suit.Hearts),
            new Card(12, Suit.Hearts),
            new Card(13, Suit.Hearts),
            new Card(1, Suit.Clubs),
            new Card(2, Suit.Clubs),
            new Card(3, Suit.Clubs),
            new Card(4, Suit.Clubs),
            new Card(5, Suit.Clubs),
            new Card(6, Suit.Clubs),
            new Card(7, Suit.Clubs),
            new Card(8, Suit.Clubs),
            new Card(9, Suit.Clubs),
            new Card(10, Suit.Clubs),
            new Card(11, Suit.Clubs),
            new Card(12, Suit.Clubs),
            new Card(13, Suit.Clubs),
            new Card(1, Suit.Spades),
            new Card(2, Suit.Spades),
            new Card(3, Suit.Spades),
            new Card(4, Suit.Spades),
            new Card(5, Suit.Spades),
            new Card(6, Suit.Spades),
            new Card(7, Suit.Spades),
            new Card(8, Suit.Spades),
            new Card(9, Suit.Spades),
            new Card(10, Suit.Spades),
            new Card(11, Suit.Spades),
            new Card(12, Suit.Spades),
            new Card(13, Suit.Spades),
            new Card(1, Suit.Diamonds),
            new Card(2, Suit.Diamonds),
            new Card(3, Suit.Diamonds),
            new Card(4, Suit.Diamonds),
            new Card(5, Suit.Diamonds),
            new Card(6, Suit.Diamonds),
            new Card(7, Suit.Diamonds),
            new Card(8, Suit.Diamonds),
            new Card(9, Suit.Diamonds),
            new Card(10, Suit.Diamonds),
            new Card(11, Suit.Diamonds),
            new Card(12, Suit.Diamonds),
            new Card(13, Suit.Diamonds),
            new Card(1, Suit.Hearts),
            new Card(2, Suit.Hearts),
            new Card(3, Suit.Hearts),
            new Card(4, Suit.Hearts),
            new Card(5, Suit.Hearts),
            new Card(6, Suit.Hearts),
            new Card(7, Suit.Hearts),
            new Card(8, Suit.Hearts),
            new Card(9, Suit.Hearts),
            new Card(10, Suit.Hearts),
            new Card(11, Suit.Hearts),
            new Card(12, Suit.Hearts),
            new Card(13, Suit.Hearts),
            new Card(1, Suit.Clubs),
            new Card(2, Suit.Clubs),
            new Card(3, Suit.Clubs),
            new Card(4, Suit.Clubs),
            new Card(5, Suit.Clubs),
            new Card(6, Suit.Clubs),
            new Card(7, Suit.Clubs),
            new Card(8, Suit.Clubs),
            new Card(9, Suit.Clubs),
            new Card(10, Suit.Clubs),
            new Card(11, Suit.Clubs),
            new Card(12, Suit.Clubs),
            new Card(13, Suit.Clubs),
            new Card(1, Suit.Spades),
            new Card(2, Suit.Spades),
            new Card(3, Suit.Spades),
            new Card(4, Suit.Spades),
            new Card(5, Suit.Spades),
            new Card(6, Suit.Spades),
            new Card(7, Suit.Spades),
            new Card(8, Suit.Spades),
            new Card(9, Suit.Spades),
            new Card(10, Suit.Spades),
            new Card(11, Suit.Spades),
            new Card(12, Suit.Spades),
            new Card(13, Suit.Spades),
            new Card(1, Suit.Diamonds),
            new Card(2, Suit.Diamonds),
            new Card(3, Suit.Diamonds),
            new Card(4, Suit.Diamonds),
            new Card(5, Suit.Diamonds),
            new Card(6, Suit.Diamonds),
            new Card(7, Suit.Diamonds),
            new Card(8, Suit.Diamonds),
            new Card(9, Suit.Diamonds),
            new Card(10, Suit.Diamonds),
            new Card(11, Suit.Diamonds),
            new Card(12, Suit.Diamonds),
            new Card(13, Suit.Diamonds));
    Collections.sort(valid);
    Collections.sort(deckCopy);
    return valid.equals(deckCopy);
  }


}
