package management;

import elements.Task;
import management.utilinterface.HistoryManager;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    public static class Node<T> {
        public T data;
        public Node next;
        public Node prev;
        public Integer nodeId;

        public Node(T data, Integer nodeId) {
            this.data = data;
            this.nodeId = nodeId;
        }

        @Override
        public String toString() {
            return "Node{" + '\'' +
                    "data=" + data + '\'' +
                    '}';
        }
    }

    public CustomLinkedList<Node> history = new CustomLinkedList(); // историю просмотров без повторов
    private Map<Integer, Node> linkIdNode = new HashMap<>();
    public LinkedList<Integer> historyOutId = new LinkedList<>();

    @Override
    public String getTasksHistoryId() {
        StringBuilder stringBuffer = new StringBuilder();
        Iterator<Integer> it = history.getTasksHistoryId().iterator();
        while (it.hasNext()) {
            stringBuffer.append(it.next()).append(",");
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }
        String out = stringBuffer.toString();
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
            Node newNode = new Node(task, task.getId());
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
            if (head != null) {
                historyOut.add(head);
                Node current = head.next;
                while (current != null) {
                    historyOut.add(current);
                    current = current.next;
                }
            }
            System.out.println();
            System.out.println("History Список перелинкованных Node" + historyOut);
            System.out.println("History Список Node хранятся в HashMap" + linkIdNode);
            return historyOut;
        }

        public LinkedList<Integer> getTasksHistoryId() {
            LinkedList<Integer> out = new LinkedList<>();
            if (head != null) {
                out.add(head.nodeId);
                Node current = head.next;
                while (current != null) {
                    out.add(current.nodeId);
                    current = current.next;
                }
            }
            return out;
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
