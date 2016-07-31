package com.citrix.xen.xenfood;

/**
 * Created by pawankumard on 7/12/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

//**** 1
public class MessagesFragment extends Fragment {


    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.messages, container, false);

        TextView profile_label=(TextView)rootView.findViewById(R.id.profile_label);
        profile_label.setText("FoodNinja");

        RatingBar profile_rating=(RatingBar) rootView.findViewById(R.id.profile_rating);
        profile_rating.setNumStars(5);
        profile_rating.setRating(3.5f);


        //****  2_begin

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       sendEmail();
                                   }
                               }
        );

        // Inflate the layout for this fragment
        return rootView;  //(Your view)
    }


    protected void sendEmail() {

        String[] TO = {"food_committee@citrix.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            getActivity().finish();
        }
        catch (android.content.ActivityNotFoundException ex) {

        }
    }
    //**** 2_end

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}