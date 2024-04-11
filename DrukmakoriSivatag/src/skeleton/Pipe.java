package skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy csövet reprezentáló osztály.
 */
public class Pipe extends Field {
    private List<FieldNode> ends;

    /**
     * Egy új csövet készít.
     */
    public Pipe() {
        super();
        this.ends = new ArrayList<>();

        Skeleton.callFunction(this, "create", null);
        Skeleton.endFunction();
    }

    /**
     * Beállítja hogy mennyi víz van a csőben.
     */
    public void setWaterVolume(Integer amount) {
        Skeleton.callFunction(this, "setWaterVolume", new Object[] { amount });
        Skeleton.endFunction();
    }

    /**
     * Kilyukasztja a csövet, úgy hogy az összes benne lévő és rajta átfolyó víz
     * kifolyik belőle.
     */
    public void breakPipe() {
        Skeleton.callFunction(this, "breakPipe", null);
        Skeleton.endFunction();
    }

    /**
     * Ha van lyuk a csövön akkor megfoltozza azt.
     */
    public void repair() {
        Skeleton.callFunction(this, "repair", null);
        Skeleton.endFunction();
    }

    /**
     * Vizet tölt a csőbe.
     * 
     * @param amount A víz mennyisége amit a csőbe szeretnénk tölteni.
     * @return A annak a víznek a mennyisége amit sikeresen betöltöttünk a csőbe.
     */
    public int flow(Integer amount) {
        Skeleton.callFunction(this, "flow", new Object[] { amount });

        int consumed = Skeleton.numberQuestion("Enter the amount of water that has flown into the pipe");

        Skeleton.endFunction();
        return consumed;
    }

    /**
     * Kiszívja a vizet a csőből.
     * 
     * @param amount A víz mennyisége amit ki akarunk szívni.
     * @return A víz mennyisége amit sikeresen leszívtunk.
     */
    public int drain(Integer amount) {
        Skeleton.callFunction(this, "drain", new Object[] { amount });

        int consumed = Skeleton.numberQuestion("Enter the amount of water that has been drained from the pipe");

        Skeleton.endFunction();
        return consumed;
    }

    /**
     * Hozzáadja a paraméterként kapott játékost, amelyik a mezőre lép, a játékosok
     * listájához
     *
     * @param p - A játékos amelyet hozzáadjuk a listához.
     * @return A hozzáadás sikeressége.
     */
    @Override
    public boolean addPlayer(Player p) {
        Skeleton.callFunction(this, "addPlayer", new Object[] { p });

        boolean success = Skeleton.yesNoQuestion("Can player move to pipe?");
        if (success) {
            players.add(p);
        }

        Skeleton.endFunction();
        return success;
    }

    /**
     * Beállítja a megadott csomópontot a cső egyik végének.
     * 
     * @param n A megadott csomópont amihez csatlakozni szeretnénk.
     * @return Visszaadja, hogy sikerült-e csatlakozni a csomóponthoz.
     */
    public boolean connect(FieldNode n) {
        Skeleton.callFunction(this, "connect", new Object[] { n });

        boolean success = Skeleton.yesNoQuestion(
                String.format("Can the pipe be connected to the %s?", n.getClass().getName()));
        if (success) {
            ends.add(n);
        }

        Skeleton.endFunction();
        return success;
    }

    /**
     * Eltávolítja a megadott csomópontot a cső végei közül.
     * 
     * @param n A csomópont amit el akarunk távolítani.
     * @return Visszaadja, hogy sikerült-e eltávolítani a csomópontot.
     */
    public boolean disconnect(FieldNode n) {
        Skeleton.callFunction(this, "disconnect", new Object[] { n });

        boolean success = Skeleton.yesNoQuestion(
                String.format("Can the pipe be disconnected from the %s?", n.getClass().getName()));
        if (success) {
            ends.remove(n);
        }

        Skeleton.endFunction();
        return success;
    }

    /**
     * Félbevágja a csövet, ezzel egy új csövet készítve.
     * 
     * @return Visszaadja az ujonnan elkészült csövet, ha sikerült a félbevágás
     *         különben pedig null-t ad vissza.
     */
    public Pipe cut() {
        Skeleton.callFunction(this, "cut", null);

        boolean success = Skeleton.yesNoQuestion("Can we cut the pipe in half?");
        if (!success) {
            Skeleton.endFunction();
            return null;
        }

        FieldNode fn = ends.get(1);

        this.disconnect(fn);
        fn.disconnect(this);

        Pipe newPipe = new Pipe();
        Skeleton.names.put(newPipe, "newPipe");
        newPipe.connect(fn);
        fn.connect(newPipe);

        Skeleton.endFunction();
        return newPipe;
    }
}
