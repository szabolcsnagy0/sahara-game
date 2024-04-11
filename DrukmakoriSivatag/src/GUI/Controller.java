package GUI;

import proto.*;
import proto.Spring;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * A játék irányításáért, a játék logikája és a megjelenítés közti kommunikáció biztosításáért felelős osztály.
 */
public class Controller {
    /**
     * A játék megjelenítéséhez használt fájlok elérési útja.
     */
    public static String assetsPath = "drukmakor_assets/";
    /**
     * A játék irányításáért felelős osztály egyetlen példánya.
     */
    public static Controller instance = new Controller();
    /**
     * A játék megjelenítéséért felelős ablak.
     */
    public Window window = new Window();
    /**
     * A pontszám, amely elérése szükséges a játék megnyeréséért.
     */
    public static int MAX_SCORE = 100;
    /**
     * A játékban résztvevő játékosokat tároló hash map.
     */
    public HashMap<Player, Viewable> players = new HashMap<>();
    /**
     * A játékban résztvevő pályaelemeket tároló hash map.
     */
    public HashMap<Field, Viewable> fields = new HashMap<>();
    /**
     * A játékban aktuálisan kiválasztott játékos.
     */
    public Player selectedPlayer = null;
    /**
     * A játékban aktuálisan kiválasztott pályaelemek listája.
     */
    public List<Field> selectedFields = new ArrayList<>();
    /**
     * Szinkronizációs objektum a szálbiztos működéshez.
     */
    private final Object syncObject = new Object();

    /**
     * Játékos kiválasztása és a játékoshoz tartozó menü megjelenítése.
     * A játékos kiválasztásával együtt a korábban kiválasztott elemek kijelölését is megszünteti.
     *
     * @param selected A kiválasztott játékos.
     */
    public void selectPlayer(Player selected) {
        Player prev = selectedPlayer;
        selectedPlayer = selected;
        if (prev != null)
            players.get(prev).update();
        players.get(selectedPlayer).update();

        // Update the fields that have been deselected
        Object[] shouldUpdate = selectedFields.toArray();
        selectedFields.clear();
        for (Object field : shouldUpdate)
            fields.get(field).update();

        window.updateMenu();
    }

    /**
     * Pályaelem kiválasztása. A kiválasztott pályaelem nézetének frissítése.
     * A menü frissítése.
     *
     * @param selected A kiválasztott pályaelem.
     */
    public void selectField(Field selected) {
        boolean removed = selectedFields.remove(selected);
        if (!removed) {
            selectedFields.add(selected);
        }
        fields.get(selected).update();
        window.updateMenu();
    }

    /**
     * Egy cső eltörése.
     */
    public void breakPipe() {
        synchronized (syncObject) {
            Pipe pipe = (Pipe) selectedPlayer.getPosition();
            selectedPlayer.breakPipe(pipe);
            endAction();
        }
    }

    /**
     * Egy cső megjavítása.
     */
    public void fixPipe() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            mechanic.fixPipe((Pipe) mechanic.getPosition());
            endAction();
        }
    }

    /**
     * Egy pumpa megjavítása.
     */
    public void fixPump() {
        synchronized (syncObject) {
            if (selectedPlayer == null)
                return;
            Pump pump = (Pump) selectedPlayer.getPosition();
            ((Mechanic) selectedPlayer).fixPump(pump);
            endAction();
        }
    }

    /**
     * Egy játékos mozgatása.
     */
    public void movePlayer() {
        synchronized (syncObject) {
            selectedPlayer.moveTo(selectedFields.get(0));
            endAction();
        }
    }

    /**
     * A pumpa pumpálási/folyatási irányának megváltoztatása.
     */
    public void changeFlow() {
        synchronized (syncObject) {
            Pump pump = (Pump) selectedPlayer.getPosition();
            selectedPlayer.setPumpDirection(pump, (Pipe) selectedFields.get(0), (Pipe) selectedFields.get(1));
            endAction();
        }
    }

    /**
     * Egy cső ragadóssá tétele.
     */
    public void makeSticky() {
        synchronized (syncObject) {
            Pipe pipe = (Pipe) selectedPlayer.getPosition();
            selectedPlayer.makeSticky(pipe);
            endAction();
        }
    }

    /**
     * Egy cső csúszóssá tétele.
     */
    public void makeSlippery() {
        synchronized (syncObject) {
            Saboteur saboteur = (Saboteur) selectedPlayer;
            saboteur.makeSlippery((Pipe) saboteur.getPosition());
            endAction();
        }
    }

    /**
     * A cső egy végének felcsatlakoztatása a kiválasztott pályaelemre.
     */
    public void connectPipe() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            try {
                mechanic.connectPipe((Pipe) selectedPlayer.getPosition(), (FieldNode) selectedFields.get(0));
            } catch (Exception e) {
            }
            endAction();
        }
    }

    /**
     * A cső egy végének lecsatlakoztatása a kiválasztott pályaelemről.
     */
    public void disconnectPipe() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            mechanic.disconnectPipe((Pipe) selectedPlayer.getPosition(), (FieldNode) selectedFields.get(0));
            endAction();
        }
    }

    /**
     * Egy pumpa felvétele a játékos eszköztárába.
     */
    public void pickupPump() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            mechanic.pickupPump();
            endAction();
        }
    }

    /**
     * Egy cső felvétele a játékos eszköztárába.
     */
    public void pickupPipe() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            mechanic.pickupPipe();
            endAction();
        }
    }

    /**
     * Egy pumpa lehelyezése a pályán.
     */
    public void placePump() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            Pipe pipe = (Pipe) mechanic.getPosition();
            Pump pump = mechanic.getPump();
            Pipe newPipe = mechanic.placePump(pump, pipe);
            if (newPipe != null) {
                ((PipeView) fields.get(pipe)).setWasCut(true);
                PumpView pumpView = new PumpView(new Point(fields.get(pipe).getPosition()), pump);
                addField(pump, pumpView);
                PipeView newPipeView = new PipeView(newPipe);
                addField(newPipe, newPipeView);
            }
            endAction();
        }
    }

    /**
     * Egy cső lehelyezése a pályán.
     */
    public void placePipe() {
        synchronized (syncObject) {
            Mechanic mechanic = (Mechanic) selectedPlayer;
            Pipe pipe = mechanic.getPipe();
            mechanic.placePipe((FieldNode) mechanic.getPosition());
            PipeView pipeView = new PipeView(pipe);
            addField(pipe, pipeView);
            endAction();
        }
    }

    /**
     * A pályaelemek tickelése, azaz az idő múlásával járó változások végrehajtása.
     */
    public void tick() {
        // Tick all pipes
        for (Field field : fields.keySet()) {
            if (field instanceof Pipe)
                ((Pipe) field).tick();
        }
        // Contains the fields that we need to tick
        Queue<FieldNode> toSee = new LinkedList<>();

        // Gets the springs and adds them to be the first fields that get ticked
        for (Field field : fields.keySet()) {
            if (field instanceof Spring) {
                toSee.add((FieldNode) field);
            }
        }

        // Contains all of the fields that have
        // been ticked so we don't tick a field twice
        Set<FieldNode> ticked = new HashSet<>();
        while (0 < toSee.size()) {
            FieldNode node = toSee.poll();
            node.tick();
            ticked.add(node);
            for (FieldNode neighbour : node.getConnectedNodes()) {
                if (!ticked.contains(neighbour)) {
                    toSee.add(neighbour);
                }
            }
        }

        window.checkGameEnded();
    }

    /**
     * Egy új játékelem hozzáadása a játékhoz.
     *
     * @param field a pályaelem
     * @param view  a pályaelem nézete
     */
    public void addField(Field field, Viewable view) {
        fields.put(field, view);
        window.addViewable(view);
    }

    /**
     * Egy új játékos hozzáadása a játékhoz.
     *
     * @param player a játékos
     * @param view   a játkos nézete
     */
    public void addPlayer(Player player, Viewable view) {
        players.put(player, view);
        window.addViewable(view);
    }

    /**
     * A Szerelők csapat pontjainak összegzése.
     *
     * @return a szerelők csapatának pontszáma
     */
    public int getMechanicScore() {
        int sum = 0;
        for (Field field : fields.keySet()) {
            if (field instanceof Cistern) {
                sum += ((Cistern) field).getDrainedWater();
            }
        }
        return sum / 1000;
    }

    /**
     * A Szabotőrök csapat pontjainak összegzése
     *
     * @return a szabotőrök csapatának pontszáma
     */
    public int getSaboteurScore() {
        int sum = 0;
        for (Field field : fields.keySet()) {
            if (field instanceof Pipe) {
                sum += ((Pipe) field).getWastedWater();
            }
        }
        return sum / 1000;
    }

    /**
     * Egy -egy játékban megtörtént akció-esemény után lefuttatandó metódus.
     * A kiválasztott játékos és pályaelemek törlése, a nézetek frissítése.
     */
    private void endAction() {
        selectedPlayer = null;
        selectedFields.clear();
        window.updateAllViews();
        window.updateMenu();
    }

    /**
     * A játék kezdőpályájának felépítése.
     */
    public void initModel() {
        // m - model, v - view
        Spring mSpring = new Spring();
        Cistern mCistern = new Cistern();

        Pipe mPipe1 = new Pipe();
        Pipe mPipe2 = new Pipe();
        Pipe mPipe3 = new Pipe();
        Pipe mPipe4 = new Pipe();
        Pipe mPipe5 = new Pipe();

        Pump mPump1 = new Pump();
        Pump mPump2 = new Pump();
        Pump mPump3 = new Pump();

        mPipe1.connect(mSpring);
        mPipe1.connect(mPump2);
        mSpring.connect(mPipe1);
        mPump2.connect(mPipe1);

        mPipe2.connect(mPump1);
        mPipe2.connect(mPump2);
        mPump1.connect(mPipe2);
        mPump2.connect(mPipe2);

        mPipe3.connect(mPump2);
        mPipe3.connect(mPump3);
        mPump2.connect(mPipe3);
        mPump3.connect(mPipe3);

        mPipe4.connect(mPump1);
        mPipe4.connect(mCistern);
        mPump1.connect(mPipe4);
        mCistern.connect(mPipe4);

        mPipe5.connect(mPump3);
        mPipe5.connect(mCistern);
        mPump3.connect(mPipe5);
        mCistern.connect(mPipe5);

        PumpView vPump1 = new PumpView(new Point(150, Window.HEIGHT / 3), mPump1);
        PumpView vPump2 = new PumpView(new Point(Window.WIDTH / 2 - 25, Window.HEIGHT / 3), mPump2);
        PumpView vPump3 = new PumpView(new Point(Window.WIDTH - 200, Window.HEIGHT / 3), mPump3);
        addField(mPump1, vPump1);
        addField(mPump2, vPump2);
        addField(mPump3, vPump3);

        SpringView vSpring = new SpringView(new Point(Window.WIDTH / 2 - 25, 30), mSpring);
        CisternView vCistern = new CisternView(new Point(Window.WIDTH / 2 - 25, 500), mCistern);
        addField(mSpring, vSpring);
        addField(mCistern, vCistern);

        PipeView vPipe1 = new PipeView(mPipe1);
        addField(mPipe1, vPipe1);
        PipeView vPipe2 = new PipeView(mPipe2);
        addField(mPipe2, vPipe2);
        PipeView vPipe3 = new PipeView(mPipe3);
        addField(mPipe3, vPipe3);
        PipeView vPipe4 = new PipeView(mPipe4);
        addField(mPipe4, vPipe4);
        PipeView vPipe5 = new PipeView(mPipe5);
        addField(mPipe5, vPipe5);

        Mechanic mMech1 = new Mechanic();
        mMech1.moveTo(mPump1);
        MechanicView vMech1 = new MechanicView(mMech1);
        addPlayer(mMech1, vMech1);

        Mechanic mMech2 = new Mechanic();
        mMech2.moveTo(mPump1);
        MechanicView vMech2 = new MechanicView(mMech2);
        addPlayer(mMech2, vMech2);

        Saboteur mSab1 = new Saboteur();
        mSab1.moveTo(mPump3);
        SaboteurView vSab1 = new SaboteurView(mSab1);
        addPlayer(mSab1, vSab1);

        Saboteur mSab2 = new Saboteur();
        mSab2.moveTo(mPump3);
        SaboteurView vSab2 = new SaboteurView(mSab2);
        addPlayer(mSab2, vSab2);
    }

    /**
     * Az alkalmazás belépési pontja és a fővezérlési logika kezdőpontja.
     * <p>
     * Inicializálja a Model-t, majd elindít egy új szálat, ami ciklikusan végrehajtja a vezérlő cselekvéseket.
     * A cselekvések között frissíti a nézeteket, majd vár egy másodpercet, mielőtt újra futna.
     */
    public static void main(String args[]) {
        Controller.instance.initModel();
        new Thread(() -> {
            try {
                while (true) {
                    synchronized (Controller.instance.syncObject) {
                        Controller.instance.tick();
                    }
                    Controller.instance.window.updateAllViews();
                    Controller.instance.window.updateMenu();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
