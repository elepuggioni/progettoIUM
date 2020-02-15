package com.example.progettoium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment{
    private Calendar date;
    private int caso;
    private int set;
    private int[] inizio;
    public DatePicker datePicker;
    //creo il listener (vedi metodi interfaccia)
    private DatePickerFragmentListener listener;


    public Dialog onCreateDialog (Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);//uso il metodo di Dialog

        //SE è null gli metto valori di default
        if(date==null){
            date = Calendar.getInstance();
            date.set(Calendar.YEAR, 1995);
            date.set(Calendar.MONTH, Calendar.JANUARY);
            date.set(Calendar.DAY_OF_MONTH, 1);
        }

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista;

        if (getCaso() == 0){    //Partenza
            vista = inflater.inflate(R.layout.departure_picker,null);
            datePicker = vista.findViewById(R.id.datePicker1);
            if (getSet() != 0)
                initDatePicker(getInizio());
        }else {     //Ritorno
            vista = inflater.inflate(R.layout.landing_picker,null);
            datePicker = vista.findViewById(R.id.datePicker2);
            if (getSet() != 0)
                initDatePicker(getInizio());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(vista);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date.set(Calendar.YEAR, datePicker.getYear());
                date.set(Calendar.MONTH, datePicker.getMonth());
                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                if(listener!=null){
                    listener.onDatePickerFragmentOkButton(DatePickerFragment.this, date);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener!=null){
                            listener.onDatePickerFragmentCancelButton(DatePickerFragment.this);
                        }
                    }
                }
        );

        return builder.create();
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setOnDatePickerFragmentChanged(DatePickerFragmentListener l){
        this.listener = l;
    }

    public int getCaso() {
        return caso;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }

    public void initDatePicker(int[]i ){
        datePicker.updateDate(i[0], i[1], i[2]);
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int[] getInizio() {
        return inizio;
    }

    public void setInizio(int[] inizio) {
        this.inizio = inizio;
    }

    //stiamo creando noi degli handle, interfaccia con i metodi che gestiranno gli eventi
    public interface DatePickerFragmentListener{
        public void onDatePickerFragmentOkButton( DialogFragment dialog, Calendar date );
        public void onDatePickerFragmentCancelButton( DialogFragment dialog );
    }
}