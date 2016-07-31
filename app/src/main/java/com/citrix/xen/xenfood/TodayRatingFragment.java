package com.citrix.xen.xenfood;

/**
 * Created by pawankumard on 7/9/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class TodayRatingFragment extends Fragment {
    private final String BASE_URL = "http://10.252.249.82:3000/";
    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.today_rating_view, container, false);
        AsyncHttpClient client = new AsyncHttpClient();
        String item_url = BASE_URL + "todayitems.json";

        client.get(item_url,new JsonHttpResponseHandler(){
            int arr_length = 0;
            List<String> food_names = new ArrayList<String>();
            List<Integer> calorie_list = new ArrayList<Integer>();
            List<Double> rating_list = new ArrayList<Double>();
            List<String> url_list = new ArrayList<String>();
            List<Integer> id_list = new ArrayList<Integer>();
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println(response.toString());
                arr_length = response.length();
                try{
                    for(int i=0; i < arr_length; ++i){
                        JSONObject temp = response.getJSONObject(i);
                        food_names.add(temp.getString("name"));
                        calorie_list.add(temp.getInt("calorie"));
                        double tot_rating = temp.getInt("tot_rating");
                        int tot_number = temp.getInt("tot_num");
                        rating_list.add(tot_rating/tot_number);
                        url_list.add(BASE_URL + temp.getString("foodimage"));
                        id_list.add(temp.getInt("id"));

                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

                RecyclerView recList = (RecyclerView) view.findViewById(R.id.cardList);
                recList.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recList.setLayoutManager(llm);

                InfoAdapter ca = new InfoAdapter(createList(arr_length,food_names,calorie_list,rating_list,url_list,id_list));
                recList.setAdapter(ca);

        /*recList.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Log.d("recycler onclick test", "pawan" + position);
                        Intent intent = new Intent(getActivity(), RatingActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("key", position); //Your id
                        intent.putExtras(b); //Put your id to your next Intent
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //finish();
                    }
                })
        );*/


                //view.setBackgroundColor(Color.parseColor("#8B5742"));

            }
        });

        /*RecyclerView recList = (RecyclerView) view.findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        InfoAdapter ca = new InfoAdapter(createList(10));
        recList.setAdapter(ca);*/

        return view;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private List<Info> createList(int size,List<String> name_list,List<Integer> calorie_list,List<Double> rating_list,List<String> url_list,List<Integer> id_list) {

        List<Info> result = new ArrayList<Info>();
        for (int i=0; i < size; i++) {
            Info ci = new Info();
            ci.name = name_list.get(i);
            ci.details = "";

            ci.rating = "" + String.format("%.1f", rating_list.get(i));
            int temp = calorie_list.get(i);
            ci.calories = "" + temp;
            ci.urlString = url_list.get(i);
            ci.id = id_list.get(i);
            int j = i+1;
            int id = getContext().getResources().getIdentifier("a" + j,"drawable", getContext().getPackageName());
            ci.bitmap = BitmapFactory.decodeResource(getResources(), id);
            result.add(ci);
        }
        return result;
    }
    /*private List<Info> createList(int size) {

        List<Info> result = new ArrayList<Info>();
        for (int i=0; i < size; i++) {
            Info ci = new Info();
            ci.name = i+"get it already";
            ci.details = "";

            ci.rating = "" + i;
            //int temp = calorie_list.get(i);
            ci.calories = "" + i*50;
            //ci.urlString = url_list.get(i);
            //ci.id = id_list.get(i);
            int j = i+1;
            int id = getContext().getResources().getIdentifier("a" + j,"drawable", getContext().getPackageName());
            ci.bitmap = BitmapFactory.decodeResource(getResources(), id);
            result.add(ci);
        }
        return result;
    }*/

    public void requestAsyncTask(String url){
        //Toast.makeText(getContext(), "in requestAsyncTask()", Toast.LENGTH_SHORT).show();
        //String url = "https://placehold.it/350x150";
        //Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
        GetXMLTask task = new GetXMLTask();
        task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,new String[]{url});
    }

    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            //Toast.makeText((), "in onPostExecute(Bitmap result)", Toast.LENGTH_SHORT).show();
            //result.createScaledBitmap(result, 100, 100, false);
            //welcomenote.append("Width : " + String.valueOf(result.getWidth()) + " Height : " + String.valueOf(result.getHeight()) + "Bytes : " + String.valueOf(result.getByteCount()));
            //profilepic.setImageBitmap(result);

            //global_pic_list.add(result);
            //map.put(ContactViewHolder.vName.toString(),result);
            //Log.d("imageDebug",ContactViewHolder.vName + "   " + result.toString() + "*** " + global_pic_list.size() + "###" + map.size() );



            //ContactViewHolder.vPhoto.setImageBitmap(result);
            //newParseUserSignup(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

}
