package skeleton;

import java.util.Scanner;

/**
 * A Menüt kezelő osztály.
 */
public class Menu {
    interface MenuOption {
        void execute();

        String getName();
    }

    /**
     * A főmenühöz tartozó menüopció
     */
    public static final MenuOption[] MAIN_MENU = {
            /**
             * A Move almenühöz tartozó menüopció.
             */
            new MenuOption() {
                public void execute() {
                    runMenu(MOVE_SUBMENU);
                }

                public String getName() {
                    return "Move";
                }
            },
            /**
             * A Time tick almenühöz tartozó menüopció.
             */
            new MenuOption() {
                public void execute() {
                    runMenu(TIME_TICK_SUBMENU);
                }

                public String getName() {
                    return "Time Tick";
                }
            },
            /**
             * A Pipe actions almenühöz tartozó menüopció.
             */
            new MenuOption() {
                public void execute() {
                    runMenu(PIPE_ACTIONS_SUBMENU);
                }

                public String getName() {
                    return "Pipe actions";
                }
            },
            /**
             * A Pump actions almenühöz tartozó menüopció.
             */
            new MenuOption() {
                public void execute() {
                    runMenu(PUMP_ACTIONS_SUBMENU);
                }

                public String getName() {
                    return "Pump actions";
                }
            }
    };

    /**
     * A Move almenühöz tartozó menüopciókat tartalmazza.
     */
    private static final MenuOption[] MOVE_SUBMENU = {
            /**
             * A Move To Pipe From Pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initMoveToPipeFromPump();
                }

                public String getName() {
                    return "Move to pipe from pump";
                }
            },
            /**
             * A Move To Pump From Pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initMovetoPumpFromPipe();
                }

                public String getName() {
                    return "Move to pump from pipe";
                }
            },
            /**
             * A Move To Cistern From Pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initMoveToCisternFromPipe();
                }

                public String getName() {
                    return "Move to cistern from pipe";
                }
            },
            /**
             * A Move To Spring From Pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initMoveToSpringFromPipe();
                }

                public String getName() {
                    return "Move to spring from pipe";
                }
            }
    };

    /**
     * A Time tick almenühöz tartozó menüopciókat tartalmazza.
     */
    private static final MenuOption[] TIME_TICK_SUBMENU = {
            /**
             * A Water flows menüopció.
             */
            new MenuOption() {
                public void execute() {
                    runMenu(WATER_FLOWS_SUBMENU);
                }

                public String getName() {
                    return "Water flows";
                }
            },
            /**
             * A Pumping into broken pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println("Pumping into broken pump");
                    Initialize.initPumpingIntoBrokenPump();
                }

                public String getName() {
                    return "Pumping into broken pump";
                }
            },
            /**
             * A Network branch filled up menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initNetworkBranchFilledUp();
                }

                public String getName() {
                    return "Network branch filled up";
                }
            }
    };

    /**
     * A Water flows almenühöz tartozó menüopciókat tartalmazza.
     */
    private static final MenuOption[] WATER_FLOWS_SUBMENU = {
            /**
             * A Water flows from spring to pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initWaterFlowsFromSpingToPipe();
                }

                public String getName() {
                    return "Water flows from spring to pipe";
                }
            },
            /**
             * A Cistern drains water menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initCisternDrainsWater();
                }

                public String getName() {
                    return "Cistern drains water";
                }
            },
            /**
             * A Pump pumps menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initPumpPumps();
                }

                public String getName() {
                    return "Pump pumps";
                }
            },
            /**
             * A Water leaks from punctured pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initWaterLeaksFromPuncturedPipe();
                }

                public String getName() {
                    return "Water leaks from punctured pipe";
                }
            },
            /**
             * A Water leaks from pipe with free end menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initWaterLeaksFromPipeWithFreeEnd();
                }

                public String getName() {
                    return "Water leaks from pipe with free end";
                }
            }
    };

    /**
     * A Pipe actions almenühöz tartozó menüopciókat tartalmazza.
     */
    private static final MenuOption[] PIPE_ACTIONS_SUBMENU = {
            /**
             * A Connect pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initConnectPipe();
                }

                public String getName() {
                    return "Connect pipe";
                }
            },
            /**
             * A Disconnect pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initDisconnectPipe();
                }

                public String getName() {
                    return "Disconnect pipe";
                }
            },
            /**
             * A Fix pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initFixPipe();
                }

                public String getName() {
                    return "Fix pipe";
                }
            },
            /**
             * A Place pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initPlacePipe();
                }

                public String getName() {
                    return "Place pipe";
                }
            },
            /**
             * A Pickup pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initPickupPipe();
                }

                public String getName() {
                    return "Pickup pipe";
                }
            },
            /**
             * A Puncture pipe menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initPuncturePipe();
                }

                public String getName() {
                    return "Puncture pipe";
                }
            }
    };

    /**
     * A Pump actions almenühöz tartozó menüopciókat tartalmazza.
     */
    private static final MenuOption[] PUMP_ACTIONS_SUBMENU = {
            /**
             * A Fix pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initFixPump();
                }

                public String getName() {
                    return "Fix pump";
                }
            },
            /**
             * A Place pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initPlacePump();
                }

                public String getName() {
                    return "Place pump";
                }
            },
            /**
             * A Mechanic sets pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initMechanicSetsPump();
                }

                public String getName() {
                    return "Mechanic sets pump";
                }
            },
            /**
             * A Saboteur sets pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initSaboteurSetsPump();
                }

                public String getName() {
                    return "Saboteur sets pump";
                }
            },
            /**
             * A Take pump menüopció.
             */
            new MenuOption() {
                public void execute() {
                    System.out.println(getName());
                    Initialize.initTakePump();
                }

                public String getName() {
                    return "Take pump";
                }
            }
    };

    /**
     * Kiírja a menü opcióit, lehetővé teszi a menüben való navigációt.
     * 
     * @param menu A menü opciói
     */
    public static void runMenu(MenuOption[] menu) {
        Scanner scanner = new Scanner(System.in);
        MenuOption[] currentMenu = menu;
        boolean isMainMenu = (currentMenu == MAIN_MENU);

        while (true) {
            System.out.println("Choose an option:");
            // Menüopciók felsorolása
            for (int i = 0; i < currentMenu.length; i++) {
                System.out.println("[" + (i + 1) + "] - " + currentMenu[i].getName());
            }
            if (isMainMenu) {
                // Programból kilépés
                System.out.println("[X] - Exit program");
            } else {
                // Menüben visszalépés
                System.out.println("[X] - Step back");
            }
            // Kiválasztott opció
            String option = scanner.nextLine().trim().toUpperCase();

            try {
                int command = Integer.parseInt(option);
                if (command > currentMenu.length || command < 1)
                    continue;
                // Opciónak megfelelő akció
                currentMenu[command - 1].execute();
            } catch (NumberFormatException ex) {
                if (option.equals("X")) {
                    break;
                }
            }
        }
    }
}
