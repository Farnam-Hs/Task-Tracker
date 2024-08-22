package repository;

import model.Task;
import repository.helper.JasonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TaskRepositoryImpl implements TaskRepository {

    private final Path path;

    public TaskRepositoryImpl(Path path) {
        this.path = path;
    }

    @Override
    public void saveTasks(Set<Task> tasks) throws IOException {
        if (!Files.exists(path))
            Files.createFile(path);
        Files.writeString(path, JasonMapper.tasksToJson(tasks));
    }

    @Override
    public Set<Task> getTasks() throws IOException {
        if (!Files.exists(path))
            return new HashSet<>();
        return JasonMapper.jsonToTasks(Files.readString(path));
    }
}
