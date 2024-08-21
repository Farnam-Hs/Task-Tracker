package service;

import model.Task;

import java.util.List;

public interface TaskService {

    void saveTasks();

    void addTask(String description);

    void updateTask(int id, String description);

    void deleteTask(int id);

    void markTaskAsTodo(int id);

    void markTaskAsInProgress(int id);

    void markTaskAsDone(int id);

    List<Task> getAllTasks();

    List<Task> getTodoTasks();

    List<Task> getInProgressTasks();

    List<Task> getDoneTasks();
}
