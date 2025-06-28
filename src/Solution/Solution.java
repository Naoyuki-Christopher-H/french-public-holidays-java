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
     * @param startDateInclusive The start date (inclusive)
     * @param endDateExclusive   The end date (exclusive)
     * @return The number of working days between the two dates
     */
    public static int getWorkingDays(LocalDate startDateInclusive, LocalDate endDateExclusive)
    {
        int workingDayCount = 0;
        LocalDate currentDate = startDateInclusive;

        while (!currentDate.isEqual(endDateExclusive))
        {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && 
                dayOfWeek != DayOfWeek.SUNDAY &&
                !isHoliday(currentDate))
            {
                workingDayCount++;
            }

            currentDate = currentDate.plusDays(1);
        }

        return workingDayCount;
    }

    /**
     * Determines if a specific date is a French public holiday.
     *
     * @param dateToCheck The date to check for holiday status
     * @return True if the date is a public holiday, false otherwise
     */
    public static boolean isHoliday(LocalDate dateToCheck)
    {
        return getHolidaysForYear(dateToCheck.getYear()).containsKey(dateToCheck);
    }

    /**
     * Returns a map of French public holidays for a given year.
     * Includes both fixed-date holidays and holidays based on Easter.
     *
     * @param targetYear The calendar year for which to retrieve holidays
     * @return A map of LocalDate to holiday name strings
     */
    public static Map<LocalDate, String> getHolidaysForYear(int targetYear)
    {
        Map<LocalDate, String> holidayMap = new HashMap<>();

        // Add all fixed-date holidays
        addFixedDateHolidays(holidayMap, targetYear);
        
        // Add movable feasts calculated from Easter Sunday
        addEasterBasedHolidays(holidayMap, targetYear);

        return holidayMap;
    }

    /**
     * Adds fixed-date French public holidays to the holiday map.
     *
     * @param holidayMap The map to populate with holidays
     * @param year       The target year for the holidays
     */
    private static void addFixedDateHolidays(Map<LocalDate, String> holidayMap, int year)
    {
        holidayMap.put(LocalDate.of(year, 1, 1), "Jour de l'an");
        holidayMap.put(LocalDate.of(year, 5, 1), "Fête du travail");
        holidayMap.put(LocalDate.of(year, 5, 8), "Victoire 1945");
        holidayMap.put(LocalDate.of(year, 7, 14), "Fête nationale");
        holidayMap.put(LocalDate.of(year, 8, 15), "Assomption");
        holidayMap.put(LocalDate.of(year, 11, 1), "Toussaint");
        holidayMap.put(LocalDate.of(year, 11, 11), "Armistice 1918");
        holidayMap.put(LocalDate.of(year, 12, 25), "Noël");
    }

    /**
     * Adds movable French public holidays (based on Easter Sunday) to the holiday map.
     *
     * @param holidayMap The map to populate with holidays
     * @param year       The target year for the holidays
     */
    private static void addEasterBasedHolidays(Map<LocalDate, String> holidayMap, int year)
    {
        LocalDate easterSunday = calculateEasterSunday(year);
        holidayMap.put(easterSunday.plusDays(1), "Lundi de Pâques");
        holidayMap.put(easterSunday.plusDays(39), "Jeudi de l'Ascension");
        holidayMap.put(easterSunday.plusDays(50), "Lundi de Pentecôte");
    }

    /**
     * Calculates the date of Easter Sunday for a given year using the
     * Meeus/Jones/Butcher Gregorian algorithm.
     * 
     * Reference: Jean Meeus' "Astronomical Algorithms" (1991)
     *
     * @param targetYear The year for which to calculate Easter Sunday
     * @return The LocalDate of Easter Sunday in the specified year
     */
    private static LocalDate calculateEasterSunday(int targetYear)
    {
        // Step 1: Calculate the Golden Number
        int goldenNumber = targetYear % 19;
        
        // Step 2: Calculate the century
        int century = targetYear / 100;
        
        // Step 3: Calculate the year within the century
        int yearInCentury = targetYear % 100;
        
        // Step 4: Calculate the century-based adjustments
        int centuryQuarter = century / 4;
        int centuryRemainder = century % 4;
        
        // Step 5: Calculate the solar correction term
        int solarCorrection = (century + 8) / 25;
        
        // Step 6: Calculate the metonic cycle adjustment
        int metonicAdjustment = (century - solarCorrection + 1) / 3;
        
        // Step 7: Calculate the epact (age of the moon on January 1)
        int epact = (19 * goldenNumber + century - centuryQuarter - metonicAdjustment + 15) % 30;
        
        // Step 8: Calculate the moon's orbit correction
        int moonOrbitCorrection = yearInCentury / 4;
        int moonOrbitRemainder = yearInCentury % 4;
        
        // Step 9: Calculate the Sunday correction
        int sundayCorrection = (32 + 2 * centuryRemainder + 2 * moonOrbitCorrection - epact - moonOrbitRemainder) % 7;
        
        // Step 10: Calculate the final adjustment
        int finalAdjustment = (goldenNumber + 11 * epact + 22 * sundayCorrection) / 451;
        
        // Step 11: Calculate the month and day components
        int monthDayCalculation = (epact + sundayCorrection - 7 * finalAdjustment + 114);
        int month = monthDayCalculation / 31;
        int day = (monthDayCalculation % 31) + 1;

        return LocalDate.of(targetYear, month, day);
    }

    /**
     * Main method for testing the functionality.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args)
    {
        // Test the working days calculation for the year 2023
        LocalDate yearStart = LocalDate.of(2023, 1, 1);
        LocalDate yearEnd = LocalDate.of(2023, 12, 31);
        
        int totalWorkingDays = getWorkingDays(yearStart, yearEnd);
        System.out.println("Total working days in 2023: " + totalWorkingDays);
        
        // Test holiday detection for Labor Day (May 1)
        LocalDate laborDay2023 = LocalDate.of(2023, 5, 1);
        System.out.println("Is " + laborDay2023 + " a holiday? " + isHoliday(laborDay2023));
        
        // Test Easter Sunday calculation
        LocalDate easter2023 = calculateEasterSunday(2023);
        System.out.println("Easter Sunday in 2023: " + easter2023);
    }
}