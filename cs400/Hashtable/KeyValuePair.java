// --== CS400 Project One File Header ==--
// Name: Sidney Heberlein
// CSL Username: sidney
// Email: sheberlein@wisc.edu
// Lecture #: 001, TR 9:30-10:45
// Notes to Grader: none

/**
 * This class groups a key-value pair into one object so that it can be stored in the hashtable's
 * array.
 */
public class KeyValuePair<KeyValue, ValueType>
{
  // instance fields
  private KeyValue key;
  private ValueType value;
  
  // constructors
  /**
   * This constructor takes a KeyValye and a ValueType and constructs a KeyValuePair object with
   * them
   * @param key the KeyValue of this KeyValuePair
   * @param value the ValueType for this KeyValuePair
   */
  public KeyValuePair(KeyValue key, ValueType value)
  {
    this.key = key;
    this.value = value;
  }
  
  // methods
  /**
   * getter for the key field
   * @return key the KeyValue of this KeyValuePair
   */
  public KeyValue getKey()
  {
    return key;
  }
  
  /**
   * getter for the value field
   * @return value the ValueType for this KeyValuePair
   */
  public ValueType getValue()
  {
    return value;
  }
  
  /**
   * setter for the key field
   * @param key the new KeyValue
   */
  public void setKey(KeyValue key)
  {
    this.key = key;
  }
  
  /**
   * getter for the value field
   * @param value the new ValueType
   */
  public void setValue(ValueType value)
  {
    this.value = value;
  }
  
}
