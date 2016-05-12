package com.synchron.ncpl.synchron;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by NCPL on 4/7/2016.
 */
public class AgendaFragment extends Fragment {
    String agenda,startTme,endTime;
    TextView txt_agenda,txt_startTime,txt_endTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agenda_fragment, container, false);
        agenda=getArguments().getString("event_agenda");
        startTme = getArguments().getString("event_startTime");
        endTime = getArguments().getString("event_endTime");

        //Toast.makeText(getActivity(),agenda, Toast.LENGTH_SHORT).show();

        txt_agenda = (TextView) view.findViewById(R.id.txt_agenda);
        txt_startTime = (TextView)view.findViewById(R.id.txt_date);
        txt_endTime = (TextView)view.findViewById(R.id.txt_time);
        txt_agenda.setText(agenda);
        txt_startTime.setText(startTme);
        txt_endTime.setText(endTime);

        return view;
    }
}
