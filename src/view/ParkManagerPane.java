package view;

import exceptions.UrbanParksSystemOperationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ParkManagerPane extends StackPane {

	private static final int MAX_BUTTON_WIDTH = 800;

	private final UrbanParksData data;
	private Pane pane;
	private HBox userInfo;

	public ParkManagerPane(UrbanParksData data, HBox userInfo) {
		this.data = data;
		this.pane = getParkManagerPane();
		this.userInfo = userInfo;
		this.setAlignment(Pos.CENTER);
	}

	private final Pane getParkManagerPane() {
		final BorderPane border = new BorderPane();
		final VBox v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);

		v.setPadding(new Insets(15, 12, 15, 12));
		v.setSpacing(10);

		Text title = new Text("Park Manager Main Menu");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
		VBox.setMargin(title, titleMargins);

		final Button viewUpcomingJobsBtn = new Button("View Upcoming Jobs");
		final Button makeNewJobsBtn = new Button("Add New Job");
		final Button logOutBtn = new Button("Log out");

		viewUpcomingJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		makeNewJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		logOutBtn.setMaxWidth(MAX_BUTTON_WIDTH);

		final Button backBtn = new Button("Back");
		backBtn.setOnAction(event -> {
			border.setCenter(null);
			v.getChildren().remove(backBtn);
		});

		v.getChildren().addAll(title, viewUpcomingJobsBtn, makeNewJobsBtn,
				logOutBtn);

		border.setTop(userInfo);
		border.setCenter(v);
		getChildren().add(border);

		viewUpcomingJobsBtn.setOnAction(event -> {
			border.setCenter(getUpcomingJobsPane());
			v.getChildren().add(backBtn);
		});

		makeNewJobsBtn.setOnAction(event -> {
//			this.pane = getNewJobFormPane();
//			border.setCenter(getNewJobFormPane());
//			Button backbtn = new Button("Back");
//			backbtn.setOnAction( event1 -> {
//				border.setRight(null);
//				border.setTop(null);
//			});
//			border.setRight(backbtn);

			border.setCenter(getNewJobFormPane());
			v.getChildren().add(backBtn);
		});

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
//			logout(root);
		});

		return border;
	}

	private final VBox getUpcomingJobsPane() {
		final VBox myJobsPane = new VBox();

		final Label label = new Label("My Upcoming Jobs");

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		for (final Job job : ((ParkManager) data.getCurrentUser()).getMyFutureJobs()) {
			final HBox jobEntry = new HBox();
			final Label nameField = new Label(job.getName());
			final Label startField =
					new Label(job.getBeginDateTime().toString());
			final Label endField =
					new Label(job.getEndDateTime().toString());
			final Label parkField = new Label(job.getPark().toString());
			final Button cancelBtn = new Button("Cancel");

			cancelBtn.setOnAction(event -> {
				try {
					data.unsubmitParkJob(job);
				} catch (UrbanParksSystemOperationException e) {
					e.printStackTrace();
				}
			});

			jobEntry.getChildren().addAll(nameField, startField, endField,
					parkField, cancelBtn);
			jobEntry.setSpacing(15);

			myJobsPane.getChildren().addAll(jobEntry, new Separator());
		}

		return myJobsPane;
	}

	private final Pane getNewJobFormPane() {
		final VBox form = new VBox();

		final HBox jobNameBox = new HBox();
		final Label jobNameLabel = new Label("Job Title: ");
		final TextField jobNameField = new TextField();
		jobNameField.setPromptText("Job Title");
		jobNameBox.getChildren().addAll(jobNameLabel, jobNameField);

		final HBox startDateBox = new HBox();
		final Label startDateLabel = new Label("Start Date: ");
		final DatePicker startDatePicker = new DatePicker();
		startDatePicker.setPromptText("Start Date");
		startDateBox.getChildren().addAll(startDateLabel, startDatePicker);

		final HBox endDateBox = new HBox();
		final Label endDateLabel = new Label("End Date: ");
		final DatePicker endDatePicker = new DatePicker();
		endDatePicker.setPromptText("End Date");
		endDateBox.getChildren().addAll(endDateLabel, endDatePicker);

		final HBox parkBox = new HBox();
		final Label parkTextLabel = new Label("Park: ");
		final TextField parkTextField = new TextField();
		parkTextField.setPromptText("Park");
		parkBox.getChildren().addAll(parkTextLabel, parkTextField);

		final HBox detailsBox = new HBox();
		final Label detailsLabel = new Label("Job Description: ");
		final TextArea detailsTextArea = new TextArea();
		detailsTextArea.setPromptText("Job Description");
		detailsBox.getChildren().addAll(detailsLabel, detailsTextArea);

		final Button submitBtn = new Button("Submit Job");

		submitBtn.setOnAction(event -> {
			Park park = new Park(parkTextField.getAccessibleText(),
					new ParkID(data.getParks().getLargestIDNumber() + 1));
			LocalDate startDate = startDatePicker.getValue();
			LocalDate endDate = endDatePicker.getValue();
			final Job newJob = new Job(jobNameField.getAccessibleText(),
					park, new JobID(data.getJobs().getLargestIDNumber() + 1),
					LocalDateTime.of(startDate, LocalTime.MIDNIGHT),
					LocalDateTime.of(endDate, LocalTime.MIDNIGHT),
					detailsTextArea.getAccessibleText(), data.getCurrentUser());

			try {
				data.addNewJobByParkManager(data.getCurrentUser(),
						newJob);
			} catch (UrbanParksSystemOperationException e) {
				e.printStackTrace(); //TODO
			}
		});

		form.getChildren().addAll(jobNameBox, startDateBox, endDateBox,
				parkBox, detailsBox, submitBtn);

		return form;
	}
}
