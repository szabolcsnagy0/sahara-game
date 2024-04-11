package skeleton;

/**
 * A ciszternákért felelős osztály
 */
public class Cistern extends FieldNode implements Tickable{
    /**
     * Konstruktor
     * A ciszternába befolyt vizet (drainedWater) 0-ra inicalizálja.
     * A cső elérhatőségét (pumpAvailable) hamisra állítja. Csövet nem vehet fel a Szerelő.
     */
    public Cistern(){
        Skeleton.callFunction(this, "create", null);
        Skeleton.endFunction();
    }

    /**
     * Függvény, ami a ciszternába folyt vizet növeli a paraméterként átadott értékkel.
     * @param amount az az int mennyiség, amivel növeli a ciszternába folyt vizet.
     */
    public void addDrainedWater(Integer amount) {
        Integer drainedAmount = Integer.valueOf(amount);
        Skeleton.names.put(drainedAmount, "drainedAmount");
        Skeleton.callFunction(this, "addDrainedWater", new Object[] { drainedAmount });
        Skeleton.endFunction();
    }

    /**
     * Egy cső felvevése a mezőről, ha nem megy akkor null értékkel tér vissza.
     *
     * @return az elvett cső vagy null
     */
    @Override
    public Pipe takePipe(){
        Skeleton.callFunction(this, "takePipe", null);
        boolean pipeAvailable = Skeleton.yesNoQuestion("Were there available pipes?");
        if (pipeAvailable) {
            Pipe newPipe = new Pipe();
            Skeleton.names.put(newPipe, "newPipe");
            Skeleton.endFunction();
            return newPipe;
        }
        Skeleton.endFunction();
        return null;
    }

    /**
     * Egy pumpa felvevése a mezőről, ha nem megy akkor null értékkel tér vissza.
     *
     * @return az elvett pumpa vagy null
     */
    @Override
    public Pump takePump(){
        Skeleton.callFunction(this, "takePump", null);
        boolean pumpAvailable = Skeleton.yesNoQuestion("Were there available pumps?");
        if (pumpAvailable) {
            Pump newPump = new Pump();
            Skeleton.names.put(newPump, "newPump");
            Skeleton.endFunction();
            return newPump;
        }
        Skeleton.endFunction();
        return null;
    }

    /**
     * Függvény, ami lekérdezi a ciszternába folyt víz mennyiségét.
     * @return a ciszternába folyt víz mennyisége.
     */
    public int getDrainedWater(){
        Skeleton.callFunction(this, "getDrainedWater()", null);
        Skeleton.endFunction();
        return 0;
    }

    /**
     * Egy időegység elteltét jelenti.
     * Az időegységnek megfelelő mennyiségű víz elfolyik a ciszternához kapcsolt csővekből a ciszternába.
     * Ezzel a mennyiséggel nő a cisztárnába befolyt víz mennyisége.
     */
    @Override
    public void tick() {
        Skeleton.callFunction(this, "tick", null);
        Integer volume = Skeleton.numberQuestion("How much would you like to drain?");
        Skeleton.names.put(volume, "volume");
        for (Pipe pipe : pipes) {
            addDrainedWater(pipe.drain(volume));
        }
        Skeleton.endFunction();
    }
}
