package marc.application;


import marc.actionBd.AfficheSelectRequest;
import marc.actionBd.MappingSelect;
import marc.actionBd.SelectRequest;
import marc.actionBd.UpdateRequest;
import marc.entity.Autobus;

public abstract class Application {

    public static void run() throws Exception {
        System.out.println(new AfficheSelectRequest(new SelectRequest("SELECT * FROM test") {}) {});
    }
}
