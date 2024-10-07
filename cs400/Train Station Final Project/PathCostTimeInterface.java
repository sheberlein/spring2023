// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.List;

/**
 * This interface contains information about a specific path. It contains an array of NodeTypes in
 * the path, as well as the total cost and time of the path.
 */
public interface PathCostTimeInterface<NodeType> 
{
  //public PathCostTime(NodeType[] path, double cost, double time);
  public List<NodeType> getPath(); //returns the path
  public double getCost(); // returns the cost
  public double getTime(); // returns the time
}
