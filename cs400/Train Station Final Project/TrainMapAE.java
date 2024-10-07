import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * This class resembles a graph of train stations and determines different cheapest paths depending 
 * on cost or time and also a recommended path
 * @author Joseph Hanson
 */
public class TrainMapAE <NodeType, EdgeType extends Number> 
extends BaseGraph<NodeType,EdgeType> 
  implements GraphADT<NodeType, EdgeType>, TrainMapInterface<NodeType, EdgeType>{
   
  /**
   * While searching for the shortest path between two nodes, a SearchNode
   * contains data about one specific path between the start node and another
   * node in the graph.  The final node in this path is stored in it's node
   * field.  The total costs of this path is stored in its main and second field, depending on 
   * which weight it is comparing.  And the predecessor SearchNode within this path is referenced 
   * by the predecessor field (this field is null within the SearchNode containing the starting
   * node in it's node field).
   *
   * SearchNodes are Comparable and are sorted by main so that the lowest cost or time
   * SearchNode has the highest priority within a java.util.PriorityQueue.
   */
  protected class SearchNode implements Comparable<SearchNode> {
    public Node node;
    public double main;
    public double second;
    public SearchNode predecessor;
    public SearchNode(Node node, double main, double second, SearchNode predecessor) {
        this.node = node;
        this.main = main;
        this.second = second;
        this.predecessor = predecessor;
    }
    public int compareTo(SearchNode other) {
        if( main > other.main ) return +1;
        if( main < other.main ) return -1;
        return 0;
    }
  }
  /**
   * This helper method creates a network of SearchNodes while computing the
   * shortest cost path between the provided start and end stations.  The
   * SearchNode that is returned by this method is represents the end of the
   * lowest cost path that is found: it's cost is the cost of that shortest path,
   * and the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the station data in the starting node for the path
   * @param end the station data  in the destination node for the path
   * @return SearchNode for the final end station within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  @Override
  public SearchNode computeShortestPathCost(NodeType start, NodeType end) 
      throws NoSuchElementException{
      //Check if nodes exist in graph
      if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
        throw new NoSuchElementException("Start or end node does not exist in graph");
      }
      //hashtable to keep track of visited nodes
      Hashtable<NodeType, SearchNode> visited = new Hashtable<>();
      //Queue to output lowest cost node
      PriorityQueue<SearchNode> queue = new PriorityQueue<>();
      //Starting node with 0 cost and time
      Node node = nodes.get(start);
      SearchNode search = new SearchNode(node, 0.0, 0.0, null);
      queue.add(search);
      while (!queue.isEmpty()) {
        //Remove next node from Priority Queue
        SearchNode removed = queue.remove();
        //If visited already has the removed node, we already has the shortest path to that node 
        //and Add edges to the queue
        if (!visited.containsKey(removed.node.data)) {
          visited.put(removed.node.data, removed);
          //Iterate through edges of removed node and add them to the queue
          for(int i = 0; i < removed.node.edgesLeaving.size(); i++) {
            Node nodeToAdd = removed.node.edgesLeaving.get(i).successor;
            //If visited already has the node, no need to add to queue
            if (!visited.containsKey(nodeToAdd.data)) {
              //calculate to new weights, main is cost and second is time
              double main = removed.main + ((RailLineAE)removed.node.edgesLeaving.get(i).data).getCost();
              double second = removed.second + ((RailLineAE)removed.node.edgesLeaving.get(i).data).getTime();
              SearchNode toAdd = new SearchNode(nodeToAdd, main, second, removed);
              queue.add(toAdd);
            }
          }
        }
      }
      //Check if it found a path and return otherwise exception
      if (!visited.containsKey(end)) {
        throw new NoSuchElementException("No path found");
      } else {
        SearchNode toReturn = visited.remove(end);
        return toReturn;
      }
  }
  
  /**
   * This helper method creates a network of SearchNodes while computing the
   * shortest path between the provided start and end stations.  The
   * SearchNode that is returned by this method is represents the end of the
   * lowest time path that is found: it's cost is the total time of all of 
   * the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the station data in the starting node for the path
   * @param end the station data  in the destination node for the path
   * @return SearchNode for the final end station within the shortest path
   * @throws NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  @Override
  public SearchNode computeShortestPathTime(NodeType start, NodeType end) 
      throws NoSuchElementException{
      //Check if nodes exist in graph
      if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
        throw new NoSuchElementException("Start or end node does not exist in graph");
      }
      //hashtable to keep track of visited nodes
      Hashtable<NodeType, SearchNode> visited = new Hashtable<>();
      //Queue to output lowest cost node
      PriorityQueue<SearchNode> queue = new PriorityQueue<>( );
      //Starting node with 0 cost
      Node node = nodes.get(start);
      SearchNode search = new SearchNode(node, 0.0, 0.0, null);
      queue.add(search);
      while (!queue.isEmpty()) {
        //Remove next node from Priority Queue
        SearchNode removed = queue.remove();
        //If visited already has the removed node, we already has the shortest path to that node 
        //and Add edges to the queue
        if (!visited.containsKey(removed.node.data)) {
          visited.put(removed.node.data, removed);
          //Iterate through edges of removed node and add them to the queue
          for(int i = 0; i < removed.node.edgesLeaving.size(); i++) {
            Node nodeToAdd = removed.node.edgesLeaving.get(i).successor;
            //If visited already has the node, no need to add to queue
            if (!visited.containsKey(nodeToAdd.data)) {
            //calculate to new weights, main is time and second is cost
              double main = removed.main + ((RailLineAE) removed.node.edgesLeaving.get(i).data).getTime();
              double second = removed.second + ((RailLineAE)removed.node.edgesLeaving.get(i).data).getCost();
              SearchNode toAdd = new SearchNode(nodeToAdd, main, second, removed);
              queue.add(toAdd);
            }
          }
        }
      }
      //Check if it found a path and return otherwise exception
      if (!visited.containsKey(end)) {
        throw new NoSuchElementException("No path found");
      } else {
        SearchNode toReturn = visited.remove(end);
        return toReturn;
      }
  }
  /**
   * This helper method creates a network of SearchNodes while computing a recommended path between 
   * the provided start and end stations by using multipliers on cost and time to value one more 
   * than the other. The SearchNode that is returned by this method is represents the end of the
   * recommended path that is found: it's cost is the total time of all of 
   * the nodes linked together through predecessor references represent
   * all of the nodes along that shortest path (ordered from end to start).
   *
   * @param start the station data in the starting node for the path
   * @param end the station data  in the destination node for the path
   * @return SearchNode for the final end station within the recommended path
   * @throws NoSuchElementException when no path from start to end is found
   *         or when either start or end data do not correspond to a graph node
   */
  @Override
  public SearchNode computeRecommendedPath(NodeType start, NodeType end) 
      throws NoSuchElementException{
      //Check if nodes exist in graph
      if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
        throw new NoSuchElementException("Start or end node does not exist in graph");
      }
      //hashtable to keep track of visited nodes
      Hashtable<NodeType, SearchNode> visited = new Hashtable<>();
      //Queue to output lowest cost node with custom comparator to calculate a recommended path
      PriorityQueue<SearchNode> queue = new PriorityQueue<>( (x,y) -> {
        //multiplies cost and time by a multiplier, adds them, then divides by two
          double timeX = x.main;
          double costX = x.second;
          double valueX = ((timeX*10)+(costX*.5))/2;
          double timeY = y.main;
          double costY = y.second;
          double valueY = ((timeY*10)+(costY*.5))/2;
          return (int)(valueX - valueY);
        }
      );
      //Starting node with 0 cost
      Node node = nodes.get(start);
      SearchNode search = new SearchNode(node, 0.0, 0.0, null);
      queue.add(search);
      while (!queue.isEmpty()) {
        //Remove next node from Priority Queue
        SearchNode removed = queue.remove();
        //If visited already has the removed node, we already has the shortest path to that node 
        //and Add edges to the queue
        if (!visited.containsKey(removed.node.data)) {
          visited.put(removed.node.data, removed);
          //Iterate through edges of removed node and add them to the queue
          for(int i = 0; i < removed.node.edgesLeaving.size(); i++) {
            Node nodeToAdd = removed.node.edgesLeaving.get(i).successor;
            //If visited already has the node, no need to add to queue
            if (!visited.containsKey(nodeToAdd.data)) {
            //calculate to new weights, main is time and second is cost
              double main = removed.main + ((RailLineAE)removed.node.edgesLeaving.get(i).data).getTime();
              double second = removed.second + ((RailLineAE)removed.node.edgesLeaving.get(i).data).getCost();
              SearchNode toAdd = new SearchNode(nodeToAdd, main, second, removed);
              queue.add(toAdd);
            }
          }
        }
      }
      //Check if it found a path and return otherwise exception
      if (!visited.containsKey(end)) {
        throw new NoSuchElementException("No path found");
      } else {
        SearchNode toReturn = visited.remove(end);
        return toReturn;
      }
  }
  /**
   * Returns the list of station values from nodes along the shortest cost path
   * from the node with the provided start value through the node with the
   * provided end value.  This list of data values starts with the start
   * value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shortest path.  This
   * method uses Dijkstra's shortest path algorithm to find this solution.
   *
   * @param start the station data in the starting node for the path
   * @param end the station data in the destination node for the path
   * @return list of stations from node along this shortest path
   */
  @Override
  public List<NodeType> getShortestPathByTime(NodeType start, NodeType end){
    SearchNode endSearch = computeShortestPathTime(start, end);
    LinkedList<NodeType> list = new LinkedList<>();
    while(endSearch != null) {
      list.add(0, endSearch.node.data);
      endSearch = endSearch.predecessor;
    }
    return list;
  }
  /**
   * Returns the list of station values from nodes along the shortest time path
   * from the node with the provided start value through the node with the
   * provided end value.  This list of data values starts with the start
   * value, ends with the end value, and contains intermediary values in the
   * order they are encountered while traversing this shortest path.  This
   * method uses Dijkstra's shortest path algorithm to find this solution.
   *
   * @param start the station data in the starting node for the path
   * @param end the station data in the destination node for the path
   * @return list of stations from node along this shortest path
   */
 @Override
  public List<NodeType> getShortestPathByCost(NodeType start, NodeType end){
    SearchNode endSearch = computeShortestPathCost(start, end);
    LinkedList<NodeType> list = new LinkedList<>();
    while(endSearch != null) {
      list.add(0, endSearch.node.data);
      endSearch = endSearch.predecessor;
    }
    return list;
  }
 /**
  * Returns the time cost of the path (sum over edge weights) of the shortest
  * path from the starting train station to the end station.  
  * This method uses Dijkstra's shortest path algorithm to find this solution.
  *
  * @param start the station data in the starting node for the path
  * @param end the station data in the destination node for the path
  * @return an array with the first index being the time and second index being the cost 
  *         of the shortest path between these nodes
  */
 @Override
 public double[] getShortestByTime(NodeType start, NodeType end){
   double[] toReturn = new double[2]; 
   double cost = computeShortestPathTime(start, end).second;
   double time = computeShortestPathTime(start, end).main;
   toReturn[0] = time;
   toReturn[1] = cost;
   return toReturn;
 }

 /**
  * Returns the cost of the path (sum over edge weights) of the shortest
  * path from the starting train station to the end station.  
  * This method uses Dijkstra's shortest path algorithm to find this solution.
  *
  * @param start the station data in the starting node for the path
  * @param end the station data in the destination node for the path
  * @return an array with the first index being the cost and second index being the time 
  *         of the shortest path between these nodes
  */
 @Override
 public double[] getShortestByCost(NodeType start, NodeType end){
   double[] toReturn = new double[2]; 
   double cost = computeShortestPathCost(start, end).main;
   double time = computeShortestPathCost(start, end).second;
   toReturn[1] = time;
   toReturn[0] = cost;
   return toReturn;
 }
 /**
  * Returns the list of station values from nodes along a recommended path
  * from the node with the provided start value through the node with the
  * provided end value.  This list of data values starts with the start
  * value, ends with the end value, and contains intermediary values in the
  * order they are encountered while traversing this recommended path.  This
  * method uses Dijkstra's shortest path algorithm to find this solution with a custom comparator.
  *
  * @param start the station data in the starting node for the path
  * @param end the station data in the destination node for the path
  * @return list of stations from node along this recommended path
  */
 @Override
 public List<NodeType> getRecommendedPath(NodeType start, NodeType end){
   SearchNode endSearch = computeRecommendedPath(start, end);
   LinkedList<NodeType> list = new LinkedList<>();
   while(endSearch != null) {
     list.add(0, endSearch.node.data);
     endSearch = endSearch.predecessor;
   }
   return list;
 }
 
 /**
  * Returns the costs of the path (sum over edge weights) of the recommended
  * path from the starting train station to the end station.  
  * This method uses Dijkstra's shortest path algorithm to find this solution.
  *
  * @param start the station data in the starting node for the path
  * @param end the station data in the destination node for the path
  * @return an array with the first index being the time and second index being the cost 
  *         of the recommended path between these nodes
  */
 @Override
 public double[] getRecommended(NodeType start, NodeType end) {
   double [] toReturn = new double[2];
   double time = computeRecommendedPath(start, end).main;
   double cost = computeRecommendedPath(start, end).second;
   toReturn[1] = cost;
   toReturn[0] = time;
   return toReturn;
 }
 //To fix errors with compiling
 @Override
 public List<NodeType> shortestPathData(NodeType start, NodeType end) {
   // TODO Auto-generated method stub
   return null;
 }

 @Override
 public double shortestPathCost(NodeType start, NodeType end) {
   // TODO Auto-generated method stub
   return 0;
 }
}
