package skeleton;

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
        this.pipe = null;
        this.pump = null;
        Skeleton.callFunction(this, "create", null);
        Skeleton.endFunction();
    }

    /**
     * A szerelő megjavít egy kilyukasztott csövet.
     *
     * @param p - A megjavítandó cső.
     */
    public void fixPipe(Pipe p) {
        Skeleton.callFunction(this, "fixPipe", new Object[] { p });
        p.repair();
        Skeleton.endFunction();
    }

    /**
     * A szerelő megjavít egy meghibásodott pumpát.
     *
     * @param p - A megjavítandó pumpa.
     */
    public void fixPump(Pump p) {
        Skeleton.callFunction(this, "fixPump", new Object[] { p });
        p.repair();
        Skeleton.endFunction();
    }

    /**
     * A szerelő felvesz egy csövet.
     */
    public void placePipe(FieldNode n) {
        Skeleton.callFunction(this, "placePipe", new Object[] { n });
        boolean hasPipe = Skeleton.yesNoQuestion("Does he have a pipe?");
        if (hasPipe) {
            pipe.connect(n);
            n.connect(pipe);
        }
        Skeleton.endFunction();
    }

    /**
     * Csatlakoztatja a paraméterként kapott csövet, a szintén paraméterként kapott
     * FieldNode csomóponthoz.
     *
     * @param p Csatlakoztatni kívánt cső.
     * @param n Csomópont, ahova a csövet csatlakoztatjuk.
     */
    public void connectPipe(Pipe p, FieldNode n) {
        Skeleton.callFunction(this, "connectPipe", new Object[] { p, n });
        if (p.connect(n)) {
            n.connect(p);
        }
        Skeleton.endFunction();
    }

    /**
     * A paraméterként kapott FieldNode csomópont és a szintén paraméterként kapott
     * cső közötti kapcsolatot megszünteti.
     *
     * @param p Lecsatlakoztatni kívánt cső.
     * @param n Csomópont, ahonnan a csövet lecsatlakoztatjuk.
     */
    public void disconnectPipe(Pipe p, FieldNode n) {
        Skeleton.callFunction(this, "disconnectPipe", new Object[] { p, n });
        if (p.disconnect(n)) {
            n.disconnect(p);
        }
        Skeleton.endFunction();
    }

    /**
     * A szerelő felvesz egy pumpát ha van a mezőn, valamint előzőleg nem
     * rendelkezett pumpával.
     * Ez a pumpa a szerelő eszköztárába kerül.
     */
    public void pickupPump() {
        Skeleton.callFunction(this, "pickupPump", null);
        boolean hasPump = Skeleton.yesNoQuestion("Does he have a pump?");
        if (hasPump == true) {
            Skeleton.endFunction();
            return;
        }
        Pump newPump = position.takePump();
        if (newPump == null) {
            Skeleton.endFunction();
            return;
        }
        setPump(newPump);
        Skeleton.endFunction();
    }

    /**
     * A szerelő letesz egy pumpát, amely az eszköztárában volt eddig, amennyiben
     * előzőleg rendelkezett pumpával.
     * A csőre letett pumpa a csövet kettéválasztja, egy új cső létrejön és a
     * megfelelő végeit a régi és új csőnek csatlakoztatja a pumpához.
     * Ezután az eszköztára kiürül és felvehet egy más pumpát az elkövetkezendő
     * körökben.
     *
     * @param pipe Ide helyezi a pumpát. Ez vágódik ketté.
     */
    public void placePump(Pump pump, Pipe pipe) {
        Skeleton.callFunction(this, "placePump", new Object[] { pump, pipe });
        Pipe newPipe = pipe.cut();
        if (newPipe == null) {
            Skeleton.endFunction();
            return;
        }
        pipe.connect(pump);
        newPipe.connect(pump);
        pump.connect(pipe);
        pump.connect(newPipe);
        setPump(null);
        Skeleton.endFunction();
    }

    /**
     * Beállítja a paraméterként kapott pumpát a szerelő pumpájává.
     *
     * @param pump Az új pumpa
     */
    public void setPump(Pump pump) {
        Skeleton.callFunction(this, "setPump", new Object[] { pump });
        this.pump = pump;
        Skeleton.endFunction();
    }

    /**
     * Beállítja a paraméterként kapott csövet a szerelő csöveként.
     *
     * @param pipe Az új cső
     */
    public void setPipe(Pipe pipe) {
        Skeleton.callFunction(this, "setPipe", new Object[] { pipe });
        this.pipe = pipe;
        Skeleton.endFunction();
    }

    /**
     * A szerelő felvesz egy csövet ha van a mezőn, valamint előzőleg nem
     * rendelkezett csővel.
     * Ez a cső a szerelő eszköztárába kerül.
     */
    public void pickupPipe() {
        Skeleton.callFunction(this, "pickupPipe", null);
        boolean hasPipe = Skeleton.yesNoQuestion("Does the mechanic have a pipe?");
        if (hasPipe) {
            Skeleton.endFunction();
            return;
        }

        Pipe newPipe = this.position.takePipe();
        if (newPipe != null) {
            setPipe(newPipe);
        }
        Skeleton.endFunction();
    }
}