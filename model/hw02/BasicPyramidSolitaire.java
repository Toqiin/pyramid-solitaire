package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent a basic implementation of PyramidSolitaireModel for
 * the type: Card.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected boolean started = false;
  protected List deck = null;
  protected List<Card> drawPile = null;
  protected int rows;
  protected int draw;
  protected ArrayList<ArrayList<Card>> pyramid = null;
  protected LinkedList<Card> stock = null;

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<>();
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
    return deck;
  }

  /**
   * Method that assigns this.pyramid to be an ArrayList of ArrayLists of Cards
   * and fills the pyramid downward with the cards pulled from the stock (which,
   * at the time this method is called by startGame, is the same as the deck).
   */
  protected void makePyramid() {
    this.pyramid = new ArrayList<>();
    for (int i = 0; i < this.rows; i++) {
      this.pyramid.add(new ArrayList<>());
      for (int j = 0; j <= i; j++) {
        if (this.stock.size() >= 1) {
          this.pyramid.get(i).add(this.stock.poll());
        } else {
          this.pyramid.get(i).add(null);
        }

      }
    }
    this.updatePyramid();
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    int sum = 0;
    for (ArrayList<Card> row : this.pyramid) {
      for (Card card : row) {
        if (card != null) {
          sum = sum + card.getValue();
        }
      }
    }
    return sum;
  }

  @Override
  public Card getCardAt(int row, int card) throws
          IllegalStateException, IllegalArgumentException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    if (row >= this.rows || card >= this.getRowWidth(row) || card < 0) {
      throw new IllegalArgumentException("Invalid row/card provided.");
    }
    if (this.pyramid.get(row).get(card) == null) {
      return null;
    } else {
      return this.pyramid.get(row).get(card);
    }
  }

  @Override
  public int getNumDraw() {
    if (this.started) {
      return this.draw;
    } else {
      return -1;
    }
  }

  @Override
  public int getNumRows() {
    if (this.started) {
      return this.rows;
    } else {
      return -1;
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    if (drawIndex < 0 || drawIndex >= this.drawPile.size()) {
      throw new IllegalArgumentException("Invalid index.");
    }
    if (this.drawPile.get(drawIndex) == null) {
      throw new IllegalArgumentException("No card there.");
    }
    if (this.stock.size() >= 1) {
      this.drawPile.set(drawIndex, this.stock.poll());
    } else {
      this.drawPile.set(drawIndex, null);
    }
  }

  /**
   * Returns if the given deck is valid or not.
   *
   * @param deck the deck to verify
   * @return true if valid deck, false if not
   */
  protected boolean validDeck(List deck) {
    if (deck == null || deck.size() != 52) {
      return false;
    }
    boolean result = true;
    for (int i = 0; i <= 3; i++) {
      for (int j = 1; j <= 13; j++) {
        switch (i) {
          case 0:
            result = result && deck.contains(new Card(j, Suit.Hearts));
            break;
          case 1:
            result = result && deck.contains(new Card(j, Suit.Clubs));
            break;
          case 2:
            result = result && deck.contains(new Card(j, Suit.Spades));
            break;
          case 3:
            result = result && deck.contains(new Card(j, Suit.Diamonds));
            break;
          default:
            break;
        }
      }
    }
    return result;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }

    List<Card> result = new ArrayList<Card>();
    for (Card c : this.drawPile) {
      if (c == null) {
        result.add(null);
      } else {
        result.add(c.copy());
      }

    }
    return result;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }

    // check if game is won
    boolean won = true;
    for (ArrayList<Card> row : this.pyramid) {
      for (Card card : row) {
        won = won && card == null;
      }
    }
    if (won) {
      return true;
    }

    // check if game is still playable
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.getRowWidth(i); j++) {
        if (this.pyramid.get(i).get(j) != null && this.pyramid.get(i).get(j).isExposed()) {
          Card current = this.pyramid.get(i).get(j);
          if (current.getValue() == 13) {
            return false;
          }
          for (Card card : this.drawPile) {
            if (card != null && current.getValue() + card.getValue() == 13) {
              return false;
            }
          }
          for (int l = 0; l < this.rows; l++) {
            for (int m = 0; m < this.getRowWidth(l); m++) {
              if (!(i == l && j == m) && this.pyramid.get(l).get(m) != null
                      && this.pyramid.get(l).get(m).isExposed()
                      && this.pyramid.get(l).get(m).getValue() + current.getValue() == 13) {
                return false;
              }
            }
          }
        }
      }
    }

    // if game is not won and no moves can be made, return if the stock
    // is empty (lose)
    boolean drawCards = false;
    for (Card c : this.getDrawCards()) {
      if (c != null) {
        drawCards = true;
        break;
      }
    }
    return (this.stock.size() < 1 && !drawCards);
  }

  @Override
  public int getRowWidth(int row) {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    if (row < 0 || row >= this.rows) {
      throw new IllegalArgumentException("Invalid row given.");
    }
    return this.pyramid.get(row).size();
  }

  /**
   * Assigns this.drawPile to be a LinkedList of Cards that are pulled from the
   * stock after the pyramid is made.
   * @param numDraw number of cards to draw into the drawPile
   */
  protected void genDraw(int numDraw) {
    LinkedList<Card> temp = new LinkedList<>();
    for (int i = 0; i < numDraw; i++) {
      if (this.stock.size() >= 1) {
        temp.add(this.stock.poll());
      } else {
        temp.add(null);
      }
    }
    this.drawPile = temp;
  }

  /**
   * Updates the exposed status of all the cards in the pyramid.
   */
  protected void updatePyramid() {
    for (int i = 0; i < this.rows - 1; i++) {
      for (int j = 0; j < this.getRowWidth(i); j++) {
        if (this.getCardAt(i, j) != null) {
          if (this.getCardAt(i + 1, j) == null && this.getCardAt(i + 1, j + 1) == null) {
            Card temp = this.pyramid.get(i).get(j);
            this.pyramid.get(i).set(j, new Card(temp.getValue(), true, temp.getSuit()));
          }
        }

      }
    }
    for (int card = 0; card < this.getRowWidth(this.rows - 1); card++) {
      if (this.getCardAt(this.rows - 1, card) != null) {
        Card temp = this.pyramid.get(this.rows - 1).get(card);
        this.pyramid.get(this.rows - 1).set(card, new Card(temp.getValue(), true, temp.getSuit()));
      }

    }
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException, IllegalArgumentException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    try {
      Card selected = this.getCardAt(row, card);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("The card index is wrong");
    }
    Card selected = this.getCardAt(row, card);
    if (selected != null && selected.isExposed() && selected.getValue() == 13) {
      this.discard(row, card);
      this.updatePyramid();
    } else {
      throw new IllegalArgumentException("Move is not valid.");
    }


  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws
          IllegalStateException, IllegalArgumentException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    try {
      Card selected1 = this.getCardAt(row1, card1);
      Card selected2 = this.getCardAt(row2, card2);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("One of the card indexes is wrong");
    }
    Card selected1 = this.getCardAt(row1, card1);
    Card selected2 = this.getCardAt(row2, card2);
    if (selected1 != null && selected2 != null && selected1.isExposed() &&
            selected2.isExposed() && (selected1.getValue()
            + selected2.getValue() == 13)) {
      this.discard(row1, card1);
      this.discard(row2, card2);
      this.updatePyramid();
    } else {
      throw new IllegalArgumentException("Move is not valid.");
    }


  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws
          IllegalStateException, IllegalArgumentException {
    if (!this.started) {
      throw new IllegalStateException("Game is not started yet.");
    }
    if (drawIndex >= this.drawPile.size() || drawIndex < 0 || row < 0
            || row >= this.rows || card > this.getRowWidth(row)) {
      throw new IllegalArgumentException("Invalid Arguments, index is out of bounds");
    }
    Card selected = this.getCardAt(row, card);
    if (selected != null && this.drawPile.get(drawIndex) != null &&
            selected.isExposed() && selected.getValue()
            + this.drawPile.get(drawIndex).getValue() == 13) {
      this.discardDraw(drawIndex);
      this.discard(row, card);
      this.updatePyramid();
    } else {
      throw new IllegalArgumentException("Move is not valid.");
    }
  }



  /**
   * Sets the card to null at the given value.
   *
   * @param row the row of the card to discard
   * @param card the cards position in the row
   */
  private void discard(int row, int card) {
    this.pyramid.get(row).set(card, null);
  }

  /**
   * Calculates the sum of 0 + 1 + ... num, which is the total amount
   * of cards in a pyramid of num rows.
   *
   * @param num the number to sum up to
   * @return sum of 0 + 1 + ... + num
   */
  protected int sum(int num) {
    return (num * (num + 1)) / 2;
  }

  @Override
  public void startGame(List deck, boolean shouldShuffle, int numRows, int numDraw) {
    if (this.validDeck(deck) && (numRows > 0) && (numDraw > 0) && (numDraw +
            this.sum(numRows) <= 52)) {
      if (shouldShuffle) {
        Collections.shuffle(deck);
      }
      this.started = true;
      this.deck = deck;
      this.stock = new LinkedList<Card>(this.deck);
      this.rows = numRows;
      this.draw = numDraw;
      this.makePyramid();
      this.genDraw(numDraw);

    } else {
      throw new IllegalArgumentException("At least one argument is not valid.");
    }
  }
}
