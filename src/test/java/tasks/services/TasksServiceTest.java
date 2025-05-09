package tasks.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.model.Task;
import tasks.model.ArrayTaskList;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceTest {

    private ArrayTaskList spyRepo;
    private TasksService service;

    @BeforeEach
    void setUp() {
        ArrayTaskList realRepo = new ArrayTaskList();
        spyRepo = spy(realRepo); // folosim spy pe un repository real
        service = new TasksService(spyRepo);
    }

    @Test
    void testFilterTasksCallsGetAll() {
        // Cream mock pentru repo și task
        ArrayTaskList mockRepo = Mockito.mock(ArrayTaskList.class);
        Task mockTask = Mockito.mock(Task.class);

        // Comportamentele simulate
        when(mockTask.isActive()).thenReturn(true);
        when(mockTask.nextTimeAfter(any(Date.class))).thenReturn(new Date());
        when(mockRepo.getAll()).thenReturn(List.of(mockTask));

        // Injectăm mock-ul în service
        TasksService service = new TasksService(mockRepo);

        Date start = new Date(System.currentTimeMillis() - 1000);
        Date end = new Date(System.currentTimeMillis() + 10000);

        Iterable<Task> result = service.filterTasks(start, end);

        // verificare: metoda getAll() a fost apelată
        verify(mockRepo, times(1)).getAll();

        // validare rezultat
        List<Task> resultList = new java.util.ArrayList<>();
        result.forEach(resultList::add);
        assertEquals(1, resultList.size());
    }

    @Test
    void testHandleTaskErrorWithInvalidTitle() {
        Task mockTask = mock(Task.class);
        when(mockTask.getTitle()).thenReturn("   "); // titlu invalid

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.handleTaskError(mockTask);
        });

        assertEquals("Titlul task-ului nu poate fi gol.", exception.getMessage());
    }
}
