package skeleton;

/**
 * A Player absztrakt osztálya.
 */
public abstract class Player {
    /**
     * A játékos pozícióját jelölő mező.
     */
    protected Field position;

    /**
     * A játékos pozíciót vált.
     *
     * @param f A játékos célpozíciója.
     */
    public void moveTo(Field f) {
        Skeleton.callFunction(this, "moveTo", new Object[] { f });
        if (f.addPlayer(this)) {
            if (position != null) {
                position.removePlayer(this);
            }
            setPosition(f);
        }
        Skeleton.endFunction();
    }

    /**
     * A játékos a paraméterként kapott pumpát átállítja, hogy melyik
     * csőből melyikbe pumpálja a vizet.
     *
     * @param pump Az átállítandó pumpa.
     * @param from Ebből a csőből fogja pumpálni a pumpa a vizet.
     * @param to   Ebbe a csőbe fogja pumpálni a pumpa a vizet.
     */
    public void setPumpDirection(Pump pump, Pipe from, Pipe to) {
        Skeleton.callFunction(this, "setPumpDirection", new Object[] { pump, from, to });
        pump.changeFlow(from, to);
        Skeleton.endFunction();
    }

    /**
     * Beállítja a játékos pozícióját a paramétrként megadott mezőre.
     *
     * @param f A játékos új pozicíója.
     */
    public void setPosition(Field f) {
        Skeleton.callFunction(this, "setPosition", new Object[] { f });
        position = f;
        Skeleton.endFunction();
    }
}
