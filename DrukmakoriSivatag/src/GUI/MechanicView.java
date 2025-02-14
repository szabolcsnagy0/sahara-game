package GUI;

import proto.Field;
import proto.Mechanic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/**
 * A Mechanic osztály megjelenítéséért felelős osztály.
 * A MechanicView egy klikkelhető gomb, amivel a játékos kiválaszthatja a Mechanic-ot, ezért a JButton-ből származik.
 */
public class MechanicView extends JButton implements Viewable {
    /**
     * A megjelnített Mechanic képének mérete.
     */
    private static int SIZE = (int) (Window.BUTTONSIZE * 0.6);
    /**
     * A játékbeli megjelenítendő Mechanic.
     */
    private Mechanic mechanic;
    /**
     * A Mechanic képe.
     */
    public static Image image = new ImageIcon(Controller.assetsPath + "mechanic.png").getImage();
    /**
     * Konstruktor.
     * @param mechanic A megjelenítendő Mechanic.
     */
    public MechanicView(Mechanic mechanic) {
        super();

        this.mechanic = mechanic;

        this.addActionListener((ActionEvent e) -> {
            Controller.instance.selectPlayer(this.mechanic);
        });

        Point position = getPosition();
        this.setBounds(position.x, position.y, SIZE, SIZE);

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
        Field field = mechanic.getPosition();

        double index = field.getPlayerIndex(mechanic);
        double dx = Math.cos(index * Math.PI / 2.0) * Window.BUTTONSIZE / 2,
                dy = Math.sin(index * Math.PI / 2.0) * Window.BUTTONSIZE / 2;

        Point position = Controller.instance.fields.get(field).getPosition();
        int cx = position.x + Window.BUTTONSIZE / 2,
                cy = position.y + Window.BUTTONSIZE / 2;

        return new Point(cx + (int) dx - SIZE / 2, cy + (int) dy - SIZE / 2);
    }
    /**
     * A megjelenítendő objektumot rajzoltatja újra, a megváltozott adatok alapján.
     * A Mechanic pozíciója változhat a játék során.
     */
    @Override
    public void update() {
        Point position = getPosition();
        setBounds(position.x, position.y, getWidth(), getHeight());
        validate();
        repaint();
    }
    /**
     * Kirajzolja a Mechanic-ot.
     * @param g a <code>Graphics</code> objektum, amit rajzoláshoz használunk.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(), h = getHeight();

        boolean isSelected = Controller.instance.selectedPlayer == mechanic;
        if (isSelected) {
            g.setColor(Color.GREEN);
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
