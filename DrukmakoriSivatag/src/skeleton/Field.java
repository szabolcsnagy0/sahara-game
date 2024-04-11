package skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy mezőt reprezentáló absztrakt osztály.
 */
public abstract class Field {
    protected List<Player> players;

    /**
     * Konstruktor.
     */
    public Field() {
        players = new ArrayList<>();
    }

    /**
     * Hozzáadja a paraméterként kapott játékost, amelyik a mezőre lép, a játékosok
     * listájához
     *
     * @param p - A játékos amelyet hozzáadjuk a listához.
     * @return
     */
    public boolean addPlayer(Player p) {
        Skeleton.callFunction(this, "addPlayer", new Object[] { p });
        players.add(p);
        Skeleton.endFunction();
        return true;
    }

    /**
     * A paraméterként kapott játékos ellép a pályaelemről és ezt kitörli a
     * játékosok listájából.
     *
     * @param p - A törlendő játékos.
     */
    public void removePlayer(Player p) {
        Skeleton.callFunction(this, "removePlayer", new Object[] { p });
        players.remove(p);
        Skeleton.endFunction();
    }

    /**
     * Visszatéríti a pályaelemen álló játékosok számát.
     *
     * @return a pályaelemen álló játékosok száma.
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     * Egy cső felvevése a mezőről, ha nem megy akkor null értékkel tér vissza.
     *
     * @return az elvett cső vagy null
     */
    public Pipe takePipe() {
        Skeleton.callFunction(this, "takePipe", null);
        Skeleton.endFunction();
        return null;
    }

    /**
     * Egy pumpa felvevése a mezőről, ha nem megy akkor null értékkel tér vissza.
     *
     * @return az elvett pumpa vagy null
     */
    public Pump takePump() {
        Skeleton.callFunction(this, "takePump", null);
        Skeleton.endFunction();
        return null;
    }
}
