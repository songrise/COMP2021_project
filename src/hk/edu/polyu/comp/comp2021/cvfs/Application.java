package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.view.CLI;

public class Application {

    public static void main(String[] args) {
        CLI app = new CLI();
        while (true){
            try{
                app.scanInput();
                app.manageCommand();
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
                continue;
            }
        }
    }
}
