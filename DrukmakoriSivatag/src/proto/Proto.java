package proto;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Proto {

    private interface Command {
        void execute(String[] args);
    }

    static private HashMap<String, Command> commands = new HashMap<>();

    static public HashMap<String, Object> names = new HashMap<>();

    // Veletlenszeruseg ki- es bekapcsolasara
    static public boolean isRandom = true;

    // Ide ment save end eseten
    static private FileWriter fileName = null;
    static private boolean saveToFile = false;

    /**
     * Megkeresi egy adott objektumhoz tartozo nevet.
     *
     * @param o Az objektum
     * @return A talalt nev, vagy null, ha nem letezik
     */
    public static String findName(Object o) {
        for (Map.Entry<String, Object> entry : names.entrySet()) {
            if (entry.getValue().equals(o)) {
                return entry.getKey();
            }
        }
        return "null";
    }

    private static void initialize() {
        commands.put("load", args -> {
            // -filename
            try {
                Scanner input = new Scanner(new FileReader(args[1]));
                boolean temp = saveToFile;
                saveToFile = false;
                read(input);
                saveToFile = temp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Fajl mentese
        commands.put("save", args -> {
            if (args[1].equals("start")) {
                // Mentes inditasa
                commands.get("save start").execute(args);
            } else {
                // Mentes vege
                commands.get("save end").execute(args);
            }
        });

        // Mentes inditasa
        commands.put("save start", args -> {
            // -filename
            try {
                fileName = new FileWriter(args[2]);
                saveToFile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Mentes vege
        commands.put("save end", args -> {
            // no args
            if(fileName != null) {
                try {
                    saveToFile = false;
                    fileName.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        commands.put("random", args -> {
            // -on or off
            if (args[1].equals("on")) {
                isRandom = true;
            } else {
                isRandom = false;
            }
        });

        commands.put("add", args -> {
            // -type -name
            switch (args[1]) {
                case "pump": {
                    names.put(args[2], new Pump());
                    break;
                }
                case "pipe": {
                    names.put(args[2], new Pipe());
                    break;
                }

                case "cistern": {
                    names.put(args[2], new Cistern());
                    break;
                }

                case "spring": {
                    names.put(args[2], new Spring());
                    break;
                }

                case "saboteur": {
                    names.put(args[2], new Saboteur());
                    break;
                }

                case "mechanic": {
                    names.put(args[2], new Mechanic());
                    break;
                }

                default:
                    break;
            }
        });

        commands.put("move", args -> {
            // -playername -fieldname
            Player p = (Player) names.get(args[1]);
            Field f = (Field) names.get(args[2]);
            p.moveTo(f);
        });

        commands.put("connect", args -> {
            // -pipename -fieldnodename -mechanicname*
            Pipe p = (Pipe) names.get(args[1]);
            FieldNode f = (FieldNode) names.get(args[2]);
            if (args.length == 4) {
                Mechanic m = (Mechanic) names.get(args[3]);
                m.connectPipe(p, f);
            } else {
                p.connect(f);
                f.connect(p);
            }
        });

        commands.put("setpump", args -> {
            // -pumpname -pipein -pipeout -playername*
            Pump pump = (Pump) names.get(args[1]);
            Pipe pipeIn = (Pipe) names.get(args[2]);
            Pipe pipeOut = (Pipe) names.get(args[3]);
            if (args.length == 5) {
                Player player = (Player) names.get(args[4]);
                player.setPumpDirection(pump, pipeIn, pipeOut);
            } else {
                pump.changeFlow(pipeIn, pipeOut);
            }
        });

        commands.put("puncturepipe", args -> {
            // -pipename -playername*
            Pipe pipe = (Pipe) names.get(args[1]);
            if (args.length == 3) {
                Player player = (Player) names.get(args[2]);
                player.breakPipe(pipe);
            } else {
                pipe.breakPipe();
            }
        });

        commands.put("pickuppipe", args -> {
            // -mechanicname
            Mechanic m = (Mechanic) names.get(args[1]);
            m.pickupPipe();
        });

        commands.put("pickuppump", args -> {
            // -mechanicname
            Mechanic m = (Mechanic) names.get(args[1]);
            m.pickupPump();
        });

        commands.put("placepump", args -> {
            // -mechanicname -pumpname
            Mechanic m = (Mechanic) names.get(args[1]);
            Pump p = m.getPump();
            names.put(args[2], p);
            m.placePump(p, (Pipe) m.position);
        });

        commands.put("placepipe", args -> {
            // -mechanicname -nodename -pipename
            Mechanic m = (Mechanic) names.get(args[1]);
            FieldNode f = (FieldNode) names.get(args[2]);
            Pipe p = m.getPipe();
            names.put(args[3], p);
            m.placePipe(f);
        });

        commands.put("fixpipe", args -> {
            // -pipename -mechanicname*
            Pipe pipe = (Pipe) names.get(args[1]);
            if(args.length == 3) {
                Mechanic m = (Mechanic) names.get(args[2]);
                m.fixPipe(pipe);
            }
            else {
                pipe.repair();
            }
        });

        commands.put("fixpump", args -> {
            // -pumpname -mechanicname*
            Pump pump = (Pump) names.get(args[1]);
            if(args.length == 3) {
                Mechanic m = (Mechanic) names.get(args[2]);
                m.fixPump(pump);
            }
            else {
                pump.repair();
            }
        });

        commands.put("breakpump", args -> {
            // -pumpname
            Pump pump = (Pump) names.get(args[1]);
            pump.breakPump();
        });

        commands.put("tick", args -> {
            // -name
            Tickable t = (Tickable) names.get(args[1]);
            t.tick();
        });

        commands.put("state", args -> {
            // -name*
            if(args.length == 2) {
                System.out.println(names.get(args[1]));
            }
            else {
                names.forEach((s, o) -> System.out.println(o));
            }
            System.out.println();
        });

        commands.put("makeslippery", args -> {
            // -pipename -saboteurname*
            Pipe pipe = (Pipe) names.get(args[1]);
            if(args.length == 3) {
                Saboteur s = (Saboteur) names.get(args[2]);
                s.makeSlippery(pipe);
            }
            else {
                pipe.makeSlippery();
            }
        });

        commands.put("makesticky", args -> {
            // -pipename -playername*
            Pipe pipe = (Pipe) names.get(args[1]);
            if(args.length == 3) {
                Player player = (Player) names.get(args[2]);
                player.makeSticky(pipe);
            }
            else {
                pipe.makeSticky();
            }
        });

        commands.put("setcurrentvolume", args -> {
            // -name -watervolume
            Object o = names.get(args[1]);
            Integer v = Integer.parseInt(args[2]);
            if(o.getClass().equals(Pipe.class)) {
                ((Pipe) o).setWaterVolume(v);
            }
            else {
                ((Pump) o).setWaterVolume(v);
            }
        });

        commands.put("setmaxvolume", args -> {
            // -name -watervolume
            Object o = names.get(args[1]);
            Integer v = Integer.parseInt(args[2]);
            if(o.getClass().equals(Pipe.class)) {
                ((Pipe) o).setMaxVolume(v);
            }
            else {
                ((Pump) o).setMaxVolume(v);
            }
        });

        commands.put("disconnect", args -> {
            // -pipename -fieldnodename -mechanicname*
            Pipe p = (Pipe) names.get(args[1]);
            FieldNode n = (FieldNode) names.get(args[2]);
            if(args.length == 4) {
                Mechanic m = (Mechanic) names.get(args[3]);
                m.disconnectPipe(p, n);
            }
            else {
                p.disconnect(n);
                n.disconnect(p);
            }
        });
    }

    public static void read(Scanner scanner) {
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] next = line.split(" ");
            commands.get(next[0]).execute(next);

            // Elmentjuk fajlba, ha szukseges
            if(saveToFile && (next.length < 2 || !next[1].equals("start"))) {
                try {
                    fileName.write(line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        initialize();
        read(new Scanner(System.in));
    }
}
