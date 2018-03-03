package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.util.List;

public final class UrbanParks extends Application {

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

	    Button buttonUnvolunteer = new Button("Unvolunteer");
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
		// Here the left side is VBox buttons that shows the future jobs that are no sign up for (buttons will contains the name of the job, nothing else)
		// The right pane will show jobs that once the user select a job
		GridPane grid = new GridPane();
		
		return grid;
	}
	
	public final Pane UnvolunteerPane(){
		// Here the left side is VBox buttons that shows the jobs sign up already
		// The right pane will be their input to unvolunteer a job
		GridPane grid = new GridPane();
		
		return grid;
	}
	
	public final Pane ViewMyJobPane(){
		//Here the we have two options;
		//One, having a left side VBox buttons that you can select to see, and display on the right
		//Or just display them all the informaton in one pane set to central.
		GridPane grid = new GridPane();
		
		return grid;
	}
	
	public final void Logout(){
		data.storeCollectionsIntoFile();
		getLoginPane();
	}
	
}
