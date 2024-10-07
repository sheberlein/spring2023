// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.List;

/**
 * This class implements the PathCostTimeInterface. It contains information about a specific path
 * @author Sidney Heberlein
 */
public class PathCostTimeBD<NodeType> implements PathCostTimeInterface<NodeType>
{
  // instance fields
  public List<NodeType> path; // all the nodes in this path.
  public double cost; // the total cost of this path.
  public double time; // the total time it takes to travel this path.
  
  /**
   * The constructor for PathCostTimeBD.
   * @param path   an array of NodeType containing all the nodes in this path.
   * @param cost   the total cost of this path.
   * @param time   the total time it takes to travel this path.
   */
  public PathCostTimeBD(List<NodeType> path, double cost, double time)
  {
    this.path = path;
    this.cost = cost;
    this.time = time;
  }
  
  /**
   * Returns an array of NodeType containing all the nodes in this path.
   */
  public List<NodeType> getPath()
  {
    return path;
  }
  
  /**
   * Returns the total cost of this path.
   */
  public double getCost()
  {
    return cost;
  }
  
  /**
   * Returns the total time it takes to travel this path.
   */
  public double getTime()
  {
    return time;
  }
  
}
