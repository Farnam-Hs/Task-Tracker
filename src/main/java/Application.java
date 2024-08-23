import cli.TaskManagerCLI;
import repository.TaskRepositoryImpl;
import service.TaskServiceImpl;

import java.io.IOException;
import java.nio.file.Path;

public class Application {

    private static final String PATH = "tasks.json";

    public static void main(String[] args) throws IOException {
        new TaskManagerCLI(new TaskServiceImpl(new TaskRepositoryImpl(Path.of(PATH)))).run();
    }
}