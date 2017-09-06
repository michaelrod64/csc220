package prog06;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import prog02.GUI;
import prog02.UserInterface;

//import prog06.LinkedQueue.Node;


public class WordPath {
	static UserInterface ui = new GUI();
	static String startWord;
	static String target;


	public static void main (String[] args) {
		boolean goodInput = false;
		boolean isDictionary = false;
		WordPath game = new WordPath();
		while(!isDictionary) {
		
			String file = ui.getInfo("Enter the name of your dictionary file");
			if (file == null) {
				return;
			}
			isDictionary = game.loadDictionary(file);
		}
		while(!goodInput) {
			startWord = ui.getInfo("Select a Starting word");
			if (startWord == null) {
				return;
			}
			goodInput = (find(startWord) != null);
			if(!goodInput) {
				ui.sendMessage("Word not found in dictionary.\nTry again.");
			}
		}
		goodInput = false;
		boolean sameLength = false;
		boolean sameWord = true;
		while (!goodInput || !sameLength || sameWord) {
			target = ui.getInfo("Select a target word");
			if (target == null) {
				return;
			}
			sameWord = target.equals(startWord);
			sameLength = (target.length() == startWord.length());
			goodInput = (find(target) != null);
			if (sameWord) {
				ui.sendMessage("Your target word cannot be the same word as your starting word");
			}
			if (!sameLength) {
				ui.sendMessage("Your target word must be the same length as your starting word");
			}
			else if(!goodInput) {
				ui.sendMessage("Word not found in dictionary.\nTry again.");
			}
		}
		
		
		String[] choices = {"Human", "Computer"};
		ui.sendMessage("Choose whether a Human or Computer will play");
		int choiceNum = ui.getCommand(choices);

		switch(choiceNum) {

		case 0: game.play();
		break;
		case 1: game.solve(startWord, target);
		break;
		}

	}
	private static class Node<String> {
		private String word;
		private Node<String> previous;


		// Constructors
		/**
		 * Creates a new node with a null next field.
		 * @param item The item stored
		 */
		private Node (String word) {
			this.word = word;
			previous = null;
		}

		/**
		 * Creates a new node that references another node.
		 * @param item The item stored
		 * @param next The node referenced by new node
		 */
		private Node (String word, Node<String> previous) {
			this.word = word;
			this.previous = previous;
		}
	} //end class Node
		
	private static Node<String> find(String findWord) {
		for (Node<String> node: nodes) {
			if(node.word.equals(findWord)) {
				return node;
			}
		}
		return null;
	}
		
	static List<Node> nodes = new ArrayList<Node>();
	
	boolean loadDictionary(String file) {
		
		    // Remember the source name.
		    try {
		    	
		      // Create a BufferedReader for the file.
		      Scanner in = new Scanner(new File(file));
		      String word;
		      Node<String> wordNode;

		      // Read each name and number and add the entry to the array.
		      while (in.hasNextLine()) {
		        word = in.nextLine();
		        
		        	wordNode = new Node<String>(word);
		        	
		        	nodes.add(wordNode);
		      }	
		      		in.close();
		        	return true;
		      

		      // Close the file.
		     
		    } catch (FileNotFoundException ex) {
		      // Do nothing: no data to load.
		      ui.sendMessage("Dictionary not found");
		      return false;
		    }
		    
		
	}
	
	private void play() {

		while(true) {
			boolean isWord = false;
			boolean goodInput = false;
			String nextWord = null;
			ui.sendMessage("Current word is " + startWord );
			ui.sendMessage("Target word is " + target);
			while(!goodInput || !isWord) {
				nextWord = ui.getInfo("Enter your next word");
				if (nextWord == null) {
					return;
				}
				goodInput = oneDegree(startWord, nextWord);
				isWord = (find(nextWord) != null);
				if(!isWord) {
					ui.sendMessage("Word is not found in the dictionary.\nTry again.");
				}
				else if (!goodInput) {
					ui.sendMessage("Your word must be the same length as the current word and "
							+ "differ by one character.\n Try again.");
				}
			}
			startWord = nextWord;
			if(startWord.equals(target)) {
				ui.sendMessage("You win!");
				return;
			}
		}
	}
	
	void solve(String startWord, String target) {
		Node nextNode = null;
		Queue<Node> queue = new LinkedQueue<Node>();
		Node startNode = find(startWord);
		queue.offer(startNode);
		while(queue.size() != 0) {
			Node currentNode = queue.poll();
			for(Node<String> node: nodes){
				if(node.previous == null && oneDegree(node.word.toString(), currentNode.word.toString()) && !(node.word.equals(startNode.word))) {
						nextNode = node;
						nextNode.previous = currentNode;
						
						
						queue.offer(nextNode);
						
						if (nextNode.word.equals(target)) {
							ui.sendMessage("The target word has been reached!");
							String s = "";
							while(nextNode.previous != null) {
								s = nextNode.word +"\n" + s;
								nextNode = nextNode.previous;
							}
							s = "Solution\n" + startWord + "\n" + s;
							ui.sendMessage(s);
							return;
						}
				}	
			}
		}
		ui.sendMessage("Sorry, no solution found");
	}
	

	private boolean oneDegree(String word1, String word2) {
		if (word1.length() != word2.length()) {
			return false;
		}
		int count = 0;
		for(int i = 0; i < word1.length(); i++) {
			if (word1.charAt(i) == word2.charAt(i))
				count++;
		}
		return count == (word1.length() - 1);

	}



}
