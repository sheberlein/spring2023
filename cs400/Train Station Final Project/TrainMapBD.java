// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * This is a placeholder class for the TrainMap class coded by the Algorithm Engineer.
 */
public class TrainMapBD <NodeType, EdgeType extends Number> extends BaseGraphBD<NodeType,EdgeType> 
implements GraphADTBD<NodeType, EdgeType>, TrainMapInterface<NodeType, EdgeType>
{
  
  /**
   * While searching for the shortest path between two nodes, a SearchNode
   * contains data about one specific path between the start node and another
   * node in the graph.  The final node in this path is stored in it's node
   * field.  The total cost of this path is stored in its cost field.  And the
   * predecessor SearchNode within this path is referenced by the predecessor
   * field (this field is null within the SearchNode containing the starting
   * node in it's node field).
   *
   * SearchNodes are Comparable and are sorted by cost so that the lowest cost
   * SearchNode has the highest priority within a java.util.PriorityQueue.
   * 
   *
   */
  protected class SearchNode implements Comparable<SearchNode> 
  {
    public Node node;
    public double main;
    public double second;
    public SearchNode predecessor;
    public SearchNode(Node node, double main, double second, SearchNode predecessor) 
    {
        this.node = node;
        this.main = main;
        this.second = second;
        this.predecessor = predecessor;
    }
    public int compareTo(SearchNode other) 
    {
        if( main > other.main ) return +1;
        if( main < other.main ) return -1;
        return 0;
    }
  }
  
  /**
   * This method computes the shortest path by cost between two nodes. It returns the final 
   * SearchNode in the path.
   */
  public TrainMapAE<NodeType, EdgeType>.SearchNode computeShortestPathCost(NodeType start, NodeType end) 
      throws NoSuchElementException
  {
    TrainMapAE<String, RailLineBD> graph = new TrainMapAE<String, RailLineBD>();
    BaseGraph<String, RailLineBD>.Node lastNode = graph.new Node("E");
    BaseGraph<String, RailLineBD>.Node thirdNode = graph.new Node("M");
    BaseGraph<String, RailLineBD>.Node secondNode = graph.new Node("B");
    BaseGraph<String, RailLineBD>.Node firstNode = graph.new Node("A");
    TrainMapAE<String, RailLineBD>.SearchNode firstSearchNode = graph.new SearchNode(firstNode, 0, 0, null); // this
    // SearchNode represents the starting node of the shortest path algorithm.
    TrainMapAE<String, RailLineBD>.SearchNode secondSearchNode = graph.new SearchNode(secondNode, 1, 5, firstSearchNode);
    TrainMapAE<String, RailLineBD>.SearchNode thirdSearchNode = graph.new SearchNode(thirdNode, 4, 10, secondSearchNode);
    TrainMapAE<String, RailLineBD>.SearchNode lastSearchNode = graph.new SearchNode(lastNode, 7, 15, thirdSearchNode);
    // this SearchNode represents the last node searched in the shortest path algorithm.
    return (TrainMapAE<NodeType, EdgeType>.SearchNode)lastSearchNode;
  }
  
  /**
   * This method computes the shortest path by time between two nodes. It returns the final 
   * SearchNode in the path.
   */
  public TrainMapAE<NodeType, EdgeType>.SearchNode computeShortestPathTime(NodeType start, NodeType end) 
      throws NoSuchElementException
  {
    TrainMapAE<String, RailLineBD> graph = new TrainMapAE<String, RailLineBD>();
    BaseGraph<String, RailLineBD>.Node lastNode = graph.new Node("E");
    BaseGraph<String, RailLineBD>.Node thirdNode = graph.new Node("M");
    BaseGraph<String, RailLineBD>.Node secondNode = graph.new Node("B");
    BaseGraph<String, RailLineBD>.Node firstNode = graph.new Node("A");
    TrainMapAE<String, RailLineBD>.SearchNode firstSearchNode = graph.new SearchNode(firstNode, 0, 0, null); // this
    // SearchNode represents the starting node of the shortest path algorithm.
    TrainMapAE<String, RailLineBD>.SearchNode secondSearchNode = graph.new SearchNode(secondNode, 1, 5, firstSearchNode);
    TrainMapAE<String, RailLineBD>.SearchNode thirdSearchNode = graph.new SearchNode(thirdNode, 4, 10, secondSearchNode);
    TrainMapAE<String, RailLineBD>.SearchNode lastSearchNode = graph.new SearchNode(lastNode, 7, 15, thirdSearchNode);
    // this SearchNode represents the last node searched in the shortest path algorithm.
    return (TrainMapAE<NodeType, EdgeType>.SearchNode)lastSearchNode;
  }
  
  /**
   * This method computes the recommended path between two nodes.
   * @param start   the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return   SearchNode for the final end node within the shortest path
   * @throws   NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  public TrainMapAE<NodeType, EdgeType>.SearchNode computeRecommendedPath(NodeType start, NodeType end) 
      throws NoSuchElementException
  {
    // throw an exception for testing purposes of the Backend code.
    throw new NoSuchElementException();
  }
  
  /**
   * This method computes the shortest path by time between two nodes. It returns a List of Nodes.
   * @param start    the data item in the starting node for the path
   * @param end    the data item in the destination node for the path
   * @return    list of data item from node along this shortest path 
   * @throws   NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  public List<NodeType> getShortestPathByTime(NodeType start, NodeType end) throws NoSuchElementException
  {
    // this list is used for testing purposes for the Backend developer.
    List<String> list = new ArrayList<String>();
    list.add("E");
    list.add("M");
    list.add("B");
    list.add("A");
    
    // return the hard-coded list.
    return (List<NodeType>)list;
  }
  
  /**
   * This method computes the shortest path by cost between two nodes. It returns a List of Nodes.
   * @param start    the data item in the starting node for the path
   * @param end    the data item in the destination node for the path
   * @return   list of data item from node along this shortest path 
   * @throws   NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  public List<NodeType> getShortestPathByCost(NodeType start, NodeType end) throws NoSuchElementException
  {
    // this list is used for testing purposes for the Backend developer.
    List<String> list = new ArrayList<String>();
    list.add("E");
    list.add("M");
    list.add("B");
    list.add("A");
    
    // return the hard-coded list.
    return (List<NodeType>)list;
  }

  /**
   * Gets the total cost and time of the shortest path by time. Returns an array with 2 elements. 
   * The first is the total cost of the path, and the second is the total time it takes to travel 
   * that path.
   * @param start   the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return   an array with 2 elements. The first is the total cost of the path, and the second is
   * the total time it takes to travel that path.
   */
  public double[] getShortestByTime(NodeType start, NodeType end) throws NoSuchElementException
  {
    return null;
  }

  /**
   * Gets the total cost and time of the shortest path by cost. Returns an array with 2 elements. 
   * The first is the total cost of the path, and the second is the total time it takes to travel 
   * that path.
   * @param start   the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return   an array with 2 elements. The first is the total cost of the path, and the second is
   * the total time it takes to travel that path.
   */
  public double[] getShortestByCost(NodeType start, NodeType end) throws NoSuchElementException
  {
    return null;
  }

  /**
   * Returns a path that finds a balance between time and cost by multiplying the two by a 
   * multiplier and adding them together.
   * @param start   the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return    a path that finds a balance between time and cost by multiplying the two by a 
   * multiplier and adding them together.
   */
  public List<NodeType> getRecommendedPath(NodeType start, NodeType end) throws NoSuchElementException
  {
    return null;
  }
  
  /**
   * Gets the total cost and time of the shortest path by recommended. Returns an array with 2  
   * elements. The first is the total cost of the path, and the second is the total time it takes 
   * to travel that path.
   * @param start   the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return   an array with 2 elements. The first is the total cost of the path, and the second is
   * the total time it takes to travel that path.
   */
  public double[] getRecommended(NodeType start, NodeType end) throws NoSuchElementException
  {
    return null;
  }
  
  // these methods are added to fix compiler errors.
  
  @Override
  public List<NodeType> shortestPathData(NodeType start, NodeType end) 
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double shortestPathCost(NodeType start, NodeType end) {
    // TODO Auto-generated method stub
    return 0;
  }
}