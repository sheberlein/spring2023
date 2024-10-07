import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.PriorityQueue;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class DWTests<NodeType, EdgeType extends Number> extends BaseGraphBD<NodeType, EdgeType>
{
        String fileLocation = "data/DOTFileDW.gv";
        String badFileLocation = "data/BadDOTFileDW.gv";
        String badFile2Location = "data/BadDOTFile2DW.gv";

        @Test
        public void test1()// ensures that the edges are correctly loaded into the graph under normal
                                                // circumstances
        {
                BaseGraphBD<String, RailLineDW> testGraph = new BaseGraphBD<String, RailLineDW>();
                DOTReaderDW<String, RailLineDW> reader = new DOTReaderDW<String, RailLineDW>();
                try
                {
                        reader.readStationsFromFile(fileLocation, testGraph);
                } catch (FileNotFoundException e)
                {
                        assertTrue(false);

                }
                assertTrue(testGraph.edgeCount == 12);
        }

        @Test
        public void test2()// Makes sure that duplicate edges aren't allowed
        {
                BaseGraphBD<String, RailLineDW> testGraph = new BaseGraphBD<String, RailLineDW>();
                DOTReaderDW<String, RailLineDW> reader = new DOTReaderDW<String, RailLineDW>();
                try
                {
                        reader.readStationsFromFile(fileLocation, testGraph);
                        reader.readStationsFromFile(fileLocation, testGraph);
                } catch (FileNotFoundException e)
                {
                        assertTrue(false);
                }
                assertTrue(testGraph.edgeCount == 12);
        }
        @Test
        public void test3()// makes sure that improperly formatted headers are not accepted
        {
                BaseGraphBD<String, RailLineDW> testGraph = new BaseGraphBD<String, RailLineDW>();
                DOTReaderDW<String, RailLineDW> reader = new DOTReaderDW<String, RailLineDW>();
                try
                {
                        reader.readStationsFromFile(badFileLocation, testGraph);
                        assertTrue(false);
                } catch (FileNotFoundException e)
                {
                        assertTrue(false);
                } catch (IllegalArgumentException e)
                {
                        assertTrue(true);
                }
                assertTrue(testGraph.edgeCount == 0);

        }

        @Test
        public void test4()// Ensures improperly formatted entries aren't accepted
        {
                BaseGraphBD<String, RailLineDW> testGraph = new BaseGraphBD<String, RailLineDW>();
                DOTReaderDW<String, RailLineDW> reader = new DOTReaderDW<String, RailLineDW>();
                try
                {
                        reader.readStationsFromFile(badFile2Location, testGraph);
                        assertTrue(false);
                } catch (FileNotFoundException e)
                {
                        assertTrue(false);
                } catch (Exception e)
                {
                        assertTrue(true);
                }
                assertTrue(testGraph.edgeCount == 6);
        }
        @Test
        public void test5()// Tests that the graph is properly constructed
        {
                BaseGraphBD<String, RailLineDW> testGraph = new BaseGraphBD<String, RailLineDW>();
                DOTReaderDW<String, RailLineDW> reader = new DOTReaderDW<String, RailLineDW>();
                try
                {
                        reader.readStationsFromFile(fileLocation, testGraph);
                } catch (FileNotFoundException e)
                {
                        assertTrue(false);

                }
                assertTrue(testGraph.nodes.get("LA").edgesLeaving.size() == 2);
                assertTrue(testGraph.nodes.get("Miami").edgesLeaving.size() == 1);
                assertTrue(testGraph.nodes.get("New York").edgesLeaving.size() == 2);
                assertTrue(testGraph.nodes.get("Chicago").edgesLeaving.size() == 3);
                assertTrue(testGraph.nodes.get("Austin").edgesLeaving.size() == 4);
                assertTrue(testGraph.nodes.get("LA").edgesEntering.size() == 2);
                assertTrue(testGraph.nodes.get("Miami").edgesEntering.size() == 1);
                assertTrue(testGraph.nodes.get("New York").edgesEntering.size() == 2);
                assertTrue(testGraph.nodes.get("Chicago").edgesEntering.size() == 3);
                assertTrue(testGraph.nodes.get("Austin").edgesEntering.size() == 4);
        }
}
