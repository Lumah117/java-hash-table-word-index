import java.util.*;

import java.util.*;
import java.io.*;
import java.lang.Math;


public class HashWordMap implements IWordMap, IHashMonitor{
	
	private WordEntry[] hashTable;
	private float numberOfEntries;
	private float maxLoadFactor;
	private int numOperations;
	private int numProbes;
	private int prime;
	private int size;
		
	public HashWordMap() {
		
		hashTable = new WordEntry[13];
		size = hashTable.length;
		numProbes = 0;
		numOperations = 0;
		prime = 13;
		maxLoadFactor = 0.5f;
		numberOfEntries = 0;
		
	}
	
	public HashWordMap (float maxLoadFactor) {
		
		this.maxLoadFactor = maxLoadFactor;
		hashTable = new WordEntry[13];
		size = hashTable.length;
		numberOfEntries = 0;
		numOperations = 0;
		numProbes = 0;
		prime = 13;
		
	}
	
	public int getSize() {
		return size;
	}


	public int getPrime() {
		return prime;
	}
	
	
	public int getNumProbes() {
		return numProbes;
	}

	public void addPos (String word, IPosition position) {
		
		int key = hashFunction(word);
		int probe = collision(word);
		String currentWord;
		WordEntry current;
		
		while (true) {
			current = hashTable[key];
			
			if (current != null) {
				currentWord = current.getKey();
				
			} else {
				currentWord = "Christopher";
			}
			
			if (currentWord.equals(word)) {
				current.addPos(position);
				
				break;
				
			} else if(current !=null && !(currentWord.equals(word))) { 
				numProbes++;
				key = key + probe;
				
				if (key >= size) {
					key = key - size;
				}
			} else {
				hashTable[key] = new WordEntry (word, position);
				numberOfEntries++;
				
				break;
			}
			 
		}
		
		updateLoadFactor();
		numOperations++;
		
	}
	
	public void removeWord (String word) throws WordException{
		
		int key = hashFunction(word);
		int probe = collision(word);
		WordEntry current;
		String currentWord;
		
		while (true) {
			current = hashTable[key];
			
			if (current != null) {
				currentWord = current.getKey();
				
			} else {
				currentWord = "Christopher";
			}
						
			if (currentWord.equals(word)) {
				hashTable[key] = new WordEntry();
				numberOfEntries--;
				
				break;
				
			} else if(current !=null && !(currentWord.equals(word))) { 
				numProbes++;
				key = key + probe;
				
				if (key >= size) {
					key = key - size;
				}
				
			} else {
				throw new WordException("There are no entries for this word. Please enter another word");

			} 
		}
		
		updateLoadFactor();
		numOperations++;
		
	}
	
	
	public void removePos (String word, IPosition position) throws WordException{
		
		// code to tell us where the word exists in the hashtable or its location
		int key = hashFunction(word);
		int probe = collision(word);
		String currentWord;
		WordEntry current;
		
		// code to loop while condition is met, in this case, if condition true.
		while (true) {
			current = hashTable[key];
			
			if (current != null) {
				currentWord = current.getKey();
				
			} else {
				currentWord = "Christopher";
			}
						
			if (currentWord.equals(word)) {
				current.removePos(position);
				
				if (current.getValues().size() == 0) {
					hashTable[key] = new WordEntry();
					numberOfEntries--;
				}
				
				break;
				
				// code to say 'else' do this
			} else if(current !=null && !(currentWord.equals(word))) { 
				numProbes++;
				key = key + probe;
				
				if (key >= size) {
					key = key - size;
				}
				
				// code to throw new word exception if the word cannot be found
			} else {
				throw new WordException("There are no entries for this word and/or position. Please enter another word/position");
						
			} 
		}
		
		updateLoadFactor();
		numOperations++;
		
	}
	
	public Iterator <String> words(){
		
		LinkedList <String> words = new LinkedList <String>();
		
		WordEntry current;
		String currentWord;
		
		for (int i = 0; i< hashTable.length;i++) {
			current = hashTable[i];
			
			if ((current != null) && !(current.getKey().equals("Christopher"))) {
				currentWord = current.getKey();
				words.add(currentWord);
			}
		}
		
		Iterator <String> wordIterator = words.iterator();
		
		return wordIterator;
	}
	
	public Iterator <IPosition> positions(String word) throws WordException{
		
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		
		int key = hashFunction(word);
		int probe = collision(word);
		WordEntry current;
		
        // code to loop while condition is met. 
		while (true) {
			current = hashTable[key];
			
			if (current == null) {
				throw new WordException("Position Iterator: It seems there are no entries for this word");
			}
			else if (current.getKey().equals(word)) {
				values = current.getValues();
				Iterator <IPosition> positionIterator = values.iterator();
				numOperations++;
				return positionIterator;
				
			} else  { 
				numProbes++;
				key = key + probe;
				
				if (key >= size) {
					key = key - size;
				}
			}
		}
		
	}
	
	public int numberOfEntries() {
		
		int WordEntryNumber = 0;
		WordEntry current;
		
		for (int i = 0; i<size;i++) {
			current = hashTable[i];
			
			if (current != null && !(current.getKey().equals("Christopher"))) {
				WordEntryNumber++;
			}
		}
		
		return WordEntryNumber;
	}
	
	public int numberOfPositions (String word) throws WordException{
		
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		
		int key = hashFunction(word);
		int probe = collision(word);
		WordEntry current;
		String currentWord;
		
		// code to say loop while condition is true, in this case, if condition is true.
		while (true) {
			current = hashTable[key];
			
			if (current != null) {
				currentWord = current.getKey();
				
			} else {
				currentWord = "Christopher";
			}
						
			if (currentWord.equals(word)) {
				values = current.getValues();
				int valueNumb = values.size();
				return valueNumb;
				
				// code to say 'else if', do the following
			} else if(current !=null && !(currentWord.equals(word))) { 
				numProbes++;
				key = key + probe;
				
				if (key >= size) {
					key = key - size;
				}
				
				// code to throw new word exception
			} else {
				throw new WordException("There are no entries for this word. Please enter another word");
			
			} 
		}
	
	}
	
	public int numberOfFiles (String word) throws WordException{
		
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		LinkedList <String> usedFiles;
		
		int key = hashFunction(word);
		int probe = collision(word);
		WordEntry current;
		IPosition currentPosition;
		String currentWord;
		Iterator <IPosition> iterator2;
		String fileName;
		int fileNumber = 0;

		// code to say loop while condition is met, in this case, if condition is true. 
		while (true) {
			current = hashTable[key];
			
			if (current != null) {
				currentWord = current.getKey();
				
			} else {
				currentWord = "Christopher";
			}
						
			if (currentWord.equals(word)) {
				values = current.getValues();
				usedFiles = new LinkedList <String>();
				iterator2 = values.iterator();
				
				while (iterator2.hasNext()) {
					currentPosition = iterator2.next();
					fileName = currentPosition.getFileName();
					
					if (!(usedFiles.contains(fileName))) {
						usedFiles.add(fileName);
						fileNumber++;
					}
				}
								
				return fileNumber;
				
				//code to say 'else if' do the following.
			} else if(current !=null && !(currentWord.equals(word))) { 
				numProbes++;
				key = key + probe;
				if (key >= size) {
					key = key - size;
				}
			} else {
				
				// code to throw new word exception
				throw new WordException("There are no entries for this word. Please enter another word");
			} 
		}
	}
	
	public int possOverview() {
		
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		Iterator <IPosition> iterator2;
		
		WordEntry current;
		int possNumber = 0;
		
		for (int i = 0; i<size;i++) {
			current = hashTable[i];
			
			if (current != null && current.getKey() != "Christopher") {
				values = current.getValues();
				iterator2 = values.iterator();
				
				while (iterator2.hasNext()) {
					possNumber++;	
					iterator2.next();
				}
			}
		}
			
		return possNumber;
	}
	
	public int fileOverview() {
		
		LinkedList <String> usedFiles = new LinkedList <String>();
		LinkedList <IPosition> values = new LinkedList <IPosition>();
		Iterator <IPosition> iterator2;
		
		IPosition currentPosition;
		WordEntry current;
		String fileName;
		int fileNumber = 0;
		
		for (int i = 0; i<size;i++) {
			current = hashTable[i];
			
			if (current != null && current.getKey() != "Christopher") {
				values = current.getValues();
				iterator2 = values.iterator();
				
				while (iterator2.hasNext()) {
					currentPosition = iterator2.next();
					fileName = currentPosition.getFileName();
					
					if (!(usedFiles.contains(fileName))) {
						usedFiles.add(fileName);
						fileNumber++;
					}
				}
			}
		}
			
		return fileNumber;
	}
	
	public float getMaxLoadFactor() {
		
		return (maxLoadFactor);
	}
	
	public float getLoadFactor() {
		
		return (float) ( (float)numberOfEntries()/ (float)size);
	}
	
	public float averNumProbes() {
		
		return (float)((float)numProbes/(float)numOperations);
	}
	
	public int hashCode (String s) {
		
		int hash = 0;
	    char[] chars = s.toCharArray();

	    for (int i = 0; i < chars.length; i++)
	      hash += ((int)chars[i])*(Math.pow(31, chars.length-i-1));
	    
	    return hash;
	}
		
	public int compress (int hash) {
		
		return hash % size;
	}
	
	public int compress2 (int hash) {
		
		return (prime - (hash % prime));
	}
	
	public int hashFunction (String word) {
		
		int hash = hashCode (word);
		int key = compress(hash);
		return key;
	}
	
	public int collision (String word) {
		
		int hash = hashCode (word);
		int key = compress2(hash);
		return key;
	}
	
	public void resize () {
			
		WordEntry[] oldHashTable = hashTable;
		prime = newPrime();
		size = newSize();
		hashTable = new WordEntry[size];
//		size = newSize();
		WordEntry current;
		
		for (int i = 0; i<oldHashTable.length;i++) {
			current = oldHashTable[i];
			
			if (current != null) {
				addWordEntry(current);
			}
		}	
		
	}
	
	public int newSize () {
		
		LinkedList <Integer> modulus;
		
		int trySize = size*2+1;
		int newSize = 0;
		int number;
		boolean found = false;
		boolean restart = false;
	
		// code to say loop while condition is met, in this case, if condition is false.
		while (found == false) {
			modulus = new LinkedList <Integer>();
			
			for (int i = 2;i <= Math.sqrt(trySize);i++) {
				modulus.add(trySize%i); 	
			
			}
			
			for (int j = 0;j < modulus.size();j++) {
				number = modulus.get(j);
				
				if (number == 0) {
					trySize = trySize + 2;
					restart = true;
					break;
				}
			}
			
			if (restart == true) {
				restart = false;
				
			} else {
				newSize = trySize;
				found = true;
			}
			
		}
		
		return newSize;
	}
	
	public int newPrime () {
		
		LinkedList <Integer> modulus;
		
		int tryPrime = size*2-1;
		int newPrime = 0;
		int number;
		boolean found = false;
		boolean restart = false;
	
		while (found == false) {
			modulus = new LinkedList <Integer>();
			
			for (int i = 2;i <= Math.sqrt(tryPrime);i++) {
				modulus.add(tryPrime%i); 	
			}
			
			for (int j = 0;j < modulus.size();j++) {
				number = modulus.get(j);
				
				if (number == 0) {
					tryPrime = tryPrime - 2;
					restart = true;
					break;
				}
			}
			
			if (restart == true) {
				restart = false;
				
			} else {
				newPrime = tryPrime;
				found = true;
			}
			
		}
		
		return newPrime;
	}
	
	public void addWordEntry (WordEntry current) {
		
		int key = hashFunction(current.getKey());
		int probe = collision(current.getKey());
		WordEntry newWordEntry;
		
		while (true) {
			newWordEntry = hashTable [key];
			
			if (newWordEntry != null) {
				numProbes++;
				key = key + probe;
				
				if (key >= size) {
					key = key - size;
				}
				
			} else {
				hashTable[key] = current;
				numOperations++;
				return;
			}	
		}

	}
	
	public void updateLoadFactor () {
		
		if (getLoadFactor() >= getMaxLoadFactor()) {
			resize();
		}
	}
	
}
