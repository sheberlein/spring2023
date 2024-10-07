// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 * This interface uses the Data Wrangler’s methods in order to load data from a file. It uses the 
 * Algorithm Engineer’s methods in the TrainMapInterface in order to format the data, which will 
 * be used by the Frontend Developer.
 */
public interface TrainExpediaBackendInterface<NodeType, EdgeType extends Number>
{
  //public TrainExpediaBackendInterface(TrainMapInterface trainMap, DOTReaderInterface dotreader);
  
  public void loadData(String filename) throws FileNotFoundException;
  
  public PathCostTimeInterface<NodeType> getShortestPathByTime(NodeType start, NodeType end); 
  // returns a 3-element array containing the path, cost, and time
  
  public PathCostTimeInterface<NodeType> getShortestPathByCost(NodeType start, NodeType end); 
  // returns a 3-element array containing the path, cost, and time
  
  public PathCostTimeInterface<NodeType> getShortestPathByRecommendation(NodeType start, NodeType end);
  
  public ArrayList<String> getDestinations(); // returns a 
  // List of destinations
}
