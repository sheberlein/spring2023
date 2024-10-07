/**
 * This class models a rail line which will be the edge data for our Train Expedia graph
 * @author Joseph Hanson
 *
 */
public class RailLineAE extends Number implements RailLineInterface{
  double cost;
  double time;
  
  /**
   * Constructor for RailLine class
   * @param cost cost of the rail line
   * @param time how long it takes to take this rail
   */
  public RailLineAE (double cost, double time) {
    this.cost = cost;
    this.time = time;
  }

  public double getCost() {
    return cost;
  }
  
  public double getTime() {
    return time;
  }
  @Override
  public int intValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long longValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public float floatValue() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double doubleValue() {
    // TODO Auto-generated method stub
    return 0;
  }
}
