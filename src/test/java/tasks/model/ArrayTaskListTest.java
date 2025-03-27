package tasks.model;

import org.junit.jupiter.api.*;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test cases for ArrayTaskList add method")
class ArrayTaskListTest {
    private ArrayTaskList taskList;

    @BeforeAll
    static void init() {
        System.out.println("Starting ArrayTaskList tests");
    }

    @BeforeEach
    void setUp() {
        taskList = new ArrayTaskList();
    }

    @AfterEach
    void tearDown() {
        taskList = null;
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Finished ArrayTaskList tests");
    }

    @Test
    @DisplayName("Valid: Add a normal task")
    void addValidTask() {
        // Arrange
        Task task = new Task("Test Task", new Date());

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one task");
    }

    @Test
    @DisplayName("Valid: Add a repeating task")
    void addValidRepeatingTask() {
        // Arrange
        Task task = new Task("Repeating Task", new Date(), new Date(System.currentTimeMillis() + 10000), 5);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one repeating task");
    }

    @Test
    @DisplayName("Invalid: Add a null task")
    void addNullTask() {
        // Arrange & Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> taskList.add(null));
        assertEquals("Task cannot be null", exception.getMessage(), "Expected exception message");

        // Motivul invalidității
        System.out.println("Invalid: Add a null task - " + exception.getMessage());
    }

    @Test
    @DisplayName("Invalid: Add duplicate tasks")
    void addDuplicateTasks() {
        // Arrange
        Task task = new Task("Duplicate Task", new Date());

        // Act
        taskList.add(task);
        taskList.add(task);

        // Assert
        assertEquals(2, taskList.size(), "Task list should allow duplicate tasks");

        // Motivul invalidității (observă că, în cazul nostru, nu există o restricție explicită pentru duplicare)
        System.out.println("Invalid: Add duplicate tasks - No restriction for duplicates in this implementation.");
    }

    @Test
    @DisplayName("Valid: Add task at lower boundary")
    void addTaskAtLowerBoundary() {
        // Arrange
        Task task = new Task("Lower Boundary Task", new Date(0));

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one task at lower boundary");
    }

    @Test
    @DisplayName("Valid: Add task at upper boundary")
    void addTaskAtUpperBoundary() {
        // Arrange
        Task task = new Task("Upper Boundary Task", new Date(Long.MAX_VALUE - 1));

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one task at upper boundary");
    }

    @Test
    @DisplayName("Invalid: Add task with negative time")
    void addTaskWithNegativeTime() {
        // Arrange & Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Task("Negative Time Task", new Date(-1)));
        assertEquals("Time cannot be negative", exception.getMessage(), "Expected exception message");

        // Motivul invalidității
        System.out.println("Invalid: Add task with negative time - " + exception.getMessage());
    }

    @Test
    @DisplayName("Invalid: Add task with zero interval")
    void addTaskWithZeroInterval() {
        // Arrange & Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Task("Zero Interval Task", new Date(), new Date(System.currentTimeMillis() + 10000), 0));
        assertEquals("interval should me > 1", exception.getMessage(), "Expected exception message");

        // Motivul invalidității
        System.out.println("Invalid: Add task with zero interval - " + exception.getMessage());
    }
}
