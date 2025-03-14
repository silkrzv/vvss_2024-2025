package tasks.model;


import org.apache.log4j.Logger;

import java.util.*;

public class ArrayTaskList extends TaskManager {

    private Task[] tasks;
    private int numberOfTasks;
    private int currentCapacity;
    private static final Logger log = Logger.getLogger(ArrayTaskList.class.getName());
    private class ArrayTaskListIterator implements Iterator<Task> {
        private int cursor;
        private int lastCalled = -1;
        @Override
        public boolean hasNext() {
            return cursor < numberOfTasks;
        }

        @Override
        public Task next() {
            if (!hasNext()){
                log.error("next iterator element doesn't exist");
                throw new NoSuchElementException("No next element");
            }
            lastCalled = cursor;
            return getTask(cursor++);
        }

        @Override
        public void remove() {
            if (lastCalled == -1){
                throw new IllegalStateException();
            }
            ArrayTaskList.this.remove(getTask(lastCalled));
            cursor = lastCalled;
            lastCalled = -1;
        }
    }
    public ArrayTaskList(){
        currentCapacity = 10;
        this.tasks = new Task[currentCapacity];
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }

    @Override
    public void add(Task task) {
        if (task == null) { // Fix C01
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (numberOfTasks == tasks.length) {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
        }
        tasks[numberOfTasks++] = task;
    }

    @Override
    public boolean remove(Task task) {
        if (task == null) { // Fix C02
            return false;
        }
        for (int i = 0; i < numberOfTasks; i++) {
            if (task.equals(tasks[i])) {
                System.arraycopy(tasks, i + 1, tasks, i, numberOfTasks - i - 1);
                tasks[--numberOfTasks] = null;
                return true;
            }
        }
        return false;
    }
    @Override
    public int size(){
        return this.numberOfTasks;
    }
    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= numberOfTasks) { // Fix C06
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return tasks[index];
    }

    @Override
    public List<Task> getAll() {
        ArrayList<Task> tks=new ArrayList<>();
        for (int i=0; i<this.numberOfTasks;i++)
            tks.add(this.tasks[i]);
        return tks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList that = (ArrayTaskList) o;

        if (numberOfTasks != that.numberOfTasks) return false;
        int i = 0;
        for (Task a : this){
            if (!a.equals(((ArrayTaskList) o).getTask(i))){
                return false;
            }
            i++;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(tasks);
        result = 31 * result + numberOfTasks;
        result = 31 * result + currentCapacity;
        return result;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "tasks=" + Arrays.toString(tasks) +
                ", numberOfTasks=" + numberOfTasks +
                ", currentCapacity=" + currentCapacity +
                '}';
    }
    @Override
    protected ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList tasks = new ArrayTaskList();
        for (int i = 0; i < this.tasks.length; i++){
            tasks.add(this.getTask(i));
        }
        return tasks;

    }
}