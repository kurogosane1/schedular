/**
 * Time conversion on this class can be better refactored to reduce the number of code lines and memory
 * @author Syed Khurshid
 */
package schedular.utilities;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import javafx.collections.ObservableList;
import schedular.DOA.AppointmentDOA;
import schedular.Model.Appointments;


/**
 * This is a class to help with Date Conversions and other date related activity
 */
public class TimeConversion {
    /**
     * THis is the default constructor
     */
    public TimeConversion(){}
    /**
     * This is the Local User Zone ID
     */
    static ZoneId userTimeZone = ZoneId.systemDefault();
    /**
     * This is the Default Time Zone in users Settings
     */
    static String defaultTimeZone = TimeZone.getDefault().getID();
    /**
     * This is to convert the data from the Spinners to a String Format
     * @param date which is the LocalDate
     * @param time which is the string time format
     * @return str which is a string
     */
    public static String convertDateTime(LocalDate date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = String.valueOf(String.valueOf(date) + " " + time);

        return str;
    }
    /**
     * This is to convert the string to LocalDateTime format
     * @param date which is the string picked up from the picker
     * @return string format of the date
     */
    public static LocalDateTime convertToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime result = LocalDateTime.parse(date, formatter);
        return result;
    }
    /**
     * This is to convert the time to String format from the Spinner
     * @param hour This is the Integer Format Spinner Hour Selected
     * @param minute This is the Integer format minute Selected
     * @return str which is the string of the hour and minutes
     */
    public static String timeStringConversion(Integer hour, Integer minute) {
        String hourCheck = hour.toString();
        String minuteCheck = minute.toString();
        if (hourCheck.length() == 1) {
            hourCheck = "0" + hourCheck;
        }
     if (minuteCheck.length() == 1) {
         minuteCheck = "0" + minuteCheck;
     }
     System.out.println(hourCheck + " " + minuteCheck);
     String str = hourCheck + ":" + minuteCheck + ":" + "00";
     return  str;
        
    };
    /**
     * This is to compare Start Time of the Appointments with the End Time of the Appointments
     * @param startTime is the Appointment Start Time Selected
     * @param endTime is end Time of the Appointment Selected
     * @return Integer value that will cause an alert to be displayed
     */
    public static Boolean compareTimes(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);
        LocalTime startT = start.toLocalTime();
        LocalTime endT = end.toLocalTime();
        if (startT.isAfter(endT)) {
            return false;
        }
        if (endT.isBefore(startT)) {
            return false;
        }
        return true; // IF everything is OK
    }
    /**
     * This is to compare Dates selected before saving to database
     * @param startDate is in a String Format Date picked
     * @param endDate is in a String Format Date picked
     * @return Integer value that will be used to compare values
     */
    public static Integer compareDates(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate startD = LocalDate.parse(startDate, formatter);
        LocalDate endD = LocalDate.parse(endDate, formatter);
        
        if (startD.isAfter(endD)) {
            return 1;
        }
        if (startD.isBefore(LocalDate.now())) {
            return 2;
        }
        if (endD.isBefore(LocalDate.now())) {
            return 3;
        }
        return 0; // If everything is OK
    }
    /**
     * This is to convert to UTC format to save to database
     * @param dateTime in a string format
     * @return Date which would be in UTC format to save to database
     */
    public static String convertTimeToUTC(String dateTime) {
        Timestamp currentTimeStamp = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime LocalDT = currentTimeStamp.toLocalDateTime();
        ZonedDateTime zoneDT = LocalDT.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcDT = zoneDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localOUT = utcDT.toLocalDateTime();
        String utcOUT = localOUT.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }
    /**
     * THis is to Compare if the Time Zone is not met
     * @param startDateTime is the starting Date Time for the Appointment
     * @param endDateTime is the end Date Time for the Appointment
     * @param appointmentDate is the Appointment Date
     * @return Boolean to the user to then take action accordingly
     */
    public Boolean compareTimeZomes(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate appointmentDate, LocalDate endAppointmentDate) {
        // Checking if they are being selected Appointment Time meets EST standard and is between 8am and 10pm
        ZonedDateTime zoneStartDateTime = ZonedDateTime.of(startDateTime, userTimeZone);
        ZonedDateTime zoneEndDateTime = ZonedDateTime.of(endDateTime, userTimeZone);

        ZonedDateTime startBusinessHour = ZonedDateTime.of(appointmentDate, LocalTime.of(8, 0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHour = ZonedDateTime.of(endAppointmentDate, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));
        if (zoneStartDateTime.isBefore(startBusinessHour) | zoneStartDateTime.isAfter(endBusinessHour)
                | zoneEndDateTime.isBefore(startBusinessHour) | zoneEndDateTime.isAfter(endBusinessHour)
                | zoneStartDateTime.isAfter(zoneEndDateTime)) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * This is to Convert Time to Local Time Zone
     * @param date is the date in string format
     * @return a string format converted to Local Timezone
     */
    public static String convertToLocalTimeZone(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(date, formatter);
        ZonedDateTime zone = ZonedDateTime.of(ldt, ZoneId.of(defaultTimeZone));
        String str = zone.toString();
        return str;
    }
    /**
     * This is to check if the Appointment selected falls anywhere in the existing appointments
     * @param startDateTime,  which are the appointment start times the user has selected
     * @param endDateTime which is the end time of the appointment that the user has selected
     * @throws SQLException which is in case an error in SQL occurs
     * @return Boolean indicating if the Appointment is not conflicting or it is
     */
    public static Boolean appointmentOverlapCheck(String startDateTime, String endDateTime) throws SQLException {
        AppointmentDOA aptDOA = new AppointmentDOA();
        ObservableList<Appointments> appointmentList = aptDOA.getAll();
        if (appointmentList.isEmpty()) {
            return true;
        }
        else {
            for (Appointments conflictCheck : appointmentList) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime startDateTimeCheck = LocalDateTime.parse(startDateTime,formatter);
                LocalDateTime endDateTimeCheck = LocalDateTime.parse(endDateTime,formatter);
                LocalDateTime startCheck =LocalDateTime.parse(conflictCheck.getStart(),formatter);
                LocalDateTime endCheck = LocalDateTime.parse(conflictCheck.getStart(), formatter);
                
                //Conflict starts before and Conflict ends any time after new appointment ends - Overlap
                if (startCheck.isBefore(startDateTimeCheck) & endCheck.isAfter(endDateTimeCheck)) {
                    return false;
                }
                // Start time falls anywhere in the new appointment
                if (startCheck.isBefore(endDateTimeCheck) & startCheck.isAfter(startDateTimeCheck)) {
                    return false;
                }
                // End Time conflict falls anywhere in the new appointment
                if (endCheck.isBefore(endDateTimeCheck) & endCheck.isAfter(startDateTimeCheck)) {
                    return false;
                }
                else{
                    return true;
                }
            }
        }
        return false;
    }
}
