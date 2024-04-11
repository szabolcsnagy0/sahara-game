package GUI.menu;

import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.actions.*;

/**
 * Az akciókat reprezentáló panel.
 */
public class ActionPanel extends JPanel {
    /**
     * Az akciógombokat tartalmazó panel.
     */
    private JPanel buttonsPanel = new JPanel();

    /**
     * Konstruktor.
     */
    public ActionPanel() {
        super();

        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Actions");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        MenuPanel.setFontTitle(title);
        
        this.add(title);

        buttonsPanel.setOpaque(false);

        buttonsPanel.add(new MoveButton());
        buttonsPanel.add(new FixPipeButton());
        buttonsPanel.add(new FixPumpButton());
        buttonsPanel.add(new BreakPipeButton());
        buttonsPanel.add(new ChangeFlowButton());
        buttonsPanel.add(new MakeStickyButton());
        buttonsPanel.add(new MakeSlipperyButton());
        buttonsPanel.add(new ConnectPipeButton());
        buttonsPanel.add(new DisconnectPipeButton());
        buttonsPanel.add(new PickupPumpButton());
        buttonsPanel.add(new PickupPipeButton());

        this.add(buttonsPanel);

        update();
    }

    /**
     * Frissíti az elérhető akciók paneljét, és csak azon akciógombok jelennek meg, amelyek elvégezhetőek.
     */
    public void update() {
        for (Component component : buttonsPanel.getComponents()) {
            if (!(component instanceof ActionButton))
                continue;

            ActionButton btn = (ActionButton) component;
            btn.setVisible(btn.canPerform());
        }
    }
}
