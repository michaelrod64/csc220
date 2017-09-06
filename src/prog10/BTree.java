package prog10;
import java.util.*;

public class BTree<K extends Comparable<K>, V>
  extends AbstractMap<K, V> implements Map<K, V> {
  
  /**
   * At the bottom of the tree, this is an ordinary (prog02) directory
   * entry in the directory list.  It contains the V value in
   * listOrValue.  Inside the tree, this is a list entry.  Its
   * listOrValue points to list the next level down.
   */
  private class Entry {
    private K key;
    private Object /* V or List<Entry> */ listOrValue;

    private Entry (K key, V value) {
      this.key = key;
      listOrValue = value;
    }

    private Entry (List<Entry> list) {
      key = list.get(0).key;
      listOrValue = list;
    }

    private boolean bottom () {
      return !(listOrValue != null &&
               listOrValue instanceof ArrayList &&
	       getList().size() > 0 &&
               getList().get(0) instanceof BTree.Entry);
    }

    public K getKey () {
      return key;
    }

    public V getValue () {
      return (V) listOrValue;
    }

    public V setValue (V value) {
      V old = getValue();
      listOrValue = value;
      return old;
    }

    public List<Entry> getList () {
      return (List<Entry>) listOrValue;
    }

    public String toString () {
      return "" + getKey() + " " + getValue();
    }
  }

  // (4) means give it a capacity (length) of 4.  size is set to 0.
  private List<Entry> list = new ArrayList<Entry>(4);

  /**
   * Find the index of the entry that contains key or the index at
   * which it should be inserted.  This is like the find method in
   * prog02 except you should not bother with binary search since n is
   * so small.
   * For example if
   *   list.get(0).key equals "Alex"
   *   list.get(1).key equals "Lisa"
   *   list.get(2).key equals "Reid"
   * findInList("Lisa", list) should return 1 because that's Lisa's
   * index, but findInList("Ivy", list) should also return 1 because
   * that's where Ivy should go, and findInList("Zoe", list) should
   * return 3, right?  What about findInList("Aaron", list)?
   */
  private int findInList (K key, List<Entry> list) {
    

    int i = 0;
    while(i < list.size() && list.get(i).key.compareTo(key) < 0) {
    			i++;
    }
    return i;
    
    
    // This is just a place holder.  It never returns -1.
  }

  /**
   * The previous find method was like the one in prog02.  This
   * findInListOfLists method is for finding which list, in a list of
   * lists, contains the key we are looking for.  It returns -1 if the
   * key is definitely not there.
   * If the keys are as stated in the previous example,
   *   list.get(0).getList() contains "Alex" up to (but not including) "Lisa"
   *   list.get(1).getList() contains "Lisa" up to (but not including) "Reid"
   *   list.get(2).getList() contains "Reid" and beyond.
   * findInListOfLists("Lisa") should still be 1, but
   * findInListOfLists("Ivy") should be 0 now, and
   * findInListOfLists("Zoe") should be 2, and
   * findInListOfLists("Aaron") should be -1.
   */
  private int findInListOfLists (K key, List<Entry> list) {
    // EXERCISE
	  int i = -1;
	  while(i + 1 < list.size() && list.get(i + 1).key.compareTo(key) <= 0) {
		  i++;
	  }
	  return i;
	 
    //return -1; // Incorrect (usually).
  }

  /**
   * Recursively find the leaf entry for the key in the tree.
   * Return null if not there.
   */
  private Entry find (K key, List<Entry> list) {
    // If the list is empty, it is not there.
    if (list.size() == 0)
      return null;

    // If the list entries are leaves (contain data not lists):
    if (list.get(0).bottom()) {
      // EXERCISE
    	int index = findInList(key, list);
      // Use findInList to get the index of the key, if it is there.
    	if(index < list.size() && list.get(index).key.equals(key)) {
    		return list.get(index);
    	}
      // Check if the entry at that index matches the key.
      // (Be careful looking for "zero".)

        return null; // if it is not there
    }
    // List entries are lists.
    else {
    	
    	int index = findInListOfLists(key, list);
    	
    	if (index < 0) {
    		return null;
    	}
      // EXERCISE
      // Use findInListOfLists to get the index of the list that has
      // the key in it, if it is there.

      // Recursively find the key in the list at i.
      // (Be careful looking for Aaron.)
      return find(key, list.get(index).getList());/* Change null to the list at i. */
    }
  }

  public boolean containsKey (Object key) {
    return find((K) key, list) != null;
  }

  public V get (Object obj) {
    K key = (K) obj;
    Entry entry = find(key, list);
    if (entry == null) {
    	return null;
    }
    return entry.getValue();
    // EXERCISE
    //return null; // Incorrect.  Fix this.
  }

  /**
   * Split off a right hand list for a list containing four elements.
   * Before this call, original list contains [0], [1], [2], [3].
   * After the call, original list contains [0] and [1], and
   * returned list contains what used to be [2] and [3].
   * As always, new list capacity should be 4.
   */
  private List<Entry> split (List<Entry> list) {
    List<Entry> right = new ArrayList<Entry>(4);
    right.add(list.get(2));
    right.add(list.get(3));
    list.remove(3);
    list.remove(2);
    return right;
  }

  public V put (K key, V value) {
    Entry entry = find(key, list);
    if (entry != null) {
      V old = entry.getValue();
      entry.setValue(value);
      return old;
    }

    insert(key, value, list);

    // Look at this!!!
    if (list.size() == 4) {
      List<Entry> left = list;
      List<Entry> right = split(list);
      list = new ArrayList<Entry>(4);
      list.add(new Entry(left));
      list.add(new Entry(right));
    }

    return null;
  }
      
  /**
   * Recursively insert an entry with key and value into list.
   * Precondition:  entry must not be there.
   */
  private void insert (K key, V value, List<Entry> list) {
    // If we are at the bottom level:
    if (list.size() == 0 || list.get(0).bottom()) {

      // EXERCISE

      // Create a new Entry.
    	Entry entry = new Entry(key, value);
      // Remember:  this is an ordinary list of entries (leaf not internal).
      // How do you get the index where to insert it?
    	int index = findInList(key, list);

      // Insert it using a method in the List API.
    	list.add(index, entry);
      // Just one line.  No loop necessary.

    }
    // Not at the bottom level -- list of lists.
    else {
      // EXERCISE

      // Get the index at which it should be inserted.
      int i = findInListOfLists(key, list); // Incorrect.  Fix this.
      // Where should Aaron go?  (Modify i for that case.)
      if(i < 0) {
    	  i = 0;
      }

      // Get the entry at index i.
      Entry entry = list.get(i);
      // Get its (sub)list.
      List<Entry> sublist = entry.getList();

      // Recursively insert the key and value into this sublist.
      insert(key, value, sublist);
      // Update entry's key to be the first key in the sublist.
      entry.key = sublist.get(0).key;

      // Sublist is too big, needs to be split.
      if (sublist.size() == 4) {
        // Split off the right half of the list.
        List<Entry> rightlist = split(sublist);

        // Create a new Entry with rightlist.
        Entry right = new Entry(rightlist);

        // EXERCISE
        // Insert it into list. What index?  (Hint: to the right of index i.)
        list.add(i + 1, right);
      }
    }
  }
      
  public V remove (K key) {
    Entry entry = find(key, list);

    if (entry == null)
      return null;

    remove(key, list);

    // EXERCISE
    if (list.size() == 1 && !list.get(0).bottom()) {
    	list = list.get(0).getList();
    }
    // If the list has only one element and that element is not a
    // leaf, then throw away the list and that entry and set list
    // equal to that entry's list.


    return entry.getValue();
  }

  /**
   * Recursively remove the entry with key from the list.
   * Precondition:  entry must be there.
   */
  private void remove (K key, List<Entry> list) {
    // EXERCISE

    // If we are at the bottom level:
	  if(list.get(0).bottom()) {
		  int index = findInList(key, list);
		  list.remove(index);
	  }

      // Find the index of the key and remove the entry at that index.
      // The List API has a remove method you should use.
	  

    // Else:
	  
	  else {
		  int index = findInListOfLists(key, list);
		  Entry entry = list.get(index);
		  List<Entry> sublist = entry.getList();
		  remove(key, sublist);
		  entry.key = sublist.get(0).key;
		  
		  
		  
		  
	  
      // First find the index of the sublist which contains the key.

      // Get the entry at that index.

      // Get its list, call it the sublist.

      // Recursively remove the key from that sublist.

      // Make sure to update the key field of the entry because the
      // sublist may have a new key at index 0 now.


      // If the size of the sublist equals 1:
		  
		  if(sublist.size() == 1) {
			  if (index == list.size() - 1) {
				  index--;
				  entry = list.get(index);
				  sublist = entry.getList();
			  }
			  sublist.addAll(list.get(index + 1).getList());
			  list.remove(index + 1);
			  
		  }
		  
		  
		// We need to merge the sublist with its left or right
	        // neighbor in list.  To keep things simple, merge it with the
	        // one on the right, with index+1, but if the index is the
	        // last index, that's not going to work.  So in that case,
	        // first decrement the index and make the entry and sublist be
	        // the ones which belong to that one.

	        // Add all the elements of the sublist to the right to the current
	        // sublist.  You can just use the addAll method of ArrayList.

	        // Remove the entry to the right from list.
		  
		  
		  // If the size of the sublist is now 4:
          // Split the sublist.  Look at insert to see how this is
          // done.
		  if(sublist.size() == 4) { //code taken from insert
			        // Split off the right half of the list.
			        List<Entry> rightlist = split(sublist);

			        // Create a new Entry with rightlist.
			        Entry right = new Entry(rightlist);

			        // EXERCISE
			        // Insert it into list. What index?  (Hint: to the right of index i.)
			        list.add(index + 1, right);
		  }
		  
	  }
        

       

  }

  private void initTree () {
    // Entry[] nodes = { 
    BTree.Entry[] entries = { 
      new Entry((K) "eight", (V) (Integer) 8),
      new Entry((K) "five", (V) (Integer) 5),
      new Entry((K) "four", (V) (Integer) 4),
      new Entry((K) "nine", (V) (Integer) 9),
      new Entry((K) "one", (V) (Integer) 1),
      new Entry((K) "seven", (V) (Integer) 7),
      new Entry((K) "six", (V) (Integer) 6),
      new Entry((K) "three", (V) (Integer) 3),
      new Entry((K) "two", (V) (Integer) 2),
      new Entry((K) "zero", (V) (Integer) 0)
    };
    Integer[][][] levels = {{{0, 1}, {2, 3}, {4, 5}, {6, 7}, {8, 9}},
                            {{0, 1}, {2, 3, 4}},
                            {{0, 1}}};
    List<Entry>[][] lists = (List<Entry>[][]) new List[levels.length][];

    lists[0] = (List<Entry>[]) new List[levels[0].length];
    for (int i = 0; i < levels[0].length; i++) {
      List<Entry> list = new ArrayList<Entry>();
      for (int j = 0; j < levels[0][i].length; j++)
        list.add(entries[levels[0][i][j]]);
      lists[0][i] = list;
    }

    for (int h = 1; h < levels.length; h++) {
      lists[h] = (List<Entry>[]) new List[levels[h].length];
      for (int i = 0; i < levels[h].length; i++) {
        List<Entry> list = new ArrayList<Entry>();
        for (int j = 0; j < levels[h][i].length; j++) {
          list.add(new Entry(lists[h-1][levels[h][i][j]]));
          lists[h][i] = list;
        }
      }
    }

    list = lists[lists.length-1][0];
  }

  // Required to implement Map<K, V>.
  public Set<Map.Entry<K,V>> entrySet () { return null; }

  int maxDepth;

  private boolean test () {
    maxDepth = -1;
    if (list.size() > 3)
      return false;
    for (int i = 0; i < list.size(); i++) {
      K up = null;
      if (i < list.size()-1) {
        up = list.get(i+1).key;
        if (list.get(i).key.compareTo(up) >= 0)
          return false;
        if (list.get(i).bottom() != list.get(i+1).bottom())
          return false;
      }
      if (list.get(i).bottom())
        maxDepth = 0;
      else if (!test(list.get(i).getList(), list.get(i).key, up, 1))
        return false;
    }
    return true;
  }

  private boolean test (List<Entry> list, K lo, K up, int depth) {
    if (list.size() < 2 || list.size() > 3)
      return false;
    if (!list.get(0).key.equals(lo))
      return false;
    if (up != null && list.get(list.size()-1).key.compareTo(up) >= 0)
      return false;
    for (int i = 0; i < list.size(); i++) {
      K up2 = null;
      if (i < list.size()-1) {
        up2 = list.get(i+1).key;
        if (list.get(i).key.compareTo(up2) >= 0)
          return false;
        if (list.get(i).bottom() != list.get(i+1).bottom())
          return false;
      }
      if (list.get(i).bottom()) {
        if (maxDepth != -1 && maxDepth != depth)
          return false;
        maxDepth = depth;
      }
      else if (!test(list.get(i).getList(), list.get(i).key, up, depth+1))
        return false;
    }
    return true;
  }
    

  public String toString () {
    if (test())
      return "-------------------------\n" + toString(list, 0);
    else
      return "------!!BAD TREE!!-------\n" + toString(list, 0);
  }
  
  private String toString (List<Entry> list, int indent) {
    String ret = "";
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < indent; j++)
        ret = ret + "    ";
      ret = ret + list.get(i).key;
      if (list.get(i).bottom())
        ret = ret + " " + (V) list.get(i).getValue() + "\n";
      else
        ret = ret + "\n" + toString(list.get(i).getList(), indent+4);
    }
    return ret;
  }

  public static void main (String[] args) {
    String[] nums = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
    BTree<String, Integer> tree = new BTree<String, Integer>();
    tree.initTree();
    System.out.println(tree);

    for (String num: nums)
      System.out.println("containsKey(" + num + ") = " + tree.containsKey(num));

    System.out.println("containsKey(" + "apple" + ") = " + tree.containsKey("apple"));
    System.out.println("containsKey(" + "google" + ") = " + tree.containsKey("google"));
    System.out.println("containsKey(" + "zzz" + ") = " + tree.containsKey("zzz"));

    for (String num: nums)
      System.out.println("get(" + num + ") = " + tree.get(num));

    System.out.println("get(" + "apple" + ") = " + tree.get("apple"));
    System.out.println("get(" + "google" + ") = " + tree.get("google"));
    System.out.println("get(" + "zzz" + ") = " + tree.get("zzz"));

    String[] nums2 = { "ten", "eleven", "twelve", "thirteen", "fourteen",
                       "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };

    int n = 10;
    for (String num2: nums2) {
      System.out.println("put(" + num2 + ", " + n + ") = " + tree.put(num2, n));
      n++;
      System.out.println(tree);
    }

    for (int i = 0; i < 10; i++) {
      System.out.println("put(" + nums[i] + ", " + -i + ") = " + tree.put(nums[i], -i));
      System.out.println(tree);
    }

    for (int i = 0; i < 10; i++) {
      System.out.println("remove(" + nums[i] + ") = " + tree.remove(nums[i]));
      System.out.println(tree);
    }
  }
}
