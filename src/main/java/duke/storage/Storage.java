package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

/**
 * Handles reading and writing of data.
 */
public class Storage {

    private File file;

    /**
     * Creates a Storage object that has access to the text file as specified by the filepath.
     * @param filePath Path in which the text file is stored or to be stored.
     */
    public Storage(String filePath) {
        file = new File(filePath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads data from the text file and output it as a list of tasks.
     * @return An array list consisting of all previously stored tasks.
     */
    public ArrayList<Task> readData() {
        ArrayList<Task> list = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String[] temp = scanner.nextLine().split("\\|");
                Task task;

                if (temp[0].equals("D")) {
                    task = new Deadline(temp[2], LocalDate.parse(temp[3]));
                } else if (temp[0].equals("E")) {
                    task = new Event(temp[2], LocalDate.parse(temp[3]));
                } else {
                    task = new Todo(temp[2]);
                }

                if (temp[1].equals("X")) {
                    task.markTask(true, false);
                }
                list.add(task);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("OOPS!!! Duke.Main.Duke is unable to locate your file!");
        }
        return list;
    }

    /**
     * Saves the current tasks into the text file as specified by the filepath.
     * @param list List of tasks created by the user.
     */
    public void saveData(ArrayList<Task> list) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            for (Task task: list) {
                fileWriter.write(task.getCharId()
                        + "|"
                        + task.getMark()
                        + "|"
                        + task.getUserInput()
                        + "|"
                        + task.getDate()
                        + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("OOPS!!! Duke.Main.Duke is unable to write to your file!");
        }
    }
}
