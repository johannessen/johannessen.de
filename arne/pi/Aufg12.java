/**
  * Blatt: 1
  * Aufgabe: 2
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.0a1
  *
  * @see <a href="http://www.informatik.uni-ulm.de/urg/cgi-bin/pw?Aushang=4554&amp;Home=102&amp;Layout=uni&amp;Methode=Drucken" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik II</q></a>
  * 
  * <strong>Note:</strong> Alpha release! See notes for class HashTable for details.
  */
public class Aufg12
{
	public static void main (String[] args)
	{
		HashTable table = new HashTable(17);
		
		// Aufgabe c):
		int[] primaryKey = {93, 84, 73, 44, 38, 52, 56, 57};
		for (int i = 0; i < primaryKey.length; i++)
			table.insertEntry(new HashEntry(primaryKey[i], Integer.toString(primaryKey[i])));
		System.out.println("Hash-Tabelle nach Aufgabe c):");
		printTable(table);
		
		// Aufgabe d):
		final int SEARCH_KEY = 73;
		int address = table.findEntry(SEARCH_KEY);
		System.out.print("Suche nach Datensatz "+SEARCH_KEY+" ergibt Position ");
		System.out.println(address+": '"+table.getEntry(address).data(0)+"'");
		
		// Aufgabe e):
		final int REMOVE_KEY = 56;
		table.deleteEntry(REMOVE_KEY);
		System.out.println("Lšschen von Datensatz "+REMOVE_KEY+" ergibt Hash-Tabelle:");
		printTable(table);
		
		System.out.println();
	}
	
	static void printTable (HashTable table)
	{
		HashEntry entry;
		
		for (int address = 0; address < table.length(); address++) 

		{
			entry = table.getEntry(address);
			System.out.print((address<10?" ":"")+address+": ");
			if (entry != null)
			System.out.println(entry.primaryKey()+" '"+entry.data(0)+"' ("+entry.firstPointer()+", "+entry.secondPointer()+")");
			else
				System.out.println("null");
		}
	}
}


class HashEntry
{
	int primaryKey;  // was pk
	String[] data = new String[40];		// hmmmm. What do we need 41 strings for?!
										// If you REALLY intended this, it is easy
										// to extend the code to cover the rest of
										// the data array. I will restrict the use
										// to the first one.
	int firstPointer;
	int secondPointer;
	
	/** Intended for use by firstPointer and secondPointer in cases where
	  * there is nothing for these pointers to refer to.
	  */
	final static int NIL = -1;
    
	HashEntry ()
	{
		firstPointer = NIL;
		secondPointer = NIL;
	}
	
	HashEntry (int primaryKey, String data0)
	{
		firstPointer = NIL;
		secondPointer = NIL;
		this.primaryKey = primaryKey;
		this.data[0] = data0;
	}
	
	int primaryKey ()
	{
		return primaryKey;
	}
	
	int primaryKey (int primaryKey)
	{
		int temp = this.primaryKey;
		this.primaryKey = primaryKey;
		return temp;
	}
	
	String data (int index)
	{
		return data[index];
	}
	
	String data (int index, String data)
	{
		String temp = this.data[index];
		this.data[index] = data;
		return temp;
	}
	

	int firstPointer ()
	{
		return firstPointer;
	}
	
	int firstPointer (int firstPointer)
	{
		int temp = this.firstPointer;
		this.firstPointer = firstPointer;
		return temp;
	}
	
	int secondPointer ()
	{
		return secondPointer;
	}
	
	int secondPointer (int secondPointer)
	{
		int temp = this.secondPointer;
		this.secondPointer = secondPointer;
		return temp;
	}
}


/** Known issue:
  * The Method deleteEntry does not correct the pointers properly. This bug is expected
  * to be fixed by early afternoon of Tue, 2002-04-30 in Version 1.0 final.
  */
class HashTable
{
	/** The hash table is indeed an one-dimensional array of HashEntries.
	  */
	HashEntry[] entry;
	
	/** The hash table constructor needs the desired size of the array of entries.
	  */
	HashTable (int tableSize)
	{
		entry = new HashEntry[tableSize];
	}
	
	/** The length method returns the size of the array of entries. The index of the
	  * last element is <code>length() - 1</code>.
	  */
	int length ()
	{
		return entry.length;
	}
	
	/** This is the hashing function as supplied by the exercise text. Note that the
	  * given pk value must not be less than zero to prevent break of mod function!
	  */
	private int hash (int pk, int i)
	{
		return (pk + i * i) % length(); 
	}
	
	/** The insertEntry method inserts the given HashEntry into the HashTable at the
	  * appropiate position based on the hashing function and the primary key from the
	  * given entry and sets the pointer right if necessary.
	  * @return The hashed final address of the inserted entry. You may pass this to
	  * <code>getEntry()</code>.
	  */
	int insertEntry (HashEntry entry)
	{
		int hash;
		HashEntry tempEntry;
		
		for (int i = 0; true; i++)
		{
			hash = hash(entry.primaryKey, i);
			
			if (this.entry[hash] == null)
			{	// we found a free space, so insert the entry here
				this.entry[hash] = entry;
				break;
			}
			
			else
				if (i == 0)
					// try the firstPointer first
					if (this.entry[hash].firstPointer == HashEntry.NIL)
						// fill in reference to next hashed address
						this.entry[hash].firstPointer = hash(entry.primaryKey, 1);
					else
					{	// the first pointer is not right, so we follow the second
						tempEntry = this.entry[this.entry[hash].firstPointer];
						while (tempEntry.secondPointer != HashEntry.NIL)
							tempEntry = this.entry[tempEntry.secondPointer];
						tempEntry.secondPointer = hash(entry.primaryKey, 1);
					}
				
				else  // i > 0
				{	// follow secondPointer right away
					tempEntry = this.entry[hash];
					while (tempEntry.secondPointer != HashEntry.NIL)
						tempEntry = this.entry[tempEntry.secondPointer];
					tempEntry.secondPointer = hash(entry.primaryKey, i + 1);
				}
		}
		return hash; 
	}
	
	/** This method finds the entry with the given primary key in the table and
	  * returns its address, i. e. its hash table array index.
	  */
	int findEntry (int primaryKey)
	{
		int hash;
		HashEntry tempEntry;
		
		for (int i = 0; true; i++)
		{
			hash = hash(primaryKey, i);
			if (hash < 0)
				break;  // recover from binary carry
			
			tempEntry = entry[hash];
			if (tempEntry != null && tempEntry.primaryKey == primaryKey)
				return hash;
		}
		
		// return non-existent array index if primaryKey was not found
		return -1;
    }
	
	/** This method returns the entry at the given address (= array index).
	  */
	HashEntry getEntry (int address)
	{
		return entry[address];
	}
	
	/** This method deletes the entry with the given primary key from the table.
	  */
	void deleteEntry (int primaryKey)
	{
		int address = findEntry (primaryKey);
		
		// we still have to correct the pointers here ...
		
        entry[address] = null;
	}
}