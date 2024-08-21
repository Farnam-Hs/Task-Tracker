package service;

import exception.NoSuchTaskException;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.repository.FakeTaskRepositoryImpl;

import java.util.List;

import static model.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceImplTest {

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(new FakeTaskRepositoryImpl());
    }

    @Test
    void task_service_must_be_able_to_add_a_new_task() {
        addOneTasksToTaskService();

        assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    void first_new_task_id_should_be_1() {
        addOneTasksToTaskService();

        Task firstTask = taskService.getAllTasks().getFirst();
        assertEquals(1, firstTask.getId());
    }

    @Test
    void second_new_task_id_should_be_2() {
        addTwoTasksToTaskService();

        Task secondTask = taskService.getAllTasks().getLast();
        assertEquals(2, secondTask.getId());
    }

    @Test
    void new_task_description_should_be_the_as_same_as_adding_time_without_updating() {
        addOneTasksToTaskService();

        Task newTask = taskService.getAllTasks().getFirst();
        assertEquals("Practice Java", newTask.getDescription());
    }

    @Test
    void new_task_status_should_be_todo_without_marking() {
        addOneTasksToTaskService();

        Task newTask = taskService.getAllTasks().getFirst();
        assertEquals(TODO, newTask.getStatus());
    }

    @Test
    void new_task_created_time_and_update_time_must_be_equal_before_any_update() {
        addOneTasksToTaskService();

        Task newTask = taskService.getAllTasks().getFirst();
        assertEquals(newTask.getCreatedTime(), newTask.getUpdatedTime());
    }

    @Test
    void should_throw_exception_if_task_does_not_exists_while_updating() {
        addOneTasksToTaskService(); // Task ID is 1

        assertThrows(NoSuchTaskException.class, () -> taskService.updateTask(2, "Practice Python"));
    }

    @Test
    void task_description_should_be_edited_after_update() {
        String newDescription = "Practice and Learn Java!";

        addOneTasksToTaskService();
        taskService.updateTask(1, newDescription);

        String description = taskService.getAllTasks().getFirst().getDescription();
        assertEquals(newDescription, description);
    }

    @Test
    void should_throw_exception_if_task_does_not_exists_while_deleting() {
        addOneTasksToTaskService(); // Task ID is 1

        assertThrows(NoSuchTaskException.class, () -> taskService.deleteTask(2));
    }

    @Test
    void task_should_be_able_to_delete() {
        addOneTasksToTaskService();
        taskService.deleteTask(1);

        assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void should_throw_exception_if_task_does_not_exists_while_marking_as_in_progress() {
        addOneTasksToTaskService(); // Task ID is 1

        assertThrows(NoSuchTaskException.class, () -> taskService.markTaskAsInProgress(2));
    }

    @Test
    void task_should_be_able_marked_as_in_progress() {
        addOneTasksToTaskService();
        taskService.markTaskAsInProgress(1);

        Task task = taskService.getAllTasks().getFirst();
        assertEquals(IN_PROGRESS, task.getStatus());
    }

    @Test
    void should_throw_exception_if_task_does_not_exists_while_marking_as_done() {
        addOneTasksToTaskService(); // Task ID is 1

        assertThrows(NoSuchTaskException.class, () -> taskService.markTaskAsDone(2));
    }

    @Test
    void task_should_be_able_marked_as_done() {
        addOneTasksToTaskService();
        taskService.markTaskAsDone(1);

        Task task = taskService.getAllTasks().getFirst();
        assertEquals(DONE, task.getStatus());
    }

    @Test
    void should_throw_exception_if_task_does_not_exists_while_marking_as_todo() {
        addOneTasksToTaskService(); // Task ID is 1

        assertThrows(NoSuchTaskException.class, () -> taskService.markTaskAsTodo(2));
    }

    @Test
    void task_should_be_able_marked_as_todo() {
        addOneTasksToTaskService();
        taskService.markTaskAsDone(1);
        taskService.markTaskAsTodo(1);

        Task task = taskService.getAllTasks().getFirst();
        assertEquals(TODO, task.getStatus());
    }

    @Test
    void tasks_should_be_given_in_their_ids_order() {
        addThreeTasksToTaskService();

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.get(0).getId());
        assertEquals(2, tasks.get(1).getId());
        assertEquals(3, tasks.get(2).getId());
    }

    @Test
    void todo_tasks_should_be_given_in_their_ids_order() {
        addThreeTasksToTaskService();
        taskService.markTaskAsInProgress(2);

        List<Task> tasks = taskService.getTodoTasks();
        assertEquals(1, tasks.get(0).getId());
        assertEquals(3, tasks.get(1).getId());
    }

    @Test
    void in_progress_tasks_should_be_given_in_their_ids_order() {
        addThreeTasksToTaskService();
        taskService.markTaskAsInProgress(2);
        taskService.markTaskAsInProgress(1);

        List<Task> tasks = taskService.getInProgressTasks();
        assertEquals(1, tasks.get(0).getId());
        assertEquals(2, tasks.get(1).getId());
    }

    @Test
    void done_tasks_should_be_given_in_their_ids_order() {
        addThreeTasksToTaskService();
        taskService.markTaskAsDone(3);
        taskService.markTaskAsDone(1);

        List<Task> tasks = taskService.getDoneTasks();
        assertEquals(1, tasks.get(0).getId());
        assertEquals(3, tasks.get(1).getId());
    }

    @Test
    void new_task_id_must_be_one_more_than_biggest_exist_task_id() {
        addThreeTasksToTaskService();
        taskService.deleteTask(1);
        taskService.deleteTask(2);
        taskService.addTask("Clean the Home");

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size());
        assertEquals(4, tasks.getLast().getId());
        assertEquals(3, tasks.getFirst().getId());
    }

    private void addOneTasksToTaskService() {
        taskService.addTask("Practice Java");
    }

    private void addTwoTasksToTaskService() {
        addOneTasksToTaskService();
        taskService.addTask("Shutdown the PC");
    }

    private void addThreeTasksToTaskService() {
        addTwoTasksToTaskService();
        taskService.addTask("Learn Problem Solving");
    }
}