package com.kostikum.timeoverseer.db.converter;

import androidx.room.TypeConverter;

import org.joda.time.LocalDate;

public class DateConverter {
    @TypeConverter
    public static LocalDate toDate(String string) {
        return string == null ? null : LocalDate.parse(string);
    }

    @TypeConverter
    public static String toString(LocalDate localDate) {
        return localDate == null ? null : localDate.toString();
    }
}
