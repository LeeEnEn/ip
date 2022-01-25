public class Deadline extends Task {

    protected String by;

    Deadline(String description, String by) {
        super(description);
        this.by = by.substring(3);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}