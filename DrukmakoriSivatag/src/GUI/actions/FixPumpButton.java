package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import proto.Field;
import proto.Mechanic;
import proto.Player;
import proto.Pump;
/**
 * Gomb a pumpa javításához.
 */
public class FixPumpButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public FixPumpButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("FIX PUMP");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.fixPump();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos pozíciója pumpa, és a pumpa törött,
     * akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Mechanic))
            return false;

        if (Controller.instance.selectedFields.size() != 0)
            return false;

        Field position = player.getPosition();
        return position instanceof Pump && ((Pump) position).isBroken();
    }
}
