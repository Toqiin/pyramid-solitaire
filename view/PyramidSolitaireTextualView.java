package cs3500.pyramidsolitaire.view;

import java.io.IOException;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Class that is a visual representation of a model of the Pyramid
 * Solitaire game.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable app;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable app) {
    this.model = model;
    this.app = app;
  }

  @Override
  public String toString() {
    try {
      int ignored = this.model.getScore();
    } catch (IllegalStateException e) {
      return "";
    }
    if (this.model.isGameOver()) {
      if (this.model.getScore() == 0) {
        return "You win!";
      } else {
        return "Game over. Score: " + this.model.getScore();
      }

    }
    String result = "";
    for (int i = 0; i < model.getNumRows(); i++) {
      result = result + this.makeRow(i);
      result = result + "\n";
    }
    result = result + this.makeDraw();
    return result;
  }

  /**
   * returns the toString of the given object.
   *
   * @param card the card object to convert to string
   * @return
   */
  private String cardString(Object card) {
    String result = card.toString();
    if (result.length() == 2) {
      result = result + " ";
    }
    return result;
  }

  /**
   * Converts the drawPile to text.
   *
   * @return a String of the draw pile
   */
  private String makeDraw() {
    String result = "Draw: ";
    boolean empty = true;
    for (Object o : this.model.getDrawCards()) {
      empty = empty && (o == null);
    }
    if (empty) {
      return "Draw:";
    }
    for (int i = 0; i < this.model.getNumDraw(); i++) {
      // If there is no card in the spot, it is represented as 2
      // spaces with a comma space before the next card.
      if (this.model.getDrawCards().get(i) == null) {
        result = result + "  ";
        if (i < this.model.getNumDraw() - 1) {
          result = result + ", ";
        }
      } else {
        result = result + this.model.getDrawCards().get(i).toString();
        if (i < this.model.getNumDraw() - 1) {
          result = result + ", ";
        }
      }


    }
    return result;
  }

  /**
   * Converts the given row to a String.
   *
   * @param rowIdx the row to do it on
   * @return String of the row
   */
  private String makeRow(int rowIdx) {
    String result = "";
    String spaces = "";
    for (int i = 0; i < (2 * (model.getNumRows() - rowIdx - 1)); i++) {
      spaces = spaces + " ";
    }
    for (int i = 0; i < model.getRowWidth(rowIdx); i++) {
      if (this.model.getCardAt(rowIdx, i) == null) {
        result = result + "   ";
      } else {
        result = result + cardString(this.model.getCardAt(rowIdx, i));
      }

      if (i < model.getRowWidth(rowIdx) - 1) {
        result = result + " ";
      }
    }
    if (result.length() > 0 && result.substring(result.length() - 1).equals(" ")) {
      result = result.substring(0,  result.length() - 1);
    }

    if (this.model.getCardAt(rowIdx, rowIdx) == null) {
      result = result.stripTrailing();
    }

    return spaces + result;
  }

  @Override
  public void render() throws IOException {
    this.app.append(this.toString()).append("\n");
  }
}
