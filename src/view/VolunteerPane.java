package view;

import exceptions.LessThanMinDaysAwayException;
import exceptions.VolunteerDailyJobLimitException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Job;
import model.UrbanParksData;

public class VolunteerPane extends StackPane {

	private static final int MAX_BUTTON_WIDTH = 800;

	private final UrbanParksData data;
	private HBox userInfo;

	public VolunteerPane(UrbanParksData data, HBox userInfo) {
		super();
		this.data = data;
		this.setAlignment(Pos.CENTER);
		this.userInfo = userInfo;
		getVolunteerPane();
	}

	public final void getVolunteerPane() {
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

		viewMyJobsBtn.setOnAction(event ->
				border.setCenter(getMyJobsPane(border)));

		viewFutureJobsBtn.setOnAction(event ->
				border.setCenter(getFutureJobsPane(border)));

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
			StackPane root = (StackPane) this.getParent();
			UrbanParks.logout(root);
		});
	}

	private final ScrollPane getMyJobsPane(BorderPane root) {
		final VBox myJobsPane = new VBox();
		final Label label = new Label("My Jobs");

		Button backBtn = new Button("Back");
		myJobsPane.getChildren().add(backBtn);

		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getVolunteerPane();
		});

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		for (final Job job : data.getAllFutureJobs()) {
			if (job.hasVolunteer(data.getCurrentUser())) {
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
				final Button cancelBtn = new Button("Cancel");

				cancelBtn.setOnAction(event -> {
					data.cancelAssignment(data.getCurrentUser(), job);
					root.setCenter(getMyJobsPane(root));
				});

				jobEntry.getChildren().addAll(nameField, startField, endField,
						parkField, cancelBtn);
				jobEntry.setSpacing(15);

				myJobsPane.getChildren().add(jobEntry);
			}
		}

		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		sp.setContent(myJobsPane);

		return sp;
	}

	private final ScrollPane getFutureJobsPane(BorderPane root) {
		final VBox myJobsPane = new VBox();
		final Label label = new Label("Jobs");

		Button backBtn = new Button("Back");
		myJobsPane.getChildren().add(backBtn);

		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getVolunteerPane();
		});

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		for (final Job job : data.getAllFutureJobs()) {
			if (!job.hasVolunteer(data.getCurrentUser())) {
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
				final Button signUpBtn = new Button("Sign Up");

				signUpBtn.setOnAction(event -> {
					try {
						data.assign(data.getCurrentUser(), job);
						root.setCenter(getFutureJobsPane(root));
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

		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		sp.setContent(myJobsPane);

		return sp;
	}
}
