package com.mobdeve.xx22.rivera.josecarlos.animotrack;

//import android.content.Context;
//import android.graphics.drawable.Drawable;
//
//import androidx.core.content.ContextCompat;
//
//import com.prolificinteractive.materialcalendarview.CalendarDay;
//import com.prolificinteractive.materialcalendarview.DayViewDecorator;
//import com.prolificinteractive.materialcalendarview.DayViewFacade;
//
//public class EventImageDecorator implements DayViewDecorator {
//    private final CalendarDay date;
//    private final Drawable drawable;
//
//    public EventImageDecorator(Context context, CalendarDay date, int drawableRes) {
//        this.date = date;
//        this.drawable = ContextCompat.getDrawable(context, drawableRes);
//    }
//
//    @Override
//    public boolean shouldDecorate(CalendarDay day) {
//        return day.equals(date);
//    }
//
//    @Override
//    public void decorate(DayViewFacade view) {
//        view.setBackgroundDrawable(drawable);
//    }
//}

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Collection;
import java.util.HashSet;

public class EventImageDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private final HashSet<CalendarDay> dates;

    public EventImageDecorator(ProfilePage context, Collection<CalendarDay> dates, int drawableResource) {
        this.drawable = ContextCompat.getDrawable(context, drawableResource);
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}
