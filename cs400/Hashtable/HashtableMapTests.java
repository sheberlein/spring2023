// --== CS400 Project One File Header ==--
// Name: Sidney Heberlein
// CSL Username: sidney
// Email: sheberlein@wisc.edu
// Lecture #: 001, TR 9:30am-10:45am
// Notes to Grader: none
import java.util.NoSuchElementException;

public class HashtableMapTests 
{
  /**
   * testing the 2 constructors of the HashtableMap class, as well as the getSize() and 
   * getCapacity() methods
   * @return true if this test verifies a correct functionality and false otherwise
   */
  public static boolean test1()
  {
    try
    {
      // testing the first constructor (the one with a paremeter for capacity)
      HashtableMap<Integer, String> map1 = new HashtableMap<Integer, String>(6);
      if (map1.getSize() != 0)
      {
        System.out.println("test1: your size was not properly set to 0 using the first "
            + "constructor.");
        return false;
      }
      if (map1.getCapacity() != 6)
      {
        System.out.println("test1: your capacity was not properly set to 6 using the first "
            + "constructor.");
        return false;
      }
      
      // testing the second constructor (the one without a parameter for capacity)
      HashtableMap<Integer, String> map2 = new HashtableMap<Integer, String>();
      if (map2.getSize() != 0)
      {
        System.out.println("test1: your size was not properly set to 0 using the second "
            + "constructor.");
        return false;
      }
      if (map2.getCapacity() != 8)
      {
        System.out.println("test1: your capacity was not properly set to 8 using the second "
            + "constructor.");
        return false;
      }
    }
    catch (Exception e)
    {
      System.out.println("An unexpected exception was thrown in test1.");
      return false;
    }
    return true; // return true if this test passes
  }
  
  /**
   * testing the put() method of the HashtableMap class
   * @return true if this test verifies a correct functionality and false otherwise
   */
  public static boolean test2()
  {
    try
    {
      HashtableMap<Integer, String> map1 = new HashtableMap<Integer, String>(6); // a new
      // HashtableMap with capacity 6
      // case 1: testing giving it a null key
      boolean flip = false; // this should be set to true in the catch block
      try
      {
        map1.put(null, "test");
      }
      catch (IllegalArgumentException e)
      {
        flip = true;
      }
      if (flip == false)
      {
        System.out.println("test2 case 1: your put method did not throw the correct exception when "
            + "passed a null key.");
        return false;
      }
      HashtableMap<Integer, String> map = new HashtableMap<Integer, String>(6); // a new
      // HashtableMap with capacity 6
      // case 2: testing putting a valid value into the hashtable
      map.put(1, "test"); // this should not throw an exception
      if (!map.get(1).equals("test"))
      {
        System.out.println("test2 case2: your put method did not correctly put the key-value pair "
            + "into the correct spot in the hashtable.");
        return false;
      }
      
      // case 3: testing putting a valid value into the hashtable that maps to the same key
      map.put(7, "test2"); // should be put at index 2, because something is already at index 1
      if (!map.get(7).equals("test2"))
      {
        System.out.println("test2 case3: your put method did not correctly put the key-value pair "
            + "into the correct spot in the hashtable.");
        return false;
      }
      
      // case 4: testing putting a duplicate value into the hashtable
      flip = false;
      try
      {
        map.put(1, "test3");
      }
      catch(IllegalArgumentException e)
      {
        flip = true;
      }
      if (flip == false)
      {
        System.out.println("test2 case 4: your put method did not throw the correct exception when "
            + "passed a duplicate key.");
        return false;
      }
    }
    catch (Exception e)
    {
      System.out.println("An unexpected exception was thrown in test2.");
      return false;
    }
    return true; // return true if this test passes
  }
  
  /**
   * testing the containsKey() method in the HashtableMap class
   * @return true if this test verifies a correct functionality and false otherwise
   */
  public static boolean test3()
  {
    try
    {
      HashtableMap<Integer, String> map = new HashtableMap<Integer, String>(6); // a new
      // HashtableMap with capacity 6
      map.put(1, "test");
      // case 1: testing when the value does not exist
      if (map.containsKey(4) != false)
      {
        System.out.println("test3 case 1: your containsKey method did not return false when passed "
            + "a key that was not in the hashtable.");
        return false;
      }
      
      // case 2: testing when the value does exist
      if (map.containsKey(1) != true)
      {
        System.out.println("test3 case 2: your containsKey method did not return true when passed "
            + "a key that was in the hashtable.");
        return false;
      }
    }
    catch (Exception e)
    {
      System.out.println("An unexpected exception was thrown in test3.");
      return false;
    }
    return true; // return true if this test passes
  }
  
  /**
   * testing the get() method in the HashtableMap class
   * @return true if this test verifies a correct functionality and false otherwise
   */
  public static boolean test4()
  {
    try
    {
      HashtableMap<String, String> map = new HashtableMap<String, String>(6); // a new
      // HashtableMap with capacity 6
      map.put("hello", "test");
      // case 1: test getting a value that doesn't exist in the hashtable
      boolean flip = false; // this should be set to true in the catch block
      try
      {
        map.get("hi"); // this should throw an exception
      }
      catch (NoSuchElementException e)
      {
        flip = true;
      }
      if (flip == false)
      {
        System.out.println("test4 case 1: your get() method did not throw the correct exception "
            + "when the key did not exist in the hashtable.");
        return false;
      }
      
      // case 2: test getting a value that does exist in the hashtable
      if (!map.get("hello").equals("test"))
      {
        System.out.println("test4 case 2: your get() method did notreturn the correct value when "
            + "the key existed in the hashtable.");
        return false;
      }
    }
    catch (Exception e)
    {
      System.out.println("An unexpected exception was thrown in test4.");
      return false;
    }
    return true; // return true if this test passes
  }
  
  /**
   * testing the remove() method in the HashtableMap class
   * @return true if this test verifies a correct functionality and false otherwise
   */
  public static boolean test5()
  {
    try
    {
      HashtableMap<Integer, String> map = new HashtableMap<Integer, String>(6); // a new
      // HashtableMap with capacity 6
      map.put(5, "test5");
      map.put(2, "test2");
      map.put(1, "test1");
      
      if (!map.remove(2).equals("test2"))
      {
        System.out.println("test5: your remove method did not return the correct value.");
        return false;
      }
      if (map.getSize() != 2)
      {
        System.out.println("test5: your remove method did not properly update size.");
        return false;
      }
      
      // testing removing an element that does not exist in the hashtable
      boolean flip = false; // this should be set to true in the catch block
      try
      {
        map.remove(4); // this should throw a NoSuchElementException
      }
      catch (NoSuchElementException e)
      {
        flip = true;
      }
      if (flip == false)
      {
        System.out.println("test5: your remove method did not throw the correct exception when "
            + "trying to remove a key that did not exist in the hashtable.");
        return false;
      }
    }
    catch (Exception e)
    {
      System.out.println("An unexpected exception was thrown in test5.");
      return false;
    }
    return true; // return true if this test passes
  }
  
  /**
   * main method for running the tests
   * @param args an array of strings
   */
  public static void main(String[] args) 
  {
    System.out.println("test1: " + test1());
    System.out.println("test2: " + test2());
    System.out.println("test3: " + test3());
    System.out.println("test4: " + test4());
    System.out.println("test5: " + test5());
  }

}
