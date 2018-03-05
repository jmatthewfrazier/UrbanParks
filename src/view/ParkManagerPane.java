package view;

import exceptions.InvalidUserException;
import exceptions.JobIDNotFoundInCollectionException;
import exceptions.LessThanMinDaysAwayException;
import exceptions.UrbanParksSystemOperationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ParkManagerPane extends StackPane {

	private static final int MAX_BUTTON_WIDTH = 800;

	private final UrbanParksData data;
	private HBox userInfo;

	public ParkManagerPane(UrbanParksData data, HBox userInfo) {
		super();
		this.data = data;
		this.userInfo = userInfo;
		this.setAlignment(Pos.CENTER);
		getParkManagerPane();
	}

	private final void getParkManagerPane() {
		final BorderPane border = new BorderPane();
		final VBox v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);

		v.setPadding(new Insets(15, 12, 15, 12));
		v.setSpacing(10);

		Text title = new Text("Park Manager Main Menu");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
		VBox.setMargin(title, titleMargins);

		final Button viewUpcomingJobsBtn = new Button("View My Upcoming Jobs");
		final Button viewAllJobsBtn = new Button("View All Jobs");
		final Button makeNewJobsBtn = new Button("Add New Job");
		final Button logOutBtn = new Button("Log out");

		viewUpcomingJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		viewAllJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		makeNewJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		logOutBtn.setMaxWidth(MAX_BUTTON_WIDTH);

		v.getChildren().addAll(title, viewUpcomingJobsBtn, viewAllJobsBtn, 
				makeNewJobsBtn, logOutBtn);

		border.setTop(userInfo);
		border.setCenter(v);
		getChildren().add(border);

		viewUpcomingJobsBtn.setOnAction(event ->
				border.setCenter(getUpcomingJobsPane(border)));
		
		viewAllJobsBtn.setOnAction(event ->
				border.setCenter(getJobsPane(border, null, null)));

		makeNewJobsBtn.setOnAction(event ->
				border.setCenter(getNewJobFormPane(border)));

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
			StackPane root = (StackPane) this.getParent();
			UrbanParks.logout(root);
		});
	}

	private final ScrollPane getUpcomingJobsPane(BorderPane root) {
		final VBox myJobsPane = new VBox();
		final Label label = new Label("My Upcoming Jobs");
		final Button backBtn = new Button("Back");
		final HBox h = new HBox();
		h.setAlignment(Pos.CENTER);
		h.getChildren().addAll(label, backBtn);
		myJobsPane.getChildren().add(h);

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getParkManagerPane();
		});

		myJobsPane.getChildren().add(label);

		for (final Job job : ((ParkManager) data.getCurrentUser())
				.getMyFutureJobs()) {
			final HBox jobEntry = new HBox();
			final Label nameField = new Label(job.getName());
			final Label startField =
					new Label(job.getBeginDateTime().getMonth() + " " + job
							.getBeginDateTime().getDayOfMonth() + ", " + job
							.getBeginDateTime().getYear());
			final Label endField =
					new Label(job.getEndDateTime().getMonth() + " " + job
							.getEndDateTime().getDayOfMonth() + ", " + job
							.getEndDateTime().getYear());
			final Label parkField = new Label(job.getPark().toString());
			final Button cancelBtn = new Button("Delete");

			cancelBtn.setOnAction(event -> {
				try {
					data.unsubmitParkJob(job);
					root.setCenter(getUpcomingJobsPane(root));
				} catch (LessThanMinDaysAwayException | InvalidUserException
						| JobIDNotFoundInCollectionException e) {

					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Urban Parks");
					alert.setHeaderText("Invalid Job Cancellation");
					if (e instanceof LessThanMinDaysAwayException) {
						alert.setContentText("Sorry, this job is too soon in " +
								"the future to cancel.");
					} else if (e instanceof
							JobIDNotFoundInCollectionException) {
						alert.setContentText("Sorry, you do not have " +
								"permission to cancel this job.");
					} else {
						alert.setContentText("Sorry, you may not delete a job" +
								" that you did not create.");
					}

					alert.showAndWait();
				}
			});

			jobEntry.getChildren().addAll(nameField, startField, endField,
					parkField, cancelBtn);
			jobEntry.setSpacing(15);

			myJobsPane.getChildren().addAll(jobEntry, new Separator());
		}

		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		sp.setContent(myJobsPane);

		return sp;
	}

	private final Pane getNewJobFormPane(Pane root) {
		final VBox form = new VBox();

		Button backBtn = new Button("Back");
		form.getChildren().add(backBtn);
		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getParkManagerPane();
		});

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
			Park park = new Park(parkTextField.getText(),
					new ParkID(data.getParks().getLargestIDNumber() + 1));
			LocalDate startDate = startDatePicker.getValue();
			LocalDate endDate = endDatePicker.getValue();
			final Job newJob = new Job(jobNameField.getCharacters().toString(),
					park, new JobID(data.getJobs().getLargestIDNumber() + 1),
					LocalDateTime.of(startDate, LocalTime.MIDNIGHT),
					LocalDateTime.of(endDate, LocalTime.MIDNIGHT),
					detailsTextArea.getText(), data.getCurrentUser());

			try {
				data.addNewJobByParkManager(data.getCurrentUser(), newJob);
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Urban Parks");
				alert.setHeaderText("New job submitted.");
				alert.setContentText("Your job was successfully submitted!");

				alert.showAndWait();

				root.getChildren().clear();
				getParkManagerPane();
			} catch (UrbanParksSystemOperationException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Urban Parks");
				alert.setHeaderText("Invalid Job Sign Up");
//				if (e instanceof VolunteerDailyJobLimitException) {
//					alert.setContentText("Sorry, you are currently " +
//							"already signed up for a job on this " + 
//							"date.");
//				} else {
//					alert.setContentText("Sorry, this job is too soon" +
//							" in the future to sign up for.");
//				}
				alert.setContentText(e.getMsgString());
				alert.showAndWait();
			}
		});

		form.getChildren().addAll(jobNameBox, startDateBox, endDateBox,
				parkBox, detailsBox, submitBtn);

		return form;
	}
	
	private final ScrollPane getJobsPane(BorderPane root, LocalDate start, 
			LocalDate end) {
		final ScrollPane sp = new ScrollPane();
		final VBox myJobsPane = new VBox();
		final Label label = new Label("Upcoming Jobs");
		List<Job> selectedJobList;
		LocalDate startDate = start;
		LocalDate endDate = end;

		Button backBtn = new Button("Back");
		myJobsPane.getChildren().add(backBtn);
		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getParkManagerPane();
		});
		
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.BOTTOM_LEFT);
		hbox.setSpacing(10);
		
		VBox vbox1 = new VBox();
		Label filterALbl = new Label("Start of Date Range");
		DatePicker datePicker1 = new DatePicker(startDate);
		vbox1.getChildren().addAll(filterALbl, datePicker1);

		VBox vbox2 = new VBox();
		Label filterBLbl = new Label("End of Date Range");
		DatePicker datePicker2 = new DatePicker(endDate);
		vbox2.getChildren().addAll(filterBLbl, datePicker2);
		
		Button goBtn = new Button("Go");
		goBtn.setOnAction(event -> {
			LocalDate startingDate = datePicker1.getValue();
			LocalDate endingDate = datePicker2.getValue();
			if ((startingDate == null) || (endingDate == null)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setContentText("Please select two dates.");
				alert.showAndWait();
			} else if (endingDate.isBefore(startingDate)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setContentText("The start of the date range must come " + 
				"before the end of the date range.\nPlease try again.");
				alert.showAndWait();
			} else {
				root.setCenter(getJobsPane(root, startingDate, endingDate));
			}
		});
		
		Button clearBtn = new Button("Clear");
		clearBtn.setOnAction(event -> {
			root.setCenter(getJobsPane(root, null, null));
		});
		
		if (startDate == null || endDate == null) {
			selectedJobList = data.getJobs().getList();
		} else {
			selectedJobList = data.getJobsInDateRange(startDate.atStartOfDay(), 
					endDate.atStartOfDay());
		}

		Label rangeText = new Label("Select a Date Range");
		hbox.getChildren().addAll(rangeText, vbox1, vbox2, goBtn, clearBtn);
		myJobsPane.getChildren().add(hbox);
		
		for (final Job job : selectedJobList) {
			final HBox jobEntry = new HBox();
			jobEntry.setAlignment(Pos.CENTER_LEFT);
			final Label nameField = new Label(job.getName());
			final Label startField =
					new Label(job.getBeginDateTime().getMonth() + " " + job
							.getBeginDateTime().getDayOfMonth() + ", " + job
							.getBeginDateTime().getYear());
			final Label endField =
					new Label(job.getEndDateTime().getMonth() + " " + job
							.getEndDateTime().getDayOfMonth() + ", " + job
							.getEndDateTime().getYear());
			final Label parkField = new Label(job.getPark().toString());
			final Button viewBtn = new Button("View Details");
			viewBtn.setOnAction(event -> {
				myJobsPane.getChildren().clear();
				myJobsPane.getChildren().add(viewJobDetailsPane(job, root, 
						startDate, endDate));
			});

			jobEntry.getChildren().addAll(nameField, startField, endField,
					parkField, viewBtn);
			jobEntry.setSpacing(15);

			myJobsPane.getChildren().addAll(jobEntry, new Separator());
		}
		
		sp.setContent(myJobsPane);

		return sp;
	}
	
	private final VBox viewJobDetailsPane(Job job, BorderPane root, 
			LocalDate start, LocalDate end) {
		VBox jobDetails = new VBox();
		jobDetails.setAlignment(Pos.CENTER_LEFT);
		
		Button backBtn = new Button("Back");
		backBtn.setOnAction(event -> {
			root.setCenter(getJobsPane(root, start, end));
		});

		Text title = new Text("Job Details");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
        VBox.setMargin(title, titleMargins);
        
		Insets dataMargins = new Insets(10, 10, 0, 0);
		
        Text jobTitle = new Text("Job Title: " + job.getName());
        jobTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        VBox.setMargin(jobTitle, dataMargins);
        
        Text jobID = new Text("Job ID: " + job.getID().getJobIDNumber());
        jobID.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        VBox.setMargin(jobID, dataMargins);
        
        Text jobStart = new Text("Start Date: " + job.getBeginDateTime()
        		.toLocalDate());
        jobStart.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        VBox.setMargin(jobStart, dataMargins);
        
        Text jobEnd = new Text("End Date: " + job.getEndDateTime()
        		.toLocalDate());
        jobEnd.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        VBox.setMargin(jobEnd, dataMargins);
        
        Text jobPark = new Text("Location: " + job.getPark());
        jobPark.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        VBox.setMargin(jobPark, dataMargins);
        
        Text jobDescription = new Text("Description: " + job.getDescription());
        jobDescription.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        VBox.setMargin(jobDescription, dataMargins);
        jobDescription.setWrappingWidth(800);
        
        jobDetails.getChildren().addAll(backBtn, title, jobTitle, jobID, 
        		jobStart, jobEnd, jobPark, jobDescription);
		
		return jobDetails;
	}
}
