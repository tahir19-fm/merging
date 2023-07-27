package com.taomish.app.android.farmsanta.farmer.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    public final static long MILLIS_PER_HOUR = 60 * 60 * 1000L;
    public final static long MILLIS_PER_MINUTE = 60 * 1000L;
    public final static String SERVER_DATE_FORMAT =  "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public final static String FERTILIZER_DATE_FORMAT =  "dd-MM-yyyy HH:mm:ss";

    public String getApiFormat(String dateString) {
        String outputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String inputDateFormat = "dd-MM-yyyy";
        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);
        if (dateString != null) {
            try {
                Date getAbbreviate = input.parse(dateString);   // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return dateString;
    }

    public String getApiFormat(String dateString, String inputFormat) {
        String outputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        SimpleDateFormat input = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);
        if (dateString != null && !dateString.isEmpty()) {
            try {
                Date getAbbreviate = input.parse(dateString);    // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return dateString;
    }

    public String getMobileFormat(String dateString) {
        String inputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String outputDateFormat = "dd-MM-yyyy";
        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);
        if (dateString != null) {
            try {
                Date getAbbreviate = input.parse(dateString);    // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return dateString;
    }

    public String getDateMonthFormat(String dateString) {
        String inputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String outputDateFormat = "dd MMMM";
        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);
        if (dateString != null) {
            try {
                Date getAbbreviate = input.parse(dateString);    // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return dateString;
    }

    public String getDateMonthYearFormat(String dateString) {
        String inputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String outputDateFormat = "dd MMM, yyyy";
        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);
        if (dateString != null) {
            try {
                Date getAbbreviate = input.parse(dateString);    // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return dateString;
    }

    public String getDayDateMonthFormat(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        String outputDateFormat = "EEE, dd MMM";

        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);

        return output.format(calendar.getTime());    // format output
    }

    public String getHourMinuteFormat(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        String outputDateFormat = "hh:mm aa";

        SimpleDateFormat output = new SimpleDateFormat(outputDateFormat, Locale.ENGLISH);

        return output.format(calendar.getTime());    // format output
    }

    public String getHourMinuteFormat(long timeInMillis, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        SimpleDateFormat output = new SimpleDateFormat(format, Locale.ENGLISH);

        return output.format(calendar.getTime());    // format output
    }

    public String getDateMonthYearHourMinuteFormat(String dateTime) {
        String dt = dateTime;
        if (dateTime != null && dateTime.length() > 23) {
            dt = dateTime.substring(0, dateTime.length() - (dateTime.length() - 23));
        }

        String inputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        String outputFormat = "dd MMMM, yyyy' | 'HH:mm";
        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
        if (dt != null) {
            try {
                Date getAbbreviate = input.parse(dt);    // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return dateTime != null ? dateTime : "";
    }

    public Date getTimeStampFormat(String dateString) {
        Date getAbbreviate;
        String inputDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        SimpleDateFormat input = new SimpleDateFormat(inputDateFormat, Locale.ENGLISH);

        if (dateString != null) {
            try {
                getAbbreviate = input.parse(dateString);
                // parse input
                if (getAbbreviate != null) {
                    return getAbbreviate;    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return null;
    }

    public boolean isPostedWithin24hours(String createdTimeStamp) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new DateUtil().getTimeStampFormat(createdTimeStamp);

        try {
            return Math.abs(timestamp.getTime() - date.getTime()) > MILLIS_PER_DAY;
        } catch (Exception e) {
            return false;
        }
    }


    public String getDifferenceHours(String createdTimeStamp) throws ParseException {
        /*Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date utcDate  = new DateUtil().getDateInUTCFormat(createdTimeStamp);*/
        Date date = new DateUtil().getTimeStampFormat(createdTimeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault());
        Date dd = localDateFormat.parse(simpleDateFormat.format(new Date()));
        try {
            long timeStamp = 0;
            if (dd != null) {
                timeStamp = (Math.abs(dd.getTime() - date.getTime())) / MILLIS_PER_HOUR;
            }
            if (timeStamp == 0) {
                long minutes = 0;
                if (dd != null) {
                    minutes = (Math.abs(dd.getTime() - date.getTime())) / MILLIS_PER_MINUTE;
                }
                if (minutes == 0) {
                    return "just now";
                }
                return minutes + " m";
            }
            return timeStamp + " h";
        } catch (Exception e) {
            e.printStackTrace();
            return "0h";
        }
    }

    public int getAge(int year, int month, int date) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(Calendar.YEAR, year);
        dob.set(Calendar.MONTH, month);
        dob.set(Calendar.DATE, date);

        int age = today.get(Calendar.YEAR) - year;
        int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
        int dobDayOfYear = dob.get(Calendar.DAY_OF_YEAR);
        if (todayDayOfYear < dobDayOfYear) {
            age--;
        }
        return age;
    }

    public static String getCurrentDay(String format) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat localDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return localDateFormat.format(date);
    }

    public static String getDay(String format, int forwardDay) {
        long date = Calendar.getInstance().getTimeInMillis() + (forwardDay) * (1000 * 60 * 60 * 24);
        SimpleDateFormat localDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return localDateFormat.format(date);
    }

    public static String getCurrentTime(String format) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat localDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return localDateFormat.format(timeInMillis);
    }

    public static String getDate(long timeInMillis, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        SimpleDateFormat output = new SimpleDateFormat(format, Locale.ENGLISH);
        return output.format(calendar.getTime());    // format output
    }

    public static String convertDateFormat(String dateString, String inputFormat, String outputFormat) {
        SimpleDateFormat input = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
        if (dateString != null && !dateString.isEmpty()) {
            try {
                Date getAbbreviate = input.parse(dateString);    // parse input
                if (getAbbreviate != null) {
                    return output.format(getAbbreviate);    // format output
                }
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return dateString;
    }
}
