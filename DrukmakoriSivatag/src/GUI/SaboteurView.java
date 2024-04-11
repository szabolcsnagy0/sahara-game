package GUI;

import proto.Field;
import proto.Saboteur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A szabotőr (Saboteur) megjelenítéséért felelős osztály.
 */
public class SaboteurView extends JButton implements Viewable {
    /**
     * A megjelenített szabotőr ikon mérete.
     */
    private static int SIZE = (int) (Window.BUTTONSIZE * 0.6);
    /**
     * A játékbeli megjelenítendő szabotőr.
     */
    private Saboteur saboteur;
    /**
     * A szabotőr ikonja.
     */
    public static Image image = new ImageIcon(Controller.assetsPath + "saboteur.png").getImage();
    /**
     * Konstruktor.
     * @param saboteur A megjelenítendő szabotőr.
     */
    public SaboteurView(Saboteur saboteur) {
        super();

        this.saboteur = saboteur;

        this.addActionListener((ActionEvent e) -> {
            Controller.instance.selectPlayer(saboteur);
        });

        Point position = getPosition();
        this.setBounds(position.x, position.y, SIZE, SIZE);

        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setRolloverEnabled(true);
    }
    /**
     * Visszaadja a szabotőr pozícióját.
     * @return A szabotőr pozíciója.
     */
    @Override
    public Point getPosition() {
        Field field = saboteur.getPosition();

        double index = field.getPlayerIndex(saboteur);
        double dx = Math.cos(index * Math.PI / 2.0) * Window.BUTTONSIZE / 2,
                dy = Math.sin(index * Math.PI / 2.0) * Window.BUTTONSIZE / 2;

        Point position = Controller.instance.fields.get(field).getPosition();
        int cx = position.x + Window.BUTTONSIZE / 2,
                cy = position.y + Window.BUTTONSIZE / 2;

        return new Point(cx + (int) dx - SIZE / 2, cy + (int) dy - SIZE / 2);
    }
    /**
     * A szabotőr megjelenítésének frissítése a modellbeli változások alapján.
     * A szabotőr pozíciója változhat a játék során.
     */
    @Override
    public void update() {
        Point position = getPosition();
        setBounds(position.x, position.y, getWidth(), getHeight());
        validate();
        repaint();
    }
    /**
     * A szabotőrt kirajzoló metódus.
     * @param g az <code>Graphics</code> objektum, amit a kirajzoláshoz használunk.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();

        boolean isSelected = Controller.instance.selectedPlayer == saboteur;
        if (isSelected) {
            g.setColor(new Color(0, 255, 0, 255));
            g.fillOval(0, 0, w, h);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 2, 2, w - 4, h - 4, null);

        if (this.getModel().isRollover()) {
            g.setColor(new Color(0, 0, 0, 50));
            g.fillOval(2, 2, w - 4, h - 4);
        }
    }
}
