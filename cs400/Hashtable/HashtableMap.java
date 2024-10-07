// --== CS400 Project One File Header ==--
// Name: Sidney Heberlein
// CSL Username: sidney
// Email: sheberlein@wisc.edu
// Lecture #: 001, TR 9:30am-10:45am
// Notes to Grader: none
import java.util.NoSuchElementException;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>
{
  // instance fields
  protected KeyValuePair<KeyType, ValueType>[] array;
  private int size; // the number of keys stored within this collection
  private KeyValuePair<KeyType, ValueType> sentinel; // a sentinel value to store in locations that
  // old key-value pairs have been removed from
  private double loadFactor; // the loadFactor
  @SuppressWarnings("unchecked")
  
  // constructors
  public HashtableMap(int capacity)
  {
    array = (KeyValuePair<KeyType, ValueType>[])new KeyValuePair[capacity];
    size = 0;
    sentinel = new KeyValuePair<KeyType, ValueType>(null, null);
    loadFactor = 0;
  }
  @SuppressWarnings("unchecked")
  public HashtableMap() // with default capacity = 8\
  {
    array = (KeyValuePair<KeyType, ValueType>[])new KeyValuePair[8];
    size = 0;
    sentinel = new KeyValuePair<KeyType, ValueType>(null, null);
    loadFactor = 0;
  }
  
  // methods
  
  // add a new key-value pair/mapping to this collection
  // throws exception when key is null or duplicate of one already stored
  public void put(KeyType key, ValueType value) throws IllegalArgumentException
  {
    if (key == null)
    {
      throw new IllegalArgumentException("The given key cannot be null.");
    }
    KeyValuePair<KeyType, ValueType> pair = new KeyValuePair<KeyType, ValueType>(key, value);
    int hashIndex = Math.abs(key.hashCode()) % getCapacity(); // the index the new value will be stored at
    if (containsKey(key))
      {
        throw new IllegalArgumentException("Value already exists in the hashtable.");
      }
      int i = hashIndex;
      do // will perform inside contents at least once (unless exception is thrown)
      {
        if (array[i] == null || array[i] == sentinel)
        {
          array[i] = pair; // if there is nothing stored at that index, we don't need to look 
          // for another index to put it at
          size++; // increment size after adding
          break; // out of the loop
        }
        i = (i + 1) % getCapacity(); // update i to keep looking for a value
      }
      while (i != hashIndex);
      updateLoadFactor();
      if (loadFactor >= 0.7)
      {
        resize(); // if the load factor is greater than 0.7 after adding the pair, resize the array
      }
  }
  
  // resizes the array if the load factor is greater than 70%
  public void resize()
  {
    KeyValuePair<KeyType, ValueType>[] dupe = this.array;
    // clear the array so that everything will be null and not sentinel
    clear(); // (removes any sentinel values too)
    // for loop and put all the things from the old array into the new array (double capacity)
    this.array = (KeyValuePair<KeyType, ValueType>[])new KeyValuePair[getCapacity()*2];
    for (int i = 0; i < dupe.length; i++)
    {
      if (!(dupe[i] == null || dupe[i] == sentinel))
      {
        put(dupe[i].getKey(), dupe[i].getValue());
      }
    }
    updateLoadFactor();
  }

  // check whether a key maps to a value within this collection
  public boolean containsKey(KeyType key)
  {
    try
    {
      get(key);
      return true; // if we get to this line, an exception wasn't thrown, so the key exists
    }
    catch (NoSuchElementException e)
    {
      return false; // if we get to this line, an exception was thrown, so the key does not exist
    }
  }

  // retrieve the specific value that a key maps to
  // throws exception when key is not stored in this collection
  public ValueType get(KeyType key) throws NoSuchElementException
  {
    int index = Math.abs(key.hashCode()) % getCapacity();
    while (array[index] != null)
    {
      if (array[index].getKey().equals(key))
      {
          return array[index].getValue(); // if the key is found, return the value at that key
      }
      index = (index + 1) % getCapacity();
    }
    throw new NoSuchElementException("The key is not stored in this collection."); // if we get 
    // here in the code, the key was not stored in the array, so throw an exception.
  }

  // remove the mapping for a given key from this collection
  // throws exception when key is not stored in this collection
  public ValueType remove(KeyType key) throws NoSuchElementException
  {
    int index = Math.abs(key.hashCode()) % getCapacity();
    while (array[index] != null)
    {
      if (array[index].getKey().equals(key))
        {
          ValueType returnThis = array[index].getValue();
          array[index] = sentinel; // replace with a sentinel value
          size--;
          updateLoadFactor();
          return returnThis; // if the key is found, return the value at that key
        }
      index = (index + 1) % getCapacity();
    }
    throw new NoSuchElementException("The key is not stored in this collection."); // if we get
    // here in the code, the key was not stored in the array, so throw an exception
  }

  // remove all key-value pairs from this collection
  public void clear()
  {
    for (int i = 0; i < getCapacity(); i++)
    {
      array[i] = null; // replace everything in the array with null values
      size = 0;
    }
    loadFactor = 0;
  }
  
  // retrieve the number of keys stored within this collection
  public int getSize()
  {
    return size;
  }

  // retrieve this collection's capacity (size of its underlying array)
  public int getCapacity()
  {
    return array.length;
  }
  
  // updates the load factor
  public void updateLoadFactor()
  {
    loadFactor = ((double)size)/(double)getCapacity();
  }
  
  public double retLoadFactor()
  {
    return loadFactor;
  }
  
  
}
