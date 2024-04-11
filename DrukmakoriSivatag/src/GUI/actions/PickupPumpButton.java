package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import proto.Cistern;
import proto.Mechanic;
import proto.Player;
/**
 * Gomb a pumpa felvételéhez.
 */
public class PickupPumpButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public PickupPumpButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("PICKUP PUMP");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.pickupPump();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos pozíciója ciszterna,
     * és a játékosnál nincs pumpa, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Mechanic))
            return false;

        return Controller.instance.selectedFields.size() == 0
                && player.getPosition() instanceof Cistern && ((Mechanic) player).getPump() == null;
    }
}
