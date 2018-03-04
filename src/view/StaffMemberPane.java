package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.UrbanParksData;

public class StaffMemberPane extends Pane {

	private final UrbanParksData data;
	private Pane pane;

	public StaffMemberPane(UrbanParksData data) {
		this.data = data;
		this.pane = getStaffMemberPane();
	}

	private final Pane getStaffMemberPane() {
		final BorderPane border = new BorderPane();
		final VBox v = new VBox();

		v.setPadding(new Insets(15, 12, 15, 12));
		v.setSpacing(10);

		final Button viewJobsBtn = new Button("View Jobs");

		final Button setJobCapacityBtn = new Button("Set Job Capacity");

		final Button logOutBtn = new Button("Log out");

		v.getChildren().addAll(viewJobsBtn, setJobCapacityBtn, logOutBtn);

		border.setBottom(v);
		getChildren().add(border);

		viewJobsBtn.setOnAction(event -> {
			getChildren().remove(border);
//			getChildren().add(getUpcomingJobsPane());
		});

		setJobCapacityBtn.setOnAction(event -> {
			getChildren().remove(border);
//			getChildren().add(getNewJobFormPane());
		});

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
//			logout(root);
		});

		return border;
	}
}
