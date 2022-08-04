package management;

import elements.Node;
import elements.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new LinkedList<>(); //хранит историю просмотров
    public CustomLinkedList<Task> historyUtil = new CustomLinkedList();

    private final int NUMBER_OF_ENTRIES = 10; //количество записей хранимых в истории

    @Override
    public List<Task> getHistory() {
        System.out.println("История просмотров пользователя:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + " " + history.get(i));
        }

        historyUtil.getTasks();

        return history;
    }

    @Override
    public void add(Task task) {
        if (history.isEmpty()) {
            history.add(task);
        } else {
            if (history.size() < NUMBER_OF_ENTRIES) {
                history.add(task);
            } else {
                history.remove(0);
                history.add(task);
            }
        }
    }

    @Override
    public void remove(int id) {

    }

    class CustomLinkedList<T> {

        public Node<T> head;
        public Node<T> tail;
        private int size = 0;

        void linkLast(T value) {
            Node<T> l = tail;
            Node<T> newNode = new Node<T>(value);
            tail = newNode;
            if (l == null)
                head = newNode;
            else
                l.next = newNode;
            size++;
        }

        void getTasks() {
            int count = 0;
            System.out.println();
            System.out.println(count++ + "-" + head);
            Node temp = head.next;
            while (temp != null) {
                System.out.println(count + "-" + temp);
                temp = temp.next;
                count++;
            }
            System.out.println(count + "-" + tail);
            System.out.println("size: " + (size + 1));
        }

        @Override
        public String toString() {
            return "CustomLinkedList{" +
                    "head=" + head +
                    ", tail=" + tail +
                    ", size=" + size +
                    '}';
        }
    }

    @Override
    public void addToHistoryNew(Task task) {
        historyUtil.linkLast(task);
    }

}
