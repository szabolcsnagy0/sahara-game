package proto;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy csomópontot reprezentáló absztrakt osztály.
 */
public abstract class FieldNode extends Field implements Tickable {
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
        pipes.add(p);
    }

    /**
     * Lecsatlakoztatja a csomópontról a paraméterként kapott csövet.
     *
     * @param p - A cső amelyiket lecsatlakoztatjuk.
     */
    public void disconnect(Pipe p) {
        pipes.remove(p);
    }
    /**
     * A csomóponthoz csatlakoztatott másik csomópontok listáját adja vissza.
     * @return A csomóponthoz csatlakoztatott másik csomópontok listája.
     */
    public List<FieldNode> getConnectedNodes() {
        List<FieldNode> nodes = new ArrayList<>();
        for (int i = 0; i < pipes.size(); i++) {

            for (FieldNode end : pipes.get(i).getEnds()) {
                if (end != this) {
                    nodes.add(end);
                    break;
                }
            }
        }
        return nodes;
    }

    /**
     * A csomópont tickelése, adatainak léptetése.
     */
    public abstract void tick();

    /**
     * A csomópont string reprezentációját adja vissza.
     * @return A csomópont string reprezentációja.
     */
    @Override
    public String toString() {
        if (pipes.isEmpty())
            return "null";
        String s = "";
        for (Pipe p : pipes) {
            s += (Proto.findName(p) + ", ");
        }
        return s.substring(0, s.length() - 2);
    }
}
