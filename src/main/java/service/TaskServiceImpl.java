package service;

import model.Task;
import model.TaskStatus;
import repository.TaskRepository;
import exception.NoSuchTaskException;

import java.util.Set;
import java.util.List;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static model.TaskStatus.*;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final Set<Task> tasks;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.tasks = taskRepository.getTasks();
    }

    @Override
    public void saveTasks() {
        taskRepository.saveTasks(tasks);
    }

    @Override
    public void addTask(String description) {
        LocalDateTime now = now();
        tasks.add(new Task(getNewId(), description, TODO, now, now));
    }

    @Override
    public void updateTask(int id, String description) {
        Task originalTask = findTask(id);

        Task updatedTask = new Task(
                originalTask.getId(),
                description,
                originalTask.getStatus(),
                originalTask.getCreatedTime(),
                now()
        );

        tasks.remove(originalTask);
        tasks.add(updatedTask);
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(findTask(id));
    }

    @Override
    public void markTaskAsTodo(int id) {
        markTaskStatus(id, TODO);
    }

    @Override
    public void markTaskAsInProgress(int id) {
        markTaskStatus(id, IN_PROGRESS);
    }

    @Override
    public void markTaskAsDone(int id) {
        markTaskStatus(id, DONE);
    }

    @Override
    public List<Task> getAllTasks() {
        return tasks.stream()
                .sorted()
                .toList();
    }

    @Override
    public List<Task> getTodoTasks() {
        return getTasksByStatus(TODO);
    }

    @Override
    public List<Task> getInProgressTasks() {
        return getTasksByStatus(IN_PROGRESS);
    }

    @Override
    public List<Task> getDoneTasks() {
        return getTasksByStatus(DONE);
    }

    private int getNewId() {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }

    private Task findTask(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchTaskException(String.format("Task with ID %d not found", id)));
    }

    private void markTaskStatus(int taskId, TaskStatus newStatus) {
        Task originalTask = findTask(taskId);

        Task changedStatusTask = new Task(
                originalTask.getId(),
                originalTask.getDescription(),
                newStatus,
                originalTask.getCreatedTime(),
                originalTask.getUpdatedTime()
        );

        tasks.remove(originalTask);
        tasks.add(changedStatusTask);
    }

    private List<Task> getTasksByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .sorted()
                .toList();
    }

}
