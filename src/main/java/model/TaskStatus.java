package model;

public enum TaskStatus {
    TODO("Todo"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String titleCaseStatus;

    private TaskStatus(String titleCaseStatus) {
        this.titleCaseStatus = titleCaseStatus;
    }

    public String getTitleCaseStatus() {
        return titleCaseStatus;
    }
}
