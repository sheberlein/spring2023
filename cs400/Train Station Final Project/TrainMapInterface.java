import java.util.List;
import java.util.NoSuchElementException;

/**
 * This interface is to be implemented by TrainMapAE, gives the ability to compute multiple 
 * different lowest cost path depending on which weight and also a recommended path
 * 
 * @author Joseph Hanson
 */
public interface TrainMapInterface<NodeType, EdgeType extends Number> {
    public TrainMapAE.SearchNode computeShortestPathCost(NodeType start, NodeType end);
    public TrainMapAE.SearchNode computeShortestPathTime(NodeType start, NodeType end);
    public TrainMapAE.SearchNode computeRecommendedPath(NodeType start, NodeType end);
    
    public List<NodeType> getShortestPathByTime(NodeType start, NodeType end) 
        throws NoSuchElementException;
    public List<NodeType> getShortestPathByCost(NodeType start, NodeType end)
        throws NoSuchElementException;
    public List<NodeType> getRecommendedPath(NodeType start, NodeType end)
        throws NoSuchElementException;

    public double[] getShortestByTime(NodeType start, NodeType end);
    public double[] getShortestByCost(NodeType start, NodeType end);
    public double[] getRecommended(NodeType start, NodeType end);
}
