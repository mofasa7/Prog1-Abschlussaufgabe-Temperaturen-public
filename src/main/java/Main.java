public class Main {
    public static void main(String[] args) {
        // Path to your data file (adjust if you place the file elsewhere)
        String filename = "produkt_klima_tag_20041101_20241231_00131.txt";
        int year1 = 2014;
        int year2 = 2024;
        String stationName = "Geringswalde-Altgeringswalde";

        try {
            TemperatureTimeSeries series = new TemperatureTimeSeries(filename);

            TemperatureTimeSeries series2014 = series.filterByYear(year1);
            TemperatureTimeSeries series2024 = series.filterByYear(year2);

            double avg2014 = series2014.getOverallAvg();
            double avg2024 = series2024.getOverallAvg();

            System.out.println("Station: " + stationName);
            System.out.println("Year 1: " + year1 + ", Avg Temperature: " + avg2014);
            System.out.println("Year 2: " + year2 + ", Avg Temperature: " + avg2024);

            System.out.println("\nShort summary:");
            if (avg2024 > avg2014) {
                System.out.println("The average temperature increased over 10 years.");
            } else if (avg2024 < avg2014) {
                System.out.println("The average temperature decreased over 10 years.");
            } else {
                System.out.println("The average temperature remained the same over 10 years.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
