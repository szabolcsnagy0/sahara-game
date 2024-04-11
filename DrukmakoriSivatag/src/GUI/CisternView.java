package GUI;

import proto.Cistern;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

/**
 * A Ciszterna megjelenítéséért felelős osztály.
 */

public class CisternView extends JButton implements Viewable {
    /**
     * A ciszterna objektum, amit meg kell jeleníteni.
     */
    private Cistern cistern;
    /**
     * A ciszterna pozíciója.
     */
    private Point position;
    /**
     * A normál állapotú ciszterna képe.
     */
    private static Image normal = new ImageIcon(Controller.assetsPath + "cistern.png").getImage();
    /**
     * A csővel rendelkező ciszterna képe.
     */
    private static Image pipeAvailable = new ImageIcon(Controller.assetsPath + "cistern_pipe.png").getImage();
    /**
     * A ciszterna épp megjelnítendő képe.
     * Lehet normal vagy pipeAvailable.
     */
    private Image image = normal;

    /**
     * Konstruktor.
     * @param position A ciszterna pozíciója.
     * @param cistern A megjelenítendő ciszterna.
     */
    public CisternView(Point position, Cistern cistern) {
        super();

        this.cistern = cistern;
        this.position = position;

        this.addActionListener((ActionEvent e) -> {
            Controller.instance.selectField(cistern);
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            private Point start = null;
            /**
             * A pumpa mozgatását megvalósító metódus.
             * @param e a mozgatás eseménye.
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - start.x, dy = e.getY() - start.y;
                ((CisternView)e.getComponent()).setPosition(new Point(getPosition().x + dx, getPosition().y + dy));
                Controller.instance.window.updateAllViews();
            }
            /**
             * A pumpa mozgatásának kezdőpontját eltároló metódus.
             * @param e a mozgatás eseménye.
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                start = e.getPoint();
            }
        });
        this.setBounds(getPosition().x, getPosition().y, Window.BUTTONSIZE, Window.BUTTONSIZE);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setRolloverEnabled(true);
    }
    /**
     * Visszaadja a megjelenítendő objektum pozícióját.
     * @return A megjelenítendő objektum pozíciója.
     */
    @Override
    public Point getPosition() {
        return position;
    }

    /**
     * Beállítja a ciszterna pozícióját.
     * @param position Az új pozíció
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * A megjelenítendő objektumot rajzoltatja újra, a megváltozott adatok alapján.
     * A ciszterna képe változik, ha a rajta lévő cső elérhető.
     */
    @Override
    public void update() {
        this.setBounds(getPosition().x, getPosition().y, Window.BUTTONSIZE, Window.BUTTONSIZE);
        image = cistern.isPipeAvailable() ? pipeAvailable : normal;
        validate();
        repaint();
    }

    /**
     * Kirajzolja a ciszerna mezőt.
     * @param g a <code>Graphics</code> objektum, amit rajzoláshoz használunk.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();
        boolean isSelected = Controller.instance.selectedFields.contains(cistern);
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.fillRoundRect(0, 0, w, h, 25, 25);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 2, 2, w - 4, h - 4, null);

        if (this.getModel().isRollover()) {
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRoundRect(2, 2, w - 4, h - 4, 25, 25);
        }
    }
}