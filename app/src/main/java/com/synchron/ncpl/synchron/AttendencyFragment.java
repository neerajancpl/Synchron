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
public class AttendencyFragment extends Fragment {
    TextView txt_attendence,txt_status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.attendency_fragment, container, false);
        String attendence =getArguments().getString("event_attendence");
        String status =getArguments().getString("Status");
        txt_attendence= (TextView)v.findViewById(R.id.txt_number);
        txt_status= (TextView)v.findViewById(R.id.text_status);
        txt_attendence.setText(attendence);
        txt_status.setText(status);
        return v;
    }
}
