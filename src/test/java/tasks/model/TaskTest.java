package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        try {
            task=new Task("new task",Task.getDateFormat().parse("2023-02-12 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void testTaskCreation() throws ParseException {
//       assert task.getTitle() == "new task";
//        System.out.println(task.getFormattedDateStart());
//        System.out.println(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
//       assert task.getFormattedDateStart().equals(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
//    }

    @Test
    void testSetTitle() {
        task.setTitle("Modified title");
        assertEquals("Modified title", task.getTitle(), "Titlul nu a fost setat corect");
    }

    @Test
    void testSetActiveStatus() {
        task.setActive(true);
        assertTrue(task.isActive(), "Task-ul ar fi trebuit să fie activ");

        task.setActive(false);
        assertFalse(task.isActive(), "Task-ul ar fi trebuit să fie inactiv");
    }




}