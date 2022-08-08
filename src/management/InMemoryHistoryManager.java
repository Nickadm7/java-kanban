package management;

import elements.Node;
import elements.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new LinkedList<>(); // история 10 просмотров с повторами
    private CustomLinkedList<Task> historyUtil = new CustomLinkedList(); // историю просмотров без повторов
    private ArrayList<Node> historyOut = new ArrayList<>();
    private Map<Integer, Node> linkIdNode = new HashMap<>();
    private final int NUMBER_OF_ENTRIES = 10; //количество записей хранимых в истории

    @Override
    public List<Task> getHistory() {
        System.out.println();
        System.out.println("Старый формат истории просмотров пользователя:");
        for (int i = 0; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
        System.out.println();
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
        historyUtil.removeNode(linkIdNode.get(id)); // удаляем Node из двусвязанного списка
        linkIdNode.remove(id); // удаляем запись о Node из Map
    }

    class CustomLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;
        private int size = 0;

        Node linkLast(Task task) {
            Node oldTail = tail;
            Node newNode = new Node(task);
            //linkIdNode.put(id, newNode);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
            newNode.prev = oldTail;
            size++;
            return newNode;
        }

        ArrayList<Node> getTasks() {
            historyOut.clear();
            int count = 0;
            historyOut.add(head);
            Node current = head.next;
            while (current != null) {
                historyOut.add(current);
                current = current.next;
                count++;
            }
            System.out.println("History Список перелинкованных Node" + historyOut);
            System.out.println("History Список Node хранятся в HashMap" + linkIdNode);
            return historyOut;
        }


        public void removeNode(Node node) {
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                head = node.next;
            }
            if (node.next != null)
                node.next.prev = node.prev;
            else
                tail = node.prev;
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
    public void addToHistoryNew(Integer id, Task task) {
        if (linkIdNode.containsKey(id)) {
            historyUtil.removeNode(linkIdNode.get(id));
            linkIdNode.remove(id);
            linkIdNode.put(id, historyUtil.linkLast(task));
        } else {
            linkIdNode.put(id, historyUtil.linkLast(task));
        }
    }
}
