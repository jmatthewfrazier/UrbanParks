package view;

import model.JobCollection;
import model.Volunteer;

import javax.swing.*;

/**
 * Created by dave on 2/13/18.
 */
public class VolunteerGUIPanel extends JPanel {

    private JobCollection jobWallet;

    private Volunteer user;

    public VolunteerGUIPanel(final JobCollection paramJobWallet,
                             final Volunteer paramUser) {
        super();
        this.jobWallet = paramJobWallet;
        this.user = paramUser;

    }
}
