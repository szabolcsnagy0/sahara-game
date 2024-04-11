package GUI;

import proto.Pump;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * A pumpa (Pump) megjelenítéséért felelős osztály.
 */
public class PumpView extends JButton implements Viewable {
    /**
     * A pumpa pozíciója.
     */
    private Point position;
    /**
     * A megjelenítendő pumpa.
     */
    private Pump pump;
    /**
     * A normál állapotú pumpa képe.
     */
    private static Image normal = new ImageIcon(Controller.assetsPath + "pump.png").getImage();
    /**
     * A törött pumpa képe.
     */
    private static Image broken = new ImageIcon(Controller.assetsPath + "pump_broken.png").getImage();
    /**
     * A pumpa épp megjelenítendő képe.
     * Lehet normal vagy broken, alapértelmezetten normal.
     */
    private Image actual = normal;
    /**
     * Konstruktor.
     * @param position A megjelenítendő pumpa pozíciója.
     * @param pump A megjelenítendő pumpa.
     */
    public PumpView(Point position, Pump pump) {
        super();

        this.pump = pump;
        this.position = position;

        this.addActionListener((ActionEvent e) -> {
            Controller.instance.selectField(this.pump);
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
                ((PumpView)e.getComponent()).setPosition(new Point(getPosition().x + dx, getPosition().y + dy));
                Controller.instance.window.updateAllViews();
            }
            /**
             * Az egér mozgatása során ha elhúzunk egy mezőt a helyéről a mozgás kezdőpontja az lesz,
             * ahova az egeret a húzás előtt éppen elmozgattuk.
             * Ez a metódus ezt a pontot tárolja el.
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
     * A pumpa megjelenítésének frissítése.
     * A pumpa törött, vagy normál állapotától függően a megfelelő képet állítja be.
     */
    @Override
    public void update() {
        this.setBounds(getPosition().x, getPosition().y, Window.BUTTONSIZE, Window.BUTTONSIZE);
        actual = pump.isBroken() ? broken : normal;
        validate();
        repaint();
    }
    /**
     * Visszaadja a pumpa pozícióját.
     * @return A pumpa pozíciója.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Beállítja a pumpa pozícióját.
     * @param position Az új pozíció
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * A pumpát kirajzoló metódus.
     * @param g az <code>Graphics</code> objektum, amit a kirajzoláshoz használunk.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();
        boolean isSelected = Controller.instance.selectedFields.contains(pump);
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.fillRoundRect(0, 0, w, h, 25, 25);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(actual, 2, 2, w - 4, h - 4, null);

        if (this.getModel().isRollover()) {
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRoundRect(2, 2, w - 4, h - 4, 25, 25);
        }
    }
}
