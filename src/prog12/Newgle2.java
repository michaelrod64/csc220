package prog12;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Newgle2 implements SearchEngine {

	PageTrie page2index = new PageTrie();
	WordTable word2index = new WordTable();
	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	
	
		public void gather(Browser browser, List<String> startingURLs) {
			page2index.read(pageDisk);
			word2index.read(wordDisk);
		}
		
		public class PageComparator implements Comparator<Long> {

			public int compare(Long o1, Long o2) {
				return pageDisk.get(o1).getRefCount() - pageDisk.get(o2).getRefCount();	
			}
		}
		
		public String[] search (List<String> keyWords, int numResults) {
			Iterator<Long>[] pageIndexIterators = (Iterator<Long>[]) new Iterator[keyWords.size()];
			long[] currentPageIndices;
			PriorityQueue<Long> bestPageIndices;
			
			currentPageIndices = new long[keyWords.size()];
			bestPageIndices = new PriorityQueue<Long>(12, new PageComparator());
			
			for(int i = 1; i < pageIndexIterators.length; i++) {
				String keyWord = keyWords.get(i);
				long index = word2index.get(keyWord); //returns long
				pageIndexIterators[i] = wordDisk.get(index).iterator();
				
				if(pageIndexIterators[i].hasNext() == true) {
					currentPageIndices[i] = pageIndexIterators[i].next();
				}
			}
			
			boolean movedFoward = false;
			
			do {
				
				if (allEqual(currentPageIndices)) {
				bestPageIndices.offer(currentPageIndices[0]);
					
				movedFoward = moveForward(currentPageIndices, pageIndexIterators,
							currentPageIndices[0] + 1);

				} else {
					movedFoward = moveForward(currentPageIndices, pageIndexIterators, -1);
				}
			} while (movedFoward);
			
			String[] resultString = new String[bestPageIndices.size()];
			int i;
			i = resultString.length - 1;
			while(!(bestPageIndices.isEmpty())) {
				long j = bestPageIndices.poll();
				 PageFile page = pageDisk.get(j);
				 resultString[i] = page.url;
				i--;
			}
			return resultString;
		}
		
		private boolean allEqual(long[] array) {
			int i = 0;
			while(i < (array.length - 1) && array[i] == array[i + 1]) {
				i++;
			}
			if (i == array.length - 1) {
				return true;
			}
			return false;
		}
		
		private boolean moveForward(long[] currentPageIndices, 
				Iterator<Long>[] pageIndexIterators, long maxValue) {
			
			if (maxValue < 0) {
			
			
			
			for (int k = 0; k < currentPageIndices.length; k++) {
				if (currentPageIndices[k] > maxValue) {
					maxValue = currentPageIndices[k];
				}
			}
			}
			
				int i = 0;
			while (i < pageIndexIterators.length) {
				if(!(pageIndexIterators[i].hasNext())) {
					return false;
				}
				if (currentPageIndices[i] < maxValue) {
					currentPageIndices[i] = pageIndexIterators[i].next();
				}
			}
			return true;
			
		}
		
}
