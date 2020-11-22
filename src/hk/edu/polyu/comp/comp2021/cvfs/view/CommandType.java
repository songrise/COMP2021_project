package hk.edu.polyu.comp.comp2021.cvfs.view;

@SuppressWarnings("ALL") //the semantics of this enum is clear enough, added javaDoc will be very redundent.
enum CommandType {
    newDisk(1), newDoc(3), newDir(1), delete(1),
    rename(2), changeDir(1), list(0), rList(0),
    newSimpleCri(4), newNegation(2), newBinaryCri(4),
    printAllCriteria(0), search(1), rSearch(1),
    store(0), load(0), undo(0), redo(0), quit(0);
    private final int numParameter;

    CommandType(int num) {
        numParameter = num;
    }

    int getNumOfParameters() {
        return numParameter;
    }
}
