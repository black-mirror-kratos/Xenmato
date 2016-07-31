package com.citrix.xen.xenfood;

/**
 * Created by pawankumard on 7/9/2016.
 */
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CaloriesCalcFragment extends Fragment {

    private static TextView calories;
    private ImageView calculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calories_calc_view, container, false);
        calories = (TextView) view.findViewById(R.id.calories_count_calc);
        calculate = (ImageView) view.findViewById(R.id.calories_icon_calc);


        return view;
    }

    public static void MyCaloriesClickMethod(View view){
        //Log.d("sdf","hue");
        calories.setText("" + InfoAdapter.totalCalories);
        //calories.setBackgroundColor(Color.BLUE);
    }
    public static void MyResetClickMethod(View view){
        //Log.d("sdf","hue");
        InfoAdapter.totalCalories = 0;
        calories.setText("" + 0);
        //calories.setBackgroundColor(Color.BLUE);
    }

}
