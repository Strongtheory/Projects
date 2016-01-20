import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class PacketList<T> implements Queue<T> {

    private int size;
    private Node first, last;


    /**
     * Node structure.
     */
    private class Node {
        private T element;
        private Node nextElement;
    }

    /**
     * Constructor
     */
    public PacketList() {
        first = null;
        last = null;
    }

    synchronized public PacketList<T> enqueue(T element)
    {
        Node curr = last;
        last = new Node();
        last.element = element;
        if (size++ == 0)
            first = last;
        else
            curr.nextElement = last;
        return this;
    }

    /**
     * Get and remove the first node in the queue
     * @return
     */
    synchronized public T dequeue()
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();
        T element = first.element;
        first = first.nextElement;
        if (--size == 0) {
            first = null;
            last = null;
        }
        return element;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        Node temp = first;
        while (temp != null) {
            sb.append(temp.element).append(", ");
            temp = temp.nextElement;
        }
        return sb.toString();
    }

    public ArrayList<T> returnArrayList(){
        ArrayList<T> myList = new ArrayList<T>();
        Node node = first;

        for(int i = 0; i < size; i++){
            myList.add(node.element);
            node = node.nextElement;
        }
        return myList;
    }

    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean contains(Object arg0) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return (first == null);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public boolean remove(Object arg0) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Object[] toArray() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        return null;
    }

    @Override
    public boolean add(T arg0) {
        return false;
    }

    @Override
    public T element() {
        return null;
    }

    @Override
    public boolean offer(T arg0) {
        return false;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T remove() {
        return null;
    }

}