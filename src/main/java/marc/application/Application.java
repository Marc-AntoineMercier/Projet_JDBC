package marc.application;


import marc.model.AfficheSelectRequest;
import marc.model.SelectRequest;

public abstract class Application {

    public static void run() throws Exception {
        System.out.println(new AfficheSelectRequest(new SelectRequest("Select * FROM test")));
    }
}
