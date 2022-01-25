package duke.task;

public class Todo extends Task {

    public Todo(String description) {
        super(description, "T", null);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}