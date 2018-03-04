package view;

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

public class StaffMemberPane extends StackPane {
	
	private static final int MAX_BUTTON_WIDTH = 800;

	private final UrbanParksData data;
	private final HBox userInfo;
	private Pane pane;
//	private ArrayList<Job> jobList;
	private ToggleGroup jobGroup;


	public StaffMemberPane(UrbanParksData data, HBox userInfo) {
		super();
		this.data = data;
		this.userInfo = userInfo;
		this.pane = getStaffMemberPane();
		this.setAlignment(Pos.CENTER);
	}

	private final Pane getStaffMemberPane() {
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
//		final TextField capacityField = new TextField();
//		final HBox capacityBox = new HBox();
//		capacityBox.getChildren().addAll(setJobCapacityBtn, capacityField);

//		String text = capacityField.getCharacters().toString();
//		setJobCapacityBtn.setOnAction(event -> {
//			boolean isNumber = true;
//
//			for (int i = 0; i < text.length(); i++) {
//				if (text.charAt(i) < '0' || text.charAt(i) > '9') {
//					isNumber = false;
//				}
//			}
//
//			if (isNumber) {
//				try {
//					data.setJobCollectionCapacity(Integer.valueOf(text));
//				} catch (UrbanParksSystemOperationException e) {
//					e.printStackTrace();
//				}
//			}
//		});


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
			border.setRight(getJobsPane());
			Button backbtn = new Button("Back");
			backbtn.setOnAction( event1 -> {
				border.setRight(null);
				border.setTop(null);
			});
			border.setTop(backbtn);
		});
		
		setJobCapacityBtn.setOnAction(event -> {
			getChildren().remove(border);
			getChildren().add(getJobCapacityPane());
		});

		logOutBtn.setOnAction(event -> {
			getChildren().remove(border);
//			logout(root);
		});

		return this;
	}

	private final ScrollPane getJobsPane() {
		final ScrollPane sp = new ScrollPane();
		final VBox myJobsPane = new VBox();
		final Label label = new Label("Upcoming Jobs");
		
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

		hbox.getChildren().addAll(vbox1, vbox2);
		myJobsPane.getChildren().add(hbox);
		
		int i = 0;
		for (final Job job : data.getJobs().getList()) {
			final HBox jobEntry = new HBox();
			final RadioButton rb = new RadioButton("" + i);
			rb.setToggleGroup(jobGroup);
			if (i == 0) rb.setSelected(true);
			i++;
			final Label nameField = new Label(job.getName());
			final Label startField =
					new Label(job.getBeginDateTime().toString());
			final Label endField =
					new Label(job.getEndDateTime().toString());
			final Label parkField = new Label(job.getPark().toString());

			jobEntry.getChildren().addAll(rb, nameField, startField, endField,
					parkField);
			jobEntry.setSpacing(15);

			myJobsPane.getChildren().addAll(jobEntry, new Separator());
		}
		
		sp.setContent(myJobsPane);

		return sp;
	}
	
	private final Pane getJobCapacityPane() {
		final BorderPane border = new BorderPane();
		final VBox jobCapacityData = new VBox();
		
		final Text title = new Text("Set the maximum number of pending park " + 
				"jobs:");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		Insets titleMargins = new Insets(20, 10, 0, 0);
        VBox.setMargin(title, titleMargins);
        
        final Text currentNum = new Text("Current maximum number of jobs: " +
				data.getCurrentMaxJobs());
        currentNum.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        
        final TextField capacityField = new TextField();
        final Button setJobCapacityBtn = new Button("Set New Job Capacity");
		final HBox capacityBox = new HBox();
		capacityBox.getChildren().addAll(setJobCapacityBtn, capacityField);
		
		jobCapacityData.getChildren().addAll(title, currentNum, capacityBox);

		String text = capacityField.getCharacters().toString();
		setJobCapacityBtn.setOnAction(event -> {
			boolean isNumber = true;

			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) < '0' || text.charAt(i) > '9') {
					isNumber = false;
				}
			}

			if (isNumber) {
				try {
					data.setJobCollectionCapacity(Integer.valueOf(text));
				} catch (UrbanParksSystemOperationException e) {
					e.printStackTrace();
				}
			}
		});

		border.setTop(userInfo);
		border.setCenter(jobCapacityData);
		
		return border;
	}
}
