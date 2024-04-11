package skeleton;

/**
 * A forrásokért felelős osztály
 * A rendszert, pontosabban a belőle kivezető csöveket látja el vízzel
 */
public class Spring extends FieldNode implements Tickable {

    /**
     * Konstruktor
     */
    public Spring() {
        Skeleton.callFunction(this, "create", null);
        Skeleton.endFunction();
    }

    /**
     * Egy időegység elteltét jelenti.
     * A forráshoz kapcsolt csövekbe a lehető legtöbb vizet folyatja a forrásból.
     */
    @Override
    public void tick() {
        Skeleton.callFunction(this, "tick", null);
        Integer amount = Skeleton.numberQuestion("How much water do you want to flow");
        Skeleton.names.put(amount, "amount");
        for (Pipe pipe : pipes) {
            pipe.flow(amount);
        }
        Skeleton.endFunction();
    }
}
