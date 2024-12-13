package marc.application;

import marc.actionBd.MappingSelectRequest;
import marc.actionBd.SelectRequest;

public abstract class Application {

    public static void run() throws Exception {
        Integer i = new MappingSelectRequest<Integer>(new SelectRequest("SELECT COUNT(*) FROM bonjour"){}){}.getValue();
        System.out.println(i);
    }
}
