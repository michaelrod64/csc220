package prog04;

import prog02.PhoneDirectory;
import java.io.*;
import java.util.Scanner;

/** This is an implementation of the prog02.PhoneDirectory interface
 *   that uses a doubly linked list to store the data.
 *   @author vjm
 */
public class DLLBasedPD implements PhoneDirectory {
  /** The head (first entry) and tail (last entry) of the linked list
   * that stores the directory data */
  protected DLLEntry head, tail;
  
  /** The data file that contains the directory data */
  protected String sourceName = null;
    
  /** Boolean flag to indicate whether the directory was
      modified since it was either loaded or saved. */
  protected boolean modified = false;
    
  /** Method to load the data file.
      pre:  The directory storage has been created and it is empty.
      If the file exists, it consists of name-number pairs
      on adjacent lines.
      post: The data from the file is loaded into the directory.
      @param sourceName The name of the data file
  */
  public void loadData (String sourceName) {
    // Remember the source name.
    this.sourceName = sourceName;
    try {
      // Create a Scanner for the file.
      Scanner in = new Scanner(new File(sourceName));

      // Read each name and number and add the entry to the array.
      while (in.hasNextLine()) {
        String name = in.nextLine();
        String number = in.nextLine();
        // Add an entry for this name and number.
        addOrChangeEntry(name, number);
      }
      // Close the file.
      in.close();
    } catch (FileNotFoundException ex) {
      // Do nothing: no data to load.
      return;
    } catch (Exception ex) {
      System.err.println("Load of directory failed.");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
  /** Method to save the directory.
      pre:  The directory has been loaded with data.
      post: Contents of directory written back to the file in the
      form of name-number pairs on adjacent lines.
      modified is reset to false.
  */
  public void save () {
    try {
      // Create PrintStream for the file.
      PrintStream out = new PrintStream(new File(sourceName));
      
      // Write each directory entry to the file.
      // EXERCISE
      for (DLLEntry entry = head; entry != null; entry = entry.getNext()) {
        // Write the name.
        out.println(entry.getName());
        // Write the number.
        out.println(entry.getNumber());
        // EXERCISE
        
      }
      
      // Close the file and reset modified.
      out.close();
      modified = false;
    } catch (Exception ex) {
      System.err.println("Save of directory failed");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed
      @param number The new number to be assigned
      @return The old number or, if a new entry, null
  */
  public String addOrChangeEntry (String name, String number) {
    modified = true;
    DLLEntry entry = find(name);
    if (entry != null) {
      String oldNumber = entry.getNumber();
      entry.setNumber(number);
      return oldNumber;
    } else {
      entry = new DLLEntry(name, number);
      if (tail == null) {
    	  tail = head = entry;
      }
      else {
      tail.setNext(entry);
      entry.setPrevious(tail);
      tail = entry;
      }
      // Add entry to end of list.
      // EXERCISE

      return null;
    }
  }
    
  /** Find an entry in the directory.
      @param name The name to be found
      @return The entry with the same name or null if it is not there.
  */
  protected DLLEntry find (String name) {
    // EXERCISE
	  DLLEntry entry = head;
	  while(entry != null) {
		// If this is the entry you want
	    // return it.
		  if (entry.getName().equals(name)) {
			  return entry;
		  }
		  entry = entry.getNext();
    // For each entry in the directory.
    // What is the first?  What is the next?  How do you know you got them all?
	  }
      

    return null; // Name not found.
  }
  
  /** Remove an entry from the directory.
      @param name The name of the person to be removed
      @return The current number. If not in directory, null is
      returned
  */
  public String removeEntry (String name) {
    // Call find to find the entry to remove.
    DLLEntry entry = find(name);
    // If it is not there, forget it!
    if (entry == null)
      return null;

    // Get the next entry and the previous entry.
    // EXERCISE
    DLLEntry previous = entry.getPrevious();
    DLLEntry next = entry.getNext();
    
    // Two cases:  previous is null (entry is head) or not
    // EXERCISE
    
    
//    if (previous == null && next == null) {
//    	head = tail = null;
//    	
//    }
//    else if (previous == null) {
//    	next.setPrevious(null);
//    	head = next;
//    }
//    else if (next == null)
//    {
//    	previous.setNext(null);
//    	tail = previous;
//    }
//    else {
//    	previous.setNext(next);
//    	next.setPrevious(previous);
//    }
    
    if (previous == null){
    	head = next;
    }
    else {
    	previous.setNext(next);
    }
    // Two cases:  next is null (entry is tail) or not
    // EXERCISE
    if (next == null) {
    	tail = previous;
    }
    else {
    	next.setPrevious(previous);
    }
    
    

    modified = true;
    return entry.getNumber();
  }

  /** Look up an entry.
      @param name The name of the person
      @return The number. If not in the directory, null is returned
  */
  public String lookupEntry (String name) {
    DLLEntry entry = find(name);
    if (entry != null && entry.getName().equals(name))
      return entry.getNumber();
    return null;
  }
}
