package prog06;

import java.util.*;

/**
 * Implements the Queue interface using a circular array.
 **/
public class ArrayQueue<E> extends AbstractQueue<E> implements Queue<E> {

	// Data Fields
	/** Index of the front of the queue. */
	private int front;
	/** Index of the back of the queue. */
	private int back;
	/** Current size of the queue. */
	private int size;
	/** Default capacity of the queue. */
	private static final int DEFAULT_CAPACITY = 2;
	/** Array to hold the items. */
	private E[] theItems;

	// Constructors
	/**
	 * Construct a queue with the default initial capacity.
	 */
	public ArrayQueue() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Construct a queue with the specified initial capacity.
	 * 
	 * @param initCapacity
	 *            The initial capacity
	 */
	@SuppressWarnings("unchecked")
	public ArrayQueue(int initCapacity) {
		theItems = (E[]) new Object[initCapacity];
		front = 0;
		back = theItems.length - 1;
		size = 0;
	}

	// Public Methods
	/**
	 * Inserts an item at the back of the queue.
	 * 
	 * @post item is added to the back of the queue.
	 * @param item
	 *            The element to add
	 * @return true (always successful)
	 */
	@Override
	public boolean offer(E item) {
		if (size == theItems.length)
			reallocate();
		// Move back forward one, but if it goes past the end of the
		// array, go back to zero.
		back = (back + 1) % theItems.length;
		// Store the new item at back.
		theItems[back] = item;
		size++;
		return true;
	}

	/**
	 * Returns the item at the front of the queue without removing it.
	 * 
	 * @return The item at the front of the queue if successful; return null if
	 *         the queue is empty
	 */
	@Override
	public E peek() {
		if (size == 0)
			return null;
		return theItems[front];
	}

	/**
	 * Removes the entry at the front of the queue and returns it if the queue
	 * is not empty.
	 * 
	 * @post front references item that was second in the queue.
	 * @return The item removed if successful or null if not
	 */
	@Override
	public E poll() {
		if (size == 0)
			return null;
		int oldfront = front;
		front = (front + 1) % theItems.length;
		size--;
		return theItems[oldfront];
		// EXERCISE 3
	}

	/**
	 * Return the size of the queue
	 * 
	 * @return The number of items in the queue
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns an iterator to the elements in the queue
	 * 
	 * @return an iterator to the elements in the queue
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iter();
	}

	private boolean labReallocate = false; // changed from true to false to test

	// Private Methods
	/**
	 * Double the capacity and reallocate the items.
	 * 
	 * @pre The array is filled to capacity.
	 * @post The capacity is doubled and the first half of the expanded array is
	 *       filled with items.
	 */
	@SuppressWarnings("unchecked")
	private void reallocate() {
		int newCapacity = 2 * theItems.length;
		E[] newItems = (E[]) new Object[newCapacity];
		if (labReallocate) {
			int j = front;
			for (int i = 0; i < size; i++) {
				newItems[i] = theItems[j];
				j = (j + 1) % theItems.length;
			}
			
		} else {
			if (front > back) {
			System.arraycopy(theItems, front, newItems, 0, theItems.length - front);
			System.arraycopy(theItems, 0, newItems, (theItems.length - front), back + 1);
			}
			else {
				System.arraycopy(theItems, front, newItems, 0, (back - front + 1));
			}
			
		}
			// EXERCISE 8
		
		front = 0;
		back = size - 1;
		theItems = newItems;
	}

	private boolean labIterator = false;

	/** Inner class to implement the Iterator<E> interface. */
	private class Iter implements Iterator<E> {
		// Data Fields

		// Index of next element */
		private int index;

		// Count of elements accessed so far */ //removed for step 9
		private int count = 0;

		// Methods
		// Constructor
		/**
		 * Initializes the Iter object to reference the first queue element.
		 */
		public Iter() {
			if (labIterator) {
				index = front;
			} else if (size == 0) {
				index = -1;
				// EXERCISE 9
			} else {
				index = front;
			}
		}

		/**
		 * Returns true if there are more elements in the queue to access.
		 * 
		 * @return true if there are more elements in the queue to access.
		 */
		@Override
		public boolean hasNext() {
			if (labIterator)
				return count < size;
			else {
				return index != -1;
				// EXERCISE 9
				// return false; // not right
			}
		}

		/**
		 * Returns the next element in the queue.
		 * 
		 * @pre index references the next element to access.
		 * @post index and count are incremented.
		 * @return The element with subscript index
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E returnValue = theItems[index];
			if (labIterator) {
				index = (index + 1) % theItems.length;
				count++;
			} else {
				
				if (index == back) {
					index = -1;
				}
				else {
					index = (index + 1) % theItems.length;
				}
				// EXERCISE 9
			}

			return returnValue;
		}

		/**
		 * Remove the item accessed by the Iter object -- not implemented.
		 * 
		 * @throws UnsupportedOperationException
		 *             when called
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
