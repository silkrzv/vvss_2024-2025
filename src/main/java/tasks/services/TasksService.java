package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.util.Date;

public class TasksService {

    private ArrayTaskList tasks;

    public TasksService(ArrayTaskList tasks){
        this.tasks = tasks;
    }

    public ObservableList<Task> getObservableList(){
        return FXCollections.observableArrayList(tasks.getAll());
    }
    public String getIntervalInHours(Task task){
        int seconds = task.getRepeatInterval();
        int minutes = seconds / DateService.SECONDS_IN_MINUTE;
        int hours = minutes / DateService.MINUTES_IN_HOUR;
        minutes = minutes % DateService.MINUTES_IN_HOUR;
        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);//hh:MM
    }
    public String formTimeUnit(int timeUnit){
        StringBuilder sb = new StringBuilder();
        if (timeUnit < 10) sb.append("0");
        if (timeUnit == 0) sb.append("0");
        else {
            sb.append(timeUnit);
        }
        return sb.toString();
    }

    public int parseFromStringToSeconds(String stringTime){//hh:MM
        String[] units = stringTime.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int result = (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
        return result;
    }

    public Iterable<Task> filterTasks(Date start, Date end){
        TasksOperations tasksOps = new TasksOperations(getObservableList());
        Iterable<Task> filtered = tasksOps.incoming(start,end);
        return filtered;
    }
//    implementare handleTaskError(Task t) : void
    public void handleTaskError(Task t) {
        if (t == null) {
            throw new IllegalArgumentException("Task-ul nu poate fi null.");
        }
        if (t.getTitle() == null || t.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Titlul task-ului nu poate fi gol.");
        }
        if (t.isRepeated()) {
            if (t.getStartTime().after(t.getEndTime())) {
                throw new IllegalArgumentException("Timpul de început trebuie să fie înainte de timpul de sfârșit pentru un task recurent.");
            }
            if (t.getRepeatInterval() <= 0) {
                throw new IllegalArgumentException("Intervalul de repetare trebuie să fie mai mare decât zero.");
            }
        } else {
            if (t.getTime() == null) {
                throw new IllegalArgumentException("Timpul unui task non-recurent nu poate fi null.");
            }
        }
    }

}
