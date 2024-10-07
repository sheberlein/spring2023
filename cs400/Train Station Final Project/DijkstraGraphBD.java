// --== CS400 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Group and Team: AK red
// Group TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraphBD<NodeType, EdgeType extends Number> extends BaseGraphBD<NodeType,EdgeType>
    implements GraphADTBD<NodeType, EdgeType> 
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
     */
    protected class SearchNode implements Comparable<SearchNode> 
    {
        public Node node; // the final node in this path
        public double cost;
        public SearchNode predecessor; // the predecessor SearchNode within this path
        public SearchNode(Node node, double cost, SearchNode predecessor) 
        {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) 
        {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) 
        throws NoSuchElementException
    {
      // we need to check if start and end data both correspond to a graph node. If they don't,
      // throw a NoSuchElementException
      if (!containsNode(start) || !containsNode(end))
        throw new NoSuchElementException("Start or end data do not correspond to a graph node.");
      
      // create a hashtable to keep track of the already visited nodes.
      Hashtable<NodeType, SearchNode> visitedNodes = new Hashtable<NodeType, SearchNode>();
      
      // create a priority queue to keep track of the possible nodes to be visited next.
      PriorityQueue<SearchNode> queue = new PriorityQueue<SearchNode>(); 
      
      // make a starting node with no predecessor, and a total path cost of 0.
      SearchNode startingNode = new SearchNode(nodes.get(start), 0, null);
      
      // add this new starting node to the priority queue.
      queue.add(startingNode);
      
      // while the queue is not empty, we need to keep searching for more nodes to visit.
      while(!queue.isEmpty())
      {
        // we need to remove and store the first element in the priority queue.
        SearchNode current = queue.remove();
        
        // check if we have reached the end node. If we have, return the current search node.
        if (current.node.data.equals(end))
        {
          return current;
        }
        
        // if we have not reached the end node, continue here
        
        // we need to put the current node in the hashtable, since we have now visited it.
        visitedNodes.put(current.node.data, current);
        
        // extend the path further. For each edge leaving the current node...
        for (int i = 0; i < current.node.edgesLeaving.size(); i++)
        {
          // we need to get the data of the successor node
          NodeType succ = current.node.edgesLeaving.get(i).successor.data;
          
          // if this successor node was not already visited, we need to process it
          if (!visitedNodes.containsKey(succ))
          {
            // the new cost is the current cost plus the cost of the edge to the next node
            double newCost = current.cost + current.node.edgesLeaving.get(i).data.doubleValue();
            
            // this SearchNode will be added to the priority queue.
            SearchNode temp = new SearchNode(nodes.get(succ), newCost, current);
            
            // add the temporary node to the priority queue.
            queue.add(temp);
          }
        }
        
      }
      
      // if we get to this point, no path from start to end is found. So, throw an exception.
      throw new NoSuchElementException("No path from start to end was found.");

    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     * @throws NoSuchElementException when no path from start to end is found or when either start
     *         or end data do not correspond to a graph node
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) 
        throws NoSuchElementException
    {
      // we need to check if start and end data both correspond to a graph node. If they don't,
      // throw a NoSuchElementException
      if (!containsNode(start) || !containsNode(end))
        throw new NoSuchElementException("Start or end data do not correspond to a graph node.");
      
      // a new list to store the node types
      List<NodeType> list = new LinkedList<NodeType>();
      
      // this stores the last SearchNode in the sequence of SearchNodes from start to end
      SearchNode current = computeShortestPath(start, end);
      
      // while the current SearchNode is not null, we need to keep adding elements to the list.
      while (current != null)
      {
        // add the SearchNode's node's data to the beginning of the linked list
        list.add(0, current.node.data);
        
        // update the current node to be the predecessor.
        current = current.predecessor;
      }
        return list;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path from the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     * @throws NoSuchElementException when no path from start to end is found or when either start
     *         or end data do not correspond to a graph node
     */
    public double shortestPathCost(NodeType start, NodeType end) throws NoSuchElementException
    {
      // we need to check if start and end data both correspond to a graph node. If they don't,
      // throw a NoSuchElementException
      if (!containsNode(start) || !containsNode(end))
        throw new NoSuchElementException("Start or end data do not correspond to a graph node.");
      
      // this stores the last SearchNode in the sequence of SearchNodes from start to end
      SearchNode current = computeShortestPath(start, end);
      
      // return the cost of the current SearchNode. This will store the cost of the entire path.
      return current.cost;
    }
}