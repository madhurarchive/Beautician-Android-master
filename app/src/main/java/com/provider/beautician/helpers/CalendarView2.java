package com.provider.beautician.helpers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.provider.beautician.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarView2 extends LinearLayout
{
    // for logging
    private static final String TAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 41;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    //event handling
    private EventHandler eventHandler = null;

    private int selectedPosition;
    private Date selectDate,selectDatePosition;
    private boolean isStartDate,isSelect;
    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CalendarView2(Context context)
    {
        super(context);
    }

    public CalendarView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView2(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();
        currentDate.add(Calendar.MONTH, 0);
        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs)
    {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try
        {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        }
        finally
        {
            ta.recycle();
        }
    }
    private void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedPosition = selectedPosition+1;
                currentDate.add(Calendar.MONTH, +1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedPosition = selectedPosition-1;
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
		/*grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
			{
				// handle long-press
				if (eventHandler == null)
					return false;

				eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
				return true;
			}
		});*/

    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {
        updateCalendar(null);
    }

    public void setUpdateCalendar(int position,Date date,Date selDate,boolean isStart,boolean isSelect)
    {
        selectDate = date;
        selectDatePosition = selDate;
        isStartDate  = isStart;
        this.isSelect = isSelect;
        currentDate.add(Calendar.MONTH, position);
        selectedPosition = position;
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events)
    {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];
    }


    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        // days with events
        private HashSet<Date> eventDays;
        private ArrayList<Date> dates;
        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
        {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            this.dates = days;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent)
        {
            // day in question
            final Date date = getItem(position);
            int day = date.getDate();
            final int month = date.getMonth();
            final int year = date.getYear();

            // today
            final Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            // if this day has an event, specify event image
            view.setBackgroundResource(0);

            // clear styling
            ((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView)view).setTextColor(Color.BLACK);

            Log.e(TAG,"isStartDate === "+isStartDate+" isSelect"+isSelect);
            ((TextView)view).setTextColor(getResources().getColor(R.color.colorBlack));

            if ((month == today.getMonth()) && (day == today.getDate())) {
                view.setBackground(getResources().getDrawable(R.drawable.ic_inventory_product_edit_bg));
                ((TextView)view).setTextColor(getResources().getColor(R.color.colorBlack));
            }

            if (isStartDate && isSelect && date.after(selectDatePosition)){
                ((TextView)view).setTextColor(getResources().getColor(R.color.colorDivider));
            }else if (!isStartDate && !isSelect && date.before(selectDatePosition)){
                ((TextView)view).setTextColor(getResources().getColor(R.color.colorDivider));
            }else if (!isStartDate && isSelect && date.before(selectDatePosition)){
                ((TextView)view).setTextColor(getResources().getColor(R.color.colorDivider));
            }

            if (selectDate!=null && selectDate.getDate() != 0 && selectDate.getDate() == day && selectDate.getMonth() == month){
                ((TextView)view).setTextColor(getResources().getColor(R.color.colorWhite));
                view.setBackgroundColor(getResources().getColor(R.color.colorBlack));
            }
            if (date.getMonth()!=currentDate.getTime().getMonth()){
                ((TextView)view).setTextColor(getResources().getColor(R.color.colorDivider));
            }



            // set text
            ((TextView)view).setText(String.valueOf(date.getDate()));

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((TextView)view).getCurrentTextColor()!=getResources().getColor(R.color.colorDivider)) {
                        eventHandler.onDayLongPress(date, selectedPosition);
                    }
                }
            });

            return view;
        }
    }
    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler
    {
        void onDayLongPress(Date date,int selectedPosition);
    }
}
