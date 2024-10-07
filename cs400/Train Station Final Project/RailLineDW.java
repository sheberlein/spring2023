public class RailLineDW extends Number implements RailLineInterface
{
        private double cost;
        private double time;

        public RailLineDW(double c, double t)
        {
                cost = c;
                time = t;
        }

        @Override
        public double getCost()
        {
                return cost;
        }

        @Override
        public double getTime()
        {
                return time;
        }

        @Override
        public int intValue()
        {
                // TODO Auto-generated method stub
                return 0;
        }

        @Override
        public long longValue()
        {
                // TODO Auto-generated method stub
                return 0;
        }

        @Override
        public float floatValue()
        {
                // TODO Auto-generated method stub
                return 0;
        }
        @Override
        public double doubleValue()
        {
                // TODO Auto-generated method stub
                return 0;
        }

}
