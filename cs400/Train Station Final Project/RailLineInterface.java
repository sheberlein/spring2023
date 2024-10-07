// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

/**
 * This interface is the EdgeType used, it extends number when created as a class.
 * @author Sidney Heberlein
 *
 */
public interface RailLineInterface 
{
  //public RailLineInterface(double cost, double time);
  public double getCost(); // returns the cost of one line/path
  public double getTime(); // returns the time it takes to travel one line/path
}
