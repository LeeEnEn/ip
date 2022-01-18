import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    private ArrayList<Task> list = new ArrayList<>();
    private String hLine = "____________________________________________________________";

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        Duke duke = new Duke();
        duke.echo(logo + "\nHello! I'm Duke\nWhat can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String[] input = scanner.nextLine().split(" ", 2);

            duke.echo(input);

            if (input[0].equals("bye")) {
                break;
            }
        }
    }

    public void echo(String userInput) {
        System.out.println(hLine + "\n" + userInput + "\n" + hLine);
    }

    public void echo(String[] userInput) {
        System.out.println(hLine + "\n");
        try {
            switch (userInput[0]) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                case "list":
                    readList();
                    break;
                case "mark":
                    list.get(Integer.parseInt(userInput[1]) - 1).markTask(true);
                    break;
                case "unmark":
                    list.get(Integer.parseInt(userInput[1]) - 1).markTask(false);
                    break;
                case "delete":
                    delete(Integer.parseInt(userInput[1]) - 1);
                    break;
                default:
                    addList(userInput);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Duke has noticed that the number you provided does not \nmatch the number of task you have." +
                    "\nPlease enter a valid task number!");
        }
        System.out.println(hLine + "\n");
    }

    public void addList(String[] userInput) {
        Task task = null;

        try {
            if (userInput.length == 1) {
                throw new DukeException("Error");
            }

            String[] strings = userInput[1].split("/");

            if (strings.length == 1 && !userInput[0].equals("todo")) {
                throw new ArrayIndexOutOfBoundsException();
            }

            switch (userInput[0]) {
                case "deadline":
                    task = new Deadline(strings[0], strings[1]);
                    break;
                case "event":
                    task = new Event(strings[0], strings[1]);
                    break;
                case "todo":
                    task = new Todo(userInput[1]);
                    break;
            }

            if (task == null || userInput[1].strip().isEmpty()) {
                throw new DukeException("Error in task");
            } else {
                list.add(task);
                System.out.println("Got it. I have added this task:\n" + task
                        + "\nNow you have " + list.size() + " tasks in the list.");
            }

        } catch (DukeException e) {
            if (isCommandRecognized(userInput[0])) {
                System.out.println(" ☹ OOPS!!! The description of a " + userInput[0] + " cannot be empty.");
            } else if (e.getMessage().equals("Error in task") || e.getMessage().equals("Error")) {
                System.out.println("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("☹ OOPS!!! Description is empty or invalid timeframe!");
        }
    }

    public void readList() {
        System.out.println("Here are the task in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).toString());
        }
    }

    private boolean isCommandRecognized(String input) {
        String temp = input.toLowerCase();
        return temp.equals("todo") || temp.equals("event") || temp.equals("deadline") || temp.equals("delete");
    }

    public void delete(int pos) {
        String description = this.list.get(pos).toString();
        this.list.remove(pos);
        System.out.println("Noted. I've removed this task:\n" + description + "\nNow you have " + this.list.size() + " tasks in the list.");
    }
}
