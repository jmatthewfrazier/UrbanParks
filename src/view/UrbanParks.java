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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.util.List;

public final class UrbanParks extends Application {

	private final UrbanParksData data;

	public static void main(String[] args) { launch(args); }

	private UrbanParks() {
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
		border.setLeft(addFirstGridPane());
		border.setRight(addSecondGridPane());
		border.setBottom(addHBox());

		return border;
	}

	public final Pane addFirstGridPane(){
		final GridPane grid = new GridPane();

		Text title = new Text("Future Jobs");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));

		List<Job> jobList = ((Volunteer) data.getCurrentUser()).getJobList();

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
//				new Volunteer();
			}
		});

		hb.getChildren().addAll(buttonHome);

		return hb;
	}
}
