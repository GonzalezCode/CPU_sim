/**
*@author Marcus Gonzalez
*@version 20170310
*
*/
public class ObjectQueue implements ObjectQueueInterface{
	private Object[] item;
	private int front;
	private int rear;
	private int count;
	/**
	*Constructor for objects of class ObjectQueue
	*/
	public ObjectQueue() {
		item = new Object[1];
		front = 0;
		rear  = -1;
		count = 0;
	}
	/**
	*@return count returns true if queue is empty (count == 0)
	*/
	public boolean isEmpty() {
		return count == 0;
	}
	/**
	*@return count returns true if queue is full
	*/
	public boolean isFull() {
		return count == item.length;
	}
	/**
	*Clears the queue
	*/
	public void clear() {
		item = new Object[1];
		front = 0;
		rear  = -1;
		count = 0;
	}
	 /**
	*@param o the object to be inserted
	*/
	public void insert(Object o) {
		if (isFull())
			resize(2 * item.length);
		rear = (rear+1) % item.length;
		item[rear] = o;
		++count;
	}
	/**
	*@return temp object at the top of the queue
	*returns and removes the object at the top of the queue
	*/
	public Object remove() {
		if (isEmpty()) {
			System.out.println("Queue Underflow");
			System.exit(1);
		}
		Object temp = item[front];
		item[front] = null;
		front = (front+1) % item.length;
		--count;
		if (count == item.length/4 && item.length != 1)
			resize(item.length/2);
		return temp;
	}
	/**
	*@return item[front] item at the front of the queue
	*returns the object at the top of the queue without removing it
	*/
	public Object query() {
		if (isEmpty()) {
			System.out.println("Queue Underflow");
			System.exit(1);
		}
		return item[front];
	}
	/**
	*@param size the size of the new queue
	*resizes the queue to the size of the parameter
	*/
	private void resize(int size) {
		Object[] temp = new Object[size];
		for (int i = 0; i < count; ++i) {
			temp[i] = item[front];
			front = (front+1) % item.length;
		}
		front = 0;
		rear = count-1;
		item = temp;
	}
}