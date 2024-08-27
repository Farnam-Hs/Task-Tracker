package repository.helper;

import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.*;

public class JasonMapper {

    public static String tasksToJson(Set<Task> tasks) {
        return tasks.stream()
                .map(JasonMapper::convertTaskToJson)
                .collect(joining(",", "[", "]"));
    }

    public static Set<Task> jsonToTasks(String json) {
        if (json == null || json.isEmpty())
            return new HashSet<>();
        return Arrays.stream(json.substring(2, json.length() - 2).split("},\\{"))
                .map(JasonMapper::convertJsonToTask)
                .collect(toSet());
    }

    private static String convertTaskToJson(Task task) {
        return "{" +
                "\"id\":" + task.getId() + "," +
                "\"description\":\"" + task.getDescription() + "\"," +
                "\"status\":\"" + task.getStatus() + "\"," +
                "\"createdTime\":\"" + task.getCreatedTime() + "\"," +
                "\"updatedTime\":\"" + task.getUpdatedTime() + "\"" +
                "}";
    }

    private static Task convertJsonToTask(String json) {
        String[] keyValuePairs = json.replaceAll("\"", "").split(","); // key:value

        int id = 0;
        String description = null;
        TaskStatus status = null;
        LocalDateTime createdTime = null;
        LocalDateTime updatedTime = null;

        for (String keyValuePair : keyValuePairs) {
            String key = keyValuePair.substring(0, keyValuePair.indexOf(":"));
            String value = keyValuePair.substring(keyValuePair.indexOf(":") + 1);
            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "description" -> description = value;
                case "status" -> status = TaskStatus.valueOf(value);
                case "createdTime" -> createdTime = LocalDateTime.parse(value);
                case "updatedTime" -> updatedTime = LocalDateTime.parse(value);
            }
        }

        return new Task(id, description, status, createdTime, updatedTime);
    }
}
