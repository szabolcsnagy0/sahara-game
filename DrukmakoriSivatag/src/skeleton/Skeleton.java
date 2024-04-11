package skeleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A program konzolos működéséért felelős osztály.
 */
public class Skeleton {
    /**
     * Jelzi, hogy a konzolra való kiírás során mekkora az aktuális indentálás
     * mértéke.
     */
    static public int indent = 1;

    /**
     * Az aktuális objektumok és nevüket tárolja.
     */
    static public HashMap<Object, String> names = new HashMap<>();
    static private final Scanner scanner = new Scanner(System.in);

    /**
     * Kiírja az aktuális indentálásnak megfelelő tabulátor mennyiséget.
     */
    static private void printIndent() {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
    }

    /**
     * Egy metódus hívását jelzi a konzolon, a megfelelő formázottságban.
     * 
     * @param object       Az objektum, amelynek a metódusát meghívtuk.
     * @param functionName A meghívott metódus neve.
     * @param params       A metódushívás paraméterei.
     */
    static public void callFunction(Object object, String functionName, Object[] params) {
        System.out.print(">");
        // Indentálunk
        printIndent();

        String paramsString = "";
        // Amennyiben vannak paraméterek, azokat is kiírjuk
        if (params != null) {
            // Összefűzzük a metódushívás paramétereit, az objektumokat vesszővel
            // elválasztva
            paramsString = String.join(", ", Arrays.asList(params).stream()
                    .map((param) -> {
                        // Ha a paraméter null, akkor nem írjuk ki a típusát
                        if (param == null)
                            return "[null]";
                        return String.format("[%s: %s]", names.get(param), param.getClass().getName());
                    })
                    .toList());
        }

        // Kiírjuk a metódushívást.
        if (names.get(object) == null) {
            System.out.printf("[%s].%s(%s)\n", object.getClass().getName(), functionName, paramsString);
        } else {
            System.out.printf("[%s: %s].%s(%s)\n", names.get(object), object.getClass().getName(), functionName,
                    paramsString);
        }

        indent++;
    }

    /**
     * Az aktuális függvényhívás befejeződött.
     * Csökkentjük az indentet, és kiírjuk az metódushívás befejeződését jelző
     * karaktert.
     */
    static public void endFunction() {
        indent--;
        printIndent();
        System.out.println("<");
    }

    /**
     * Kiír egy eldöntendő kérdést a konzolra.
     * Addig folytatja a kérdés kiírását, ameddig a felhasználó nem ad egy, a
     * formátumnak megfelelő választ
     * A válaszok az Y vagy N karakterek lehetnek
     * 
     * @param message A kérdés szövege
     * @return false, ha nemmel válaszolt a felhasználó, true, ha igennel.
     */
    static public boolean yesNoQuestion(String message) {
        while (true) {
            System.out.print("+");
            printIndent();
            System.out.print(message + " Y/N: ");
            String input = scanner.nextLine().toLowerCase().trim();
            if (input.equals("y"))
                return true;
            if (input.equals("n"))
                return false;
        }
    }

    /**
     * Kiír egy kérdést a konzolra, amely válaszul egy számot vár.
     * Amennyiben a felhasználó válasza nem egy érvényes szám, a kérdés
     * megismétlődik.
     * 
     * @param message A kérdés szövege
     * @return A felhasználó által beírt szám.
     */
    static public int numberQuestion(String message) {
        while (true) {
            System.out.print("+");
            printIndent();
            System.out.print(message + ": ");
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {

            }
        }
    }

    /**
     * Main függvény
     * 
     * @param args Argumentumok
     */
    public static void main(String[] args) {
        Menu.runMenu(Menu.MAIN_MENU);
    }
}