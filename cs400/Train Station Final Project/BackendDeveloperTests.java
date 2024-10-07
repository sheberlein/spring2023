// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.List;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * This is a tester class for the Backend Developer.
 * @author Sidney Heberlein
 *
 */
public class BackendDeveloperTests<NodeType, EdgeType extends Number> extends BaseGraphBD<NodeType, EdgeType>
{
  
  /**
   * This test ensures that the getShortestPathByCost method in the TrainExpeidaBackendBD class 
   * works correctly.
   */
  @Test
  public void test1()
  {
    // a new TrainMapBD object that will be passed to the TrainExpediaBackendBD constructor
    TrainMapAE<String, RailLineAE> graph = new TrainMapAE<String, RailLineAE>();
    
    // a new DOTReaderBD object that will be passed to the TrainExpediaBackendBD constructor
    DOTReaderDW<String, RailLineAE> reader = new DOTReaderDW<String, RailLineAE>();
    
    // a new TrainExpediaBackendBD object
    TrainExpediaBackendBD<String, RailLineAE> expedia = 
        new TrainExpediaBackendBD<String, RailLineAE>(graph, reader);
    
    try
    {
      expedia.loadData("data/DOTFileDW.gv");
    }
    catch (FileNotFoundException e)
    {
      assertTrue(false);
    }
    
    // this is the actual last node computed by the computeShortestPathCost method
    PathCostTimeInterface<String> actualLastNode = expedia.getShortestPathByCost("New York", "LA");
    
    // ensure that the path of the actual last node contained all of the correct nodes. It should 
    // contain nodes New York, Austin, and LA
    assertTrue(actualLastNode.getPath().toString().contains("New York"), "Test 1: The returned path does "
        + "not contain New York.");
    assertTrue(actualLastNode.getPath().toString().contains("Austin"), "Test 1: The returned path does "
        + "not contain Austin.");
    assertTrue(actualLastNode.getPath().toString().contains("LA"), "Test 1: The returned path does "
        + "not contain LA.");
  }
  
  
  /**
   * This test ensures that the getDestinations() method in the TrainExpediaBackendBD class returns
   * the correct list of Strings.
   */
  @Test
  public void test2()
  {
    // a new TrainMapAE object that will be passed to the TrainExpediaBackendBD constructor
    TrainMapAE<String, RailLineAE> graph = new TrainMapAE<String, RailLineAE>();
    
    // a new DOTReaderDW object that will be passed to the TrainExpediaBackendBD constructor
    DOTReaderDW<String, RailLineAE> reader = new DOTReaderDW<String, RailLineAE>();
    
    // a new TrainExpediaBackendBD object
    TrainExpediaBackendBD<String, RailLineAE> expedia = 
        new TrainExpediaBackendBD<String, RailLineAE>(graph, reader);
    
    try
    {
      expedia.loadData("data/DOTFileDW.gv");
    }
    catch (FileNotFoundException e)
    {
      assertTrue(false);
    }
    
    // this is the actual list of Strings returned by the getDestinations method
    ArrayList<String> actualList = expedia.getDestinations();
    
    // assure that the List has the 3 correct nodes leaving node A. They should be H, B, and M.
    assertTrue(actualList.toString().contains("Austin"), "The list does not contain H.");
    assertTrue(actualList.toString().contains("Chicago"), "The list does not contain B.");
    assertTrue(actualList.toString().contains("LA"), "The list does not contain M.");  
  }
  
  
  /**
   * This test tests the correctness of the getPath(), getCost(), and getTime() methods in the
   * PathCostTime class. This also ensures the correctness of the constructor of the class.
   */
  @Test
  public void test3()
  {
    // this is a new ArrayList that represents the path of the train.
    List<String> path = new ArrayList<String>();
    path.add("Toronto");
    path.add("Ontario");
    path.add("Nova Scotia");
    
    // this is a new PathCostTime object with the above path, a cost of 5.0, and a time of 100.
    PathCostTimeInterface<String> pct = new PathCostTimeBD<String>(path, 5.0, 100);
    
    // assure that the getPath() method returns the correct list.
    assertEquals(path, pct.getPath(), "Test 3: the incorrect path was returned.");
    
    // assure that the getCost() method returns the correct cost.
    assertEquals(5.0, pct.getCost(), "Test 3: the incorrect cost was returned.");
    
    // assure that the getTime() method returns the correct time.
    assertEquals(100, pct.getTime(), "Test 3: the incorrect time was returned.");
  }
  
  
  /**
   * This test assures that the getShortestPathByRecommendation method in the TrainExpediaBackendBD 
   * class throws a NoSuchElementException when no path is found between the nodes. That is, when 
   * the Algorithm Engineer's method computeRecommendedPath in the TrainMapBD class throws a 
   * NoSuchElementException.
   */
  @Test
  public void test4()
  {
    // a new TrainMapBD object that will be passed to the TrainExpediaBackendBD constructor
    TrainMapBD<String, RailLineAE> graph = new TrainMapBD<String, RailLineAE>();
    
    // a new DOTReaderBD object that will be passed to the TrainExpediaBackendBD constructor
    DOTReaderBD<String, RailLineAE> reader = new DOTReaderBD<String, RailLineAE>();
    
    // a new TrainExpediaBackendBD object
    TrainExpediaBackendBD<String, RailLineAE> expedia = 
        new TrainExpediaBackendBD<String, RailLineAE>(graph, reader);
    
    // assert that this method throws the correct exception.
    assertThrows(NoSuchElementException.class, 
        () -> expedia.getShortestPathByRecommendation("A", "E"), "Test 4: a NoSuchElementException "
            + "was not thrown.");
  }
  
  
  /**
   * This test ensures that the getShortestPathBytime method in the TrainExpeidaBackendBD class 
   * works correctly.
   */
  @Test
  public void test5()
  {
    // a new TrainMapBD object that will be passed to the TrainExpediaBackendBD constructor
    TrainMapAE<String, RailLineAE> graph = new TrainMapAE<String, RailLineAE>();
    
    // a new DOTReaderBD object that will be passed to the TrainExpediaBackendBD constructor
    DOTReaderDW<String, RailLineAE> reader = new DOTReaderDW<String, RailLineAE>();
    
    // a new TrainExpediaBackendBD object
    TrainExpediaBackendBD<String, RailLineAE> expedia = 
        new TrainExpediaBackendBD<String, RailLineAE>(graph, reader);
    
    try
    {
      expedia.loadData("data/DOTFileDW.gv");
    }
    catch (FileNotFoundException e)
    {
      assertTrue(false);
    }
    
    // this is the actual last node computed by the computeShortestPathCost method
    PathCostTimeInterface<String> actualLastNode = expedia.getShortestPathByTime("New York", "LA");
    
    // ensure that the path of the actual last node contained all of the correct nodes. It should 
    // contain nodes New York, Austin, and LA
    assertTrue(actualLastNode.getPath().toString().contains("New York"), "Test 5: The returned path does "
        + "not contain New York.");
    assertTrue(actualLastNode.getPath().toString().contains("Austin"), "Test 5: The returned path does "
        + "not contain Austin.");
    assertTrue(actualLastNode.getPath().toString().contains("LA"), "Test 5: The returned path does "
        + "not contain LA.");
  }
  
  
  /**
   * This test ensures that data is correctly loaded from a correctly formatted file.
   */
  @Test
  public void CodeReviewOfDataWranglerTest1()
  {
    // this is the correctly formatted file.
    String file = "data/DOTFileDW.gv";
    
    BaseGraphBD<String, RailLineDW> testGraph = new BaseGraphBD<String, RailLineDW>();
    DOTReaderDW<String, RailLineDW> reader = new DOTReaderDW<String, RailLineDW>();
    try
    {
            reader.readStationsFromFile(file, testGraph);
    } 
    catch (FileNotFoundException e)
    {
      // An exception should not have been thrown.
      assertTrue(false);
    }
    assertTrue(testGraph.edgeCount == 12);
    
  }
  
  
  /**
   * This test ensures that when data is loaded from a correctly formatted file, the graph is
   * correctly made.
   */
  @Test
  public void CodeReviewOfDataWranglerTest2()
  {
    // this is the correctly formatted file.
    String file = "data/DOTFileDW.gv";
    
    TrainMapAE<String, RailLineAE> testGraph = new TrainMapAE<String, RailLineAE>();
    DOTReaderDW<String, RailLineAE> reader = new DOTReaderDW<String, RailLineAE>();
    try
    {
            reader.readStationsFromFile(file, testGraph);
    } 
    catch (FileNotFoundException e)
    {
      // An exception should not have been thrown.
      assertTrue(false);
    }
    
    // the path leading out of New York to Chicago should have a cost of 60 and a time of 8
    assertEquals(testGraph.nodes.get("New York").edgesLeaving.get(0).data.getCost(), 60);
    assertEquals(testGraph.nodes.get("New York").edgesLeaving.get(0).data.getTime(), 8);
    
    // the path leading out of Austin to New York should have a cost of 80 and a time of 10
    assertEquals(testGraph.nodes.get("Austin").edgesLeaving.get(0).data.getCost(), 80);
    assertEquals(testGraph.nodes.get("Austin").edgesLeaving.get(0).data.getTime(), 10);
    
    // the path leading out of Miami to Austin should have a cost of 40 and a time of 5
    assertEquals(testGraph.nodes.get("Miami").edgesLeaving.get(0).data.getCost(), 40);
    assertEquals(testGraph.nodes.get("Miami").edgesLeaving.get(0).data.getTime(), 5); 
  }
  
  
  /**
   * Integration test 1. This test ensures that the getShortestPathByTime method in the 
   * TrainExpediaBackendBD class works, this time using the Algorithm Engineer's TrainMapAE class, 
   * and loading in data from a file using the DataWrangler's file and code.
   */
  @Test
  public void Integration1()
  {
    // a new TrainMapBD object that will be passed to the TrainExpediaBackendBD constructor
    TrainMapAE<String, RailLineAE> graph = new TrainMapAE<String, RailLineAE>();
    
    // a new DOTReaderBD object that will be passed to the TrainExpediaBackendBD constructor
    DOTReaderDW<String, RailLineAE> reader = new DOTReaderDW<String, RailLineAE>();
    
    // a new TrainExpediaBackendBD object
    TrainExpediaBackendBD<String, RailLineAE> expedia = 
        new TrainExpediaBackendBD<String, RailLineAE>(graph, reader);
    
    try
    {
      expedia.loadData("data/DOTFileDW.gv");
    }
    catch (FileNotFoundException e)
    {
      assertTrue(false);
    }
    
    // this is the actual last node computed by the computeShortestPathCost method
    PathCostTimeInterface<String> actualLastNode = expedia.getShortestPathByTime("New York", "LA");
    
    // ensure that the path of the actual last node contained all of the correct nodes. It should 
    // contain nodes New York, Austin, and LA
    assertTrue(actualLastNode.getPath().toString().contains("New York"), "Test 5: The returned path does "
        + "not contain New York.");
    assertTrue(actualLastNode.getPath().toString().contains("Austin"), "Test 5: The returned path does "
        + "not contain Austin.");
    assertTrue(actualLastNode.getPath().toString().contains("LA"), "Test 5: The returned path does "
        + "not contain LA.");
  }
  
  
  /**
   * Integration test 2. This test assures that the getShortestPathByRecommendation method in the 
   * TrainExpediaBackendBD class throws a NoSuchElementException when no path is found between the 
   * nodes. That is, when the Algorithm Engineer's method computeRecommendedPath in the TrainMapBD 
   * class throws a NoSuchElementException.
   */
  @Test
  public void Integration2()
  {
    // a new TrainMapAE object that will be passed to the TrainExpediaBackendBD constructor
    TrainMapAE<String, RailLineAE> graph = new TrainMapAE<String, RailLineAE>();
    
    // a new DOTReaderBD object that will be passed to the TrainExpediaBackendBD constructor
    DOTReaderDW<String, RailLineAE> reader = new DOTReaderDW<String, RailLineAE>();
    
    // a new TrainExpediaBackendBD object
    TrainExpediaBackendBD<String, RailLineAE> expedia = 
        new TrainExpediaBackendBD<String, RailLineAE>(graph, reader);
    
    // assert that this method throws the correct exception.
    assertThrows(NoSuchElementException.class, 
        () -> expedia.getShortestPathByRecommendation("A", "E"), "Test 4: a NoSuchElementException "
            + "was not thrown.");
  }
}
