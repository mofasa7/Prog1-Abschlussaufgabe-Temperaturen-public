import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureTimeSeriesTest {

    /**
     * Tests for the method getMinTemperature() in the TemperatureTimeSeries class.
     * <p>
     * The getMinTemperature() method calculates the minimum temperature value
     * from all the TemperatureDataPoint instances in the dataPoints list.
     */

    @Test
    public void testGetMinTemperatureWithSingleDataPoint() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        series.addDataPoint(new TemperatureDataPoint(-5.0, 0.0, 10.0, 2025));
        assertEquals(-5.0, series.getMinTemperature());
    }

    @Test
    public void testGetMinTemperatureWithMultipleDataPoints() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        series.addDataPoint(new TemperatureDataPoint(3.5, 5.0, 8.5, 2025));
        series.addDataPoint(new TemperatureDataPoint(-10.0, 2.0, 15.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(-2.0, 0.0, 5.0, 2025));
        assertEquals(-10.0, series.getMinTemperature());
    }

    @Test
    public void testGetMinTemperatureWithNoDataPoints() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        assertEquals(Double.MAX_VALUE, series.getMinTemperature());
    }

    @Test
    public void testGetMinTemperatureWithAllPositiveTemperatures() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        series.addDataPoint(new TemperatureDataPoint(5.0, 10.0, 15.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(3.0, 8.0, 12.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(7.0, 9.0, 10.0, 2025));
        assertEquals(3.0, series.getMinTemperature());
    }

    @Test
    public void testGetMinTemperatureWithIdenticalTemperatures() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        series.addDataPoint(new TemperatureDataPoint(5.0, 5.0, 5.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(5.0, 5.0, 5.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(5.0, 5.0, 5.0, 2025));
        assertEquals(5.0, series.getMinTemperature());
    }

    /**
     * Tests for the method getTemperatureDifferences() in the TemperatureTimeSeries class.
     * <p>
     * The getTemperatureDifferences() method calculates the differences between
     * adjacent TemperatureDataPoint instances in the dataPoints list.
     */

    @Test
    public void testGetTemperatureDifferencesWithMultipleDataPoints() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        series.addDataPoint(new TemperatureDataPoint(10.0, 15.0, 20.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(8.0, 12.0, 18.0, 2025));
        series.addDataPoint(new TemperatureDataPoint(5.0, 10.0, 15.0, 2025));

        TemperatureDataPoint[] diffs = series.getTemperatureDifferences();

        assertEquals(2, diffs.length);
        assertEquals(2.0, diffs[0].getMinTemperature());
        assertEquals(3.0, diffs[0].getAvgTemperature());
        assertEquals(2.0, diffs[0].getMaxTemperature());
        assertEquals(3.0, diffs[1].getMinTemperature());
        assertEquals(2.0, diffs[1].getAvgTemperature());
        assertEquals(3.0, diffs[1].getMaxTemperature());
    }

    @Test
    public void testGetTemperatureDifferencesWithNoDataPoints() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        TemperatureDataPoint[] diffs = series.getTemperatureDifferences();

        assertNotNull(diffs);
        assertEquals(0, diffs.length);
    }

    @Test
    public void testGetTemperatureDifferencesWithSingleDataPoint() {
        TemperatureTimeSeries series = new TemperatureTimeSeries();
        series.addDataPoint(new TemperatureDataPoint(10.0, 15.0, 20.0, 2025));

        TemperatureDataPoint[] diffs = series.getTemperatureDifferences();

        assertNotNull(diffs);
        assertEquals(0, diffs.length);
    }

    /**
     * Tests for constructors in the TemperatureTimeSeries class.
     */

    @Test
    public void testEmptyConstructor() {
        // Test that the empty constructor does not raise any exceptions
        assertDoesNotThrow(() -> new TemperatureTimeSeries());
    }

    public boolean isSameDatapoints(TemperatureDataPoint dp1, TemperatureDataPoint dp2) {
        return
                dp1.getMinTemperature() == dp2.getMinTemperature() &&
                dp1.getAvgTemperature() == dp2.getAvgTemperature() &&
                dp1.getMaxTemperature() == dp2.getMaxTemperature();
    }

    @Test
    public void testConstructorWithFileSuccess() throws Exception {
        // Create a temporary file with sample data
        String tempFileName = "test_data.txt";
        try (FileWriter writer = new FileWriter(tempFileName)) {
            writer.write("STATIONS_ID;MESS_DATUM;QN_3;  FX;  FM;QN_4; RSK;RSKF; SDK;SHK_TAG;  NM; VPM;  PM; TMK; UPM; TXK; TNK; TGK;eor\n");  // Simulate header
            writer.write("427;20210103;   10;  12.3;   5.8;    3;   8.2;   8;    0.000;   0;   7.9;   5.9; 1008.88;    0.3;   94.04;    0.8;   -0.9;   -0.9;eor\n");
            writer.write("427;20220104;   10;   7.2;   4.3;    3;   0.0;   8;    0.000;   6;   7.8;   6.1; 1010.74;    0.6;   95.21;    0.9;    0.4;    0.1;eor\n");
        }

        // Test that the constructor reads the file correctly
        TemperatureTimeSeries series = new TemperatureTimeSeries(tempFileName);
        ArrayList<TemperatureDataPoint> dataPoints = series.getDataPoints();

        assertEquals(2, dataPoints.size());
        assertTrue(isSameDatapoints(new TemperatureDataPoint(-0.9, 0.3, 0.8, 2021), dataPoints.get(0)));
        assertTrue(isSameDatapoints(new TemperatureDataPoint(0.4, 0.6, 0.9, 2022), dataPoints.get(1)));
    }

    @Test
    public void testConstructorWithFileNotFound() {
        String invalidFileName = "non_existent_file.txt";
        assertThrows(Exception.class, () -> new TemperatureTimeSeries(invalidFileName));
    }
}