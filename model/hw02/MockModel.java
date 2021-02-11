package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Class to represent a mock implementation of PyramidSolitaireModel for
 * the type: Card.
 */
public class MockModel extends BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {


  final StringBuilder log;

  /**
   * Constructs a BasicPyramidSolitaire.
   */
  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);

  }


  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    log.append(String.format("row1 = %d, card1 = %d, row2 = %d, card2 = %d\n",
            row1, card1, row2, card2));
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    log.append(String.format("row1 = %d, card1 = %d\n",
            row, card));
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    log.append(String.format("draw = %d, row1 = %d, card1 = %d\n",
            drawIndex, row, card));
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    log.append(String.format("draw = %d\n",
            drawIndex));
  }

}
