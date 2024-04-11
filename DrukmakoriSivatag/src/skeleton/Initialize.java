package skeleton;

/**
 * Az inicializálásokért felelős osztály.
 */
public class Initialize {
    /**
     * Jelzi az inicializáló blokk elejét
     */
    private static void logInitBegin() {
        System.out.println("--Initialization: ---------------------------");
    }

    /**
     * Jelzi a szekvencia futásának kezdetét
     */
    private static void logSequenceBegin() {
        System.out.println("--Test case sequence: ---------------------------");
    }

    /**
     * A Connect Pipe teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initConnectPipe() {
        logInitBegin();
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "pipe");
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "mechanic");
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");

        logSequenceBegin();
        m.connectPipe(pipe, pump);

        Skeleton.names.clear();
    }

    /**
     * A Disconnect Pipe teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initDisconnectPipe() {
        logInitBegin();
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "pipe");
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");
        Cistern c = new Cistern();
        Skeleton.names.put(c, "c");

        pipe.connect(pump);
        pipe.connect(c);
        pump.connect(pipe);
        c.connect(pipe);

        logSequenceBegin();
        m.disconnectPipe(pipe, pump);

        Skeleton.names.clear();
    }

    /**
     * A Fix Pipe teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initFixPipe() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pipe p = new Pipe();
        Skeleton.names.put(p, "p");

        m.moveTo(p);

        logSequenceBegin();
        m.fixPipe(p);

        Skeleton.names.clear();
    }

    /**
     * A Place Pipe teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initPlacePipe() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "pipe");
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");

        m.moveTo(pump);
        m.setPipe(pipe);

        logSequenceBegin();
        m.placePipe(pump);

        Skeleton.names.clear();
    }

    /**
     * A Pickup Pipe teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    static public void initPickupPipe() {
        logInitBegin();
        Mechanic mechanic = new Mechanic();
        Skeleton.names.put(mechanic, "mechanic");
        Cistern cistern = new Cistern();
        Skeleton.names.put(cistern, "cistern");

        mechanic.moveTo(cistern);
        cistern.tick();

        logSequenceBegin();
        mechanic.pickupPipe();

        Skeleton.names.clear();
    }

    /**
     * A Puncture Pipe teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initPuncturePipe() {
        logInitBegin();
        Saboteur s = new Saboteur();
        Skeleton.names.put(s, "s");
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "pipe");

        s.moveTo(pipe);

        logSequenceBegin();
        s.breakPipe(pipe);

        Skeleton.names.clear();
    }

    /**
     * A Fix Pump teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initFixPump() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pump p = new Pump();
        Skeleton.names.put(p, "p");

        m.moveTo(p);

        logSequenceBegin();
        m.fixPump(p);

        Skeleton.names.clear();
    }

    /**
     * A Place Pump teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initPlacePump() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "pipe");
        Pump end0 = new Pump();
        Skeleton.names.put(end0, "ends[0]");
        Pump end1 = new Pump();
        Skeleton.names.put(end1, "ends[1]");
        pipe.connect(end0);
        end0.connect(pipe);
        pipe.connect(end1);
        end1.connect(pipe);

        m.setPump(pump);
        m.moveTo(pipe);

        logSequenceBegin();
        m.placePump(pump, pipe);

        Skeleton.names.clear();
    }

    /**
     * A Mechanic Sets Pump teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    public static void initMechanicSetsPump() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");
        Pipe to = new Pipe();
        Skeleton.names.put(to, "to");
        Pipe from = new Pipe();
        Skeleton.names.put(from, "from");

        m.moveTo(pump);

        logSequenceBegin();
        m.setPumpDirection(pump, from, to);

        Skeleton.names.clear();
    }

    /**
     * A Saboteur Sets Pump teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    public static void initSaboteurSetsPump() {
        logInitBegin();
        Saboteur s = new Saboteur();
        Skeleton.names.put(s, "s");
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");
        Pipe to = new Pipe();
        Skeleton.names.put(to, "to");
        Pipe from = new Pipe();
        Skeleton.names.put(from, "from");

        s.moveTo(pump);

        logSequenceBegin();
        s.setPumpDirection(pump, from, to);

        Skeleton.names.clear();
    }

    /**
     * A Take Pump teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initTakePump() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Cistern c = new Cistern();
        Skeleton.names.put(c, "c");

        m.moveTo(c);

        logSequenceBegin();
        m.pickupPump();

        Skeleton.names.clear();
    }

    /**
     * A Move To Pipe From Pump teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    static public void initMoveToPipeFromPump() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pump pos = new Pump();
        Skeleton.names.put(pos, "pos");
        Pipe dest = new Pipe();
        Skeleton.names.put(dest, "dest");

        dest.connect(pos);
        pos.connect(dest);

        m.moveTo(pos);

        logSequenceBegin();
        m.moveTo(dest);

        Skeleton.names.clear();
    }

    /**
     * A Move To Pump From Pipe teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    static public void initMovetoPumpFromPipe() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pipe pos = new Pipe();
        Skeleton.names.put(pos, "pos");
        Pump dest = new Pump();
        Skeleton.names.put(dest, "dest");

        dest.connect(pos);
        pos.connect(dest);
        m.moveTo(pos);

        logSequenceBegin();
        m.moveTo(dest);

        Skeleton.names.clear();
    }

    /**
     * A Move To Cistern From Pipe teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    static public void initMoveToCisternFromPipe() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pipe pos = new Pipe();
        Skeleton.names.put(pos, "pos");
        Cistern dest = new Cistern();
        Skeleton.names.put(dest, "dest");

        dest.connect(pos);
        pos.connect(dest);
        m.moveTo(pos);

        logSequenceBegin();
        m.moveTo(dest);

        Skeleton.names.clear();
    }

    /**
     * A Move To Spring From Pipe teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    static public void initMoveToSpringFromPipe() {
        logInitBegin();
        Mechanic m = new Mechanic();
        Skeleton.names.put(m, "m");
        Pipe pos = new Pipe();
        Skeleton.names.put(pos, "pos");
        Spring dest = new Spring();
        Skeleton.names.put(dest, "dest");

        dest.connect(pos);
        pos.connect(dest);
        m.moveTo(pos);

        logSequenceBegin();
        m.moveTo(dest);

        Skeleton.names.clear();
    }

    /**
     * A Network Branch Filled Up teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    static public void initNetworkBranchFilledUp() {
        logInitBegin();
        Spring spring = new Spring();
        Skeleton.names.put(spring, "spring");
        Pipe pipe1 = new Pipe();
        Skeleton.names.put(pipe1, "pipe1");
        pipe1.connect(spring);
        spring.connect(pipe1);

        Pump pump1 = new Pump();
        Skeleton.names.put(pump1, "pump1");
        pipe1.connect(pump1);
        pump1.connect(pipe1);
        Pipe pipe2 = new Pipe();
        Skeleton.names.put(pipe2, "pipe2");
        pipe2.connect(pump1);
        pump1.connect(pipe2);

        Pump pump2 = new Pump();
        Skeleton.names.put(pump2, "pump2");
        pipe2.connect(pump2);
        pump2.connect(pipe2);

        pump1.changeFlow(pipe1, pipe2);
        pump2.changeFlow(pipe2, null);
        Integer volume = Skeleton.numberQuestion("Enter the max volume of the pumps");
        Skeleton.names.put(volume, "volume");
        pump2.addVolume(volume);
        pump1.addVolume(volume);

        spring.tick();
        pump1.tick();
        Integer maxVolume = Skeleton.numberQuestion("Enter the max volume of the pipes");
        Skeleton.names.put(maxVolume, "maxVolume");
        pipe2.setWaterVolume(maxVolume);
        pipe1.setWaterVolume(maxVolume);

        logSequenceBegin();
        spring.tick();
        pump1.tick();
        pump2.tick();

        Skeleton.names.clear();
    }

    /**
     * A Pumping Into Broken Pump teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    static public void initPumpingIntoBrokenPump() {
        logInitBegin();
        Spring spring = new Spring();
        Skeleton.names.put(spring, "spring");
        Pipe pipe1 = new Pipe();
        Skeleton.names.put(pipe1, "pipe1");
        pipe1.connect(spring);
        spring.connect(pipe1);

        Pump pump1 = new Pump();
        Skeleton.names.put(pump1, "pump1");
        pipe1.connect(pump1);
        pump1.connect(pipe1);
        Pipe pipe2 = new Pipe();
        Skeleton.names.put(pipe2, "pipe2");
        pipe2.connect(pump1);
        pump1.connect(pipe2);
        Pump pump2 = new Pump();
        Skeleton.names.put(pump2, "pump2");
        pipe2.connect(pump2);
        pump2.connect(pipe2);

        pump1.changeFlow(pipe1, pipe2);
        pump2.changeFlow(pipe2, null);

        pump2.breakPump();

        logSequenceBegin();
        spring.tick();
        pump1.tick();
        pump2.tick();

        Skeleton.names.clear();
    }

    /**
     * A Water Flows From Spring To Pipe teszteset kommunikációs diagramnak
     * megfelelő inicializáló függvénye.
     */
    public static void initWaterFlowsFromSpingToPipe() {
        logInitBegin();
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "p");

        Spring spring = new Spring();
        Skeleton.names.put(spring, "s");

        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");

        pipe.connect(spring);
        spring.connect(pipe);

        pump.connect(pipe);
        pipe.connect(pump);

        logSequenceBegin();
        spring.tick();

        Skeleton.names.clear();
    }

    /**
     * A Cistern Drains Water teszteset kommunikációs diagramnak megfelelő
     * inicializáló függvénye.
     */
    public static void initCisternDrainsWater() {
        logInitBegin();
        Pipe pipe = new Pipe();
        Skeleton.names.put(pipe, "pipe");

        Cistern cistern = new Cistern();
        Skeleton.names.put(cistern, "cistern");

        pipe.connect(cistern);
        cistern.connect(pipe);

        Integer amount = Skeleton.numberQuestion("Enter the amount of water you want in the pipe");
        Skeleton.names.put(amount, "amount");
        pipe.setWaterVolume(amount);

        logSequenceBegin();
        cistern.tick();

        Skeleton.names.clear();
    }

    /**
     * A Pump Pumps teszteset kommunikációs diagramnak megfelelő inicializáló
     * függvénye.
     */
    public static void initPumpPumps() {
        logInitBegin();
        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");

        Spring spring = new Spring();
        Skeleton.names.put(spring, "spring");

        Pipe pipe2 = new Pipe(), pipe1 = new Pipe();
        Skeleton.names.put(pipe2, "pipe2");
        Skeleton.names.put(pipe1, "pipe1");

        Cistern cistern = new Cistern();
        Skeleton.names.put(cistern, "cistern");

        pump.connect(pipe1);
        pump.connect(pipe2);

        pipe2.connect(pump);
        pipe2.connect(spring);

        pipe1.connect(pump);
        pipe1.connect(cistern);

        cistern.connect(pipe1);

        spring.connect(pipe2);
        spring.tick();

        pump.changeFlow(pipe2, pipe1);

        logSequenceBegin();
        pump.tick();

        Skeleton.names.clear();
    }

    /**
     * A Water Leaks From Punctured Pipe teszteset kommunikációs diagramnak
     * megfelelő inicializáló függvénye.
     */
    public static void initWaterLeaksFromPuncturedPipe() {
        logInitBegin();
        Pipe puncturedPipe = new Pipe();
        Skeleton.names.put(puncturedPipe, "puncturedPipe");

        Spring spring = new Spring();
        Skeleton.names.put(spring, "s");

        Pump pump = new Pump();
        Skeleton.names.put(pump, "pump");

        puncturedPipe.connect(spring);
        spring.connect(puncturedPipe);

        pump.connect(puncturedPipe);
        puncturedPipe.connect(pump);

        puncturedPipe.breakPipe();

        logSequenceBegin();
        spring.tick();

        Skeleton.names.clear();
    }

    /**
     * A Water Leaks From Pipe With Free End teszteset kommunikációs diagramnak
     * megfelelő inicializáló függvénye.
     */
    public static void initWaterLeaksFromPipeWithFreeEnd() {
        logInitBegin();
        Pipe puncturedPipe = new Pipe();
        Skeleton.names.put(puncturedPipe, "pipe");

        Spring spring = new Spring();
        Skeleton.names.put(spring, "s");

        puncturedPipe.connect(spring);
        spring.connect(puncturedPipe);

        logSequenceBegin();
        spring.tick();

        Skeleton.names.clear();
    }
}
