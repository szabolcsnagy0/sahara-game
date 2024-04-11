package GUI;

import java.awt.*;
/**
 * A játékban megjelenő objektumokat reprezentáló interfész.
 */
public interface Viewable {
    /**
     * A megjelenítendő objektumot rajzoltatja újra, a megváltozott adatok alapján.
     */
    public void update();
    /**
     * Visszaadja a megjelenítendő objektum pozícióját.
     * @return A megjelenítendő objektum pozíciója.
     */
    public Point getPosition();
}
