package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import proto.FieldNode;
import proto.Mechanic;
import proto.Player;
/**
 * Gomb a cső lerakásához, elhelyezéséhez.
 */
public class PlacePipeButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public PlacePipeButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("PLACE PIPE");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.placePipe();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos egy Mechanic, és a Mechanic pozíciója FieldNode,
     * és a játékosnál van cső, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Mechanic))
            return false;

        if(Controller.instance.selectedFields.size() != 0
                || !(player.getPosition() instanceof FieldNode))
            return false;
        return ((Mechanic)player).getPipe() != null;
    }
}
