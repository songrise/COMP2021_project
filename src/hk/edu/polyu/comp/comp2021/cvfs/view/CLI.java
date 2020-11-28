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
    private Command crtCommand;

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
        System.out.println("***Welcome to CVFS!***");
        while (true) {
            String input = scanInput();
            crtCommand = setCommand(input);
            if (crtCommand != null) {
                executeCommand(crtCommand, crtCommand.getParameters());
                if (crtCommand.getType() == CommandType.quit) {
                    break;
                }
            }
        }
    }

    private String scanInput() {
        System.out.print(system.getPath()+">");
        String input = scanner.nextLine();
        return input.trim();
    }

    private Command setCommand(String input) {
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

    private void executeCommand(Command cmd, String[] parameters) {
        CommandRunner runner = CommandRunnerFactory.getRunner(cmd);
        if (runner != null) {
            runner.execute(system, parameters);
        }
    }
}
