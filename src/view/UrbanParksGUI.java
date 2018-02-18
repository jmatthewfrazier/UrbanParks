package view;

import exceptions.UserNotFoundException;
import listeners.CloseApplicationWindowListener;
import listeners.UserLogInToSystemListener;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static model.UserRole.PARK_MANAGER;
import static model.UserRole.VOLUNTEER;

/**
 * Created by dave on 2/13/18.
 */
public class UrbanParksGUI {

    private static final int TEXT_WIDTH = 30;

    private User currentUser;

    private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;

    private String frameTitle = "Urban Parks Volunteer Management System";

    private final JFrame frame;

    private JTextField userInputField;

    private JTextArea outputDisplayArea;

    private JButton loginButton;


    public UrbanParksGUI() {
        frame = new JFrame(frameTitle);
        setupGUI();
    }

    public UrbanParksGUI(final JobCollection paramJobs,
                         final UserCollection paramUsers,
                         final ParkCollection paramParks) {

        this.jobs = paramJobs;
        this.parks = paramParks;
        this.users = paramUsers;

        frame = new JFrame("!THIS IS THE FRAME TEXT!!!");
        setupGUI();
    }

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
    public void displayLoginPanel(final String loginMsg) { //login msg can change
        // if a new login or if previous login failed.
        //since each login should not retain info from previous sessions,
        //I think it is a good idea to just pop a new one on each time
        final JPanel loginPanel = createLoginPanel(loginMsg);
        frame.setContentPane(loginPanel);

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
            if (users.containsUserID(userInput)) {
                currentUser = users.getUserFromUserID(userInput);
            }
        } catch (UserNotFoundException unfe) {
            //user input not valid
            //return to loginPanel
            displayLoginPanel(getUserIDNotFoundMsg());
        } //TODO-below here is it correct to create new instances? do we need to ?
        //get new panel for appropriate user role, pass along user info as needed
        if (currentUser.getUserRole().equals(VOLUNTEER)) {
            frame.setContentPane(new VolunteerGUIPanel(jobs, new Volunteer(currentUser.getFirstName(),
                    currentUser.getLastName(), currentUser.getID())));
        } else if (currentUser.getUserRole().equals(PARK_MANAGER)) {
            //TODO is this correct? to create a new PM instance here?
            frame.setContentPane(new ParkManagerGUIPanel(jobs, (new ParkManager(currentUser.getFirstName(),
                    currentUser.getLastName(), currentUser.getID()))));
        } else {
            frame.setContentPane(new StaffMemberGUIPanel(jobs, new StaffMember(currentUser.getFirstName(),
                    currentUser.getLastName(), currentUser.getID())));
        }
    }

    private String getUserIDNotFoundMsg() {
        final StringBuilder sb = new StringBuilder();
        sb.append("That User ID was not found");
        return sb.toString();
    }

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

    //end class UrbanParksGUI
}
