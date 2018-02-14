package view;

import listeners.UserLogInToSystemListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dave on 2/13/18.
 */
public class UrbanParksGUI extends JPanel {

    private static final int TEXT_WIDTH = 30;

   // private JPanel panel;

    private Map<String, JComponent> componentMap;

    private String frameTitle = "Urban Parks Volunteer Management System";

    private final JTextField userLoginInputField;

    private final JButton loginButton;


    public UrbanParksGUI() {
        super();
        loginButton = new JButton("Sign In");
        userLoginInputField = new JTextField(TEXT_WIDTH);
        setupPanel();
    }

    public UrbanParksGUI(HashMap<String, JComponent> componentHashMap) {
        super();
        loginButton = new JButton("Sign In");
        userLoginInputField = new JTextField(TEXT_WIDTH);
        componentMap = componentHashMap;
        setupPanel();

    }

    private void setupPanel() {
        //loginButton.addActionListener(new RootPanelLogInListener());
        //frame.frameInit();
        loginButton.addActionListener(new UserLogInListener());
        loginButton.setEnabled(false);
        userLoginInputField.addActionListener(new EnterUserNameListener());
        userLoginInputField.grabFocus();
        //addComponentsFromMap();

        setLayout(new BorderLayout());
    }

    /**
     * Creates and displays the application frame.
     */
    public void display() {
        final JFrame frame = new JFrame("!THIS IS THE FRAME TEXT!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setContentPane(this);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        userLoginInputField.grabFocus();
        //getRootPane().setDefaultButton(myCountValuesButton);
    }

    private class EnterUserNameListener implements ActionListener {

        public void actionPerformed(final ActionEvent theEvent) {
            final String userInputString = userLoginInputField.getText();
            if (!userInputString.isEmpty()) {
                loginButton.setEnabled(true);
            }
        }
    }

    private class UserLogInListener implements ActionListener {

        public void actionPerformed(final ActionEvent theEvent) {
            //ended here
            //if a user is whatever role, swap that panel to the frame then repaint it

        }
    }

    //end class UrbanParksGUI
}
