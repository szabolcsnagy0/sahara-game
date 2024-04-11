package skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy csomópontot reprezentáló absztrakt osztály.
 */
public abstract class FieldNode extends Field {
    /**
     * A csomóponthoz csatlakoztatott csövek.
     */
    protected List<Pipe> pipes = new ArrayList<>();

    /**
     * A csomóponthoz csatlakoztatja a paraméterként kapott csövet
     *
     * @param p - A cső amelyiket csatlakoztatjuk.
     */
    public void connect(Pipe p) {
        Skeleton.callFunction(this, "connect", new Object[] { p });
        pipes.add(p);
        Skeleton.endFunction();
    }

    /**
     * Lecsatlakoztatja a csomópontról a paraméterként kapott csövet.
     *
     * @param p - A cső amelyiket lecsatlakoztatjuk.
     */
    public void disconnect(Pipe p) {
        Skeleton.callFunction(this, "disconnect", new Object[] { p });
        if (pipes != null) {
            pipes.remove(p);
        }
        Skeleton.endFunction();
    }
}
