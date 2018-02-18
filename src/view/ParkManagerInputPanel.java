package view;

import javax.swing.*;

/**
 * Created by dave on 2/13/18.
 */
public class ParkManagerInputPanel extends JPanel {

    private JButton createButton;

    private JButton returnButton;

    private JButton updateButton;

    private JButton deleteButton;


    public ParkManagerInputPanel() {
        super();

        setupButtonGroup();
    }

    private void setupButtonGroup(){
        //should be disabled if a job number is selected,
        //otherwise it should default as initially available
        createButton = new JButton("Add New Job");

        //should never be diabled? but just give an error message is needed?
        returnButton = new JButton("View All Future Jobs");

        //should be disabled unless a job number is entered into text field
        updateButton = new JButton("Edit This Job");

        //should be disabled unless a job number is entered into text field
        deleteButton = new JButton("Delete This Job");

        this.add(createButton);
        this.add(returnButton);
        this.add(updateButton);
        this.add(deleteButton);


    }
}
