package proto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Egy csövet reprezentáló osztály.
 */
public class Pipe extends Field implements Tickable {
    /**
     * A cső maximális vízmennyiségére egy érték.
     */
    static final int MAX_VOLUME = 1000;
    /**
     * Az az idő másodpercekben, ameddig a cső csúszós.
     */
    static final int SLIPPERY_TIME = 10;
    /**
     * Az az idő másodpercekben, ameddig a cső ragadós.
     */
    static final int STICKY_TIME = 10;
    /**
     * Az az idő másodpercekben, ameddig a cső nem lyukasztható.
     */
    static final int MAX_BREAKABLE_TIME = 10;
    /**
     * Random számok generálására használt objektum.
     */
    private Random random = new Random();
    /**
     * A cső ki van-e lyukadva.
     */
    private boolean isBroken;
    /**
     * A cső maximális vízmennyisége.
     */
    private int maxVolume;
    /**
     * A csőben lévő víz mennyisége.
     */
    private int currentVolume;
    /**
     * A csőben elpazarolt víz mennyisége.
     */
    private int wastedWater;
    /**
     * Az az idő, ameddig a cső nem lyukasztható.
     */
    private int timeUntilBreakable;
    /**
     * Az az idő, ameddig a cső csúszós.
     */
    private int slipperyUntil;
    /**
     * Az az idő, ameddig a cső ragadós.
     */
    private int stickyUntil;
    /**
     * Logikai változó, ami azt jelzi, hogy a csőben volt-e víz.
     */
    private boolean hadWater = false;
    /**
     * A cső végpontjai.
     */
    private List<FieldNode> ends;

    /**
     * Egy új csövet készít.
     */
    public Pipe() {
        super();

        isBroken = false;

        maxVolume = MAX_VOLUME;
        currentVolume = 0;

        wastedWater = 0;

        timeUntilBreakable = 0;
        slipperyUntil = 0;
        stickyUntil = 0;

        ends = new ArrayList<>(2);
    }

    /**
     * A cső végpontjait adja vissza egy listában.
     * @return a cső végpontjai.
     */
    public List<FieldNode> getEnds() {
        return ends;
    }

    /**
     * Beállítja hogy mennyi víz van a csőben.
     *
     * @param amount - a beállítani kívánt víz mennyisége
     */
    public void setWaterVolume(int amount) {
        if (maxVolume < amount)
            throw new IllegalArgumentException("Can't put more water into pipe than the maximum allowed volume");
        if (amount < 0)
            throw new IllegalArgumentException("Water volume in pipe can't be less than 0");

        currentVolume = amount;
    }

    /**
     * Beállítja a cső maximális kapacitását.
     *
     * @param amount - a kapacitás értéke
     */
    public void setMaxVolume(int amount) {
        if (amount < currentVolume)
            throw new IllegalArgumentException("Max volume cannot be less than the current volume");
        if (amount < 0)
            throw new IllegalArgumentException("Max volume of pipe can't be less than 0");

        maxVolume = amount;
    }

    /**
     * A cső egy adott ideig csúszossá válik. Ha egy játékos rálép,
     * akkor átcsúszik arra a pályaelemre, amelyikre a cső másik vége csatlakoztatva
     * van.
     */
    void makeSlippery() {
        slipperyUntil = SLIPPERY_TIME;
    }

    /**
     * A cső egy adott ideig ragadóssá válik. Ha valamelyik játékos
     * rálép, akkor rövid ideig nem tud a pályelemről továbblépni.
     */
    void makeSticky() {
        stickyUntil = STICKY_TIME;
    }

    /**
     * Kilyukasztja a csövet, úgy hogy az összes benne lévő és rajta átfolyó víz
     * kifolyik belőle.
     */
    public void breakPipe() {
        if (0 < timeUntilBreakable)
            return;
        isBroken = true;
        wastedWater += currentVolume;
        currentVolume = 0;
    }

    /**
     * Ha van lyuk a csövön akkor megfoltozza azt.
     */
    public void repair() {
        isBroken = false;

        if (Proto.isRandom) {
            timeUntilBreakable = random.nextInt(0, MAX_BREAKABLE_TIME);
        } else {
            timeUntilBreakable = 1;
        }
    }

    /**
     * Vizet tölt a csőbe.
     *
     * @param amount A víz mennyisége amit a csőbe szeretnénk tölteni.
     * @return A annak a víznek a mennyisége amit sikeresen betöltöttünk a csőbe.
     */
    public int flow(int amount) {
        if (isBroken || ends.size() != 2) {
            wastedWater += Math.min(maxVolume, amount);
            if(amount > 0) hadWater = true;
            return amount;
        }

        int consumed = Math.min(maxVolume - currentVolume, amount);
        if (consumed > 0) hadWater = true;
        currentVolume += consumed;
        return consumed;
    }

    /**
     * Kiszívja a vizet a csőből.
     *
     * @param amount A víz mennyisége amit ki akarunk szívni.
     * @return A víz mennyisége amit sikeresen leszívtunk.
     */
    public int drain(int amount) {
        int drained = Math.min(currentVolume, amount);
        currentVolume -= drained;
        return drained;
    }

    /**
     * Metódus, ami visszaadja, hogy a csőben volt-e víz.
     * @return igaz, ha volt víz a csőben, hamis, ha nem volt.
     */
    public boolean hasWaterFlown() {
        return hadWater;
    }

    /**
     * Hozzáadja a paraméterként kapott játékost, amelyik a mezőre lép, a játékosok
     * listájához
     *
     * @param p - A játékos amelyet hozzáadjuk a listához.
     * @return A hozzáadás sikeressége.
     */
    @Override
    public Field addPlayer(Player p) {
        int standing = getNumberOfPlayers();
        if (0 < standing) {
            return null;
        }

        if (0 < slipperyUntil) {
            Field slipTo;
            if (Proto.isRandom) {
                slipTo = ends.get(random.nextInt(ends.size()));
            } else {
                slipTo = ends.get(0);
            }
            slipTo.addPlayer(p);
            return slipTo;
        }

        super.addPlayer(p);
        return this;
    }

    /**
     * A paraméterként kapott játékos ellép a pályaelemről és ezt kitörli a
     * játékosok listájából. Ha a cső amiről el akar lépni a játékos ragadós,
     * azt nem teheti meg, addig amíg a cső ragadós ideje le nem jár.
     *
     * @param p - a törlendő játékos.
     * @return sikeres-e a játékos eltávolítása a csőről
     */
    @Override
    public boolean removePlayer(Player p) {
        if (0 < stickyUntil)
            return false;

        super.removePlayer(p);
        return true;
    }

    /**
     * Beállítja a megadott csomópontot a cső egyik végének.
     *
     * @param n A megadott csomópont amihez csatlakozni szeretnénk.
     * @return Visszaadja, hogy sikerült-e csatlakozni a csomóponthoz.
     */
    public boolean connect(FieldNode n) {
        if (ends.size() == 2)
            throw new IllegalArgumentException("A pipe can't have more than two ends");

        if (currentVolume != 0) {
            return false;
        }

        ends.add(n);
        return true;
    }

    /**
     * Eltávolítja a megadott csomópontot a cső végei közül.
     *
     * @param n A csomópont amit el akarunk távolítani.
     * @return Visszaadja, hogy sikerült-e eltávolítani a csomópontot.
     */
    public boolean disconnect(FieldNode n) {
        if (currentVolume != 0) {
            return false;
        }

        return ends.remove(n);
    }

    /**
     * Félbevágja a csövet, ezzel egy új csövet készítve.
     *
     * @return Visszaadja az ujonnan elkészült csövet, ha sikerült a félbevágás
     * különben pedig null-t ad vissza.
     */
    public Pipe cut() {
        if (currentVolume != 0 || ends.size() != 2) {
            return null;
        }

        FieldNode fn = ends.get(1);

        this.disconnect(fn);
        fn.disconnect(this);

        Pipe newPipe = new Pipe();
        newPipe.connect(fn);
        fn.connect(newPipe);

        return newPipe;
    }

    /**
     * Egy időegység elteltét jelenti. Az idő amíg újból lehet lyukasztani,
     * amíg ragadós vagy amíg csúszós a cső, csökken.
     */
    @Override
    public void tick() {
        if (currentVolume == 0)
            hadWater = false;
        if (0 < timeUntilBreakable)
            timeUntilBreakable--;

        if (0 < slipperyUntil)
            slipperyUntil--;

        if (0 < stickyUntil)
            stickyUntil--;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean hasNeighbour(Field field) {
        return ends.contains(field);
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public String toString() {
        String playerList = "";
        if (players.isEmpty())
            playerList = "null";
        else {
            for (Player p : players) {
                playerList += (Proto.findName(p) + ", ");
            }
            playerList = playerList.substring(0, playerList.length() - 2);
        }

        return "Pipe " +
                Proto.findName(this) +
                " with ends: " +
                (ends.size() < 1 ? "null" : Proto.findName(ends.get(0))) +
                ", " +
                (ends.size() < 2 ? "null" : Proto.findName(ends.get(1))) +
                " max volume: " +
                this.maxVolume +
                " current volume: " +
                this.currentVolume +
                " wasted water: " +
                this.wastedWater +
                " broken: " +
                (isBroken ? "true" : "false") +
                " breakable: " +
                this.timeUntilBreakable +
                " slippery: " +
                this.slipperyUntil +
                " sticky: " +
                this.stickyUntil +
                " standing player: " +
                (players.size() < 1 ? "null" : Proto.findName(players.get(0)));
    }

    /**
     * Metódus, ami visszaadja, hogy a cső lyukas-e.
     * @return igaz, ha lyukas, hamis, ha nem.
     */
    public boolean isBroken() {
        return isBroken;
    }

    /**
     * Metódus, ami visszaadja, hogy a cső ragadós-e.
     * @return igaz, ha ragadós, hamis, ha nem.
     */
    public boolean isSticky() {
        return stickyUntil > 0;
    }
    /**
     * Metódus, ami visszaadja, hogy a cső csúszós-e.
     * @return igaz, ha csúszós, hamis, ha nem.
     */
    public boolean isSlippery() {
        return slipperyUntil > 0;
    }
    /**
     * Metódus, ami visszaadja, hogy a csőben van-e víz.
     * @return igaz, ha nincs víz, hamis, ha a csőben a vízmennyiség nem 0.
     */
    public boolean isEmpty() {
        return currentVolume == 0;
    }

    /**
     * Metódus, ami visszaadja a csőből elfolyt, elpazarolt víz mennységét.
     * @return a csőből elfolyt víz mennyisége.
     */
    public int getWastedWater() {
      return wastedWater;
    }
    /**
     * Metódus, ami visszaadja, hogy a cső törhető-e.
     * @return igaz, ha törhető, hamis, ha nem.
     */
    public boolean isBreakable() {
        return timeUntilBreakable == 0;
    }
}
