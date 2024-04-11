package GUI.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import GUI.Controller;
import proto.Field;
import proto.Pipe;
import proto.Player;
import proto.Pump;

/**
 * Gomb a víz áramlásának megváltoztatásához.
 */
public class ChangeFlowButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet beállítása.
     */
    public ChangeFlowButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("CHANGE FLOW");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.changeFlow();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 2,
     * és a kiválasztott játékos pozíciója pumpa, és a kiválasztott mezők csők,
     * és a kiválasztott mezők szomszédosak a játékos pozíciójával, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (player == null || !(player.getPosition() instanceof Pump))
            return false;

        List<Field> selectedFields = Controller.instance.selectedFields;
        if (selectedFields.size() != 2)
            return false;

        if(!(selectedFields.get(0) instanceof Pipe) || !(selectedFields.get(1) instanceof Pipe))
            return false;

        return (player.getPosition()).hasNeighbour(selectedFields.get(0)) && (player.getPosition()).hasNeighbour(selectedFields.get(1));
    }
}
