package view;

public class TerminalApplication {

    // TODO
//    public static void main(String args[]) {
//        // Run UI
//        JobMap jobs = new JobMap();
//        System.out.println("Hello, welcome to Urban Parks!\n");
//
//        System.out.println("Enter your name: ");
//
//    }


    public static void main(String[] args) {
        //create the actual collection to store jobs in
        //fire up the UI and pass it the system's job storage collection
        UrbanParksSystemUserInterface newInterface =
                new UrbanParksSystemUserInterface();

        //run the actual program
        newInterface.runInterface();
    }
}
