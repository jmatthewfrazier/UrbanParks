package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Job;
import model.UrbanParksData;
import model.User;

public final class Volunteer extends Application {

	private final UrbanParksData data;

	public static void main(String[] args) { launch(args); }

	public Volunteer() {
		super();
		data = new UrbanParksData();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Volunteer Interface");
		Pane root = getVolunteerPane();
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	public final Pane getVolunteerPane(){
		final BorderPane border = new BorderPane();
		
		Text text = new Text("User: " + data.getCurrentUser().getFullName()
						+ "login as Volunteer");
		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		border.setTop(text);
		border.setLeft(addFirstGridPane());
		border.setRight(addSecondGridPane());
		border.setBottom(addHBox());
		
		return border;
	}
	
	public final Pane addFirstGridPane(){
		final GridPane grid = new GridPane();
		
		Text title = new Text("Future Jobs");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
		
		User currentUser = data.getCurrentUser();
		ArrayList<Job> jobList = ((Volunteer) currentUser).getJobList();

	    return grid;
		
		
	}
	
	public final Pane addSecondGridPane(){
		final GridPane grid = new GridPane();

	    return grid;
	}
	
	public final HBox addHBox(){
		final HBox hb = new HBox();
		hb.setPadding(new Insets(15, 12, 15, 12));
		hb.setSpacing(10);
		hb.setStyle("-fx-background-color: #336699");
		
		Button buttonHome = new Button("Home");
		buttonHome.setPrefSize(100, 20);
		
		buttonHome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				new Volunteer();
			}
		});
		
		hb.getChildren().addAll(buttonHome);
		
		return hb;
	}
}
