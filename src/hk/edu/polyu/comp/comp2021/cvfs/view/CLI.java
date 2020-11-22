package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Command Line Interface for CVFS
 */
public class CLI {
    private final CVFS system;
    private final Scanner scanner;

    /**
     * constructs a CLI
     */
    public CLI() {
        this.system = new CVFS();
        this.scanner = new Scanner(System.in);
    }

    /**
     * start the CLI
     */
    public void run() {
        boolean running = true;
        while (running) {
            String input = scanInput();
            Command command = checkInput(input);
            if (command != null) {
                manageCommand(command.getType(), command.getParameters());
                if (command.getType() == CommandType.quit) {
                    running = false;
                }
            }
        }
    }

    private String scanInput() {
        System.out.print(system.getPath());
        String input = scanner.nextLine();
        return input.trim();
    }

    private Command checkInput(String input) {
        if (input.length() != 0) {
            try {
                String[] inputAfterSplit = input.split(" ");
                CommandType type = CommandType.valueOf(inputAfterSplit[0]);
                String[] parameters;
                if (inputAfterSplit.length == type.getNumOfParameters() + 1) {
                    parameters = Arrays.copyOfRange(inputAfterSplit, 1, inputAfterSplit.length);
                    return new ConcreteCommand(type, parameters);
                } else {
                    System.out.println("command failed because: Illegal set of parameters");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("command failed because: Unsupported command");
            }
        }
        return null;
    }

    private void manageCommand(CommandType type, String[] parameters) {
        CommandRunner runner = CommandRunnerFactory.getRunner(type);
        if (runner != null) {
            runner.execute(system,parameters);
        }
    }
}
