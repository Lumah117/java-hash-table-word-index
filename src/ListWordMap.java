import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListWordMap implements IWordMap {
	
	private LinkedList <WordEntry> wordMap;
	private Iterator <WordEntry> wordIterator;
	
	public ListWordMap() {
		
		wordMap = new LinkedList <WordEntry>();
	}
	
	@Override // code for adding a position
	public void addPos(String word, IPosition position) {
		
		wordIterator = wordMap.iterator();
		WordEntry current;
		String key;
		
		//code to loop while condition is met.
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			key = (String) current.getKey();
			
			if (key.equals(word)) {
				current.addPos(position);
				
				return;
			}
			
		}
		
		wordMap.add(new WordEntry(word, position));
		
	}
	
	public Iterator <String> words() {
		
		LinkedList <String> keys = new LinkedList <String>();
		
		wordIterator = wordMap.iterator();
		WordEntry current;
		String key;
		
		// code to loop while condition is met
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			key = (String) current.getKey();
			keys.add(key);
		}
		
		Iterator <String> iterator = keys.iterator();
		
		return iterator;
	}
	
	@Override
	public int numberOfEntries() {
		
		int numberOfEntries = wordMap.size();
		
		return numberOfEntries;
	}
	
	@Override // code to remove a word from the map
	public void removeWord(String word) throws WordException {
		
		wordIterator = wordMap.iterator();
		WordEntry current;
		String key;
		
		// code to loop while condition is met.
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			key = (String) current.getKey();
			
			if (key.equals(word)) {
				wordMap.remove(current);
				
				return;
			}
		}
		
		throw new WordException("It seems there are no entries for this word. Please enter another word");

}
	
	@Override // code to remove position from the map
	public void removePos(String word, IPosition position) throws WordException {

		wordIterator = wordMap.iterator();
		WordEntry current;
		String key;
		
		// code to loop while condition is met
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			key = (String) current.getKey();
			
			if (key.equals(word)) {
				((List<WordEntry>) current).remove(position);
				
				if (current.getValues().size()==0) {
					wordMap.remove(current);
				}
				
				return;
			}
			
	    }
		
		throw new WordException ("It seems there are no entries for this word. Please enter another word");
	
	}
	
	@Override
	public Iterator <IPosition> positions(String word) throws WordException {
		
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		
		wordIterator = wordMap.iterator();
		WordEntry current;
		String key;
		
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			key = (String) current.getKey();
			
			if (key.equals(word)) {
				values = (LinkedList<IPosition>) current.getValues();
				Iterator <IPosition> positionIterator = values.iterator();
				
				return positionIterator;
			}
	}
		
		throw new WordException ("It seems there are no entries for this word. Please enter another word");

	}
	
	public int numberOfFiles (String word) throws WordException {
		
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		LinkedList <String> usedFile;
		Iterator <IPosition> iterator1;
		
		wordIterator = wordMap.iterator();
		WordEntry current;
		IPosition currentPosition;
		String fileName;
		int fileNumber = 0;
		String key;
		
		//code to loop while the condition is met
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			key = (String) current.getKey();
			
			if (key.equals(word)) {
				values = (LinkedList<IPosition>) current.getValues();
				usedFile = new LinkedList <String>();
				iterator1 = values.iterator();
				
				while (iterator1.hasNext()) {
					currentPosition = iterator1.next();
					fileName = currentPosition.getFileName();
					
					if (!(usedFile.contains(fileName))) {
						usedFile.add(fileName);		
						fileNumber++;
					}
				}
				
				return fileNumber;
				
			}
		}
		
		throw new WordException ("It seems there are no entries for this word. Please enter another word");

	}
	
	public int positionOverview() {
	
		LinkedList <IPosition> value = new LinkedList <IPosition>();
		wordIterator = wordMap.iterator();
		Iterator <IPosition> iterator1;
		
		WordEntry current;
		int positionNumber = 0;
		
		//code to loop while the condition is met
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			value = (LinkedList<IPosition>) current.getValues();
			iterator1 = value.iterator();
			
			while (iterator1.hasNext()) {
				positionNumber++;
				iterator1.next();
			}
				
		}
		
		return positionNumber;
		
	}
	
	public int fileOverview() {
		
		LinkedList <IPosition> value = new LinkedList <IPosition>();
		LinkedList <String> usedFile = new LinkedList <String>();
		Iterator <IPosition> iterator1;
		
		wordIterator = wordMap.iterator();
		WordEntry current;
		IPosition currentPosition;
		String fileName;
		int fileNumber = 0;
		
		// code to loop while the condition is met
		while (wordIterator.hasNext()) {
			current = wordIterator.next();
			value = (LinkedList<IPosition>) current.getValues();
			iterator1 = value.iterator();
			
			while (iterator1.hasNext()) {
				currentPosition = iterator1.next();
				fileName = currentPosition.getFileName();
				
				if (!(usedFile.contains(fileName))) {
					usedFile.add(fileName);
					fileNumber++;
				}
			}
		}
		
		return fileNumber;
		
	}		
		
}
