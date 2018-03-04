package view;

import exceptions.LessThanMinDaysAwayException;
import exceptions.VolunteerDailyJobLimitException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Job;
import model.UrbanParksData;

public class VolunteerPane extends StackPane {

	private static final int MAX_BUTTON_WIDTH = 800;

	private final UrbanParksData data;
//	private Pane pane;
	private HBox userInfo;

	public VolunteerPane(UrbanParksData data, HBox userInfo) {
		super();
		this.data = data;
//		this.pane = getVolunteerPane();
		this.setAlignment(Pos.CENTER);
		this.userInfo = userInfo;
		getVolunteerPane();
	}

	public final Pane getVolunteerPane() {
		final BorderPane border = new BorderPane();
		final VBox v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);

		v.setPadding(new Insets(15, 12, 15, 12));
		v.setSpacing(10);

		Text title = new Text("Volunteer Main Menu");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
		VBox.setMargin(title, titleMargins);

		final Button viewMyJobsBtn = new Button("View My Jobs");
		final Button viewFutureJobsBtn = new Button("View Future Jobs");
		final Button logOutBtn = new Button("Log out");

		viewMyJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		viewFutureJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		logOutBtn.setMaxWidth(MAX_BUTTON_WIDTH);

		v.getChildren().addAll(title, viewMyJobsBtn, viewFutureJobsBtn,
				logOutBtn);

		border.setTop(userInfo);
		border.setCenter(v);
		getChildren().add(border);

		viewMyJobsBtn.setOnAction(event -> {
			border.setRight(getMyJobsPane());
			Button backbtn = new Button("Back");
			backbtn.setOnAction( event1 -> {
				border.setRight(null);
				border.setTop(null);
			});
			border.setTop(backbtn);
		});

		viewFutureJobsBtn.setOnAction(event -> {
			border.setRight(getFutureJobsPane());
			Button backbtn = new Button("Back");
			backbtn.setOnAction( event1 -> {
					border.setRight(null);
					border.setTop(null);
			});
			border.setTop(backbtn);
		});

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
//			logout(root);
		});

		return this;
	}

	private final VBox getMyJobsPane() {
		final VBox myJobsPane = new VBox();
		final Label label = new Label("My Jobs");

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		for (final Job job : data.getAllFutureJobs()) {
			if (job.hasVolunteer(data.getCurrentUser())) {
				final HBox jobEntry = new HBox();
				final Label nameField = new Label(job.getName());
				final Label startField =
						new Label(job.getBeginDateTime().toString());
				final Label endField =
						new Label(job.getEndDateTime().toString());
				final Label parkField = new Label(job.getPark().toString());
				final Button cancelBtn = new Button("Cancel");

				cancelBtn.setOnAction(event ->
						data.cancelAssignment(data.getCurrentUser(), job));

				jobEntry.getChildren().addAll(nameField, startField, endField,
						parkField, cancelBtn);
				jobEntry.setSpacing(15);

				myJobsPane.getChildren().add(jobEntry);
			}
		}

		return myJobsPane;
	}

	private final VBox getFutureJobsPane() {
		final VBox myJobsPane = new VBox();
		final Label label = new Label("Jobs");

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		System.out.println(data.getAllFutureJobs().size());

		for (final Job job : data.getAllFutureJobs()) {
			if (!job.hasVolunteer(data.getCurrentUser())) {
				final HBox jobEntry = new HBox();
				final Label nameField = new Label(job.getName());
				final Label startField =
						new Label(job.getBeginDateTime().toString());
				final Label endField =
						new Label(job.getEndDateTime().toString());
				final Label parkField = new Label(job.getPark().toString());
				final Button signUpBtn = new Button("Sign Up");

				signUpBtn.setOnAction(event -> {
					try {
						data.assign(data.getCurrentUser(), job);
					} catch (VolunteerDailyJobLimitException | LessThanMinDaysAwayException e) {

						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Urban Parks");
						alert.setHeaderText("Invalid Job Sign Up");

						if (e instanceof VolunteerDailyJobLimitException) {
							alert.setContentText("Sorry, you are currently " +
									"already signed up for a job on this date.");
						} else {
							alert.setContentText("Sorry, this job is too soon" +
									" in the future to sign up for.");
						}

						alert.showAndWait();
					}
				});

				jobEntry.getChildren().addAll(nameField, startField, endField,
						parkField, signUpBtn);
				jobEntry.setSpacing(15);

				myJobsPane.getChildren().addAll(jobEntry, new Separator());
			}
		}

		return myJobsPane;
	}


//	public final void DisplaySignUpPane(){
//		BorderPane bord = new BorderPane();
//
//		final VBox vb = new VBox();
//		vb.setPadding(new Insets(10));
//		vb.setSpacing(10);
//
//		final Text name = new Text("Available Jobs");
//		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//		vb.getChildren().add(name);
//
//		final List<Job> futureJobList = data.getAllFutureJobs();
//		final List<Job> volunteerJobList =
//				((Volunteer) data.getCurrentUser()).getJobList();
//
//		if (futureJobList.size() == 0){
//			final Text message = new Text("Sorry, there is currently no " +
//					"available jobs");
//			message.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//			bord.setCenter(message);
//		}
//		else{
//			for (int i = 0; i< futureJobList.size(); i++){
//				if (!volunteerJobList.contains(futureJobList.get(i)) &&
//						((Volunteer) data.getCurrentUser()).canSignUpForJob(futureJobList.get(i))){
//					final Job theJob = futureJobList.get(i);
//					final Button btn = new Button();
//					btn.setText(futureJobList.get(i).getName());
//					btn.setOnAction(event -> {
//						final Pane displayPane = displayJobDetail(theJob);
//						bord.setRight(displayPane);
//					});
//					vb.getChildren().add(btn);
//				}
//			}
//			//Trying to add buttons on the bottom to confirm sign up or cancel that will bring the user to the previous page
//
////			final HBox hb = new HBox();
////			final HBox hbBtn = new HBox(10);
////			hbBtn.setAlignment(Pos.BOTTOM_CENTER);
////			final Button btnConfirm = new Button();
////			final Button btnCancel = new Button();
////			btnConfirm.setText("Confirm");
////			btnCancel.setText("Cancel");
////			btnConfirm.setOnAction(event -> {
////				((Volunteer) data.getCurrentUser()).signUpForJob(theJob);
////			});
////
////			btnCancel.setOnAction(event -> {
////
////			});
////			hb.getChildren().addAll(btnConfirm, btnCancel);
////
////			bord.setLeft(vb);
////			bord.setBottom(hb);
//		}
//		getChildren().add(bord);
//	}
//
//	public final void DisplayUnvolunteerPane() {
//		final BorderPane bord = new BorderPane();
//
//		final VBox vb = new VBox();
//		vb.setPadding(new Insets(10));
//		vb.setSpacing(10);
//
//		final Text name = new Text("Sign Up Jobs");
//		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//		vb.getChildren().add(name);
//
//		final List<Job> volunteerJobList =
//				((Volunteer) data.getCurrentUser()).getJobList();
//
//		if (volunteerJobList.size() == 0){
//			Text message = new Text("Sorry, you don't have Job sign up already");
//			message.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//			bord.setLeft(message);
//		}
//		else{
//			for (int i = 0; i < volunteerJobList.size(); i++){
//				if (volunteerJobList.get(i).isJobStartAfterEqualDate(
//						LocalDateTime.now().plusDays(MINIMUM_REMOVAL_BUFFER))){
//					final Job theJob = volunteerJobList.get(i);
//					final Button btn = new Button();
//					btn.setText(volunteerJobList.get(i).getName());
//					btn.setOnAction(event -> {
//						bord.setRight(displayJobDetail(theJob));
//					});
//					vb.getChildren().add(btn);
//				}
//			}
//			bord.setLeft(vb);
//		}
//		getChildren().add(bord);
//	}
//
//	public final void ViewMyJobPane(){
//		final ScrollPane scroll = new ScrollPane();
//
//		final Volunteer vol = (Volunteer) data.getCurrentUser();
//		final List<Job> jobList = vol.getJobList();
//
//		for (int i = 0; i < jobList.size(); i++){
//			//TO-DO I am not sure how to set the size of this scroll pane
//		}
//		getChildren().add(scroll);
//	}
//
//	public final Pane displayJobDetail(Job theJob){
//		final GridPane grid = new GridPane();
//
//		final Label jobName = new Label("Job Name: " + theJob.getName());
//		grid.add(jobName, 0, 0);
//
//		final Label parkName = new Label("Park Name: " + theJob.getPark().toString());
//		grid.add(parkName, 0, 2);
//
//		final Label startTime = new Label("Start Time: " +
//				theJob.getBeginDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		grid.add(startTime, 0, 4);
//
//		final Label endTime = new Label("End Time: " +
//				theJob.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		grid.add(endTime, 0, 6);
//
//		return grid;
//	}
}
