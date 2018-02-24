package listeners;

import view.UrbanParksGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dave on 2/18/18.
 */
public class logoutUserListener implements ActionListener {

    private UrbanParksGUI systemGUI;

    public logoutUserListener(UrbanParksGUI systemGUI) {
        this.systemGUI = systemGUI;
    }

    public void actionPerformed(ActionEvent ae) {
        systemGUI.displayLoginPanel(systemGUI.createGenericLoginMsg());
    }
}
