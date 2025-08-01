import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureDataPointTest {

    /**
     * Class TemperatureDataPoint
     * <p>
     * This class represents temperature data records for a specific year. Each record consists
     * of a minimum, average, and maximum temperature. It allows for initialization using raw
     * values or by parsing a formatted string. Additionally, it provides methods to retrieve
     * temperature values and to calculate the temperature differences between two records.
     * <p>
     * Method getTemperatureDifference(TemperatureDataPoint other)
     * <p>
     * This method computes the absolute difference between the minimum, average, and maximum
     * temperatures of the current TemperatureDataPoint object and another provided object. The
     * returned result is a new TemperatureDataPoint instance that encapsulates these differences,
     * set with the year of the current object.
     */

    @Test
    void testConstructorWithDirectValues() {
        // Arrange
        double min = 10.0;
        double avg = 15.0;
        double max = 20.0;
        int year = 2020;

        // Act
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(min, avg, max, year);

        // Assert
        assertEquals(min, dataPoint.getMinTemperature());
        assertEquals(avg, dataPoint.getAvgTemperature());
        assertEquals(max, dataPoint.getMaxTemperature());
        assertEquals(year, dataPoint.getYear());
    }

    @Test
    void testConstructorWithStringInput() {
        // Arrange
        String input = "Some;20210104;That;Does;Not;Matter;Much;But;Has;17;Columns;Including;13;15.0;And;20.0;10.0;eor;";

        // Act
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(input);

        // Assert
        assertEquals(10.0, dataPoint.getMinTemperature());
        assertEquals(15.0, dataPoint.getAvgTemperature());
        assertEquals(20.0, dataPoint.getMaxTemperature());
        assertEquals(2021, dataPoint.getYear());
    }

    @Test
    void testTemperatureDifferenceWithPositiveValues() {
        // Arrange
        TemperatureDataPoint dataPoint1 = new TemperatureDataPoint(10.0, 15.0, 20.0, 2020);
        TemperatureDataPoint dataPoint2 = new TemperatureDataPoint(5.0, 10.0, 15.0, 2020);

        // Act
        TemperatureDataPoint difference = dataPoint1.getTemperatureDifference(dataPoint2);

        // Assert
        assertEquals(5.0, difference.getMinTemperature());
        assertEquals(5.0, difference.getAvgTemperature());
        assertEquals(5.0, difference.getMaxTemperature());
        assertEquals(2020, difference.getYear());
    }

    @Test
    void testTemperatureDifferenceWithNegativeValues() {
        // Arrange
        TemperatureDataPoint dataPoint1 = new TemperatureDataPoint(-5.0, 0.0, 5.0, 2021);
        TemperatureDataPoint dataPoint2 = new TemperatureDataPoint(-10.0, -5.0, 0.0, 2021);

        // Act
        TemperatureDataPoint difference = dataPoint1.getTemperatureDifference(dataPoint2);

        // Assert
        assertEquals(5.0, difference.getMinTemperature());
        assertEquals(5.0, difference.getAvgTemperature());
        assertEquals(5.0, difference.getMaxTemperature());
        assertEquals(2021, difference.getYear());
    }

    @Test
    void testTemperatureDifferenceWithMixedValues() {
        // Arrange
        TemperatureDataPoint dataPoint1 = new TemperatureDataPoint(-10.0, 0.0, 10.0, 2022);
        TemperatureDataPoint dataPoint2 = new TemperatureDataPoint(5.0, -5.0, 15.0, 2022);

        // Act
        TemperatureDataPoint difference = dataPoint1.getTemperatureDifference(dataPoint2);

        // Assert
        assertEquals(15.0, difference.getMinTemperature());
        assertEquals(5.0, difference.getAvgTemperature());
        assertEquals(5.0, difference.getMaxTemperature());
        assertEquals(2022, difference.getYear());
    }

    @Test
    void testTemperatureDifferenceWithZeroValues() {
        // Arrange
        TemperatureDataPoint dataPoint1 = new TemperatureDataPoint(0.0, 0.0, 0.0, 2023);
        TemperatureDataPoint dataPoint2 = new TemperatureDataPoint(0.0, 0.0, 0.0, 2023);

        // Act
        TemperatureDataPoint difference = dataPoint1.getTemperatureDifference(dataPoint2);

        // Assert
        assertEquals(0.0, difference.getMinTemperature());
        assertEquals(0.0, difference.getAvgTemperature());
        assertEquals(0.0, difference.getMaxTemperature());
        assertEquals(2023, difference.getYear());
    }

    @Test
    void testTemperatureDifferenceWithSameDataPoints() {
        // Arrange
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(5.0, 10.0, 15.0, 2024);

        // Act
        TemperatureDataPoint difference = dataPoint.getTemperatureDifference(dataPoint);

        // Assert
        assertEquals(0.0, difference.getMinTemperature());
        assertEquals(0.0, difference.getAvgTemperature());
        assertEquals(0.0, difference.getMaxTemperature());
        assertEquals(2024, difference.getYear());
    }

    @Test
    void testToStringWithPositiveValues() {
        // Arrange
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(10.0, 15.0, 20.0, 2023);

        // Act
        String result = dataPoint.toString();

        // Assert
        assertEquals("Temperatures on given day in 2023: 10.0 - 20.0, average: 15.0.", result);
    }

    @Test
    void testToStringWithNegativeValues() {
        // Arrange
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(-10.0, -5.0, 0.0, 2024);

        // Act
        String result = dataPoint.toString();

        // Assert
        assertEquals("Temperatures on given day in 2024: -10.0 - 0.0, average: -5.0.", result);
    }

    @Test
    void testToStringWithMixedValues() {
        // Arrange
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(-5.0, 0.0, 10.0, 2025);

        // Act
        String result = dataPoint.toString();

        // Assert
        assertEquals("Temperatures on given day in 2025: -5.0 - 10.0, average: 0.0.", result);
    }

    @Test
    void testIsInYearWhenYearMatches() {
        // Arrange
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(10.0, 15.0, 20.0, 2020);

        // Act
        boolean result = dataPoint.isInYear(2020);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void testIsInYearWhenYearDoesNotMatch() {
        // Arrange
        TemperatureDataPoint dataPoint = new TemperatureDataPoint(10.0, 15.0, 20.0, 2020);

        // Act
        boolean result = dataPoint.isInYear(2019);

        // Assert
        assertEquals(false, result);
    }
}