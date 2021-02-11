package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.hw02.MockModel;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

/**
 * Class that represents the controller for pyramid solitaire.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private Readable rd;
  private Appendable ap;
  private boolean quit = false;


  /**
   * Constructor for a new PyramidSolitaireTextualController.
   * @param rd input to the controller
   * @param ap output from the controller
   * @throws IllegalArgumentException if either parameter is null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null || ap ==  null) {
      throw new IllegalArgumentException("Both arguments must be non-null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * Method to play a game of pyramid solitaire through the controller.
   * @param model The game of solitaire to be played
   * @param deck The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numRows How many rows should be in the pyramid
   * @param numDraw How many draw cards should be visible
   * @param <K> the type of card that the model uses
   * @throws IllegalArgumentException if the model is null or not startable.
   * @throws IllegalStateException if the appendable/readable fails.
   */
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                           boolean shuffle, int numRows, int numDraw)
          throws IllegalArgumentException, IllegalStateException {
    try {
      if (model == null) {
        throw new IllegalArgumentException("Model/deck cannot be null.");
      }
      try {
        model.startGame(deck, shuffle, numRows, numDraw);
      } catch (IllegalArgumentException e) {
        throw new IllegalStateException("Game cannot be started with the current state.");
      }

      Scanner scan = new Scanner(this.rd);

      while (!model.isGameOver() && !this.quit) {
        if (!(model instanceof MockModel)) {
          new PyramidSolitaireTextualView(model, this.ap).render();
          this.ap.append(String.format("Score: %d\n", model.getScore()));
        }

        String input;
        if (scan.hasNext()) {
          input = scan.next();
        } else {
          throw new IOException("Coster.");
        }
        switch (input) {
          case "rm1":
            this.rm1(scan, model);
            break;
          case "rm2":
            this.rm2(scan, model);
            break;
          case "rmwd":
            this.rmwd(scan, model);
            break;
          case "dd":
            this.ddCont(scan, model);
            break;
          case "q":
          case "Q":
            this.quit(model);
            break;
          default:
            this.ap.append("You have entered an invalid command.\n");
            break;
        }
      }

      if (model.isGameOver()) {
        new PyramidSolitaireTextualView(model, this.ap).render();
      }

    } catch (IOException e) {
      throw new IllegalStateException(
              "The Appendable or Readable failed to properly transmit inputs/outputs.");
    }



  }

  // returns the number input and will keep waiting until a valid input is given
  private <K> String handleInput(Scanner scan, PyramidSolitaireModel<K> model) throws IOException {
    while (true) {
      if (scan.hasNextInt()) {
        return Integer.toString(scan.nextInt());
      }
      String next = "";
      if (scan.hasNext()) {
        next = scan.next();
      }
      if (next.equals("q") || next.equals("Q")) {
        this.quit(model);
        return "quit";
      } else if (!next.equals("")) {
        this.ap.append(String.format(
                "Due to the invalid input '%s', you need to continue inputting numbers.\n", next));
      } else {
        return "";
      }
    }

  }

  // quits the game and renders the final state
  private <K> void quit(PyramidSolitaireModel<K> model) throws IOException {
    if (model instanceof MockModel) {
      this.ap.append("quit\n");
      this.quit = true;
    } else {
      this.ap.append("Game quit!\n");
      this.ap.append("State of game when quit:\n");
      new PyramidSolitaireTextualView(model, this.ap).render();
      this.ap.append(String.format("Score: %d\n", model.getScore()));
      this.quit = true;
    }

  }

  // removes with draw
  private <K> void rmwd(Scanner scan, PyramidSolitaireModel<K> model) throws IOException {
    String in1 = this.handleInput(scan, model);
    String in2 = this.handleInput(scan, model);
    String in3 = this.handleInput(scan, model);
    if (!in1.equals("quit") && !in2.equals("quit") && !in3.equals("quit")) {
      int draw = Integer.parseInt(in1);
      int row = Integer.parseInt(in2);
      int card = Integer.parseInt(in3);
      try {
        model.removeUsingDraw(draw - 1, row - 1, card - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
      }

    }
  }

  // discard draw
  private <K> void ddCont(Scanner scan, PyramidSolitaireModel<K> model) throws IOException {
    String in1 = this.handleInput(scan, model);
    if (!in1.equals("quit")) {
      int draw = Integer.parseInt(in1);
      try {
        model.discardDraw(draw - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
      }
    }
  }

  // remove two cards
  private <K> void rm2(Scanner scan, PyramidSolitaireModel<K> model) throws IOException {
    String in1 = this.handleInput(scan, model);
    String in2 = this.handleInput(scan, model);
    String in3 = this.handleInput(scan, model);
    String in4 = this.handleInput(scan, model);
    if (!in1.equals("quit") && !in2.equals("quit") &&
            !in3.equals("quit") && !in4.equals("quit")) {
      int row1 = Integer.parseInt(in1);
      int card1 = Integer.parseInt(in2);
      int row2 = Integer.parseInt(in3);
      int card2 = Integer.parseInt(in4);
      try {
        model.remove(row1 - 1, card1 - 1, row2 - 1, card2 - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
      }

    }
  }

  // remove one card
  private <K> void rm1(Scanner scan, PyramidSolitaireModel<K> model) throws IOException {
    String in1 = this.handleInput(scan, model);
    String in2 = this.handleInput(scan, model);
    if (!in1.equals("quit") && !in2.equals("quit")) {
      int row = Integer.parseInt(in1);
      int card = Integer.parseInt(in2);
      //this.ap.append(String.format("rm1: %d, %d\n", row, card));
      try {
        model.remove(row - 1, card - 1);
      } catch (IllegalArgumentException e) {
        this.ap.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
      }
    }
  }

}
