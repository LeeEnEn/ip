package duke.ui;

/**
 * Handles outputting of visuals to the user.
 */
public class Ui {

    private String hLine = "____________________________________________________________";

    /**
     * Shows a welcome message to the user upon running the program.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        System.out.println(hLine + "\n");
        System.out.println(logo + "\nHello! I'm Duke.Main.Duke\nWhat can I do for you?");
        System.out.println(hLine + "\n");
    }

    /**
     * Prints a goodbye message to the user.
     */
    public void goodbye() {
        echo("Bye. Hope to see you again soon!");
    }

    /**
     * Prints the message between two horizontal lines.
     * @param input Message to be printed.
     */
    public void echo(String input) {
        showLine();
        System.out.println(input);
        showLine();
    }

    /**
     * Prints a horizontal dash to mark the start or end of a message.
     */
    public void showLine() {
        System.out.println(hLine + "\n");
    }
}
