package GUI.actions;

import java.awt.event.ActionEvent;
import java.util.List;

import GUI.Controller;
import proto.*;

/**
 * Gomb a cső lecsatlakoztatásához.
 */
public class DisconnectPipeButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public DisconnectPipeButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("DISCONNECT PIPE");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.disconnectPipe();
        });
    }

    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 1,
     * és a kiválasztott játékos pozíciója cső, és a játékos egy Mechanic, és a cső üres,
     * ahonnan lecsatlakoztatjuk valójában egyik vége a csőnek, akkor igazat ad vissza.
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

        Field from = selectedFields.get(0);
        if (!(from instanceof FieldNode)) {
            return false;
        }

        return (player.getPosition() instanceof Pipe) && from.hasNeighbour(player.getPosition()) && ((Pipe)player.getPosition()).isEmpty();
    }
}
