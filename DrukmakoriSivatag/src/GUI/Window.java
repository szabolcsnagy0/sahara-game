package GUI;

import javax.swing.*;

import GUI.menu.MenuPanel;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.HashMap;

/**
 * A játék ablakát reprezentáló osztály.
 */
public class Window extends JFrame {
    /**
     * A játék ablakának szélessége.
     */
    public static final int WIDTH = 1280;
    /**
     * A játék ablakának magassága.
     */
    public static final int HEIGHT = 800;
    /**
     * A játékban megjelenő gombok mérete.
     */
    public static final int BUTTONSIZE = 50;
    /**
     * A játékfelület háttere (sivatag)
     */
    private static Image background = new ImageIcon(Controller.assetsPath + "background.png").getImage();
    /**
     * A megjelenítendő objektumok tárolója
     */
    private static HashMap<Class<?>, Integer> zOrder = new HashMap<>();

    static {
        zOrder.put(MechanicView.class, 2);
        zOrder.put(SaboteurView.class, 2);
        zOrder.put(PumpView.class, 1);
        zOrder.put(CisternView.class, 1);
        zOrder.put(SpringView.class, 1);
        zOrder.put(PipeView.class, 0);
    }

    /**
     * A játékmenüt megjelenítő panel.
     */
    private MenuPanel menu = new MenuPanel();
    /**
     * A játékfelület megjelenítésére szolgáló panel.
     */
    private JLayeredPane map = new JLayeredPane() {
        @Override
        public void paintComponent(Graphics g) {
            getGraphics2D(g).drawImage(background, 0, 0, Window.WIDTH, Window.HEIGHT, null);
        }
    };

    /**
     * Konstruktor.
     */
    public Window() {
        super();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Drukmakori sivatag");
        this.setVisible(true);
        setResizable(false);
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(getSize());

        map.setLayout(null);
        map.setBounds(0, 0, Window.WIDTH, Window.HEIGHT);

        this.setLayout(new BorderLayout());
        this.add(map);
        this.add(menu, BorderLayout.SOUTH);
    }

    /**
     * Hozzáad egy megjelenítendő objektumot a játékfelülethez és ki is rajzoltatja.
     *
     * @param view A megjelenítendő objektum.
     */
    public void addViewable(Viewable view) {
        Component component = (Component) view;
        map.add(component, zOrder.get(view.getClass()));
        map.validate();
        map.repaint();
    }

    /**
     * Frissíti az összes megjelenítendő objektumot nézetét, azaz újrarajzoltatja őket..
     */
    public void updateAllViews() {
        map.repaint();
        for (Viewable view : Controller.instance.fields.values())
            view.update();

        for (Viewable view : Controller.instance.players.values())
            view.update();
    }

    /**
     * Visszaadja a megadott grafikus objektumot, amit a megfelelő beállításokkal a kirajzoláshoz használunk.
     *
     * @param g A grafikus objektum.
     * @return A grafikus objektum.
     */
    static public Graphics2D getGraphics2D(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2d;
    }

    /**
     * Frissíti a menüpanel tartalmát.
     */
    public void updateMenu() {
        menu.update();
    }

    /**
     * Ellenőrzi, hogy véget ért-e a játék.
     * Amennyiben igen, megjelenít egy dialógusablakot, kiírva a nyertes csapatot.
     */
    public void checkGameEnded() {
        int mechanicScore = Controller.instance.getMechanicScore();
        int saboteurScore = Controller.instance.getSaboteurScore();
        if (mechanicScore <= Controller.MAX_SCORE && saboteurScore <= Controller.MAX_SCORE) {
            return;
        }
        String text = null;
        ImageIcon icon = null;
        if (mechanicScore > Controller.MAX_SCORE && saboteurScore > Controller.MAX_SCORE) {
            text = "It's a tie!";
        } else if (mechanicScore > Controller.MAX_SCORE) {
            text = "The Mechanic team won the game!";
            icon = new ImageIcon(MechanicView.image.getScaledInstance(BUTTONSIZE, BUTTONSIZE, Image.SCALE_SMOOTH));
        } else {
            text = "The Saboteur team won the game!";
            icon = new ImageIcon(SaboteurView.image.getScaledInstance(BUTTONSIZE, BUTTONSIZE, Image.SCALE_SMOOTH));
        }
        if (icon == null)
            JOptionPane.showMessageDialog(this, text, "The game has ended", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, text, "The game has ended", JOptionPane.INFORMATION_MESSAGE, icon);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
