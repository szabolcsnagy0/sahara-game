package GUI.actions;

import javax.swing.JButton;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Gomb ősosztály, amiből az akciókat reprezentáló gombok származnak le.
 */
public abstract class ActionButton extends JButton {
    /**
     * Konstruktor.
     */
    public abstract boolean canPerform();

    /**
     * A gomb kirajzolása.
     * @param g a <code>Graphics</code> objektum, amit a komponens kirajzolásához használunk.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.getModel().isRollover()) {
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 25, 25);
        }
        g.setColor(Color.BLACK);
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 25, 25);
    }
}
