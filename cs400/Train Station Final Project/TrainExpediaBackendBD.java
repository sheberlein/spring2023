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
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * This class implements the TrainExpediaBackendInterface. It uses the DataWrangler's methods in
 * order to load data from a file. It formats the data using the Algorithm Engineer's methods, which
 * will be used by the Frontend Developer
 * 
 * @author Sidney Heberlein
 */
public class TrainExpediaBackendBD<NodeType, EdgeType extends RailLineAE>
    implements TrainExpediaBackendInterface<NodeType, EdgeType> {
  DOTReaderInterface<NodeType, EdgeType> dotreader; // a DOTReader object that reads data from a
                                                    // file
  TrainMapAE<NodeType, EdgeType> trainMap; // a TrainMap object that computes shortest paths
  BaseGraphBD<NodeType, EdgeType> graph; // a BaseGraphBD object that stores a set of nodes, along
  // with a set of directed and weighted edges connecting those nodes.

  /**
   * The constructor for this class.
   * 
   * @param trainMap  a TrainMapInterface object
   * @param dotreader a DOTReaderInterface object
   */
  public TrainExpediaBackendBD(TrainMapInterface<NodeType, EdgeType> trainMap,
      DOTReaderInterface<NodeType, EdgeType> dotreader) {
    this.dotreader = new DOTReaderDW();
    this.trainMap = new TrainMapAE();
    this.graph = new BaseGraphBD(); //forgot to declare this
  }
  /**
   * Loads data from a file into a BaseGraphBD . Uses the DataWrangler's method in the DOTReader
   * class.
   * 
   * @param filename the name of a DOT file to load data from
   */
  public void loadData(String filename) throws FileNotFoundException {
    dotreader.readStationsFromFile(filename, trainMap);
  }

  /**
   * Gets the shortest path by time. Returns a PathCostTimeInterface object containing information
   * about the path, cost, and time.
   * 
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return a PathCostTimeInterface object with information about the path.
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  public PathCostTimeInterface<NodeType> getShortestPathByTime(NodeType start, NodeType end)
      throws NoSuchElementException {
 
    // this stores the last SearchNode in the sequence of SearchNodes from start to end. It will be
    // updated in the try-catch block.
    TrainMapAE<NodeType, EdgeType>.SearchNode current = null;

    try {
      current = trainMap.computeShortestPathTime(start, end);
    } catch (NoSuchElementException e) {
      // throw an exception if the computeShortestPathTime method from the AE throws an exception.
      throw new NoSuchElementException("No path was found between start and end, or start or "
          + "end data do not correspond to a graph node.");
    }

    double time = current.main;
    double cost = current.second;

    // a List of NodeType of the shortest path between start and end
    List<NodeType> shortest = trainMap.getShortestPathByTime(start, end);

    // return a new PathCostTime object with the shortest list, the cost, and the time
    return new PathCostTimeBD<NodeType>(shortest, cost, time);
  }
  /**
   * Gets the shortest path by cost. Returns a PathCostTimeInterface object containing information
   * about the path, cost, and time.
   * 
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return a PathCostTimeInterface object with information about the path.
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  public PathCostTimeInterface<NodeType> getShortestPathByCost(NodeType start, NodeType end)
      throws NoSuchElementException {

    // this stores the last SearchNode in the sequence of SearchNodes from start to end. It will be
    // updated in the try-catch block.
    TrainMapAE<NodeType, EdgeType>.SearchNode current = null;

    try {
      current = trainMap.computeShortestPathCost(start, end);
    } catch (NoSuchElementException e) {
      // throw an exception if the computeShortestPathTime method from the AE throws an exception.
      throw new NoSuchElementException("No path was found between start and end, or start or "
          + "end data do not correspond to a graph node.");
    }

    double cost = current.main;
    double time = current.second;

    // a List of NodeType of the shortest path between start and end by cost of the path
    List<NodeType> shortest = trainMap.getShortestPathByCost(start, end);

    // return a new PathCostTime object with the shortest list, the cost, and the time
    return new PathCostTimeBD<NodeType>(shortest, cost, time);
  }
  /**
   * Gets the shortest path per our recommendation. Returns a PathCostTimeInterface object
   * containing information about the path, cost, and time.
   * 
   * @param start the data item in the starting node for the path
   * @param end   the data item in the destination node for the path
   * @return a PathCostTimeInterface object with information about the path.
   * @throws NoSuchElementException when no path from start to end is found or when either start or
   *                                end data do not correspond to a graph node
   */
  public PathCostTimeInterface<NodeType> getShortestPathByRecommendation(NodeType start,
      NodeType end) throws NoSuchElementException {

    // this stores the last SearchNode in the sequence of SearchNodes from start to end. It will be
    // updated in the try-catch block.
    TrainMapAE<NodeType, EdgeType>.SearchNode current = null;

    try {
      current = trainMap.computeRecommendedPath(start, end);
    } catch (NoSuchElementException e) {
      // throw an exception if the computeShortestPathTime method from the AE throws an exception.
      throw new NoSuchElementException("No path was found between start and end, or start or "
          + "end data do not correspond to a graph node.");
    }

    double cost = current.main;
    double time = current.second;

    // a List of NodeType of the recommended path between start and end
    List<NodeType> shortest = trainMap.getRecommendedPath(start, end);

    // return a new PathCostTime object with the shortest list, the cost, and the time
    return new PathCostTimeBD<NodeType>(shortest, cost, time);
  }
  /**
   * Returns a List of destinations from the start node to any other node.
   * 
   * @return a List of destinations from the start node to any other node.
   */
  public ArrayList<String> getDestinations() {
    // this is a new ArrayList that will be returned from this method. It will hold the destination
    // nodes from the given start node.
    ArrayList<String> list = new ArrayList<String>();

    Enumeration<TrainMapAE<NodeType, EdgeType>.Node> valuesEnum = trainMap.nodes.elements();
    ArrayList<TrainMapAE<NodeType, EdgeType>.Node> nodeList = new ArrayList<>();
   
    //loop to add nodes into nodeList
    while (valuesEnum.hasMoreElements()) {
      nodeList.add(valuesEnum.nextElement());
    }
    
    // iterate through all of the edges leaving the start node. Add the successor's data to list.
    for (int i = 0; i < nodeList.size(); i++) {
      list.add(nodeList.get(i).data.toString());
    }
    return list;
  }


}
