package app.minervati.com.br.keepinmind.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import app.minervati.com.br.keepinmind.R;

/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout {
    // for logging
    private static final String LOGTAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    //event handling
    private EventHandler eventHandler = null;

    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    private HashMap<Date, Integer> eventsAux;

    // seasons' rainbow
    int[] rainbow = new int[]{
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btnPrev = (ImageView) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView) findViewById(R.id.calendar_next_button);
        txtDate = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
                return true;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View cell, int position, long id) {
                eventHandler.onDayPress((Date) view.getItemAtPosition(position));
            }
        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(eventsAux);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashMap<Date, Integer> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));
        //grid.setVerticalSpacing(40);
        grid.setPadding(0, 30, 0, 0);

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        txtDate.setText(sdf.format(currentDate.getTime()).substring(0,1).toUpperCase()
                + sdf.format(currentDate.getTime()).substring(1));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));
    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashMap<Date, Integer> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashMap<Date, Integer> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            TextView tv = (TextView) view;
            // if this day has an event, specify event image
            view.setBackgroundResource(0);
            //tv.setPadding(30, 30, 30, 30);

            // clear styling
            tv.setTypeface(null, Typeface.NORMAL);
            tv.setTextColor(Color.BLACK);

            if (month != today.getMonth() || year != today.getYear()) {
                // if this day is outside current month, grey it out
                tv.setTextColor(getResources().getColor(R.color.greyed_out));
            } else if (day == today.getDate()) {
                // if it is today, set it to blue/bold
                tv.setTypeface(null, Typeface.BOLD);
                tv.setTextColor(getResources().getColor(R.color.today));
            }

            // set text
            tv.setText(String.valueOf(date.getDate()));
            if (eventDays != null) {
                eventsAux = (HashMap<Date, Integer>) eventDays.clone();
                for (Date eventDate : eventDays.keySet()) {
                    switch ( eventDays.get(eventDate) ){
                        case 1:
                            addAnEvent(view, eventDate, day, month, year, R.drawable.reminder_starting);
                            break;
                        case 2:
                            addAnEvent(view, eventDate, day, month, year, R.drawable.reminder_low_risk);
                            break;
                        case 3:
                            addAnEvent(view, eventDate, day, month, year, R.drawable.reminder_mid_risk);
                            break;
                        case 4:
                            addAnEvent(view, eventDate, day, month, year, R.drawable.reminder_tpm);
                            break;
                        case 5:
                            addAnEvent(view, eventDate, day, month, year, R.drawable.reminder_ending);
                            break;
                        case 6:
                            addAnEventColorBackground(view, eventDate, day, month, year, Color.RED);
                            break;
                    }
                }
            }
            return view;
        }
    }

    /**
     * Método responsavel por inserir uma cor de background para determinada data.
     * @param view
     * @param eventDate
     * @param day
     * @param month
     * @param year
     * @param color
     */
    public void addAnEventColorBackground(View view, Date eventDate, int day, int month, int year,
                                          int color) {
        if (eventDate.getDate() == day &&
                eventDate.getMonth() == month &&
                eventDate.getYear() == year) {
            ((TextView) view).setTextColor(Color.WHITE);
            ((TextView) view).setBackgroundColor(color);
        }
    }

    /**
     * Método responsável por inserir um drawable resource para determinada data.
     * @param view
     * @param eventDate
     * @param day
     * @param month
     * @param year
     * @param drawable
     */
    public void addAnEvent(View view, Date eventDate, int day, int month, int year,
                           int drawable) {
        if (eventDate.getDate() == day &&
                eventDate.getMonth() == month &&
                eventDate.getYear() == year) {
            ((TextView) view).setBackgroundResource(drawable);
        }
    }

    /**
     * Método responsavel por inserir uma cor de background, cor do texto e
     * um drawable resource para determinada data.
     * @param view
     * @param eventDate
     * @param day
     * @param month
     * @param year
     * @param colorText
     * @param colorBackground
     * @param drawable
     */
    public void addAnEventWithColorAndDrawable(View view, Date eventDate, int day, int month, int year,
                           int colorText, int colorBackground, int drawable) {
        if (eventDate.getDate() == day &&
                eventDate.getMonth() == month &&
                eventDate.getYear() == year) {
            ((TextView) view).setTextColor(colorText);
            ((TextView) view).setBackgroundColor(colorBackground);
            ((TextView) view).setBackgroundResource(drawable);
        }
    }
    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayLongPress(Date date);
        void onDayPress(Date date);
    }
}
