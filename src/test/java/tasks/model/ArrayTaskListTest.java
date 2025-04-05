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
    @DisplayName("Valid: Add a task with title being a string")
    void TC1_ECP() {
        // Arrange
        Task task = new Task("Test Task", new Date(), new Date(), 4);

        // Act4
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one task");
    }

    @Test
    @DisplayName("InValid: Add a task with title being not a string")
    void TC2_ECP() {
        // Arrange
        Task task = new Task(null, new Date(), new Date(), 5);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(0, taskList.size(), "Task should have a title being a string");
    }

    @Test
    @DisplayName("InValid: Add two tasks with the same title")
    void TC3_ECP() {
        // Arrange
        Task task1 = new Task("Duplicate Task", new Date(), new Date(), 5);
        Task task2 = new Task("Duplicate Task", new Date(), new Date(), 6);

        // Act
        taskList.add(task1);
        taskList.add(task2);

        // Assert
        assertEquals(1, taskList.size(), "Task list shouldn't contain two tasks with the same title");
        assertEquals("Duplicate Task", taskList.getTask(0).getTitle());
        assertEquals("Duplicate Task", taskList.getTask(1).getTitle());
        assertNotSame(taskList.getTask(0), taskList.getTask(1)); // Asigură că sunt instanțe diferite
    }


    @Test
    @DisplayName("Valid: Add a task with title length 254")
    void TC4_BVA() {
        // Arrange
        String title = "M".repeat(254); // Creează un titlu de exact 254 caractere
        Task task = new Task(title, new Date(), new Date(), 5);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one task");
        assertEquals(title, taskList.getTask(0).getTitle());
    }

    @Test
    @DisplayName("Valid: Add a task with title length 255")
    void TC5_BVA() {
        // Arrange
        String title = "M".repeat(255); // Titlu de exact 255 caractere
        Task task = new Task(title, new Date(), new Date(), 5);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size(), "Task list should contain one task");
        assertEquals(title, taskList.getTask(0).getTitle());
    }

    @Test
    @DisplayName("Invalid: Add a task with title length 256")
    void TC6_BVA() {
        // Arrange
        String title = "M".repeat(256); // Titlu de exact 256 caractere
        Task task = new Task(title, new Date(), new Date(), 5);

        // Act
        taskList.add(task);

        // Act & Assert
        assertEquals(0, taskList.size(), "The task sholdn't have a title length > 255");
    }

    @Test
    @DisplayName("Invalid: Add a task with title length 0")
    void TC7_BVA() {
        // Arrange
        String title = ""; // Titlu gol
        Task task = new Task(title, new Date(), new Date(), 5);

        // Act
        taskList.add(task);

        // Act & Assert
        assertEquals(0, taskList.size(), "The task sholdn't have a title length < 1");
    }
}