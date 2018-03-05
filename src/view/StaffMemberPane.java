package view;

import exceptions.InvalidJobCollectionCapacityException;
import exceptions.InvalidUserException;
import exceptions.UrbanParksSystemOperationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Job;
import model.UrbanParksData;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.UnaryOperator;

public class StaffMemberPane extends StackPane {
	
	private static final int MAX_BUTTON_WIDTH = 800;

	private final UrbanParksData data;
	private final HBox userInfo;

	public StaffMemberPane(UrbanParksData data, HBox userInfo) {
		super();
		this.data = data;
		this.userInfo = userInfo;
		this.setAlignment(Pos.CENTER);
		getStaffMemberPane();
	}

	private final void getStaffMemberPane() {
		final BorderPane border = new BorderPane();
		final VBox v = new VBox();
		v.setAlignment(Pos.TOP_CENTER);

		v.setPadding(new Insets(15, 12, 15, 12));
		v.setSpacing(10);
		
		Text title = new Text("Staff Member Main Menu");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
        VBox.setMargin(title, titleMargins);

		final Button viewJobsBtn = new Button("View Jobs");
		final Button setJobCapacityBtn = new Button("Set Job Capacity");
		final Button logOutBtn = new Button("Log out");
		
		viewJobsBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		setJobCapacityBtn.setMaxWidth(MAX_BUTTON_WIDTH);
		logOutBtn.setMaxWidth(MAX_BUTTON_WIDTH);

		v.getChildren().addAll(title, viewJobsBtn, setJobCapacityBtn,
				logOutBtn);
		
		border.setTop(userInfo);
		border.setCenter(v);
		getChildren().add(border);

		viewJobsBtn.setOnAction(event -> border.setCenter(getJobsPane(border,
				data.getJobs().getList())));


		setJobCapacityBtn.setOnAction(event ->
				border.setCenter(getJobCapacityPane(border)));

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
			StackPane root = (StackPane) this.getParent();
			UrbanParks.logout(root);
		});
	}

	private final ScrollPane getJobsPane(BorderPane root, List<Job> jobList) {
		final ScrollPane sp = new ScrollPane();
		final VBox myJobsPane = new VBox();
		final Label label = new Label("Upcoming Jobs");

		Button backBtn = new Button("Back");
		myJobsPane.getChildren().add(backBtn);
		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getStaffMemberPane();
		});
		
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		myJobsPane.setSpacing(15);
		myJobsPane.setPadding(new Insets(10, 0, 0, 10));

		myJobsPane.getChildren().add(label);

		HBox hbox = new HBox();
		VBox vbox1 = new VBox();
		Label filterALbl = new Label("Date 1");
		DatePicker datePicker1 = new DatePicker();
		vbox1.getChildren().addAll(filterALbl, datePicker1);

		VBox vbox2 = new VBox();
		Label filterBLbl = new Label("Date 2");
		DatePicker datePicker2 = new DatePicker();
		vbox2.getChildren().addAll(filterBLbl, datePicker2);


		Button filterBtn = new Button("Filter");
		filterBtn.setOnAction(event -> {
			List<Job> filteredJobs = data.getJobsInDateRange(LocalDateTime.of
					(datePicker1.getValue(), LocalTime.MIDNIGHT),
					LocalDateTime.of(datePicker2.getValue(), LocalTime.MIDNIGHT));
			root.getChildren().clear();
			root.setCenter(getJobsPane(root, filteredJobs));
		});

		hbox.getChildren().addAll(vbox1, vbox2, filterBtn);
		myJobsPane.getChildren().add(hbox);
		
		for (final Job job : jobList) {
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

			jobEntry.getChildren().addAll(nameField, startField, endField,
					parkField);
			jobEntry.setSpacing(15);

			myJobsPane.getChildren().addAll(jobEntry, new Separator());
		}
		
		sp.setContent(myJobsPane);

		return sp;
	}
	
	private final Pane getJobCapacityPane(BorderPane root) {
		final BorderPane border = new BorderPane();
		final VBox jobCapacityData = new VBox();

		Button backBtn = new Button("Back");
		jobCapacityData.getChildren().add(backBtn);

		backBtn.setOnAction(event -> {
			root.getChildren().clear();
			getStaffMemberPane();
		});


		final Text title = new Text("Set the maximum number of pending park " + 
				"jobs:");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
        VBox.setMargin(title, titleMargins);
        
        final Text currentNum = new Text("Current maximum number of jobs: " +
				data.getCurrentMaxJobs());
        currentNum.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        final TextField capacityField = new TextField();

		UnaryOperator<TextFormatter.Change> integerFilter = change -> {
			String input = change.getText();
			if (input.matches("[0-9]*")) {
				return change;
			}
			return null;
		};
        capacityField.setTextFormatter(new TextFormatter<String>(integerFilter));

        final Button setJobCapacityBtn = new Button("Set New Job Capacity");
		final HBox capacityBox = new HBox();
		capacityBox.getChildren().addAll(setJobCapacityBtn, capacityField);
		
		jobCapacityData.getChildren().addAll(title, currentNum, capacityBox);


		setJobCapacityBtn.setOnAction(event -> {
			try {
				data.setJobCollectionCapacity(Integer.valueOf(capacityField
						.getText()));
			} catch (InvalidJobCollectionCapacityException | InvalidUserException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Urban Parks");
				alert.setHeaderText("Job Capacity Error");

				if (e instanceof InvalidJobCollectionCapacityException) {
					alert.setContentText("Sorry, the capacity you specified " +
							"is invalid. Please choose a non-negative integer" +
							".");
				} else {
					alert.setContentText("Sorry, you do not have permission " +
							"to change the job collection capacity.");
				}
			}


			root.getChildren().clear();
			root.setCenter(getJobCapacityPane(root));
		});

		border.setTop(userInfo);
		border.setCenter(jobCapacityData);
		
		return border;
	}
}
