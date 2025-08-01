import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TemperatureTimeSeries {
    private ArrayList<TemperatureDataPoint> dataPoints;

    public TemperatureTimeSeries() {
        dataPoints = new ArrayList<>();
    }

    public TemperatureTimeSeries(String filename) throws Exception {
        dataPoints = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                if (line.trim().isEmpty()) continue;
                TemperatureDataPoint point = new TemperatureDataPoint(line);
                if (point.getYear() != 0) dataPoints.add(point);
            }
        } catch (Exception e) {
            throw new Exception("Fehler beim Einlesen der Datei " + filename);
        }
    }

    public void addDataPoint(TemperatureDataPoint dataPoint) {
        dataPoints.add(dataPoint);
    }

    public ArrayList<TemperatureDataPoint> getDataPoints() {
        return dataPoints;
    }

    public int getNumDatapoints() {
        return dataPoints.size();
    }

    public TemperatureTimeSeries filterByYear(int year) {
        TemperatureTimeSeries filtered = new TemperatureTimeSeries();
        for (TemperatureDataPoint point : dataPoints) {
            if (point.isInYear(year)) {
                filtered.addDataPoint(point);
            }
        }
        return filtered;
    }

    public double getMinTemperature() {
        if (dataPoints.isEmpty()) return Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        for (TemperatureDataPoint point : dataPoints) {
            if (point.getMinTemperature() < min) min = point.getMinTemperature();
        }
        return min;
    }

    public double getMaxTemperature() {
        if (dataPoints.isEmpty()) return -Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for (TemperatureDataPoint point : dataPoints) {
            if (point.getMaxTemperature() > max) max = point.getMaxTemperature();
        }
        return max;
    }

    public double getOverallAvg() {
        if (dataPoints.isEmpty()) return 0.0;
        double sum = 0.0;
        for (TemperatureDataPoint point : dataPoints) {
            sum += point.getAvgTemperature();
        }
        return sum / dataPoints.size();
    }

    public TemperatureDataPoint[] getTemperatureDifferences() {
        if (dataPoints.size() < 2) return new TemperatureDataPoint[0];
        TemperatureDataPoint[] diffs = new TemperatureDataPoint[dataPoints.size() - 1];
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            diffs[i] = dataPoints.get(i + 1).getTemperatureDifference(dataPoints.get(i));
        }
        return diffs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Temperature time series with ").append(getNumDatapoints()).append(" data points:\n");
        for (TemperatureDataPoint point : dataPoints) {
            sb.append("* ").append(point.toString()).append("\n");
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        // Simple main to test this class independently
        try {
            TemperatureTimeSeries series = new TemperatureTimeSeries("produkt_klima_tag_20041101_20241231_00131.txt");
            System.out.println(series);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
