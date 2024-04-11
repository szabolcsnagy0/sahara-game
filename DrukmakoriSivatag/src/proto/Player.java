package proto;

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
        if (position != null) {
            boolean canMove = position.removePlayer(this);
            if (!canMove) {
                return;
            }
        }
        Field newPosition = f.addPlayer(this);
        if (newPosition == null) {
            if (position != null) {
                position.addPlayer(this); // Nem tudtunk átlépni úgyhogy helyben maradunk
            }
            return;
        }

        setPosition(newPosition); // Sikeresen átléptünk
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
        pump.changeFlow(from, to);
    }

    /**
     * Beállítja a játékos pozícióját a paramétrként megadott mezőre.
     *
     * @param f A játékos új pozicíója.
     */
    public void setPosition(Field f) {
        position = f;
    }

    /**
     * A játékos egy paraméterként kapott csövet kilyukaszt.
     * Ezután a cső állapota lyukas lesz, és a kilyukasztott csőből a víz a homokba folyik.
     *
     * @param p - a cső amelyiket kilyukasztjuk
     */
    public void breakPipe(Pipe p) {
        p.breakPipe();
    }

    /**
     * A játékos ragadóssá teszi a paraméterként kapott csövet. Ha a cső ragadós
     * valamelyik játékos rálép, akkor rövid ideig nem tud a pályelemről továbblépni.
     *
     * @param p - a cső amelyiket ragadossá tesszöl
     */
    public void makeSticky(Pipe p) {
        p.makeSticky();
    }

    /**
     * Metódus, amely visszaadja a játékos pozícióját, azaz azt a mezőt, amin a játékos áll.
     * @return A játékos pozíciója.
     */
    public Field getPosition() {
        return position;
    }
}
