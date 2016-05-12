package com.synchron.ncpl.synchron;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by NCPL on 4/7/2016.
 */
public class SpeakerFragment extends Fragment {

    String speakerName,aboutSpeaker,speakerDesg;
    LinearLayout myLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_fragment, container, false);
        myLinearLayout = (LinearLayout)view.findViewById(R.id.layout);
        speakerName =getArguments().getString("event_speaker");
        aboutSpeaker = getArguments().getString("AboutSpeaker");
        speakerDesg = getArguments().getString("SpeakerDesg");
        String str = speakerName;
        String[] temp;

        // delimiter
        String delimiter = "sari123sari123";
        // given string will be split by the argument delimiter provided. *//**//*
        temp = str.split(delimiter);
        // print substrings
        for(int j =0; j < temp.length ; j++) {
            System.out.println(temp[j]);
        }
        String[] speakerName =temp;
        String[] speakerDes = speakerDesg.split(delimiter);
        String[] speakerabout = aboutSpeaker.split(delimiter);
        final TextView[] myTextViews = new TextView[speakerName.length];
        final TextView[] myTextViews1 = new TextView[speakerDes.length];
        final TextView[] myTextViews2 = new TextView[speakerabout.length];

        for(int i=0;i<speakerName.length;i++){

            final TextView rowTextView = new TextView(getActivity());
            final TextView rowTextView1 = new TextView(getActivity());
            final TextView rowTextView2 = new TextView(getActivity());

            rowTextView.setText(speakerName[i]);
            rowTextView1.setText(speakerDes[i]);
            rowTextView2.setText(speakerabout[i]);
            myLinearLayout.addView(rowTextView);
            myLinearLayout.addView(rowTextView1);
            myLinearLayout.addView(rowTextView2);
            myTextViews[i] = rowTextView;
            myTextViews1[i] =rowTextView1;
            myTextViews2[i] =rowTextView2;
        }


        return view;
    }
}
