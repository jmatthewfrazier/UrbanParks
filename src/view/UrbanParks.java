package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.UrbanParksData;
import model.User;
import model.UserID;
import model.UserRole;

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

		Pane root = new Pane();
		displayLoginPane(root);
//		root.getChildren().add(new VolunteerPane(data));


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

	protected final void displayLoginPane(Pane root) {
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

		root.getChildren().add(grid);

		btn.setOnAction(event -> {
			final UserID userID = new UserID(userTextField.getText());
			data.loginUserID(userID);


			if (data.getCurrentUser().equals(User.getNullUser())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setContentText("Invalid username");

				alert.showAndWait();
			} else {
				root.getChildren().remove(grid);
				if (data.getCurrentUser().getUserRole()
						.equals(UserRole.VOLUNTEER)) {
					root.getChildren().add(new VolunteerPane(data));
				} else if (data.getCurrentUser().getUserRole()
						.equals(UserRole.PARK_MANAGER)) {
					root.getChildren().add(new ParkManagerPane(data));
				} else if (data.getCurrentUser().getUserRole()
						.equals(UserRole.STAFF_MEMBER)) {
					root.getChildren().add(new StaffMemberPane(data));
				}
			}
		});
		}
	
//	public final void DisplayVolunteerPane(Pane root){
//		final BorderPane border = new BorderPane();
//
//		Text text = new Text("User: " + data.getCurrentUser().getFullName()
//				+ "\tlogin as Volunteer");
//		text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//		border.setTop(text);
//
//		final HBox hbox = new HBox();
//	    hbox.setPadding(new Insets(15, 12, 15, 12));
//	    hbox.setSpacing(10);
//	    hbox.setStyle("-fx-background-color: #336699;");
//
//	    Button buttonSignUp = new Button("Sign up");
//	    buttonSignUp.setPrefSize(100, 20);
//
//	    Button buttonUnvolunteer = new Button("Unvolunteer");
//	    buttonUnvolunteer.setPrefSize(100, 20);
//	    hbox.getChildren().addAll(buttonSignUp, buttonUnvolunteer);
//
//	    Button buttonViewMyJob = new Button("View My Job");
//	    buttonViewMyJob.setPrefSize(100, 20);
//
//	    Button buttonLogout = new Button("Log out");
//	    buttonLogout.setPrefSize(100, 20);
//	    hbox.getChildren().addAll(buttonViewMyJob, buttonLogout);
//
//	    border.setBottom(hbox);
//	    root.getChildren().add(border);
//
//	    buttonSignUp.setOnAction(event -> {
//	    	root.getChildren().remove(border);
//	    	DisplaySignUpPane(root);
//	    });
//
//	    buttonUnvolunteer.setOnAction(event -> {
//	    	root.getChildren().remove(border);
//	    	DisplayUnvolunteerPane(root);
//	    });
//
//	    buttonViewMyJob.setOnAction(event -> {
//	    	root.getChildren().remove(border);
//	    	ViewMyJobPane(root);
//	    });
//
//	    buttonLogout.setOnAction(event -> {
//	    	root.getChildren().remove(border);
//	    	logout(root);
//	    });
//	}
//
//
//	public final void DisplaySignUpPane(Pane root){
//		BorderPane bord = new BorderPane();
//
//		final VBox vb = new VBox();
//		vb.setPadding(new Insets(10));
//		vb.setSpacing(10);
//
//		Text name = new Text("Available Jobs");
//		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//		vb.getChildren().add(name);
//
//		ArrayList<Job> futureJobList = data.getAllFutureJobs();
//		ArrayList<Job> volunteerJobList = (ArrayList<Job>) ((Volunteer) data.getCurrentUser()).getJobList();
//
//		if (futureJobList.size() == 0){
//			Text message = new Text("Sorry, there is currently no available jobs");
//			message.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//			bord.setCenter(message);
//		}
//		else{
//			for (int i = 0; i< futureJobList.size(); i++){
//				if (!volunteerJobList.contains(futureJobList.get(i)) &&
//						((Volunteer) data.getCurrentUser()).canSignUpForJob(futureJobList.get(i))){
//					Job theJob = futureJobList.get(i);
//					Button btn = new Button();
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
//		root.getChildren().add(bord);
//	}
//
//	public final void DisplayUnvolunteerPane(Pane root) {
//		BorderPane bord = new BorderPane();
//
//		final VBox vb = new VBox();
//		vb.setPadding(new Insets(10));
//		vb.setSpacing(10);
//
//		Text name = new Text("Sign Up Jobs");
//		name.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//		vb.getChildren().add(name);
//
//		ArrayList<Job> volunteerJobList = (ArrayList<Job>) ((Volunteer) data.getCurrentUser()).getJobList();
//
//		if (volunteerJobList.size() == 0){
//			Text message = new Text("Sorry, you don't have Job sign up already");
//			message.setFont(Font.font("Tahoma", FontWeight.BOLD, 14));
//			bord.setLeft(message);
//		}
//		else{
//			for (int i = 0; i < volunteerJobList.size(); i++){
//				if (volunteerJobList.get(i).isJobStartAfterEqualDate(
//							LocalDateTime.now().plusDays(MINIMUM_REMOVAL_BUFFER))){
//					Job theJob = volunteerJobList.get(i);
//					Button btn = new Button();
//					btn.setText(volunteerJobList.get(i).getName());
//					btn.setOnAction(event -> {
//						bord.setRight(displayJobDetail(theJob));
//					});
//					vb.getChildren().add(btn);
//				}
//			}
//			bord.setLeft(vb);
//		}
//		root.getChildren().add(bord);
//	}
//
//	public final void ViewMyJobPane(Pane root){
//		ScrollPane scroll = new ScrollPane();
//
//		Volunteer vol = (Volunteer) data.getCurrentUser();
//		ArrayList<Job> jobList = (ArrayList<Job>) vol.getJobList();
//
//		for (int i = 0; i < jobList.size(); i++){
//			//TO-DO I am not sure how to set the size of this scroll pane
//		}
//		root.getChildren().add(scroll);
//	}
//
//	public final Pane displayJobDetail(Job theJob){
//		GridPane grid = new GridPane();
//
//		final Label jobName = new Label("Job Name: " + theJob.getName());
//		grid.add(jobName, 0, 0);
//
//		final Label parkName = new Label("Park Name: " + theJob.getPark().toString());
//		grid.add(parkName, 0, 2);
//
//		final Label startTime = new Label("Start Time: " +
//					theJob.getBeginDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		grid.add(startTime, 0, 4);
//
//		final Label endTime = new Label("End Time: " +
//				theJob.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		grid.add(endTime, 0, 6);
//
//		return grid;
//	}

	private final HBox getUserInfo() {
		HBox userInfo = new HBox();
		userInfo.setAlignment(Pos.CENTER);
		userInfo.setMinHeight(15);
		Text user = new Text("User: " + data.getCurrentUser().getFullName());
		user.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		Text role = new Text("Role: " + data.getCurrentUser().getUserRole());
		role.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		HBox.setHgrow(user, Priority.ALWAYS);
		HBox.setHgrow(role, Priority.ALWAYS);
		userInfo.getChildren().addAll(user, role);
		return userInfo;
	}
	
	public final void logout(Pane root){
		data.storeCollectionsIntoFile();
		displayLoginPane(root);
	}
	
}
