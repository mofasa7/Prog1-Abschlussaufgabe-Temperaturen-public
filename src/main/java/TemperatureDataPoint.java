public class TemperatureDataPoint {
    private double minTemperature;
    private double avgTemperature;
    private double maxTemperature;
    private int year;

    public TemperatureDataPoint(double min, double avg, double max, int year) {
        this.minTemperature = min;
        this.avgTemperature = avg;
        this.maxTemperature = max;
        this.year = year;
    }

    public TemperatureDataPoint(String line) {
        String[] parts = line.split(";");
        if (parts.length < 18) {
            this.minTemperature = 0.0;
            this.avgTemperature = 0.0;
            this.maxTemperature = 0.0;
            this.year = 0;
            return;
        }
        try {
            String dateString = parts[1].trim();
            this.year = Integer.parseInt(dateString.substring(0, 4));
        } catch (Exception e) {
            this.year = 0;
        }
        try {
            this.avgTemperature = parse(parts[13]);
        } catch (Exception e) {
            this.avgTemperature = 0.0;
        }
        try {
            this.maxTemperature = parse(parts[15]);
        } catch (Exception e) {
            this.maxTemperature = 0.0;
        }
        try {
            this.minTemperature = parse(parts[16]);
        } catch (Exception e) {
            this.minTemperature = 0.0;
        }
    }

    private double parse(String s) {
        s = s.trim().replace(",", ".");
        if (s.equals("") || s.equals("-") || s.equals("nan")) return 0.0;
        return Double.parseDouble(s);
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getAvgTemperature() {
        return avgTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public int getYear() {
        return year;
    }

    public double[] getTemperatures() {
        return new double[]{minTemperature, avgTemperature, maxTemperature};
    }

    public TemperatureDataPoint getTemperatureDifference(TemperatureDataPoint other) {
        double minDiff = Math.abs(this.minTemperature - other.minTemperature);
        double avgDiff = Math.abs(this.avgTemperature - other.avgTemperature);
        double maxDiff = Math.abs(this.maxTemperature - other.maxTemperature);
        return new TemperatureDataPoint(minDiff, avgDiff, maxDiff, this.year);
    }

    public boolean isInYear(int year) {
        return this.year == year;
    }

    @Override
    public String toString() {
        return "Temperatures on given day in " + year + ": " +
                minTemperature + " - " + maxTemperature + ", average: " + avgTemperature + ".";
    }

    public static void main(String[] args) {
        // Simple main to test this class independently
        String testLine = "427;20210104;10;12.3;5.8;3;8.2;8;0.000;0;7.9;5.9;1008.88;15.0;94.04;20.0;10.0;eor;";
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(testLine);
        System.out.println(dataPoint);
    }
}
