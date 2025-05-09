package tasks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceIntegrationMockTaskTest {

    private TasksService service;
    private ArrayTaskList repo;

    @BeforeEach
    void setUp() {
        repo = new ArrayTaskList(); // real repository
        service = new TasksService(repo);
    }

    @Test
    void testFilterTasksWithMockedTask() {
        Task mockTask = mock(Task.class);
        when(mockTask.isActive()).thenReturn(true);
        when(mockTask.nextTimeAfter(any(Date.class))).thenReturn(new Date());

        repo.add(mockTask); // repo real, task mock

        Iterable<Task> result = service.filterTasks(
                new Date(System.currentTimeMillis() - 1000),
                new Date(System.currentTimeMillis() + 10000)
        );

        List<Task> list = new java.util.ArrayList<>();
        result.forEach(list::add);
        assertEquals(1, list.size());
    }

    @Test
    void testFilterTasksWithInactiveMockedTask() {
        Task mockTask = mock(Task.class);
        when(mockTask.isActive()).thenReturn(false); // inactiv

        repo.add(mockTask);

        Iterable<Task> result = service.filterTasks(
                new Date(System.currentTimeMillis() - 1000),
                new Date(System.currentTimeMillis() + 10000)
        );

        List<Task> list = new java.util.ArrayList<>();
        result.forEach(list::add);
        assertEquals(0, list.size());
    }
}
