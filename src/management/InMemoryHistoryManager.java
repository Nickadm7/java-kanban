package management;

import elements.Task;
import elements.TaskType;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    public static class Node<T> {
        public T data;
        public Node next;
        public Node prev;

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    private CustomLinkedList<Node> history = new CustomLinkedList(); // историю просмотров без повторов
    private Map<Integer, Node> linkIdNode = new HashMap<>();

    public Map<Integer, Node> getLinkIdNode() {
        return linkIdNode;
    }

    public String getHistoryOnlyId() {
        String out;
        StringBuilder outOnlyId = new StringBuilder();


        for (Integer key : linkIdNode.keySet()) {
            outOnlyId = outOnlyId.append(key).append(",");
        }

        out = outOnlyId.toString();
        return out;

    }

    @Override
    public ArrayList<Node> getHistory() {
        return history.getTasks();
    }

    @Override
    public void add(Integer id, Task task) {
        if (linkIdNode.containsKey(id)) {
            history.removeNode(linkIdNode.get(id));
            linkIdNode.remove(id);
        }
        linkIdNode.put(id, history.linkLast(task));
    }

    @Override
    public void remove(int id) {
        history.removeNode(linkIdNode.get(id)); // удаляем Node из двусвязанного списка
        linkIdNode.remove(id); // удаляем запись о Node из Map
    }

    class CustomLinkedList<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public Node linkLast(Task task) {
            Node oldTail = tail;
            Node newNode = new Node(task);
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

        public ArrayList<Node> getTasks() {
            ArrayList<Node> historyOut = new ArrayList<>();
            historyOut.add(head);
            Node current = head.next;
            while (current != null) {
                historyOut.add(current);
                current = current.next;
            }
            System.out.println();
            System.out.println("History Список перелинкованных Node" + historyOut);
            System.out.println("History Список Node хранятся в HashMap" + linkIdNode);
            return historyOut;
        }

        public void removeNode(Node currentNode) {
            Node previousToCurrent = currentNode.prev;
            Node nextToCurrent = currentNode.next;
            if (previousToCurrent != null) {
                if (nextToCurrent == null) {
                    tail = previousToCurrent;
                    previousToCurrent.next = null;
                } else {
                    previousToCurrent.next = nextToCurrent;
                }
            }
            if (nextToCurrent != null) {
                if (previousToCurrent == null) {
                    head = nextToCurrent;
                    nextToCurrent.prev = null;
                } else {
                    nextToCurrent.prev = previousToCurrent;
                }
            }
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
}
