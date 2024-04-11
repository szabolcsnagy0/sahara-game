package proto;

import java.util.Random;

/**
 * A ciszternákért felelős osztály
 */
public class Cistern extends FieldNode {
    /**
     * A ciszterna vizének mennyisége
     */
    private int drainedWater;
    /**
     * Az idő másodpercekben, ami után egy cső újra elérhető lesz a ciszternánál
     */
    private int pipeAvailable;

    /**
     * Konstruktor
     * A ciszternába befolyt vizet (drainedWater) 0-ra inicalizálja.
     * A cső elérhatőségét (pumpAvailable) hamisra állítja. Csövet nem vehet fel a
     * Szerelő.
     */
    public Cistern() {
        drainedWater = 0;
        pipeAvailable = 20;
    }

    /**
     * Függvény, ami a ciszternába folyt vizet növeli a paraméterként átadott
     * értékkel.
     *
     * @param amount az az int mennyiség, amivel növeli a ciszternába folyt vizet.
     */
    void addDrainedWater(int amount) {
        drainedWater += amount;
    }

    /**
     * Egy cső felvevése a mezőről, ha nem megy akkor null értékkel tér vissza.
     *
     * @return az elvett cső vagy null
     */
    @Override
    public Pipe takePipe() {
        if (pipeAvailable != 0) {
            return null;
        }
        pipeAvailable = 20;
        return new Pipe();
    }

    /**
     * Egy pumpa felvevése a ceszternáról.
     *
     * @return az elvett pumpa
     */
    @Override
    public Pump takePump() {
        return new Pump();
    }

    /**
     * Függvény, ami lekérdezi a ciszternába folyt víz mennyiségét.
     *
     * @return a ciszternába folyt víz mennyisége.
     */
    public int getDrainedWater() {
        return drainedWater;
    }

    /**
     * Függvény, ami megmondja, hogy cső elérhető-e a ciszternánál.
     *
     * @return true, ha a cső elérhető, false, ha nem.
     */
    public boolean isPipeAvailable() {
        return pipeAvailable == 0;
    }
    /**
     * Függvény, ami lekérdezi, hogy a paraméterként átadott mező szomszédos-e a
     * ciszternával, azaz a ciszternára csatlakoztatott cső-e.
     *
     * @param field a vizsgált mező
     * @return true, ha a paraméterként átadott mező szomszédos a ciszternával,
     * false, ha nem.
     */
    @Override
    public boolean hasNeighbour(Field field) {
        return pipes.contains(field);
    }

    /**
     * Egy időegység elteltét jelenti.
     * Az időegységnek megfelelő mennyiségű víz elfolyik a ciszternához kapcsolt
     * csővekből a ciszternába.
     * Ezzel a mennyiséggel nő a cisztárnába befolyt víz mennyisége.
     */
    @Override
    public void tick() {
        // TODO csak par tickenkent termeljen pipeot
        if (pipeAvailable > 0) pipeAvailable--;

        for (Pipe pipe : pipes) {
            int drained = pipe.drain(Integer.MAX_VALUE);
            addDrainedWater(drained);
        }
    }

    /**
     * Visszaadja a ciszterna string reprezentációját.
     * @return a ciszterna string reprezentációja
     */
    @Override
    public String toString() {
        String playerList = "";
        if (players.isEmpty()) playerList = "null";
        else {
            for (Player p : players) {
                playerList += (Proto.findName(p) + ", ");
            }
            playerList = playerList.substring(0, playerList.length() - 2);
        }

        return "Cistern " +
                Proto.findName(this) +
                " with ends: " +
                super.toString() +
                " pipe available: " +
                (pipeAvailable == 0 ? "true" : "false") +
                " drained water: " +
                getDrainedWater() +
                " standing players: " +
                playerList;
    }
}
