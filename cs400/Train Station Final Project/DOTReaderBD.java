// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A
import java.io.FileNotFoundException;

/**
 * This is a placeholder class for DOTReader, coded by the Data Wrangler.
 * @author sidneyheberlein
 *
 */
public class DOTReaderBD<NodeType, EdgeType extends Number> implements DOTReaderInterface<NodeType, EdgeType>
{
  /**
   * The constructor for this object.
   */
  public DOTReaderBD()
  {
    
  }
  
  /**
   * This method reads stations from a file.
   */
  public void readStationsFromFile(String filename, BaseGraphBD<NodeType, EdgeType> graph) 
      throws FileNotFoundException
  {
    
  }

  @Override
  public void readStationsFromFile(String filename, TrainMapAE<NodeType, EdgeType> trainMap)
      throws FileNotFoundException {
    // TODO Auto-generated method stub
    
  }
}
