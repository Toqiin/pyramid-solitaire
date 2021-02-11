package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Class to create a pyramid solitaire model.
 * Has a static factory method 'create' that takes an enum GameType
 * to create the correct kind of game.
 */
public class PyramidSolitaireCreator {

  /**
   * Enum GameType: One of BASIC, RELAXED, TRIPEAKS. Each
   * represents one of the three possible types of Pyramid Solitaire
   * games.
   */
  public enum GameType {
    BASIC, RELAXED, TRIPEAKS
  }

  /**
   * Returns a PyramidSolitaireModel of the proper type.
   * @param type GameType to be created
   * @return the correct type of PyramidSolitaireModel
   */
  public static PyramidSolitaireModel<Card> create(GameType type) {
    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case TRIPEAKS:
        return new TriPeakPyramidSolitaire();
      default:
        return new BasicPyramidSolitaire();
    }
  }

}
