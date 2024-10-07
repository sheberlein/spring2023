// --== CS400 Spring 2023 File Header Information ==--
// Name: Sidney Heberlein
// Email: sheberlein@wisc.edu
// Team: AK
// TA: Florian Heimerl
// Lecturer: Gary Dahl
// Notes to Grader: N/A

/**
 * Placeholder class for RailLine implemented by the Algorithm Engineer
 * @author Sidney Heberlein
 *
 */
public class RailLineBD extends Number
{
  private static final long serialVersionUID = 1L;
  double cost;
  double time;
  
  public RailLineBD(double cost, double time)
  {
    this.cost = cost;
    this.time = time;
  }
  
  /**
   * This returns the cost of one line/path
   * @return the cost of one line/path
   */
  public double getCost()
  {
    return cost;
  }
  
  /**
   * This returns the time it takes to travel one line/path
   * @return the time it takes to travel one line/path
   */
  public double getTime()
  {
    return time;
  }

  @Override
  public int intValue() {
    return 0;
  }

  @Override
  public long longValue() {
    return 0;
  }

  @Override
  public float floatValue() {
    return 0;
  }

  @Override
  public double doubleValue() {
    return 0;
  }
}
