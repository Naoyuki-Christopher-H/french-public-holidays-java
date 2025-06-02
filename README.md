# French Working Days Calculator

## Overview

This Java utility provides accurate calculation of working days (business days) in France, accounting for both fixed public holidays and movable feasts based on the liturgical calendar. The solution implements official French public holiday rules and is designed for integration into business applications, payroll systems, or workforce management tools.

## Features

### Core Functionality
- Calculates working days between two dates (exclusive of end date)
- Identifies all French public holidays for any given year
- Handles both fixed-date holidays and movable feasts
- Supports date ranges spanning multiple years

### Holiday Coverage
Includes all official French public holidays:
- Fixed-date holidays (New Year's Day, Labor Day, etc.)
- Easter-based movable feasts (Easter Monday, Ascension, Pentecost Monday)
- Comprehensive validation of holiday rules

### Technical Specifications
- Pure Java implementation (JDK 8+ compatible)
- Uses modern `java.time` API for date handling
- No external dependencies
- Allman-style code formatting
- Comprehensive JavaDoc documentation

## Usage

### Basic Calculation
```java
LocalDate startDate = LocalDate.of(2023, 1, 1);
LocalDate endDate = LocalDate.of(2023, 12, 31);
int workingDays = Solution.getWorkingDays(startDate, endDate);
```

### Holiday Checking
```java
LocalDate dateToCheck = LocalDate.of(2023, 5, 1);
boolean isHoliday = Solution.isHoliday(dateToCheck);
```

### Retrieving Holidays
```java
Map<LocalDate, String> holidays = Solution.getHolidays(2023);
```

## Implementation Details

### Easter Calculation
The solution implements the Meeus/Jones/Butcher algorithm for calculating Easter Sunday dates, which is:
- Accurate for all years in the Gregorian calendar
- Matches official French holiday determinations
- Validated against historical and future dates

### Performance Considerations
- Holiday calculations are cached per year
- Linear iteration through date ranges for working day counts
- Minimal object allocation during operations

## Integration Guidelines

### System Requirements
- Java 8 or higher
- No special dependencies required

### Best Practices
- Reuse holiday maps when making multiple queries for the same year
- Validate date ranges before calculation (end date after start date)
- Consider timezone implications for date boundaries

## Maintenance

### Version Compatibility
The API maintains backward compatibility with these guarantees:
- Method signatures will not change in patch versions
- Holiday rules will match official French declarations
- Easter calculation algorithm will remain constant

### Testing Recommendations
- Verify calculations against known holiday dates
- Test edge cases (year boundaries, leap years)
- Validate against official French holiday calendars

## Support

For issues or questions regarding implementation:
- Consult JavaDoc documentation first
- Review algorithm references for Easter calculation
- Validate against official French government holiday publications

Note: This implementation reflects standard French metropolitan public holidays. Special regional holidays or territorial variations are not included.
