package tasks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TasksServiceIntegrationRealTest {

    private TasksService service;
    private ArrayTaskList repo;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @BeforeEach
    void setUp() {
        repo = new ArrayTaskList();
        service = new TasksService(repo);
    }

    @Test
    void testFilterTasksWithRealObjects() throws ParseException {
        Task t1 = new Task("RealTask1", df.parse("2024-05-10 10:00"));
        Task t2 = new Task("RealTask2", df.parse("2024-06-01 12:00"));

        t1.setActive(true);
        t2.setActive(true);

        repo.add(t1);
        repo.add(t2);

        Date start = df.parse("2024-05-01 00:00");
        Date end = df.parse("2024-05-31 23:59");

        Iterable<Task> result = service.filterTasks(start, end);
        List<Task> list = new ArrayList<>();
        result.forEach(list::add);

        assertEquals(1, list.size(), "Trebuie să se returneze doar primul task");
        assertEquals("RealTask1", list.get(0).getTitle());
    }

    @Test
    void testHandleTaskErrorWithValidTask() throws ParseException {
        Task validTask = new Task("Valid", df.parse("2024-12-01 08:00"));
        assertDoesNotThrow(() -> service.handleTaskError(validTask));
    }
}
