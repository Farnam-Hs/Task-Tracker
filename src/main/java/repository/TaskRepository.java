package repository;

import model.Task;

import java.util.Set;

public interface TaskRepository {

    void saveTasks(Set<Task> tasks);

    Set<Task> getTasks();
}
