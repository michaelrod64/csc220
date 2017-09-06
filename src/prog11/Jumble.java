package prog11;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import prog11.OpenHashTable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] sorted = new char[word.length()];
    for (int n = 0; n < word.length(); n++) {
      char c = word.charAt(n);
      int i = n;

      while (i > 0 && c < sorted[i-1]) {
        sorted[i] = sorted[i-1];
        i--;
      }

      sorted[i] = c;
    }

    return new String(sorted, 0, word.length());
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI();
    //Map<String,String> map = new TreeMap<String,String>();
     //Map<String,String> map = new DummyList<String,String>();
    //Map<String,String> map = new SkipList<String,String>();
    //Map<String, String> map = new Tree<String, String>();
    //Map<String, String> map = new BTree<String, String>();
      //Map<String, List<String>> map = new ChainedHashTable<String, List<String>>();
      Map<String, List<String>> map = new OpenHashTable<String, List<String>>();
    
    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
      String word = in.nextLine();
      if (n++ % 1000 == 0)
	      System.out.println(word + " sorted is " + sort(word));
      
      String sortedKey = sort(word);
      List<String> word1;
      word1 = map.get(sortedKey);
      if(word1 == null) {
    	 word1 = new ArrayList<String>();	  
      }
      word1.add(word);
      map.put(sortedKey, word1);
      
      // EXERCISE 2: Insert an entry for word into map.
      // What is the key?  What is the value?
      

    }
    String unjumbled;
    while (true) {
    	
      String jumble = ui.getInfo("Enter jumble.");
      if (jumble == null)
        break;
      
      String key = sort(jumble);
      List<String> list = map.get(key);
      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?
      if(list == null) {
    	  ui.sendMessage("This jumble does not have a match");
    	  
      }
      else {
    	  ui.sendMessage(jumble + " unjumbled is " + list);
      }
        
    }
    String letters = ui.getInfo("What are the clue letters?");
    int firstLength = Integer.parseInt(ui.getInfo("How many letters is the first word?"));
    String key2;
    String sorted = sort(letters);
   for(String key1:map.keySet())
    	if (key1.length() == firstLength) {
    		key2 = "";
    		int j = 0;
    		for(int i = 0; i < sorted.length(); i++) {
    			if (j < key1.length() && sorted.charAt(i) == key1.charAt(j)) {
    				j++;
    			}
    			else if(j < key1.length() && sorted.charAt(i) - key1.charAt(j) > 0) {
    				break;
    			}
    			else {
    				key2 = key2 + sorted.charAt(i);
    			}
    		}
    		if (j == key1.length() && map.containsKey(key2)) {
    			ui.sendMessage(map.get(key1) + " " + map.get(key2));
    		}
    		
    		
    		
    	}
   
  }
}

        
    

