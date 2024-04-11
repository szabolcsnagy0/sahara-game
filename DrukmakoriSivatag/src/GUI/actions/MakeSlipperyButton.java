package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import proto.Pipe;
import proto.Player;
import proto.Saboteur;
/**
 * Gomb a csúszóssá tételhez.
 */
public class MakeSlipperyButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public MakeSlipperyButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("MAKE SLIPPERY");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.makeSlippery();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos pozíciója cső, és a cső nem csúszós,
     * akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Saboteur))
            return false;

        return Controller.instance.selectedFields.size() == 0
                && player.getPosition() instanceof Pipe && !((Pipe)player.getPosition()).isSlippery();
    }
}
