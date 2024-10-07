// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A
import java.io.FileNotFoundException;

/**
 * This interface reads information from a DOT file.
 *
 */
public interface DOTReaderInterface<NodeType, EdgeType extends Number>
{
  //public DOTReader();
  public void readStationsFromFile(String filename, BaseGraphBD<NodeType, EdgeType> graph) 
      throws FileNotFoundException;

  public void readStationsFromFile(String filename, TrainMapAE<NodeType, EdgeType> trainMap) throws FileNotFoundException;

}


