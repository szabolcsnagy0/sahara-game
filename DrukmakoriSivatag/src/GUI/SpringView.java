package GUI;

import proto.Spring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * A forrás (Spring) megjelenítéséért felelős osztály.
 */
public class SpringView extends JButton implements Viewable {
    /**
     * A megjelenítendő forrás.
     */
    private Spring spring;
    /**
     * A forrás pozíciója.
     */
    private Point position;
    /**
     * A forrás képe.
     */
    private static Image image = new ImageIcon(Controller.assetsPath + "spring.png").getImage();
    // private static Image normal_rollover = new ImageIcon(Controller.assetsPath +
    // "spring_rollover.png").getImage();
    /**
     * Konstruktor.
     * @param position A megjelenítendő forrás pozíciója.
     * @param spring A megjelenítendő forrás.
     */
    public SpringView(Point position, Spring spring) {
        super();

        this.spring = spring;
        this.position = position;

        this.addActionListener((ActionEvent e) -> {
            Controller.instance.selectField(spring);
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
                ((SpringView)e.getComponent()).setPosition(new Point(getPosition().x + dx, getPosition().y + dy));
                Controller.instance.window.updateAllViews();
            }

            /**
             * Az egér mozgatása során ha elhúzunk egy mezőt a helyéről a mozgás kezdőpontja az lesz,
             * ahova az egeret a húzás előtt éppen elmozgattuk.
             * Ez a metódus ezt a pontot tárolja el.
             * @param e a mozgás eseménye.
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
     * Visszaadja a forrás pozícióját.
     * @return A forrás pozíciója.
     */
    @Override
    public Point getPosition() {
        return position;
    }

    /**
     * Beállítja a forrás pozícióját.
     * @param position Az új pozíció
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * A forrás megjelenítésének frissítése, újrarajzolása.
     */
    @Override
    public void update() {
        this.setBounds(getPosition().x, getPosition().y, Window.BUTTONSIZE, Window.BUTTONSIZE);
        validate();
        repaint();
    }
    /**
     * A forrást kirajzoló metódus.
     * @param g az <code>Graphics</code> objektum, amit a kirajzoláshoz használunk.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();
        boolean isSelected = Controller.instance.selectedFields.contains(spring);
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