package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Class to represent a basic implementation of PyramidSolitaireModel for
 * the type: Card.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire
        implements PyramidSolitaireModel<Card> {

  /**
   * Updates the exposed status of all the cards in the pyramid. For
   * a relaxed pyramid a card must only have one empty spot below it.
   */
  @Override
  protected void updatePyramid() {
    for (int i = 0; i < this.rows - 1; i++) {
      for (int j = 0; j <= i; j++) {
        if (this.getCardAt(i, j) != null) {
          if (this.getCardAt(i + 1, j) == null || this.getCardAt(i + 1, j + 1) == null) {
            Card temp = this.pyramid.get(i).get(j);
            this.pyramid.get(i).set(j, new Card(temp.getValue(), true, temp.getSuit()));
          }
        }

      }
    }
    for (int card = 0; card < this.rows; card++) {
      if (this.getCardAt(this.rows - 1, card) != null) {
        Card temp = this.pyramid.get(this.rows - 1).get(card);
        this.pyramid.get(this.rows - 1).set(card, new Card(temp.getValue(), true, temp.getSuit()));
      }

    }
  }

}


