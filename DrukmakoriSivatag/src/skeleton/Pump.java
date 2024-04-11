package skeleton;

/**
 * A Pump osztálya.
 */
public class Pump extends FieldNode implements Tickable {
    /**
     * A pumpa bemeneti csöve
     */
    private Pipe pipeIn;
    /**
     * A pumpa kimeneti csöve
     */
    private Pipe pipeOut;

    /**
     * Konstruktor
     */
    public Pump() {
        Skeleton.callFunction(this, "create", null);
        Skeleton.endFunction();
    }

    /**
     * A pumpa meghibásodik.
     */
    public void breakPump() {
        Skeleton.callFunction(this, "breakPump", null);
        Skeleton.endFunction();
    }

    /**
     * Hozzáad egy adott mennyiségű vizet a pumpa tartájához.
     * 
     * @param amount A hozzáadandó vízmennyiség.
     */
    public void addVolume(Integer amount) {
        Skeleton.callFunction(this, "addVolume", new Object[] { amount });
        Skeleton.endFunction();
    }

    /**
     * Csökkenti a pumpában található vízmennyiséget.
     * 
     * @param amount A csökkenteni kívánt mennyiség.
     */
    public void decreaseVolume(Integer amount) {
        Skeleton.callFunction(this, "decreaseVolume", new Object[] { amount });
        Skeleton.endFunction();
    }

    /**
     * Átállítja a pumpa ki- és bemenetét.
     * 
     * @param in  Az új bemenet.
     * @param out Az új kimenet.
     */
    public void changeFlow(Pipe in, Pipe out) {
        pipeIn = in;
        pipeOut = out;
        Skeleton.callFunction(this, "changeFlow", new Object[] { in, out });
        Skeleton.endFunction();
    }

    /**
     * Amennyiben a pumpa el volt romolva, megjavul.
     */
    public void repair() {
        Skeleton.callFunction(this, "repair", null);
        Skeleton.endFunction();
    }

    /**
     * Egy időegység elteltét jelenti.
     * A pumpa véletlenszerűen elromolhat. Amennyiben a pumpa nincs elromolja, akkor
     * a bemeneti csőből a kimeneti csövőbe pumpál
     * adott mennyiségű vizet.
     */
    public void tick() {
        Skeleton.callFunction(this, "tick", null);
        boolean isBroken = Skeleton.yesNoQuestion("Would you like to break the pump?");
        if (isBroken) {
            breakPump();
            Skeleton.endFunction();
            return;
        }

        Integer amount = Skeleton.numberQuestion("How much do you want to pump?");
        Skeleton.names.put(amount, "amount");
        Integer drainedAmount = 0;
        if (pipeIn != null) {
            drainedAmount = pipeIn.drain(amount);
            Skeleton.names.put(drainedAmount, "drainedAmount");
            addVolume(drainedAmount);
        }

        Integer currentVolume = Integer.valueOf(drainedAmount);
        Skeleton.names.put(currentVolume, "currentVolume");
        if (pipeOut != null) {
            Integer flowAmount = pipeOut.flow(currentVolume);
            Skeleton.names.put(flowAmount, "flowAmount");
            decreaseVolume(flowAmount);
        }

        Skeleton.endFunction();
    }

}
