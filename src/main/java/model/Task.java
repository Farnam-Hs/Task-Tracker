package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {

    private final int id;
    private final String description;
    private final TaskStatus status;
    private final LocalDateTime createdTime;
    private final LocalDateTime updatedTime;

    public Task(int id, String description, TaskStatus status, LocalDateTime createdTime, LocalDateTime updatedTime) {
        checkId(id);
        checkDescription(description);
        checkUpdateTime(createdTime, updatedTime);

        this.id = id;
        this.description = description;
        this.status = status;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public boolean isUpdated() {
        return updatedTime.isAfter(createdTime);
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("[Task: %d | Description: %s | Status: %s | Created: %s | Updated: %s]",
                this.id,
                this.description,
                this.status.getTitleCaseStatus(),
                this.createdTime.toString(),
                isUpdated() ? this.updatedTime.toString() : "N/A"
        );
    }

    private void checkId(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("Task ID must be positive");
    }

    private void checkDescription(String description) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or empty");
        if (description.length() > 255)
            throw new IllegalArgumentException("Description is too long");
    }

    private void checkUpdateTime(LocalDateTime createdTime, LocalDateTime updatedTime) {
        if (updatedTime.isBefore(createdTime))
            throw new IllegalArgumentException("Updated time cannot be before Created time");
    }
}
