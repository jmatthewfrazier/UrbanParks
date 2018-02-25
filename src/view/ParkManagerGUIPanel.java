package view;

import controller.Controller;
import exceptions.UserInputException;
import model.Job;
import model.JobCollection;
import model.ParkManager;
import model.User;
import tests.MockJobCollection;
import tests.MockParkManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//import listeners.logoutUserListener;

/**
 * Created by Chad on 2/13/18.
 */
public class ParkManagerGUIPanel extends JPanel {

    private JTextArea textOutputDisplayArea;

    private JTextField userInputField;

    private JButton createNewJobBtn, viewJobDetailsBtn, returnMyJobsBtn,
            returnAllJobsBtn, deleteThisJobBtn, logoutBtn;

    private ParkManager parkManager;

    private PopupFactory popupFactory;

    private Popup deleteJobPopup;

    private Controller systemController;

    //will need a popup window for confirming actions, etc

    public ParkManagerGUIPanel(final Controller paramController) {

        super();

        this.systemController = paramController;
        parkManager = createParkManagerFromUser();

        setupPanelGUI();

    }

    private ParkManager createParkManagerFromUser() {
        User currentUser = systemController.getCurrentUser();
        ParkManager newParkManager =
                new ParkManager(currentUser.getFirstName(),
                        currentUser.getLastName(), systemController);
        return newParkManager;
    }

    ////////////panel gui setup methods ///////////////////////////////////////

    private void setupPanelGUI(){

        popupFactory = PopupFactory.getSharedInstance();

        //add text area for displaying text to user
        textOutputDisplayArea = new JTextArea(getIntroMsg());

        //panel for components that take input
        final JPanel inputPanel = createNewInputPanel();

        //add everything where it is supposed to go
        setLayout(new BorderLayout());
        this.add(new JScrollPane(textOutputDisplayArea), BorderLayout.NORTH );
        this.add(inputPanel, BorderLayout.CENTER);

    }

    private JPanel createNewInputPanel() {
        JPanel newPanel = new JPanel();
        //add text input field for taking user input
        userInputField = new JTextField();

        //should be disabled if a job number is selected,
        //otherwise it should default as initially available
        createNewJobBtn = new JButton("Add New Job");
        createNewJobBtn.setEnabled(true);

        //should never be diabled? but just give an error message if needed?
        returnAllJobsBtn = new JButton("All Future Jobs");
        returnAllJobsBtn.setEnabled(true);

        //should never be diabled? but just give an error message if needed?
        returnMyJobsBtn = new JButton("My Future Jobs");
        returnMyJobsBtn.setEnabled(true);

        //should be disabled unless a job number is entered into text field
        viewJobDetailsBtn = new JButton("View Job Details");
        viewJobDetailBtn.setEnabled(true);

        //should be disabled unless a job number is entered into text field
        deleteThisJobBtn = new JButton("Delete This Job");
        deleteThisJobBtn.setEnabled(false);

        //should allow the user to log out at any time
        logoutBtn = new JButton("Log Out");
        logoutBtn.setEnabled(true);

        addButtonListeners();
        newPanel.add(userInputField);
        newPanel.add(createNewBtn);
        newPanel.add(returnAllBtn);
        newPanel.add(deleteJobBtn);

        return newPanel;
    }

    private void addButtonListeners() {
        createNewBtn.addActionListener(e -> addFutureJob());
        returnAllBtn.addActionListener(e -> getAllJobs());
        //updateJobBtn.addActionListener(e -> updateChosenJob());
        deleteJobBtn.addActionListener(e -> deleteChosenJob());
//        logoutBtn.addActionListener(e ->
//                new logoutUserListener(this.getParent()));
    }

    public void addFutureJob() {
        //create new job object and add it to the list

    }

    //to remove a job, we could either go by park manager id in a job
    //or a list of jobs they created
    //may be better ot pass jobcollection a job id and a parkmanager id
    public void deleteChosenJob() {
        int userInput = -1;
        //first check the user input from the input field
        //it has to be an integer and less than the number of items displayed
        try {
            userInput = getIntegerFromUserInputField(userInputField.getText());
        } catch (UserInputException uie) {
            textOutputDisplayArea.append(uie.getMsgString());
        }

        //now input is accepted, grab the selected job and it's details
        //assuming the user input integer is the same as the index in the list
        Job jobToRemove = jobList.get(userInput);
        //display popup to confirm user REALLY wants to delete this job permanently
        JPopupMenu confirmDeleteJobMenu = new JPopupMenu("CONFIRM ACTION: " +
                "DELETE JOB");
        JPanel deleteJobPopupPanel = createDeleteJobPopupComponent(jobToRemove);
        deleteJobPopup = popupFactory.getPopup(this, deleteJobPopupPanel,50, 50);
        deleteJobPopup.show();
            //user says no, do not delete this job
            //display message job will not be deleted
            //return to "home screen"
        //user says yes, delete this job
        //deleteJobPopup.hide();
        //tell collection to remove the job at that index
        //jobWallet
        jobList.remove(userInput);
        //display message confirming job has been deleted
        textOutputDisplayArea.append(createJobDeletedMsg(jobToRemove));
        //return to "home screen"
        textOutputDisplayArea.append(getHomeScreenMsg());

    }

    private int getIntegerFromUserInputField(final String userInputStr)
                                            throws UserInputException{
        int inputInt = -1;
        int jobsListedCount = systemController.getJobs().size();
        try {
            inputInt = Integer.parseInt(userInputStr);
        } catch (NumberFormatException nef) {
            throw new UserInputException("User input was not an integer");
        }
        if (inputInt > jobsListedCount) {
            throw new UserInputException("Input number too high, not found in Jobs list");
        } else if (inputInt < 0) {
            throw new UserInputException("Input number too low, no corresponding Job number");
        }
        return inputInt;

    }

    private JPanel createDeleteJobPopupComponent(final Job jobToDelete) {
        JPanel deleteJobPopupPanel = new JPanel();
        JTextArea msgArea = new JTextArea();
        JButton deleteYesBtn = new JButton("Delete Job");
        deleteYesBtn.addActionListener(e -> deleteJobPopup.hide() );
        JButton deleteCancelBtn = new JButton("Cancel");
        deleteCancelBtn.addActionListener(e -> deleteJobCanceled());

        deleteJobPopupPanel.setLayout(new BorderLayout());
        deleteJobPopupPanel.add(msgArea, BorderLayout.NORTH);
        deleteJobPopupPanel.add(deleteYesBtn, BorderLayout.CENTER);
        deleteJobPopupPanel.add(deleteCancelBtn, BorderLayout.SOUTH);
        return deleteJobPopupPanel;
    }

    private void deleteJobCanceled() {
        //should take back to initial program state of before the delete action was began
        //erase the text in the display area
        textOutputDisplayArea.replaceRange(null, 0, Integer.MAX_VALUE);
        //now signal what just happened
        textOutputDisplayArea.append("\nJob delete action was cancelled\n");
        resetUIState();

    }

    //TODO-this will be shared logic and repeated if not put in the parent component?
    private void resetUIState() {
        textOutputDisplayArea.append(getHomeScreenMsg());
        //get focus back to input field
        userInputField.grabFocus();
        //return to initial button state for delete button
        deleteJobBtn.setEnabled(false);
    }
    ////getters and setters ///////////////////////////////////////////////////

    public List<Job> getFutureJobsISubmittedAsList() {

        return parkManager.getFutureJobsSubmittedByMe();
    }

    public List<Job> getAllJobsAsList() {

        return systemController.getJobs().getList();
    }

    /////Message factory methods///////////////////////////////////////////////

    private String createJobDeletedMsg(final Job jobToRemove) {
        StringBuilder sb = new StringBuilder();
        sb.append("this job was deleted\n");
        sb.append(jobToRemove.getName());
        return sb.toString();
    }

    private String getHomeScreenMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append("Welcome back to the home screen");
        return sb.toString();
    }

    private String getIntroMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nWelcome Park Manager \n");
        sb.append("Listing of future jobs in the Urban Parks Database \n\n");
        //something here about either creating a new job or altering a job?
        sb.append("First, please enter a job number in the text field below\n");
        sb.append("Then, use the button menu to choose an action for the job you have selected\n\n");
        return sb.toString();
    }

    //end Park Manager GUI Panel class
}
//private JButton updateJobBtn;

//should be disabled unless a job number is entered into text field
//updateJobBtn = new JButton("Edit This Job");

//    public void updateChosenJob() {
//        //first check the user input from the input field
//        //it has to be an integer and less than the number of items displayed
//        //now input is accepted, move on to fetching the job at that index
//        //fetch that job
//        //fields in the job we have to be able to update:
//    }