package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public final class UrbanParks extends Application {
	
	public static final int MINIMUM_REMOVAL_BUFFER = 2;

	private final UrbanParksData data;

	public static void main(String[] args) { launch(args); }

	public UrbanParks() {
		super();
		data = new UrbanParksData();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Urban Parks");
		Pane root = getLoginPane();
		Scene scene = new Scene(root);

//		final MenuBar menuBar = new MenuBar();
//
//		final Menu menuFile = new Menu("File");
//
//		menuBar.getMenus().add(menuFile);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private final Pane getLoginPane() {
		final GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		final Text title = new Text("Welcome to Urban Parks");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(title, 0, 0, 2, 1);

		final Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		final TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		final Button btn = new Button("Sign in");
		btn.setDefaultButton(true);

		final HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		btn.setOnAction(event -> {
			final UserID userID = new UserID(userTextField.getText());
			data.loginUserID(userID);

			if (data.getCurrentUser().equals(User.getNullUser())) {
				System.out.println("Log in was unsuccessful.");
			} else {
				System.out.println("Current user: "
						+ data.getCurrentUser().getFullName());
			}
		});

		return grid;
	}
	
	public final Pane getVolunteerPane(){
		final BorderPane border = new BorderPane();

		Text text = new Text("User: " + data.getCurrentUser().getFullName()
				+ "login as Volunteer");
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		border.setTop(text);
		border.setCenter(addButtons());

		return border;
	}

	public final Pane getParkManagerPane(){
		final BorderPane border = new BorderPane();

		Text text = new Text("User: " + data.getCurrentUser().getFullName()
				+ "login as Park Manager");
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		border.setTop(text);
		border.setCenter(addButtons());

		return border;
	}

	public final HBox addButtons(){
		final HBox hbox = new HBox();
		final HBox hb = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hb.setPadding(new Insets(20, 12, 20, 12));
	    hbox.setSpacing(10);
	    hb.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");
	    hb.setStyle("-fx-background-color: #336699;");

	    Button buttonSignup = new Button("Sign up");
	    buttonSignup.setPrefSize(100, 20);

	    Button buttonUnvolunteer = new Button("Unvolunteer");// What if the volunteer did not sign up for any job before, do we show this button to the volunteer?
	    buttonUnvolunteer.setPrefSize(100, 20);
	    hbox.getChildren().addAll(buttonSignup, buttonUnvolunteer);
	    
	    Button buttonViewMyJob = new Button("View My Job");
	    buttonViewMyJob.setPrefSize(100, 20);
	    
	    Button buttonLogout = new Button("Log out");
	    buttonLogout.setPrefSize(100, 20);
	    hb.getChildren().addAll(buttonViewMyJob, buttonLogout);
	    
	    buttonSignup.setOnAction(event -> {
	    	SignupPane();
	    });
	    
	    buttonUnvolunteer.setOnAction(event -> {
	    	UnvolunteerPane();
	    });
	    
	    buttonViewMyJob.setOnAction(event -> {
	    	ViewMyJobPane();
	    });
	    
	    buttonLogout.setOnAction(event -> {
	    	Logout();
	    });
	    

	    return hbox;
	}
	
	public final Pane SignupPane(){
		BorderPane bord = new BorderPane();
		
		final VBox vb = new VBox();
		vb.setPadding(new Insets(10));
		vb.setSpacing(10);
		
		Text name = new Text("Available Jobs");
		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		vb.getChildren().add(name);
		
		ArrayList<Job> futureJobList = data.getAllFutureJobs();
		ArrayList<Job> volunteerJobList = (ArrayList<Job>) ((Volunteer) data.getCurrentUser()).getJobList();
		
		if (futureJobList.size() == 0){
			Text message = new Text("Sorry, there is currently no available jobs");
			message.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
			bord.setCenter(message);
		}
		else{
			for (int i = 0; i< futureJobList.size(); i++){
				if (!volunteerJobList.contains(futureJobList.get(i)) &&
						((Volunteer) data.getCurrentUser()).canSignUpForJob(futureJobList.get(i))){
					Job theJob = futureJobList.get(i);
					Button btn = new Button();
					btn.setText(futureJobList.get(i).getName());
					btn.setOnAction(event -> {
						final Pane displayPane = DisplayJobDetail(theJob);
						bord.setRight(displayPane);
					});
					vb.getChildren().add(btn);
				}
			}
			//Trying to add buttons on the bottom to confirm sign up or cancel that will bring the user to the previous page
			
//			final HBox hb = new HBox();
//			final HBox hbBtn = new HBox(10);
//			hbBtn.setAlignment(Pos.BOTTOM_CENTER);
//			final Button btnConfirm = new Button();
//			final Button btnCancel = new Button();
//			btnConfirm.setText("Confirm");
//			btnCancel.setText("Cancel");
//			btnConfirm.setOnAction(event -> {
//				((Volunteer) data.getCurrentUser()).signUpForJob(theJob);
//			});
//			
//			btnCancel.setOnAction(event -> {
//				
//			});
//			hb.getChildren().addAll(btnConfirm, btnCancel);
//			
//			bord.setLeft(vb);
//			bord.setBottom(hb);
		}
		
		return bord;
	}
	
	public final Pane UnvolunteerPane(){
		BorderPane bord = new BorderPane();
		
		final VBox vb = new VBox();
		vb.setPadding(new Insets(10));
		vb.setSpacing(10);
		
		Text name = new Text("Sign Up Jobs");
		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
		vb.getChildren().add(name);
		
		ArrayList<Job> volunteerJobList = (ArrayList<Job>) ((Volunteer) data.getCurrentUser()).getJobList();
		
		if (volunteerJobList.size() == 0){
			Text message = new Text("Sorry, you don't have Job sign up already");
			message.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
			bord.setLeft(message);
		}
		else{
			for (int i = 0; i < volunteerJobList.size(); i++){
				if (volunteerJobList.get(i).isJobStartAfterEqualDate(
							LocalDateTime.now().plusDays(MINIMUM_REMOVAL_BUFFER))){
					Job theJob = volunteerJobList.get(i);
					Button btn = new Button();
					btn.setText(volunteerJobList.get(i).getName());
					btn.setOnAction(event -> {
						bord.setRight(DisplayJobDetail(theJob));
					});
					vb.getChildren().add(btn);
				}
			}
			bord.setLeft(vb);
		}
		
		return bord;
	}
	
	public final ScrollPane ViewMyJobPane(){
		ScrollPane scroll = new ScrollPane();
		
		Volunteer vol = (Volunteer) data.getCurrentUser();
		ArrayList<Job> jobList = (ArrayList<Job>) vol.getJobList();
		
		for (int i = 0; i < jobList.size(); i++){
			//TO-DO I am not sure how to set the size of this scroll pane
		}
		
		
		return scroll;
	}
	
	public final Pane DisplayJobDetail(Job theJob){
		GridPane grid = new GridPane();
		
		final Label jobName = new Label("Job Name: " + theJob.getName());
		grid.add(jobName, 0, 0);
		
		final Label parkName = new Label("Park Name: " + theJob.getPark().toString());
		grid.add(parkName, 0, 2);
		
		final Label startTime = new Label("Start Time: " + 
					theJob.getBeginDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		grid.add(startTime, 0, 4);
		
		final Label endTime = new Label("End Time: " + 
				theJob.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		grid.add(endTime, 0, 6);
		
		
		
		return grid;
	}
	
	public final void Logout(){
		data.storeCollectionsIntoFile();
		getLoginPane();
	}
	
}
