package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.*;
import static model.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private final LocalDateTime dateTime;

    public TaskTest() {
        this.dateTime = LocalDateTime.of(2004, 1, 17, 20, 0, 0);
    }

    @Test
    public void task_should_not_be_created_if_id_is_zero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(0, "practice java", TODO, dateTime, dateTime)
        );
    }

    @Test
    void task_should_not_be_created_if_id_is_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(-10, "practice java", TODO, dateTime, dateTime)
        );
    }

    @Test
    void task_should_not_be_created_if_description_is_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(1, null, TODO, dateTime, dateTime)
        );
    }

    @Test
    void task_should_not_be_created_if_description_is_empty() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(1, "", TODO, dateTime, dateTime)
        );
    }

    @Test
    void task_should_not_be_created_if_description_is_only_whitespaces() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(1, "         ", TODO, dateTime, dateTime)
        );
    }

    @Test
    void task_should_not_be_created_if_description_length_is_more_than_255() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(1, "a".repeat(256), TODO, dateTime, dateTime)
        );
    }

    @Test
    void task_should_not_be_created_if_updatedTime_is_before_createdTime() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(1, "practice time", TODO, dateTime, dateTime.minusHours(1))
        );
    }

    @Test
    void task_should_be_created_normally_with_valid_givens() {
        Task task = new Task(1, "practice java", TODO, dateTime, dateTime);

        assertEquals(task.getId(), 1);
        assertEquals(task.getDescription(), "practice java");
        assertEquals(task.getStatus(), TODO);
        assertEquals(task.getCreatedTime(), dateTime);
        assertEquals(task.getUpdatedTime(), dateTime);
    }

    @Test
    void task_update_status_should_be_true_if_updated_time_is_after_created_time() {
        LocalDateTime now = now();
        Task task = new Task(1, "practice java", TODO, dateTime, dateTime.plusHours(1));

        assertTrue(task.isUpdated());
    }

    @Test
    void two_tasks_should_be_equal_if_their_ids_are_same() {
        Task taskOne = new Task(1, "practice java", IN_PROGRESS, now().minusDays(1), now().minusDays(1));
        Task taskTwo = new Task(1, "practice coding", TODO, dateTime, dateTime);

        assertEquals(taskOne, taskTwo);
    }

    @Test
    void two_tasks_should_not_be_equal_if_their_ids_are_different() {
        Task taskOne = new Task(1, "practice java", TODO, dateTime, dateTime);
        Task taskTwo = new Task(2, "practice java", TODO, dateTime, dateTime);

        assertNotEquals(taskOne, taskTwo);
    }

    @Test
    void tasks_should_sorted_by_their_ids() {
        List<Task> actualTasks = new ArrayList<>(List.of(
                new Task(3, "practice java", TODO, dateTime, dateTime),
                new Task(1, "write documents", IN_PROGRESS, dateTime.minusHours(3), dateTime.minusHours(1)),
                new Task(2, "push the codes", DONE, dateTime.minusDays(1), dateTime.minusDays(1))
        ));

        Collections.sort(actualTasks);

        List<Task> expectedTasks = new ArrayList<>(List.of(
                new Task(1, "write documents", IN_PROGRESS, dateTime.minusHours(3), dateTime.minusHours(1)),
                new Task(2, "push the codes ", DONE, dateTime.minusDays(1), dateTime.minusDays(1)),
                new Task(3, "practice java", TODO, dateTime, dateTime)
        ));
        assertEquals(actualTasks, expectedTasks);
    }
}
