package duke.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.tasklist.TaskList;

/**
 * Handles user input and commands.
 */
public class Parser {

    /**
     * Handles user command and give an appropriate reply.
     *
     * @param userInput commands specified by the user.
     * @return A string reply that corresponds to the command from the user.
     */
    public String parse(String userInput) {
        assert userInput != null : "User command to be parsed cannot be null.";
        assert userInput.length() > 0 : "User command to be parsed cannot be empty.";

        String[] input = userInput.split(" ", 2);

        Type type = getEnumType(input[0]);
        TaskList taskList = new TaskList();

        try {
            switch (type) {
            case NULL:
                throw new DukeException("I'm sorry, but I don't know what that means.");
            case BYE:
                return "Good bye!";
            case LIST:
                return taskList.readList();
            case MARK:
                if (input.length == 1 || input[1].equals("")) {
                    throw new DukeException("You are missing the parameter for this mark command!");
                }
                return taskList.markTask(input[1]);
            case UNMARK:
                if (input.length == 1 || input[1].equals("")) {
                    throw new DukeException("You are missing the parameter for this unmark command!");
                }
                return taskList.unMarkTask(input[1]);
            case DELETE:
                if (input.length == 1 || input[1].equals("")) {
                    throw new DukeException("You are missing the parameter for this delete command!");
                }
                return taskList.delete(input[1]);
            case FIND:
                if (input.length == 1 || input[1].equals("")) {
                    throw new DukeException("You are missing the parameter for this find command!");
                }
                return taskList.find(input[1]);
            default:
                return getTask(input, type);
            }
        } catch (DukeException e) {
            return e.getMessage();
        }
    }

    /**
     * Takes in the given user input and return a newly created task specified by the user.
     * Catches any invalid command and reply appropriately.
     *
     * @param userInput User command.
     * @param type Type of task to be created.
     * @return A string reply that corresponds to the command from the user.
     */
    private String getTask(String[] userInput, Type type) throws DukeException {
        assert userInput != null : "User command to be parsed cannot be null.";
        assert type != null : "Command type cannot be null.";

        TaskList taskList = new TaskList();

        try {
            Task task;
            String[] strings = userInput[1].split("/");

            if (userInput[1].equals("")) {
                throw new DukeException("OOPS!!! The description of a " + userInput[0] + " cannot be empty.");
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
                task = null;
            }

            boolean isDuplicate = taskList.isTaskDuplicate(task);

            if (isDuplicate) {
                return "Hmm, you seem to have added this task before.";
            } else {
                taskList.add(task);
                return "Got it, I have added " + task.getUserInput() + " to the list!\n";
            }

        } catch (DukeException e) {
            return e.getMessage();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "OOPS!!! Description is empty or invalid timeframe!";
        } catch (DateTimeParseException e) {
            return "OOPS!!! Duke could not understand given date! Please enter in yyyy-mm-dd format!";
        }
    }

    /**
     * Returns user commands as an Enum type.
     *
     * @param input User command.
     * @return An Enum type corresponding to the user's command.
     */
    public Type getEnumType(String input) {
        assert input != null : "User command to get command type cannot be null.";
        assert input.length() > 0 : "User command to get command type cannot be empty.";

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
