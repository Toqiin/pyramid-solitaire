package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;

import static cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.create;

/**
 * Class to play a game of Pyramid Solitaire.
 */
public class PlaySolitaire {

  /**
   * Main method to play a game of Pyramid Solitaire.
   *
   * @param args arguments for the Main method.
   */
  public static void main(String[] args) {

    new PlaySolitaire().makeGame(args);

  }

  private static PyramidSolitaireModel<Card> build(String[] args) {
    if (args.length > 0) {
      switch (args[0]) {
        case "basic":
          return create(PyramidSolitaireCreator.GameType.BASIC);
        case "relaxed":
          return create(PyramidSolitaireCreator.GameType.RELAXED);
        case "tripeaks":
          return create(PyramidSolitaireCreator.GameType.TRIPEAKS);
        default:
          throw new IllegalArgumentException("First argument must be one of:" +
                  "'basic', 'relaxed', 'tripeaks'.");
      }
    } else {
      throw new IllegalArgumentException("First argument must be one of:" +
              "'basic', 'relaxed', 'tripeaks'.");
    }
  }

  private void makeGame(String[] args) {
    try {
      PyramidSolitaireModel<Card> model = build(args);
      if (args.length > 1) {
        if (args.length == 3) {
          try {
            int firstArg = Integer.parseInt(args[1]);
            int secondArg = Integer.parseInt(args[2]);
            new PyramidSolitaireTextualController(new InputStreamReader(System.in),
                    System.out).playGame(model, model.getDeck(), true, firstArg, secondArg);
          } catch (NumberFormatException e) {
            System.err.println("Argument" + args[0] + " must be an integer.");
            System.exit(1);
          }
        } else {
          System.err.println("You must input either only the game type or the" +
                  "type followed by exactly two integers.");
          System.exit(1);
        }

      } else {
        new PyramidSolitaireTextualController(new InputStreamReader(System.in),
                System.out).playGame(model, model.getDeck(), true, 7, 3);
      }
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

}
