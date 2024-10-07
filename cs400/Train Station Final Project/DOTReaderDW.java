// CS400 Project Three File Header
// Name: Liam Halpin
// CSL Username: halpin
// Email: whalpin@wisc.edu
// Lecture Number: 001
// Notes to Grader: none
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DOTReaderDW<NodeType, EdgeType extends Number> implements DOTReaderInterface<NodeType, EdgeType>
{

        /**
         * Loads a graph with edges specified in a DOT file
         * @param filename the file which is being loaded
         * @param graph the graph we are adding the edges to
         * @throws FileNotFoundExcpetion if the file counldn't be found
         */
        public void readStationsFromFile(String filename, BaseGraphBD<NodeType, EdgeType> graph)
                        throws FileNotFoundException
        {
                Scanner dotScanner = new Scanner(new File(filename));//Contruct Scanner
                if (!dotScanner.next().trim().equals("graph") || !dotScanner.next().trim().equals("{"))
                        throw new IllegalArgumentException();//If heading is wrong
                dotScanner.nextLine();
                while (dotScanner.hasNextLine())//Loads lines until file ends or stops
                {
                        String nextLine = dotScanner.nextLine();
                        if (nextLine.trim().equals("}"))
                                break;
                        addEdge(nextLine, graph);
                }
        }
        /**
         * Loads a graph with edges specified in a DOT file
         * @param filename the file which is being loaded
         * @param graph the graph we are adding the edges to
         * @throws FileNotFoundExcpetion if the file counldn't be found
         */
        public void readStationsFromFile(String filename, TrainMapAE<NodeType, EdgeType> graph)
                throws FileNotFoundException
        {
                Scanner dotScanner = new Scanner(new File(filename));//Contruct Scanner
                if (!dotScanner.next().trim().equals("graph") || !dotScanner.next().trim().equals("{"))
                        throw new IllegalArgumentException();//If heading is wrong
                dotScanner.nextLine();
                while (dotScanner.hasNextLine())//Loads lines until file ends or stops
                {
                        String nextLine = dotScanner.nextLine();
                        if (nextLine.trim().equals("}"))
                                break;
                        addEdge(nextLine, graph);
                }
        }
        /**
         * Uses a single string to add an edge and any necessary nodes to a TrainMap
         * @param line the string being inputed
         * @param graph the graph that is being added to
         */
        private void addEdge(String line, TrainMapAE<NodeType, EdgeType> graph)
        {
                String[] edgeParts = line.trim().split("\\[");// split line into its two parts

                String[] nodeInfos = edgeParts[0].trim().split("--");// splits node info part in two
                graph.insertNode((NodeType) nodeInfos[0].trim());// add nodes to the graph
                graph.insertNode((NodeType) nodeInfos[1].trim());

                String[] edgeDetails = edgeParts[1].trim().split(" ");// splits the details into separate strings
                edgeDetails[edgeDetails.length - 1] = edgeDetails[edgeDetails.length - 1].substring(0,
                                edgeDetails[edgeDetails.length - 1].length() - 1);
                double cost = Double.POSITIVE_INFINITY;
                double time = Double.POSITIVE_INFINITY;
                for (int i = 0; i < edgeDetails.length; i++)// assign the cost and time to the one specified in the details
                {
                        String field = edgeDetails[i].split("=")[0];
                        double value = Double.parseDouble(edgeDetails[i].split("=")[1]);
                        switch (field)
                        {
                                case "cost":
                                        cost = value;
                                        break;
                                case "time":
                                        time = value;
                                        break;
                        }
                }
                RailLineInterface edge = new RailLineAE(cost, time);//Construct edge and then insert it
                graph.insertEdge((NodeType) nodeInfos[0].trim(), (NodeType) nodeInfos[1].trim(), (EdgeType) edge);
                graph.insertEdge((NodeType) nodeInfos[1].trim(), (NodeType) nodeInfos[0].trim(), (EdgeType) edge);
        }
        /**
         * Uses a single string to add an edge and any necessary nodes to a TrainMap
         * @param line the string being inputed
         * @param graph the graph that is being added to
         */
        private void addEdge(String line, BaseGraphBD<NodeType, EdgeType> graph)
        {
                String[] edgeParts = line.trim().split("\\[");// split line into its two parts

                String[] nodeInfos = edgeParts[0].trim().split("--");// splits node info part in two
                graph.insertNode((NodeType) nodeInfos[0].trim());// add nodes to the graph
                graph.insertNode((NodeType) nodeInfos[1].trim());

                String[] edgeDetails = edgeParts[1].trim().split(" ");// splits the details into separate strings
                edgeDetails[edgeDetails.length - 1] = edgeDetails[edgeDetails.length - 1].substring(0,
                                edgeDetails[edgeDetails.length - 1].length() - 1);
                double cost = Double.POSITIVE_INFINITY;
                double time = Double.POSITIVE_INFINITY;
                for (int i = 0; i < edgeDetails.length; i++)// assign the cost and time to the one specified in the details
                {
                        String field = edgeDetails[i].split("=")[0];
                        double value = Double.parseDouble(edgeDetails[i].split("=")[1]);
                        switch (field)
                        {
                                case "cost":
                                        cost = value;
                                        break;
                                case "time":
                                        time = value;
                                        break;
                        }
                }
                RailLineInterface edge = new RailLineAE(cost, time);//Construct edge and then insert it
                graph.insertEdge((NodeType) nodeInfos[0].trim(), (NodeType) nodeInfos[1].trim(), (EdgeType) edge);
                graph.insertEdge((NodeType) nodeInfos[1].trim(), (NodeType) nodeInfos[0].trim(), (EdgeType) edge);
        }
}
