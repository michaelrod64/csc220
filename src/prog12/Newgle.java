package prog12;
import java.util.*;

public class Newgle<T> implements SearchEngine{
	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
	PageTrie page2index = new PageTrie();
	
	//step 7
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>(); //step 7
	WordTable word2index = new WordTable(); //step 7
	
	public long indexPage(String url) {
		long index = pageDisk.newFile();
		PageFile pageFile = new PageFile(index, url); 
		pageDisk.put(index, pageFile);
		page2index.put(url, index);
		System.out.println("Indexed " + url);
		
		return index;
		
	}
	
	public long indexWord(String word) {
		long wordIndex = wordDisk.newFile();
		List<Long> wordFile = new LinkedList<Long>(); 
		wordDisk.put(wordIndex, wordFile);
		word2index.put(word, wordIndex);
		
		return wordIndex;
	}
	
	public void gather (Browser browser, List<String> startingURLs){
		for(String url : startingURLs) {
			if(!page2index.containsKey(url)){
				indexPage(url);
			}
		}
		Queue<String> pageQueue = new LinkedList<String>(startingURLs);
		while(!pageQueue.isEmpty()) {
			Set<String> set = new HashSet<String>(); //step 6
			Set<String> wordSet = new HashSet<String>();
			String url = pageQueue.poll();
			if(browser.loadPage(url)) {
				for(String urls: browser.getURLs()) {
					if(!page2index.containsKey(urls)) {
						indexPage(urls);
						pageQueue.add(urls);
					}
					if(!set.contains(urls)) { //step 6
						pageDisk.get(page2index.get(urls)).incRefCount();
						set.add(urls);
					}
				}
				for(String words: browser.getWords()) {
					if(!word2index.containsKey(words)) {
						indexWord(words);
					}
					if(!wordSet.contains(words)) {
						wordDisk.get(word2index.get(words)).add(page2index.get(url)); //check step 9
						wordSet.add(words);
					}
				}
			}
		}
		System.out.println(pageDisk);
		System.out.println(page2index);
		System.out.println(wordDisk);
		System.out.println(word2index);
		page2index.write(pageDisk);
		word2index.write(wordDisk);
		
		
	}
	
	public String[] search (List<String> keyWords, int numResults) {
		return new String[0];
	}
	
	
	
	
	
	

}
