package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import proto.Pipe;
import proto.Player;
/**
 * Gomb a ragacsossá tételhez.
 */
public class MakeStickyButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public MakeStickyButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("MAKE STICKY");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.makeSticky();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos pozíciója cső, és a cső nem ragacsos,
     * akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (player == null)
            return false;

        return Controller.instance.selectedFields.size() == 0
                && player.getPosition() instanceof Pipe && !((Pipe)player.getPosition()).isSticky();
    }
}
