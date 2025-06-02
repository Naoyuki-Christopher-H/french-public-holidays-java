package Solution;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for calculating working days and identifying public holidays in France.
 * Supports both fixed-date holidays and movable feasts based on Easter Sunday.
 */
public class Solution
{
    /**
     * Calculates the number of working days (Monday to Friday excluding public holidays)
     * between two dates. The start date is inclusive, and the end date is exclusive.
     *
     * @param fromDate The start date (inclusive)
     * @param toDate   The end date (exclusive)
     * @return The number of working days between the two dates
     */
    public static int getWorkingDays(LocalDate fromDate, LocalDate toDate)
    {
        int workingDays = 0;

        while (!fromDate.isEqual(toDate))
        {
            DayOfWeek dayOfWeek = fromDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY &&
                dayOfWeek != DayOfWeek.SUNDAY &&
                !isHoliday(fromDate))
            {
                workingDays++;
            }

            fromDate = fromDate.plusDays(1);
        }

        return workingDays;
    }

    /**
     * Determines if a specific date is a French public holiday.
     *
     * @param date The date to check
     * @return True if the date is a public holiday, false otherwise
     */
    public static boolean isHoliday(LocalDate date)
    {
        return getHolidays(date.getYear()).containsKey(date);
    }

    /**
     * Returns a map of French public holidays for a given year.
     * Includes both fixed-date holidays and holidays based on Easter.
     *
     * @param year The calendar year for which to retrieve holidays
     * @return A map of LocalDate to holiday name
     */
    public static Map<LocalDate, String> getHolidays(int year)
    {
        Map<LocalDate, String> holidays = new HashMap<>();

        // Fixed-date holidays
        holidays.put(LocalDate.of(year, 1, 1), "Jour de l'an");
        holidays.put(LocalDate.of(year, 5, 1), "Fête du travail");
        holidays.put(LocalDate.of(year, 5, 8), "Victoire 1945");
        holidays.put(LocalDate.of(year, 7, 14), "Fête nationale");
        holidays.put(LocalDate.of(year, 8, 15), "Assomption");
        holidays.put(LocalDate.of(year, 11, 1), "Toussaint");
        holidays.put(LocalDate.of(year, 11, 11), "Armistice 1918");
        holidays.put(LocalDate.of(year, 12, 25), "Noël");

        // Movable feasts calculated from Easter Sunday
        LocalDate easterSunday = calculateEasterSunday(year);
        holidays.put(easterSunday.plusDays(1), "Lundi de Pâques");
        holidays.put(easterSunday.plusDays(39), "Jeudi de l'Ascension");
        holidays.put(easterSunday.plusDays(50), "Lundi de Pentecôte");

        return holidays;
    }

    /**
     * Calculates the date of Easter Sunday for a given year using the
     * Meeus/Jones/Butcher Gregorian algorithm.
     *
     * @param year The year for which to calculate Easter Sunday
     * @return The date of Easter Sunday
     */
    private static LocalDate calculateEasterSunday(int year)
    {
        // Meeus/Jones/Butcher algorithm for Gregorian calendar
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int j = c % 4;
        int k = (32 + 2 * e + 2 * i - h - j) % 7;
        int l = (a + 11 * h + 22 * k) / 451;
        int m = (h + k - 7 * l + 114);
        int month = m / 31;
        int day = (m % 31) + 1;

        return LocalDate.of(year, month, day);
    }

    /**
     * Main method for testing the functionality
     * @param args
     */
    public static void main(String[] args)
    {
        // Test the working days calculation
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        
        int workingDays = getWorkingDays(startDate, endDate);
        System.out.println("Working days in 2023: " + workingDays);
        
        // Test holiday detection
        LocalDate testDate = LocalDate.of(2023, 5, 1);
        System.out.println("Is " + testDate + " a holiday? " + isHoliday(testDate));
    }
}