import java.util.Iterator;
import java.util.LinkedList;

public class WordEntry {
	
	private LinkedList <IPosition> values = new LinkedList <IPosition>();
	private Iterator <IPosition> wordIterator;
	private String key;
	
	public WordEntry(String key, IPosition value) {
		
		this.key = key;
		this.values.add(value);
	}
	
	public WordEntry() {
		this.key = "Christopher";
	}
	
	public String getKey() {
		
		return key;
	}

	public LinkedList <IPosition> getValues() {
		
		return values;
	}
	
	//code for the removePos method 
	public void removePos(IPosition position) throws WordException {
		
		IPosition value;
		wordIterator = values.iterator();
		
		// code to say loop while the condition is met.
		while (wordIterator.hasNext()) {
			value = wordIterator.next();
			
			if (value.equals(position)) {
				values.remove(position);
				
				return;
			}
		}
		
		throw new WordException("There is no position for that word");
		
	}
	
	// code for the addPos method
	public void addPos(IPosition position) {
		
		IPosition value;
		wordIterator = values.iterator();
		
		//code to say loop while the condition is met. 
		while (wordIterator.hasNext()) {
			value = wordIterator.next();
			
			if (!(value.equals(position))) {
				values.add(position);
				
				return;
			}
		}
		
	}
	
}
