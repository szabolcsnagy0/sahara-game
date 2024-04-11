package GUI.menu;

import java.awt.Component;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import GUI.Controller;
import GUI.Window;

/**
 * A csapatok pontszámát reprezentáló panel.
 */
public class ScorePanel extends JPanel {
    /**
     * A szerelők csapatát reprezentáló kép.
     */
    private static ImageIcon mechanicImage = new ImageIcon(Controller.assetsPath + "mechanic.png");

    /**
     * A szabotőrök csapatát reprezentáló kép.
     */
    private static ImageIcon saboteurImage = new ImageIcon(Controller.assetsPath + "saboteur.png");
    static {
        mechanicImage = new ImageIcon(mechanicImage.getImage()
                .getScaledInstance(Window.BUTTONSIZE, Window.BUTTONSIZE, Image.SCALE_AREA_AVERAGING));
        saboteurImage = new ImageIcon(saboteurImage.getImage()
                .getScaledInstance(Window.BUTTONSIZE, Window.BUTTONSIZE, Image.SCALE_AREA_AVERAGING));
    }

    /**
     * A szerelők csapatának pontszámát reprezentáló címke.
     */
    JLabel mechanicScore = new JLabel(mechanicImage);
    /**
     * A szabotőrök csapatának pontszámát reprezentáló címke.
     */
    JLabel saboteurScore = new JLabel(saboteurImage);

    /**
     * Konstruktor.
     */
    public ScorePanel() {
        super();

        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Scores");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        MenuPanel.setFontTitle(mechanicScore);
        mechanicScore.setText("0");
        mechanicScore.setIconTextGap(0);
        mechanicScore.setVerticalTextPosition(SwingConstants.BOTTOM);
        mechanicScore.setHorizontalTextPosition(SwingConstants.CENTER);
        mechanicScore.setBorder(new EmptyBorder(0, 10, 0, 10));

        MenuPanel.setFontTitle(saboteurScore);
        saboteurScore.setText("0");
        saboteurScore.setIconTextGap(0);
        saboteurScore.setVerticalTextPosition(SwingConstants.BOTTOM);
        saboteurScore.setHorizontalTextPosition(SwingConstants.CENTER);
        saboteurScore.setBorder(new EmptyBorder(0, 10, 0, 10));

        JPanel panel = new JPanel();
        panel.add(mechanicScore);
        panel.add(saboteurScore);
        panel.setOpaque(false);

        MenuPanel.setFontTitle(title);

        this.add(title);
        this.add(panel);
    }

    /**
     * Frissíti a panelt, ezen belül a csapatok pontszámát.
     */
    public void update() {
        mechanicScore.setText(Integer.toString(Controller.instance.getMechanicScore()));
        saboteurScore.setText(Integer.toString(Controller.instance.getSaboteurScore()));
    }
}
