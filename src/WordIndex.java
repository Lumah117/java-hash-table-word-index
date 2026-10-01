import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/** Main class for the Word Index program */

public class WordIndex{

	// this is the line of code i have to change to check the TextFiles or the TextFiles_Shakespeare
	static final File textFilesFolder = new File("TextFiles_Shakespeare");
	
	static final FileFilter commandFileFilter = (File file) -> file.getParent()==null;
	static final FilenameFilter txtFilenameFilter = (File dir, String filename) -> filename.endsWith(".txt");
	
	static void sortit(ArrayList <Integer> time, ArrayList <String> fileName, ArrayList <LinkedList<Integer>> lines) { 
        
		if (time.size() <= 1) {
			return;
			
		} else {
			int pivot = time.get(0);
			int timeValue;
			String nameValue;
			
			LinkedList <Integer> linesValue = new LinkedList<Integer>();
			
			ArrayList <Integer> timeL = new ArrayList <Integer>();
			ArrayList <Integer> timeE = new ArrayList <Integer>();
			ArrayList <Integer> timeG = new ArrayList <Integer>();
			
			ArrayList <String> fileNameL = new ArrayList <String>();
			ArrayList <String> fileNameE = new ArrayList <String>();
			ArrayList <String> fileNameG = new ArrayList <String>();
			
			ArrayList <LinkedList<Integer>> linesL = new ArrayList <LinkedList<Integer>>();
			ArrayList <LinkedList<Integer>> linesE = new ArrayList <LinkedList<Integer>>();
			ArrayList <LinkedList<Integer>> linesG = new ArrayList <LinkedList<Integer>>();
			
			while (!time.isEmpty()) {
				timeValue = time.remove(0);
				nameValue = fileName.remove(0);
				linesValue = lines.remove(0);
				
				if (timeValue < pivot) {
					timeL.add(timeValue);
					fileNameL.add(nameValue);
					linesL.add(linesValue);
					
				} else if (timeValue == pivot) {
					timeE.add(timeValue);
					fileNameE.add(nameValue);
					linesE.add(linesValue);
					
				} else {
					timeG.add(timeValue);
					fileNameG.add(nameValue);
					linesG.add(linesValue);
				}
				
			}
			
			//Recursively calls the sort it 
			sortit (timeL, fileNameL, linesL);
			sortit (timeG, fileNameG, linesG);
			
			//Combining the less than, equal to and greater than lists
			time.addAll(timeL);
			fileName.addAll(fileNameL);
			lines.addAll(linesL);
			
			time.addAll(timeE);
			fileName.addAll(fileNameE);
			lines.addAll(linesE);
			
			time.addAll(timeG);
			fileName.addAll(fileNameG);
			lines.addAll(linesG);
				
		}
		
    } 

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	//System.out.println(args.length);
	
		if (args.length != 1 ) {
			System.err.println("Usage: WordIndex commands.txt");
			System.exit(1);
		}
		
		try{
			File commandFile = new File(args[0]);
			if (commandFile.getParent()!=null) {
				System.err.println("Use a command file in current directory");
				System.exit(1);
			}

			// code to create a command reader from a file
			WordTxtReader commandReader = new WordTxtReader(commandFile);

			// code to initialise map
			IWordMap wordPossMap = new HashWordMap();
			
			long startTime = System.currentTimeMillis();
			long previousTime = System.currentTimeMillis();
			System.out.println("Start time: "+ (System.currentTimeMillis()-startTime)+ "ms");
			System.out.println();
			

			// code for reading the content of the command file
			while(commandReader.hasNextWord()) {
				
				// code for getting the next command
				String command = commandReader.nextWord().getWord();

				switch (command) {
				// code for "addall" case.
				case "addall":
					int numEntries;
					int numberOfEntries1 = wordPossMap.numberOfEntries();
					int numberOfEntries2;
					assert(textFilesFolder.isDirectory());
					File[] listOfFiles = textFilesFolder.listFiles(txtFilenameFilter);
					Arrays.sort(listOfFiles);
					int numberOfFiles = 0;
					
					for (File textFile : listOfFiles) {
						WordTxtReader wordReader = new WordTxtReader(textFile);
						numberOfFiles++;

						while (wordReader.hasNextWord()) {
							WordPosition wordPos = wordReader.nextWord();
							wordPossMap.addPos(wordPos.getWord(), wordPos);
						}
						
						// code for printing the time in milliseconds
						System.out.println("Current time: "+ (System.currentTimeMillis()-startTime)+ "ms");
						System.out.println(textFile.getName()+" took "+ (System.currentTimeMillis()-previousTime)+ "ms");
						System.out.println();
						previousTime = System.currentTimeMillis();
					}
					
					numberOfEntries2 =  wordPossMap.numberOfEntries();
					numEntries = numberOfEntries2 - numberOfEntries1;
					
					System.out.println(numEntries+" entries have been indexed from "+ numberOfFiles+ " files");
					
					break;

					// code for "add" case.
				case "add":
					int numberOfEntries3;
					int numberOfEntries4 = wordPossMap.numberOfEntries();
					int numberOfEntries5;
					File textFile = new File(textFilesFolder, commandReader.nextWord().getWord()+".txt");
					WordTxtReader wordReader = new WordTxtReader(textFile);
					
					while (wordReader.hasNextWord()) {
						WordPosition word = wordReader.nextWord();
						wordPossMap.addPos(word.getWord(), word);
					}
					
					numberOfEntries5 =  wordPossMap.numberOfEntries();
					numberOfEntries3 = numberOfEntries5 - numberOfEntries4;
					
					System.out.println(numberOfEntries3 +" entries have been indexed from file "+ textFile.toString());
					
					break;

					// code for "search" case.
				case "search":
					try {
						
						int parse = Integer.parseInt(commandReader.nextWord().getWord());
						String word = commandReader.nextWord().getWord();
						
						ArrayList <Integer> time = new ArrayList <Integer>();
						ArrayList <String> fileName = new ArrayList <String>();
						ArrayList <LinkedList<Integer>> lines = new ArrayList <LinkedList<Integer>>();
						
						
						Iterator <String> wordIterator = wordPossMap.words();
						Iterator <IPosition> poss;
						IPosition currentPosition;
						String currentFile;
						int positionNumber;
						int fileNumber;
						boolean match;
						int counter = 0;
						int position = 0;
						
						// code to initialise the variables that are going to be displayed
						int printTime;
						String printName;
						String printLines;
						
						// code to print the head 
						positionNumber = ((HashWordMap) wordPossMap).numberOfPositions(word);
						fileNumber = ((HashWordMap) wordPossMap).numberOfFiles(word);
						System.out.println("The word '" + word + "' occurs "+ positionNumber + " time in " + fileNumber + " files:");
											
						
						poss = wordPossMap.positions(word);
						
						// code to loop while the condition is met.
						while (poss.hasNext()) {
							currentPosition = poss.next();
							currentFile = currentPosition.getFileName();
							
							match = false;
							
							for (int i = 0; i< fileName.size(); i++) {
								if (currentFile.equals(fileName.get(i))) {
									match = true;
									position = i;
									break;
								}
							}
							
							if (match == false) {
								fileName.add(currentFile); //fileName[counter] = currentFile;
								time.add(1); //time[counter] = 1;
								lines.add(new LinkedList <Integer>()); //lines[counter] = new LinkedList <Integer>();
								lines.get(counter).add(currentPosition.getLine()); //lines[counter].add(currentPosition.getLine()); get() from linkedList add()from ArrayList
								counter++;
							} else {
								time.set(position, time.get(position) + 1); //time[position] = time[position] + 1;
								lines.get(position).add(currentPosition.getLine()); //lines[position].add(currentPosition.getLine());
							}
							
						}
					
						sortit(time, fileName, lines);
											
						for (int k = 0; k<parse; k++) {
							printTime = time.get(k);
							printName = fileName.get(k);
							printLines = lines.get(k).toString();
							System.out.println("	"+ printTime + " time in " + printName);
							System.out.println("		lines "+ printLines);
						}
								
					} catch (WordException e) {
						
						System.err.println("Enter a valid word");
					}
						
					break;

					// code for "remove" case
				case "remove":
					int numberOfEntries6;
					int numberOfEntries7 = wordPossMap.numberOfEntries();
					int numberOfEntries8;
					
					File textFileToRemove = new File(textFilesFolder, commandReader.nextWord().getWord()+".txt");
					
					Iterator <String> words = wordPossMap.words();					
					Iterator <IPosition> positions;
					String currentWord;
					String currentFile;
					IPosition currentPosition;
					boolean empty = false;
				
					try {
						
						// code to say loop while condition is met.
					while (words.hasNext()) {
						currentWord = words.next();
						positions = wordPossMap.positions(currentWord);
						
						//code for nested while loop to say loop while condition is met.
						while (empty == false && positions.hasNext()) {
							currentPosition = positions.next();
							currentFile = currentPosition.getFileName();
							currentFile = currentFile.toLowerCase();
				
							if (currentFile.equals(textFileToRemove.getName())){
								wordPossMap.removePos(currentWord, currentPosition);
								try {
								positions = wordPossMap.positions(currentWord);
								} catch (WordException e) {
									empty = true;
								}
							}
							
							if (empty) {
								empty = false;
								break;
							}
							
						}
					}
					
					numberOfEntries8 =  wordPossMap.numberOfEntries();
					numberOfEntries6 = numberOfEntries7 - numberOfEntries8;
					
					if(numberOfEntries6 == 0) {
						throw new WordException("There are no entries from this file: "+textFileToRemove.toString()+". So, we can't remove them");
					}
					
					System.out.println(numberOfEntries6 +" entries have been removed from file "+ textFileToRemove.toString());
					
					} catch (WordException e) {
						System.err.println(e.toString());
					}
					
				default:
					
					break; 
				}

			}

		}
		// catch exceptions caused by file input/output errors
		catch (IOException e){ 
			
			//System.err.println("Check your file name");
			System.err.println(e);
			System.exit(1);
		}  
		
	}
	
}
