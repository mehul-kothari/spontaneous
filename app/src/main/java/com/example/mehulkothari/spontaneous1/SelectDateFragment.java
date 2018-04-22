package com.example.mehulkothari.spontaneous1;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by mehulkothari on 3/16/2017.
 */
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    TextView dateView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        dateView=(TextView)getActivity().findViewById(R.id.date_view);
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm+1, dd);
    }
    public void populateSetDate(int year, int month, int day) {
        dateView.setText(month+"/"+day+"/"+year);
    }

}






