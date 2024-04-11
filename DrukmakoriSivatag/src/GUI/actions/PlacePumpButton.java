package GUI.actions;

import java.awt.event.ActionEvent;

import GUI.Controller;
import proto.Mechanic;
import proto.Pipe;
import proto.Player;
/**
 * Gomb a pumpa lerakásához, elhelyezéséhez.
 */
public class PlacePumpButton extends ActionButton {
    /**
     * Konstruktor.
     * Alap kinézet és tualjdonságok beállítása.
     */
    public PlacePumpButton() {
        super();

        this.setContentAreaFilled(false);
        this.setRolloverEnabled(true);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setText("PLACE PUMP");
        this.addActionListener((ActionEvent e) -> {
            Controller.instance.placePump();
        });
    }
    /**
     * Metódus, ami megmondja, hogy a gomb használható, megjeleníthető-e.
     * Ha a kiválasztott játékos nem null, és a kiválasztott mezők száma 0,
     * és a kiválasztott játékos egy Mechanic, és a jatékos csövön áll, a játékosnál van pumpa,
     * és a cső nem törött, és nem is folyik benne víz, akkor igazat ad vissza.
     * @return igaz, ha a gomb használható, hamis, ha nem.
     */
    public boolean canPerform() {
        if (Controller.instance == null)
            return false;

        Player player = Controller.instance.selectedPlayer;
        if (!(player instanceof Mechanic))
            return false;

        return Controller.instance.selectedFields.size() == 0
                && player.getPosition() instanceof Pipe && ((Mechanic) player).getPump() != null
                && ((Pipe) player.getPosition()).isEmpty()
                && !((Pipe) player.getPosition()).isBroken();
    }
}
