package elements;

public class Node<T> {
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
