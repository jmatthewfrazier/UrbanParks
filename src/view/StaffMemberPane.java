package view;

import java.time.LocalDate;
import java.util.List;

import exceptions.InvalidJobCollectionCapacityException;
import exceptions.InvalidUserException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Job;
import model.UrbanParksData;

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

		viewJobsBtn.setOnAction(event -> {
			border.setCenter(getJobsPane(border, null, null));
		});
		
		setJobCapacityBtn.setOnAction(event -> {
			border.setCenter(getJobCapacityPane(border));
		});

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
			StackPane root = (StackPane) this.getParent();
			UrbanParks.logout(root);
		});
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
			getStaffMemberPane();
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
	
	private final Pane getJobCapacityPane(BorderPane root) {
		final BorderPane border = new BorderPane();
		final VBox container = new VBox();
		final VBox jobCapacityData = new VBox();
		jobCapacityData.setAlignment(Pos.TOP_CENTER);
		jobCapacityData.setPadding(new Insets(15, 12, 15, 12));
		jobCapacityData.setSpacing(10);

		Button backBtn = new Button("Back");

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
        capacityField.setTextFormatter(
        		new TextFormatter<String>(integerFilter));

        final Button setJobCapacityBtn = new Button("Set New Job Capacity");
		final HBox capacityBox = new HBox();
		capacityBox.setAlignment(Pos.CENTER);
		capacityBox.getChildren().addAll(setJobCapacityBtn, capacityField);
		
		jobCapacityData.getChildren().addAll(title, currentNum, capacityBox);
		
		setJobCapacityBtn.setOnAction(event -> {
			try{
				int input = Integer.parseInt(capacityField.getCharacters()
						.toString());
				try {
					data.setJobCollectionCapacity(Integer.valueOf(input));
				} catch (InvalidJobCollectionCapacityException | 
						InvalidUserException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Urban Parks");
					alert.setHeaderText("Job Capacity Error");

					if (e instanceof InvalidJobCollectionCapacityException) {
						alert.setContentText("Sorry, the capacity you " +
								"specified is invalid. Please choose a " + 
								"non-negative integer.");
					} else {
						alert.setContentText("Sorry, you do not have " + 
								"permission to change the job collection " + 
								"capacity.");
					}
				}
				root.setCenter(getJobCapacityPane(root));
			}catch(NumberFormatException ex){
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setContentText("Input must be an integer.");
				alert.showAndWait();
			}
		});
		
		container.getChildren().addAll(backBtn, jobCapacityData);
		border.setTop(userInfo);
		border.setCenter(container);
		
		return border;
	}
}
