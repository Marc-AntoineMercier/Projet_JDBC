package marc;

import marc.model.AfficheSelectRequest;
import marc.model.SelectRequest;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    void request() {
        try {
            new AfficheSelectRequest(new SelectRequest(null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
