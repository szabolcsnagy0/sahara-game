package GUI.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import GUI.Controller;
import proto.Field;
import proto.Player;
/**
 * Gomb a játékos mozgatásához.
 */
public class MoveButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public MoveButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("MOVE");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.movePlayer();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 1,
     * és a kiválasztott mező a kiválasztott játékos pozíciójának szomszédja,
     * akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (player == null)
            return false;

        List<Field> selectedFields = Controller.instance.selectedFields;
        if (selectedFields.size() != 1)
            return false;

        // Check if it is a neighbouring field
        return player.getPosition().hasNeighbour(selectedFields.get(0));
    }
}
