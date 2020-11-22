package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

import java.util.Arrays;
import java.util.Scanner;

public class CLI {
    final CVFS system;
    final Scanner scanner;

    public CLI() {
        this.system = new CVFS();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            String input = scanInput();
            Command command = checkInput(input);
            if (command != null) {
                manageCommand(command.type, command.parameters);
                if (command.type == CommandType.quit) {
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
                    return new Command(type, parameters);
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
        runner.execute(system,parameters);
    }
}
