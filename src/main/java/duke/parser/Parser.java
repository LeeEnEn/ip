package duke.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Handles user input and commands.
 */
public class Parser {

    private boolean isExit = false;
    private Ui ui = new Ui();

    /**
     * Handles user command and give an appropriate reply.
     * @param userInput commands specified by the user.
     */
    public void parse(String userInput) {
        String[] input = userInput.split(" ", 2);

        Type type = getEnumType(input[0]);
        TaskList taskList = new TaskList();

        try {
            switch (type) {
            case BYE:
                ui.goodbye();
                isExit = true;
                break;
            case LIST:
                taskList.readList();
                break;
            case MARK:
                taskList.markTask(Integer.parseInt(input[1]) - 1);
                break;
            case UNMARK:
                taskList.unMarkTask(Integer.parseInt(input[1]) - 1);
                break;
            case DELETE:
                taskList.delete(Integer.parseInt(input[1]) - 1);
                break;
            case FIND:
                taskList.find(input[1]);
                break;
            default:
                Task task = getTask(input, type);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            ui.echo("Duke.Main.Duke has noticed that the number you provided does not "
                    + "\nmatch the number of task you have.\nPlease enter a valid task number!");
        } catch (NumberFormatException e) {
            ui.echo("OOPS!!! Please enter a valid task number!");
        }
    }

    /**
     * Takes in the given user input and return a newly created task specified by the user.
     * Catches any invalid command and reply appropriately.
     * @param userInput User command.
     * @param type Type of task to be created.
     * @return A new task specified by the user.
     */
    private Task getTask(String[] userInput, Type type) {
        Task task = null;

        try {
            if (userInput.length == 1) {
                throw new DukeException("Error");
            }

            String[] strings = userInput[1].split("/");

            if (strings.length == 1 && !userInput[0].equals("todo")) {
                throw new ArrayIndexOutOfBoundsException();
            }

            switch (type) {
            case DEADLINE:
                task = new Deadline(strings[0], LocalDate.parse(strings[1].substring(3)));
                break;
            case EVENT:
                task = new Event(strings[0], LocalDate.parse(strings[1].substring(3)));
                break;
            case TODO:
                task = new Todo(userInput[1].strip());
                break;
            default:
                break;
            }

            if (task == null || userInput[1].strip().isEmpty()) {
                throw new DukeException("Error in task");
            }
            return task;

        } catch (DukeException e) {
            if (isCommandRecognized(userInput[0])) {
                ui.echo("☹ OOPS!!! The description of a " + userInput[0] + " cannot be empty.");
            } else if (e.getMessage().equals("Error in task") || e.getMessage().equals("Error")) {
                ui.echo("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.echo("☹ OOPS!!! Description is empty or invalid timeframe!");
        } catch (DateTimeParseException e) {
            ui.echo("☹ OOPS!!! Duke.Main.Duke could not understand given date!"
                    + " Please enter in yyyy-mm-dd format!");
        }
        return task;
    }

    /**
     * Returns a boolean depending on Duke understanding the given command.
     * @param input User command.
     * @return True if Duke understands the command, false otherwise.
     */
    private boolean isCommandRecognized(String input) {
        String temp = input.toLowerCase();
        return temp.equals("todo") || temp.equals("event") || temp.equals("deadline") || temp.equals("delete");
    }

    /**
     * Returns a boolean that signifies the user's wish to end the program.
     * @return A boolean.
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Returns user commands as an Enum type.
     * @param input User command.
     * @return An Enum type corresponding to the user's command.
     */
    public Type getEnumType(String input) {
        String temp = input.toLowerCase();
        Type type;

        switch (temp) {
        case "bye":
            type = Type.BYE;
            break;
        case "deadline":
            type = Type.DEADLINE;
            break;
        case "delete":
            type = Type.DELETE;
            break;
        case "event":
            type = Type.EVENT;
            break;
        case "find":
            type = Type.FIND;
            break;
        case "list":
            type = Type.LIST;
            break;
        case "mark":
            type = Type.MARK;
            break;
        case "unmark":
            type = Type.UNMARK;
            break;
        case "todo":
            type = Type.TODO;
            break;
        default:
            type = Type.NULL;
        }

        return type;
    }

    public enum Type {
        BYE,
        DEADLINE,
        DELETE,
        EVENT,
        FIND,
        LIST,
        MARK,
        UNMARK,
        TODO,
        NULL
    }
}
