package hk.edu.polyu.comp.comp2021.cvfs.view;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFS;
import hk.edu.polyu.comp.comp2021.cvfs.model.fileSystem.File;

import java.util.NoSuchElementException;

/**
 *
 */
class newDiskRunner extends ConcreteCommandRunner {
    /**
     *
     */
    newDiskRunner() {
        super(CommandType.newDisk);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.newDisk(Integer.parseInt(parameters[0]));// later I will put this conversion to controller
        } catch (Exception e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully created new disk size = " + parameters[0]);
    }
}

/**
 *
 */
class newDirRunner extends ConcreteCommandRunner {
    /**
     *
     */
    newDirRunner() {
        super(CommandType.newDir);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.newDir(parameters[0]);// later I will put this conversion to controller
        } catch (IllegalArgumentException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully created new directory name = " + parameters[0]);
    }
}

/**
 *
 */
class newDocRunner extends ConcreteCommandRunner {
    /**
     *
     */
    newDocRunner() {
        super(CommandType.newDoc);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.newDoc(parameters[0], parameters[1], parameters[2]);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully created new document name = " + parameters[0]);
    }
}

/**
 *
 */
class deleteRunner extends ConcreteCommandRunner {
    /**
     *
     */
    deleteRunner() {
        super(CommandType.delete);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.delFile(parameters[0]);
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully deleted file name = " + parameters[0]);
    }
}

/**
 *
 */
class renameRunner extends ConcreteCommandRunner {
    /**
     *
     */
    renameRunner() {
        super(CommandType.rename);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.rename(parameters[0], parameters[1]);
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully renamed file name = " + parameters[0]);
    }
}

/**
 *
 */
class changeDirRunner extends ConcreteCommandRunner {
    /**
     *
     */
    changeDirRunner() {
        super(CommandType.changeDir);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.changeDir(parameters[0]);
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully changed directory to " + parameters[0]);
    }
}

/**
 *
 */
class listRunner extends ConcreteCommandRunner {
    /**
     *
     */
    listRunner() {
        super(CommandType.list);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        int size = 0;
        try {
            System.out.printf("%-14s%-12s%s\n", "Name", "Type", "Size");
            System.out.println("-------------------------------");
            for (File f : cvfs.list()) {
                System.out.printf("%-14s%-12s%s\n", f.getName(), f.getType(), f.isDirectory() ? "" : f.getSize());
                size += f.getSize();
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        System.out.println(cvfs.list().size() + " files in current directory, total size = " + size);
        return true;
    }

}

/**
 *
 */
// ! TODO to be finished
class rListRunner extends ConcreteCommandRunner {
    /**
     *
     */
    rListRunner() {
        super(CommandType.rList);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        int size = 0;
        StringBuilder sb = new StringBuilder();
        boolean newIndentationFlag = false;

        try {
            int dirCount = 0;
            for (File f : cvfs.list()) {
                if (f.isDirectory()) {
                    dirCount++;
                }
            }

            System.out.printf("%-14s%-12s%s\n", "Name", "Type", "Size");
            System.out.println("-------------------------------");
            for (File f : cvfs.rlist()) {
                if (f.isDirectory()) {
                    if (!newIndentationFlag) {
                        sb.append(" ");
                        if (--dirCount == 0)
                            newIndentationFlag = true;
                    }
                }
                System.out.printf("%s%-14s%-12s%s\n", sb.toString(), f.getName(), f.getType(),
                        f.isDirectory() ? "" : f.getSize());
                size += f.getSize();
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        System.out.println(cvfs.rlist().size() + " files, total size = " + size);
        return true;
    }
}

/**
 *
 */
class newSimpleCriRunner extends ConcreteCommandRunner {
    /**
     *
     */
    newSimpleCriRunner() {
        super(CommandType.newSimpleCri);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.newSimpleCri(parameters[0], parameters[1], parameters[2], parameters[3]);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully created simple criterion name = " + parameters[0]);
    }
}

/**
 *
 */
class newNegationRunner extends ConcreteCommandRunner {
    /**
     *
     */
    newNegationRunner() {
        super(CommandType.newNegation);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.newNegation(parameters[0], parameters[1]);
        } catch (IllegalArgumentException | IllegalStateException|NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully created  negation name = " + parameters[0]);
    }
}

/**
 *
 */
class newBinaryCriRunner extends ConcreteCommandRunner {
    /**
     *
     */
    newBinaryCriRunner() {
        super(CommandType.newBinaryCri);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        if (!super.execute(cvfs, parameters)) {
            return false;
        }
        try {
            cvfs.newBinaryCri(parameters[0], parameters[1], parameters[2], parameters[3]);
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(parameters);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("Successfully created binary criterion name = " + parameters[0]);
    }
}

/**
 *
 */
class printAllCriRunner extends ConcreteCommandRunner {
    /**
     *
     */
    printAllCriRunner() {
        super(CommandType.printAllCriteria);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        try {
            System.out.println(cvfs.printAllCriteria());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        return true;
    }
}

/**
 *
 */
class searchRunner extends ConcreteCommandRunner {

    /**
     *
     */
    searchRunner() {
        super(CommandType.search);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        int size = 0;
        try {
            for (File f : cvfs.searchByCriterion(parameters[0])) {
                System.out.println(f.getFullName());
                size += f.getSize();
            }
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        System.out.println(cvfs.searchByCriterion(parameters[0]).size() + " files found, size = " + size);
        return true;
    }
}

/**
 *
 */
class rSearchRunner extends ConcreteCommandRunner {
    /**
     *
     */
    rSearchRunner() {
        super(CommandType.rSearch);
    }

    @Override
    public boolean execute(CVFS cvfs, String[] parameters) {
        int size = 0;
        try {
            for (File f : cvfs.rSearchByCriterion(parameters[0])) {
                System.out.println(f.getFullName());
                size += f.getSize();
            }
        } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        System.out.println(cvfs.rSearchByCriterion(parameters[0]).size() + " files found, size = " + size);
        return true;
    }
}

/**
 *
 */
class storeRunner extends ConcreteCommandRunner {
    /**
     *
     */
    storeRunner() {
        super(CommandType.store);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        try {
            cvfs.store();
        } catch (Exception e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(null);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("CVFS stored to local file system");
    }
}

/**
 *
 */
class loadRunner extends ConcreteCommandRunner {
    /**
     *
     */
    loadRunner() {
        super(CommandType.load);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        try {
            cvfs.load();
        } catch (Exception e) {
            System.out.println("Command failed because of an exception " + e.getCause());
            return false;
        }
        echo(null);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("CVFS loaded from local file system");
    }
}

/**
 *
 */
class undoRunner extends ConcreteCommandRunner {
    /**
     *
     */
    undoRunner() {
        super(CommandType.undo);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        try {
            cvfs.undo();
        } catch (Exception e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(null);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("successfully undo previous operation");
    }
}

/**
 *
 */
class redoRunner extends ConcreteCommandRunner {
    /**
     *
     */
    redoRunner() {
        super(CommandType.redo);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        try {
            cvfs.redo();
        } catch (Exception e) {
            System.out.println("Command failed because of: " + e.getMessage());
            return false;
        }
        echo(null);
        return true;
    }

    @Override
    void echo(String[] parameters) {
        System.out.println("successfully redo previous operation");
    }
}

/**
 *
 */
class quitRunner extends ConcreteCommandRunner {
    /**
     *
     */
    quitRunner() {
        super(CommandType.quit);
    }

    @Override
    public boolean execute(CVFS cvfs) {
        System.out.println("Bye!");
        return true;
    }

}

/**
 * A simple factory for command runner.
 */
public class CommandRunnerFactory {

    /**
     * @param t type of command
     * @return a dispatched command runner object.
     */
    public static CommandRunner getRunner(CommandType t) {
        switch (t) {
            case newDisk:
                return new newDiskRunner();
            case newDoc:
                return new newDocRunner();
            case newDir:
                return new newDirRunner();
            case delete:
                return new deleteRunner();
            case rename:
                return new renameRunner();
            case changeDir:
                return new changeDirRunner();
            case list:
                return new listRunner();
            case rList:
                return new rListRunner();
            case newSimpleCri:
                return new newSimpleCriRunner();
            case newNegation:
                return new newNegationRunner();
            case newBinaryCri:
                return new newBinaryCriRunner();
            case printAllCriteria:
                return new printAllCriRunner();
            case search:
                return new searchRunner();
            case rSearch:
                return new rSearchRunner();
            case store:
                return new storeRunner();
            case load:
                return new loadRunner();
            case undo:
                return new undoRunner();
            case redo:
                return new redoRunner();
            case quit:
                return new quitRunner();
            default:
                System.out.println("Unsupported command!");
                return null;
        }
    }
}
