package com.example.progettoium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private Calendar date;
    private int caso;
    public DatePicker datePicker;
    //creo il listener (vedi metodi interfaccia)
    private DatePickerFragmentListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
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

        if (getCaso() == 0){
            vista = inflater.inflate(R.layout.departure_picker,null);
            datePicker = vista.findViewById(R.id.datePicker1); //come costruttore prende il contesto
        }else {
            vista = inflater.inflate(R.layout.landing_picker,null);
            datePicker = vista.findViewById(R.id.datePicker2); //come costruttore prende il contesto
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //è il dialog (la finestra vuota) su cui
        // mettere il datepicker, contiene anche i bottoni

        builder.setView(vista); //qua settiamo il datepicker all'interno della finestra vuota

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //quando premiamo ok stiamo assegnando a date
                date.set(Calendar.YEAR, datePicker.getYear()); //mese, giorno e anno selezionato
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
    //interazione con il picker
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
        //calendarView.setDate(da);
    }

    public void setOnDatePickerFragmentChanged(DatePickerFragmentListener l){
        this.listener = l; //questa funzione viene chiamata nella main activity per inizializzare l'attributo listener
        //nella classe DatePickerFragment
    }

    public int getCaso() {
        return caso;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }

    //stiamo creando noi degli handle, interfaccia con i metodi che gestiranno gli eventi
    public interface DatePickerFragmentListener{
        public void onDatePickerFragmentOkButton( DialogFragment dialog, Calendar date );
        public void onDatePickerFragmentCancelButton( DialogFragment dialog );
    }
}