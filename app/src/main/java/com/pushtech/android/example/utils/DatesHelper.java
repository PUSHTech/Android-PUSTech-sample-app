package com.pushtech.android.example.utils;

import android.content.Context;
import android.content.res.Resources;

import com.pushtech.android.example.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.pushtech.android.example.utils.Constants.DAY_TIME;
import static com.pushtech.android.example.utils.Constants.HOUR_TIME;
import static com.pushtech.android.example.utils.Constants.MINUTE_TIME;
import static com.pushtech.android.example.utils.Constants.MONTH_TIME;
import static com.pushtech.android.example.utils.Constants.WEEK_TIME;
import static com.pushtech.android.example.utils.Constants.YEAR_TIME;

/**
 * Created by crm27 on 6/6/16.
 */

public class DatesHelper {

    public static String getTimeAgoString(Context ctx, long time) {

        final long result = Calendar.getInstance().getTimeInMillis() - time;
        Resources res = ctx.getResources();

        if (result < MINUTE_TIME) {
            return ctx.getString(R.string.dates_now);
        } else if (result < HOUR_TIME) {
            return String.format(res.getString(R.string.dates_minutes), result / MINUTE_TIME);
        } else if (result < DAY_TIME) {
            return String.format(res.getString(R.string.dates_hour), result / HOUR_TIME);
        } else if (result < WEEK_TIME) {
            return String.format(res.getString(R.string.dates_day), result / DAY_TIME);
        } else if (result < MONTH_TIME) {
            return String.format(res.getString(R.string.dates_week), result / WEEK_TIME);
        } else if (result < YEAR_TIME) {
            return String.format(res.getString(R.string.dates_month), result / MONTH_TIME);
        } else {
            return String.format(res.getString(R.string.dates_year), result / YEAR_TIME);
        }
    }

    public static String getDateString(Context ctx, long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", ctx.getResources().getConfiguration().locale);
        return sdf.format(calendar.getTime());
    }
}
