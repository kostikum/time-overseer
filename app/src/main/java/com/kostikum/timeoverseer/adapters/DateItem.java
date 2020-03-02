package com.kostikum.timeoverseer.adapters;

import org.joda.time.LocalDate;

public class DateItem extends ListItem {

    private LocalDate localDate;

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}
