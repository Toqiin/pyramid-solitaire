package cs3500.pyramidsolitaire.view;

import java.io.IOException;

/**
 * Interface that represents a view of PyramidSolitaire.
 */
public interface PyramidSolitaireView {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;

}
