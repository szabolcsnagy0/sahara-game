package GUI.menu;

import javax.swing.*;

import GUI.Controller;
import GUI.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A játék menüjét reprezentáló panel.
 */
public class MenuPanel extends JPanel {
    /**
     * A menüpanel háttérképe.
     */
    private static Image background = new ImageIcon(Controller.assetsPath + "panel.png").getImage();
    /**
     * Az akciókat reprezentáló akciópanel.
     */
    private ActionPanel actionPanel = new ActionPanel();
    /**
     * Az eszköztárt reprezentáló eszköztárpanel.
     */
    private InventoryPanel inventoryPanel = new InventoryPanel();
    /**
     * A pontokat reprezentáló panel.
     */
    private ScorePanel scorePanel = new ScorePanel();

    /**
     * Konstruktor.
     */
    public MenuPanel() {
        super();

        this.setMinimumSize(new Dimension(Window.WIDTH, Window.HEIGHT / 5));
        this.setPreferredSize(getMinimumSize());
        this.setLayout(new GridLayout(1, 3));

        this.add(actionPanel);
        this.add(inventoryPanel);
        this.add(scorePanel);
    }

    /**
     * Frissíti a menü tartalmát.
     */
    public void update() {
        actionPanel.update();
        inventoryPanel.update();
        scorePanel.update();
    }

    /**
     * A menüpanel háttérképért felelős felüldefiniált kirajzolófüggvény.
     */
    @Override
    public void paintComponent(Graphics g) {
        Window.getGraphics2D(g).drawImage(background, -10, -10, getWidth() + 50, getHeight() + 20, null);
    }

    /**
     * Beállítja a paraméterként kapott JLabel szöveget Chalkduster típusú fontra.
     * @param title - JLabel, a szöveg, amelynek beállítjuk a fonttípusát
     */
    public static void setFontTitle(JLabel title) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Chalkduster.ttf")).deriveFont(30f);
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            title.setFont(customFont);
        } catch (IOException|FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
