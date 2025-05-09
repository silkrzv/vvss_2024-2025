package tasks.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TasksOperationsTest {


    private TasksOperations tasksOperations;
    private Task repeatedTask1;
    private Task repeatedTask2;
    private Task repeatedInactive;

    private Date now;
    private Date in1Hour;
    private Date in2Hours;
    private Date in3Hours;
    private Date in4Hours;
    private Date in6Hours;

    @BeforeEach
    void setUp() {
        now = new Date();
        in1Hour = new Date(now.getTime() + 3600 * 1000);
        in2Hours = new Date(now.getTime() + 2 * 3600 * 1000);
        in3Hours = new Date(now.getTime() + 3 * 3600 * 1000);
        in4Hours = new Date(now.getTime() + 4 * 3600 * 1000);
        in6Hours = new Date(now.getTime() + 6 * 3600 * 1000);

        repeatedTask1 = new Task("Repeated Task 1", in1Hour, in3Hours, 3600); // 1h interval
        repeatedTask1.setActive(true);

        repeatedTask2 = new Task("Repeated Task 2", in2Hours, in4Hours, 1800); // 30min interval
        repeatedTask2.setActive(true);

//        repeatedInactive = new Task("Inactive Repeated", in1Hour, in3Hours, 3600);
//        repeatedInactive.setActive(false);

        ObservableList<Task> taskList = FXCollections.observableArrayList(repeatedTask1, repeatedTask2);
        tasksOperations = new TasksOperations(taskList);
    }

    @Test
        // Acoperă: SC, DC, CC, MCC, APC, LC
    void testIncoming_withValidRange_shouldReturnExpectedTasks() { //Path 1
        Iterable<Task> incomingTasks = tasksOperations.incoming(now, in3Hours);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertEquals(2, result.size());
        assertTrue(result.contains(repeatedTask1));
        assertTrue(result.contains(repeatedTask2));
//        assertFalse(result.contains(repeatedInactive));
    }

    @Test
        // Acoperă: SC, DC, CC, MCC, APC
    void testIncoming_startEqualsEnd_shouldReturnEmptyList() {
        Iterable<Task> incomingTasks = tasksOperations.incoming(now, now);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertTrue(result.isEmpty());
    }

        @Test
        // Acoperă: SC, DC, CC, MCC, APC
    void testIncoming_inactiveTask_shouldNotBeIncluded() {
        repeatedInactive = new Task("Inactive Repeated", in1Hour, in3Hours, 3600);
        repeatedInactive.setActive(false);
        ObservableList<Task> onlyInactive = FXCollections.observableArrayList(repeatedInactive);
        TasksOperations ops = new TasksOperations(onlyInactive);

        Iterable<Task> incomingTasks = ops.incoming(now, in3Hours);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertTrue(result.isEmpty());
    }

    @Test
        // Acoperă: SC, DC, CC, MCC, APC
    void testIncoming_startAfterEnd_shouldReturnEmptyList() { //Path 5
        Iterable<Task> incomingTasks = tasksOperations.incoming(in3Hours, now);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertTrue(result.isEmpty());
    }

    @Test
        // Acoperă: SC, DC, CC, MCC
    void testIncoming_withNullStart_shouldReturnEmptyList() {// Path 7
        Iterable<Task> incomingTasks = tasksOperations.incoming(null, in3Hours);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertTrue(result.isEmpty());
    }

    @Test
        // Acoperă: SC, DC, CC, MCC
    void testIncoming_withNullEnd_shouldReturnEmptyList() { //Path 6
        Iterable<Task> incomingTasks = tasksOperations.incoming(now, null);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertTrue(result.isEmpty());
    }

    @Test
        // Acoperă: SC, APC, LC
    void testIncoming_emptyTaskList_shouldReturnEmptyList() { //Path 4
        // Setăm o listă goală de task-uri
        ObservableList<Task> emptyTaskList = FXCollections.observableArrayList();
        TasksOperations ops = new TasksOperations(emptyTaskList);

        // Invocăm metoda incoming cu un interval valid
        Iterable<Task> incomingTasks = ops.incoming(now, in3Hours);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        // Verificăm că lista rezultată este goală
        assertTrue(result.isEmpty());
    }


    @Test
        // Acoperă: SC, DC, CC, MCC
    void testIncoming_nextTimeNotNull_thenBeforeEndOrEqualsEndFalse_shouldNotAddTask() { //Path 2
        // Task cu nextTime valid dar (beforeEnd || equalsEnd) este fals
        Task task = new Task("Task with nextTime after end", in4Hours, in6Hours, 3600);
        task.setActive(true);

        ObservableList<Task> taskList = FXCollections.observableArrayList(task);
        TasksOperations ops = new TasksOperations(taskList);

        // Invocăm metoda incoming cu un interval valid
        Iterable<Task> incomingTasks = ops.incoming(now, in3Hours);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        assertTrue(result.isEmpty());

        // Verificăm că task-ul nu a fost adăugat, deoarece (beforeEnd || equalsEnd) este fals
        assertTrue(result.isEmpty());
    }

    @Test
        // Acoperă: SC, DC, CC, MCC
    void testIncoming_nextTimeIsNull_shouldNotAddTask() { //Path 3
        // Task cu nextTime null (ex: time after task's end)
        Task taskWithNullNextTime = new Task("Task with Null NextTime", in3Hours, in4Hours, 3600);
        taskWithNullNextTime.setActive(true);

        ObservableList<Task> taskList = FXCollections.observableArrayList(taskWithNullNextTime);
        TasksOperations ops = new TasksOperations(taskList);

        // Invocăm metoda incoming cu un interval valid
        Iterable<Task> incomingTasks = ops.incoming(now, in2Hours);
        List<Task> result = new ArrayList<>();
        incomingTasks.forEach(result::add);

        // Verificăm că task-ul nu a fost adăugat, deoarece nextTime este null
        assertTrue(result.isEmpty());
    }

}