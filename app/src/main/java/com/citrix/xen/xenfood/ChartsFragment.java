package com.citrix.xen.xenfood;

/**
 * Created by pawankumard on 7/9/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class ChartsFragment extends Fragment {
    LineGraphSeries<DataPoint> series;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.charts_view, container, false);

        AsyncHttpClient client = new AsyncHttpClient();
        String item_url = "http://10.252.249.82:3000/wastes.json";

        client.get(item_url,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                int  arr_length = response.length();
                try {
                    GraphView graph = (GraphView) view.findViewById(R.id.graph);
                    series = new LineGraphSeries<DataPoint>();
                    for (int i = 0; i < arr_length; ++i) {
                        JSONObject temp = response.getJSONObject(i);
                        String date = temp.getString("r_date");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                        Date convertedDate = sdf.parse(date);
                        int amount = temp.getInt("amount");

                        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                        String display = df.format(convertedDate);
                        Log.d("ConvertedDate",display);
                        System.out.println("amount = " + amount);
                        System.out.println("i value "+ i);
                        series.appendData(new DataPoint(convertedDate, amount), true, 10);
                    }

                    graph.addSeries(series);
                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getContext()));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(7);
                    graph.getGridLabelRenderer().setLabelHorizontalHeight(1);
                    graph.getGridLabelRenderer().setTextSize(10);
                    graph.getGridLabelRenderer().setHorizontalAxisTitle("Date");
                    graph.getGridLabelRenderer().setVerticalAxisTitle("Wastage of food(kg)");
                    graph.getGridLabelRenderer().setHumanRounding(false);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        return view;
    }
}
