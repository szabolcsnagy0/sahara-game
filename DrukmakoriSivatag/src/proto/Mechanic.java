package proto;

/**
 * A Mechanic osztálya.
 */
public class Mechanic extends Player {
    /**
     * A szerelőnél levő cső.
     */
    private Pipe pipe;
    /**
     * A szerelőnél levő pumpa.
     */
    private Pump pump;

    /**
     * Konstruktor.
     */
    public Mechanic() {
        super();

        pipe = null;
        pump = null;
    }

    /**
     * A szerelő megjavít egy kilyukasztott csövet.
     *
     * @param p - A megjavítandó cső.
     */
    public void fixPipe(Pipe p) {
        p.repair();
    }

    /**
     * A szerelő megjavít egy meghibásodott pumpát.
     *
     * @param p - A megjavítandó pumpa.
     */
    public void fixPump(Pump p) {
        p.repair();
    }

    /**
     * Csatlakoztatja a paraméterként kapott csövet, a szintén paraméterként kapott
     * FieldNode csomóponthoz.
     *
     * @param p Csatlakoztatni kívánt cső.
     * @param n Csomópont, ahova a csövet csatlakoztatjuk.
     */
    public void connectPipe(Pipe p, FieldNode n) {
        if (p.connect(n)) {
            n.connect(p);
        }
    }

    /**
     * A paraméterként kapott FieldNode csomópont és a szintén paraméterként kapott
     * cső közötti kapcsolatot megszünteti.
     *
     * @param p Lecsatlakoztatni kívánt cső.
     * @param n Csomópont, ahonnan a csövet lecsatlakoztatjuk.
     */
    public void disconnectPipe(Pipe p, FieldNode n) {
        if (p.disconnect(n)) {
            n.disconnect(p);
        }
    }

    /**
     * A szerelő felvesz egy pumpát ha van a mezőn, valamint előzőleg nem
     * rendelkezett pumpával.
     * Ez a pumpa a szerelő eszköztárába kerül.
     */
    public void pickupPump() {
        if (pump != null) {
            return;
        }

        Pump newPump = position.takePump();
        if (newPump == null) {
            return;
        }
        setPump(newPump);
    }

    /**
     * A szerelő felvesz egy csövet ha van a mezőn, valamint előzőleg nem
     * rendelkezett csővel.
     * Ez a cső a szerelő eszköztárába kerül.
     */
    public void pickupPipe() {
        if(pipe != null) {
            return;
        }

        Pipe newPipe = position.takePipe();
        if (newPipe == null)
            return;

        setPipe(newPipe);
    }

    /**
     * A szerelő letesz egy pumpát, amely az eszköztárában volt eddig, amennyiben
     * előzőleg rendelkezett pumpával.
     * A csőre letett pumpa a csövet kettéválasztja, egy új cső létrejön és a
     * megfelelő végeit a régi és új csőnek csatlakoztatja a pumpához.
     * Ezután az eszköztára kiürül és felvehet egy más pumpát az elkövetkezendő
     * körökben.
     *
     * @param pump a pumpa referenciája amit lehelyez
     * @param pipe Ide helyezi a pumpát. Ez vágódik ketté.
     */
    public Pipe placePump(Pump pump, Pipe pipe) {
        Pipe newPipe = pipe.cut();

        if (newPipe == null) {
            return null;
        }

        Proto.names.put("newPipe", newPipe);
        pipe.connect(pump);
        pump.connect(pipe);

        newPipe.connect(pump);
        pump.connect(newPipe);

        setPump(null);
        return newPipe;
    }

    /**
     * A szerelő letesz egy csövet, amely az eszköztárában volt.
     * Egyik végét arra a pumpára köti amelyiken áll, a másik vége szabadon lóg.
     * Ezután az eszköztára kiürül, és ha a ciszternáknál jár az
     * elkövetkezendő körökben, akkor újabb csövet vehet fel.
     *
     * @param n - a csomópont amire ráköti a csövet
     */
    public void placePipe(FieldNode n) {
        if (pipe == null)
            return;

        pipe.connect(n);
        n.connect(pipe);
        setPipe(null);
    }

    /**
     * Beállítja a paraméterként kapott pumpát a szerelő pumpájává.
     *
     * @param pump Az új pumpa
     */
    public void setPump(Pump pump) {
        this.pump = pump;
    }

    /**
     * Visszatéríti a szerelő pumpáját
     *
     * @return A szerelőnél levő pumpa, vagy null, ha üres az eszköztár
     */
    public Pump getPump() {
        return pump;
    }

    /**
     * Beállítja a paraméterként kapott csövet a szerelő csöveként.
     *
     * @param pipe Az új cső
     */
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    /**
     * Visszatéríti a szerelőnél levő csövet.
     *
     * @return A szerelőnél levő cső, vagy null, ha nincs nála cső
     */
    public Pipe getPipe() {
        return this.pipe;
    }

    /**
     * A szerelő string reprezentációját adja vissza.
     * @return A szerelő string reprezentációja.
     */
    @Override
    public String toString() {

        return "Mechanic " +
                Proto.findName(this) +
                " on " +
                Proto.findName(this.position) +
                " has pipe: " +
                (this.pipe == null ? "false" : "true") +
                " has pump: " +
                (this.pump == null ? "false" : "true");
    }
}