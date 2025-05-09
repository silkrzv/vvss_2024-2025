package tasks.model;

import org.junit.jupiter.api.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test cases for ArrayTaskList add method")
class ArrayTaskListTest {
    private ArrayTaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new ArrayTaskList();
    }

    @Test
    void testAddTaskIncreasesSize() {
        Task mockTask = Mockito.mock(Task.class);
        taskList.add(mockTask);
        assertEquals(1, taskList.size(), "Lista ar trebui să aibă 1 task după adăugare");
    }

    @Test
    void testRemoveTaskDecreasesSize() {
        Task mockTask = Mockito.mock(Task.class);
        taskList.add(mockTask);
        boolean removed = taskList.remove(mockTask);

        assertTrue(removed, "Task-ul ar trebui să fie eliminat cu succes");
        assertEquals(0, taskList.size(), "Lista ar trebui să fie goală după eliminare");
    }
}
