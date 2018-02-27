package view;

import controller.Controller;
import exceptions.UserNotFoundException;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static model.UserRole.PARK_MANAGER;
import static model.UserRole.VOLUNTEER;

//import recycle_bin.CloseApplicationWindowListener;

/**
 * This class contains the base frame for the GUI.
 * this is where panels get popped off depending on the
 * application state
 *
 * more comments here later
 *
 * @Created by Chad on 2/13/18.
 */
public class UrbanParksGUI implements PropertyChangeListener {

    private static final int TEXT_WIDTH = 30;

    private User currentUser;

    private String frameTitle = "Urban Parks Volunteer Management System";

    private final JFrame frame;

    private JTextField userInputField;

    private JTextArea outputDisplayArea;

    private JButton loginButton;

    private Controller systemController;


    public UrbanParksGUI(Controller paramController) {
        this.systemController = paramController;
        frame = new JFrame(frameTitle);
        setupGUI();

    }

    //TODO - new constructor which uses Contorller is above here
    //TODO - old constructors which do not use the Controller are below here
//

    private void setupGUI() {
        setupFrame();


        //import in all data structures
    }

    private void setupFrame() {
        //do we need a window event to close it or is it ok to EXITONCLOSE?
        //like will there be data to write to?
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new CloseApplicationWindowListener());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //addComponentsFromMap();
        //frame.setContentPane(this);

    }

    /**
     * This panel is shared across all users so I think it is ok to be in this
     * class instead of its own.  also need to keep user info in this class and not
     * be handing it all around.
     */
    private void displayLoginPanel(final String loginMsg) { //login msg can change
        // if a new login or if previous login failed.
        //since each login should not retain info from previous sessions,
        //I think it is a good idea to just pop a new one on each time
        final JPanel loginPanel = createLoginPanel(loginMsg);
        frame.setContentPane(loginPanel);
        frame.pack();

    }

    private JPanel createLoginPanel(final String loginMsg) {
        JPanel newLoginPanel = new JPanel();
        //text area to provide info to user
        //TODO display the loginMsg to user
        outputDisplayArea = new JTextArea();

        //field to take user login info
        userInputField = new JTextField();

        //button to press to login
        //add loginListener to button
        loginButton = new JButton("Submit");
        loginButton.setEnabled(true);
        loginButton.addActionListener(e -> submitLoginInfo(userInputField.getText()));

        newLoginPanel.setLayout(new BorderLayout());
        newLoginPanel.add(outputDisplayArea);
        newLoginPanel.add(userInputField);
        newLoginPanel.add(loginButton);
        newLoginPanel.grabFocus();

        return newLoginPanel;
    }

    private void submitLoginInfo(final String usernameInput) {
        UserID userInput = new UserID(usernameInput);
        //validate the user's input info through the jobs collection
        try {
                currentUser = systemController.
                        getUserByUserID(new UserID(usernameInput));
            } catch (UserNotFoundException unfe) {
            //return to loginPanel
            displayLoginPanel(getUserIDNotFoundMsg());
        }
        //get new panel for appropriate user role, pass along user info as needed
        if (currentUser.getUserRole().equals(VOLUNTEER)) {
            //frame.setContentPane(new VolunteerGUIPanel(systemController);
        } else if (currentUser.getUserRole().equals(PARK_MANAGER)) {
            frame.setContentPane(new ParkManagerGUIPanel(systemController));
        } else {
            //frame.setContentPane(new StaffMemberGUIPanel(systemController);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if ("logoutBtn".equals(propertyName)) {
            //log out the current user
            logoutUser();
        }
    }

    public void logoutUser() {
        systemController.storeCollectionsIntoFile();
        displayLoginPanel(createGenericLoginMsg());
    }



    public String createGenericLoginMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome to Urban Parks, please log in to the system");
        return sb.toString();
    }

    public String getLoginMsg() {
        return "log in here:";
    }

    private String getUserIDNotFoundMsg() {
        final StringBuilder sb = new StringBuilder();
        sb.append("That User ID was not found");
        return sb.toString();
    }

    /////////////////////////////recycling ////////////////////////////////////
    /**
     * When this class is first initialized, this will be passed the login panel
     * once the system is running however, it should be able to swap out panels
     * depending on what the current user's role is
     *
     * @param panelToDisplay
     */
    public void displayPanel(JPanel panelToDisplay) {
        frame.add(panelToDisplay);
        //userLoginInputField.grabFocus();
        //getRootPane().setDefaultButton(myCountValuesButton);
    }

    /**
     * this method should display the frame and use the controller to decide which panel
     * to display, beginning with the login panel.
     */
    public void runSystem() {

    }

    /**
     * when the entire application window is entirely closed out, this is the listener that should
     * be triggered.
     */
    private class CloseApplicationWindowListener implements WindowListener {

        //in the event they close the window without logging out first
        //should also write/save all collections at logout
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            systemController.storeCollectionsIntoFile();
        }

        @Override
        public void windowIconified(WindowEvent windowEvent) {

        }

        @Override
        public void windowOpened(WindowEvent windowEvent) {

        }

        @Override
        public void windowDeactivated(WindowEvent windowEvent) {

        }

        @Override
        public void windowActivated(WindowEvent windowEvent) {

        }

        @Override
        public void windowDeiconified(WindowEvent windowEvent) {

        }

        @Override
        public void windowClosed(WindowEvent windowEvent) {

        }
    }

//    private class EnterUserNameListener implements ActionListener {
//
//        public void actionPerformed(final ActionEvent theEvent) {
//            final String userInputString = userLoginInputField.getText();
//            if (!userInputString.isEmpty()) {
//                loginButton.setEnabled(true);
//            }
//        }
//    }
//
//    private class UserLogInListener implements ActionListener {
//
//        public void actionPerformed(final ActionEvent theEvent) {
//            //ended here
//            //if a user is whatever role, swap that panel to the frame then repaint it
//
//        }
//    }

//    private void recycleMethod() {
//        //loginButton.addActionListener(new RootPanelLogInListener());
//        //frame.frameInit();
//        loginButton.addActionListener(new UserLogInListener());
//        loginButton.setEnabled(false);
//        userLoginInputField.addActionListener(new EnterUserNameListener());
//        userLoginInputField.grabFocus();
//    }


//    public UrbanParksGUI() {
//        frame = new JFrame(frameTitle);
//        setupGUI();
//    }
//
//    public UrbanParksGUI(final JobCollection paramJobs,
//                         final UserCollection paramUsers,
//                         final ParkCollection paramParks) {
//
//        this.jobs = paramJobs;
//        this.parks = paramParks;
//        this.users = paramUsers;
//
//        frame = new JFrame("!THIS IS THE FRAME TEXT!!!");
//        setupGUI();
//    }
    //end class UrbanParksGUI
}
