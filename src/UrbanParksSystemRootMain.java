import controller.Controller;
import view.UrbanParksGUIRoot;

/**
 * Created by dave on 2/18/18.
 */
public class UrbanParksSystemRootMain {




    private UrbanParksSystemRootMain() {

    }


    public static void main(String[] args) {

        new UrbanParksGUIRoot(new Controller()).runSystem();
    }
}
