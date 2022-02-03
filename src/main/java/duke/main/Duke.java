package duke.main;

import java.util.Scanner;

import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Behaves as a task manager to keep record of the different tasks.
 */
public class Duke {

    private Parser parser;
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    /**
     * Creates a Duke object that takes in a filepath for reading and writing of data.
     * Initializes all necessary classes.
     * @param filePath Path in which the list of task is stored.
     */
    public Duke(String filePath) {
        parser = new Parser();
        storage = new Storage(filePath);
        taskList = new TaskList();
        ui = new Ui();
    }

    /**
     * Execute the Duke bot.
     */
    private void run() {
        taskList.fetchData(storage.readData());
        ui.showWelcome();

        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            parser.parse(scanner.nextLine());
            isExit = parser.isExit();
        }
        storage.saveData(taskList.getList());
    }

    public static void main(String[] args) {
        new Duke("tasks.txt").run();
    }
}
