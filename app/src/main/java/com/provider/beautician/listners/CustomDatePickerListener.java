package com.provider.beautician.listners;

import java.util.ArrayList;
import java.util.Date;

public interface CustomDatePickerListener {
    void onSelectDate(Date date, int selectedPosition, int startDay, ArrayList<Date> dates);
}
