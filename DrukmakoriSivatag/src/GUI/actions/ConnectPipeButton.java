package GUI.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import GUI.Controller;
import proto.*;
/**
 * Gomb a cső felcsatolásához.
 */
public class ConnectPipeButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet beállítása.
     */
    public ConnectPipeButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("CONNECT PIPE");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.connectPipe();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 1,
     * és a kiválasztott játékos pozíciója cső, és a csőnek nincs még mind a két vége csatlakoztatva,
     * és a kiválasztott mező egy FieldNode, ami nem cső, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Mechanic))
            return false;

        List<Field> selectedFields = Controller.instance.selectedFields;
        if (selectedFields.size() != 1)
            return false;

        if(!(player.getPosition() instanceof Pipe) || !(selectedFields.get(0) instanceof FieldNode))
            return false;

        Pipe pipe = (Pipe)player.getPosition();
        Field node = selectedFields.get(0); // The FieldNode to connect the pipe to

        return !pipe.hasNeighbour(node) && pipe.getEnds().size() < 2;
    }
}
