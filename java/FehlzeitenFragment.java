package ml.mia20.mia20app.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import ml.mia20.mia20app.R;

public class FehlzeitenFragment extends Fragment {
    private String[] arraySpinner;

    public FehlzeitenFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_navailable, container, false);


//
//        String [] values =
//                {"Mathe","Deutsch","Englisch","1-2 years","2-4 years","4-8 years","8-15 years","Over 15 years",};
//        Spinner spinner = (Spinner) v.findViewById(R.id.stunden2);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);
        return v;



    }
}
