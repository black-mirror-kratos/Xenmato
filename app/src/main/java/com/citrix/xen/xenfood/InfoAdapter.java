package com.citrix.xen.xenfood;

/**
 * Created by pawankumard on 7/8/2016.
 */
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ContactViewHolder> {

    public static int totalCalories = 0;

    private List<Info> contactList;

    public InfoAdapter(List<Info> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        final int j = i;
        final Info contact = contactList.get(i);
        if(!contact.clicked) {
            contactViewHolder.vCaloriesIcon.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            contactViewHolder.vCaloriesIcon.setBackgroundColor(Color.parseColor("#8800ff"));
        }
        contactViewHolder.vCaloriesIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event
                Log.d("here","cliked!" + j);
                if(contact.clicked == true){
                    contactViewHolder.vCaloriesIcon.setBackgroundColor(Color.parseColor("#00000000"));
                    contact.clicked = false;
                    totalCalories -= Integer.parseInt(contactViewHolder.vCaloriesCount.getText().toString());
                }
                else{
                    contact.clicked = true;
                    contactViewHolder.vCaloriesIcon.setBackgroundColor(Color.parseColor("#8800ff"));
                    totalCalories += Integer.parseInt(contactViewHolder.vCaloriesCount.getText().toString());
                    Log.d("here","" + totalCalories);
                    //contactViewHolder.vCaloriesCount.setVisibility(View.INVISIBLE);
                }

            }
        });

        final Info ci = contactList.get(i);
        contactViewHolder.vName.setText(ci.name);
        contactViewHolder.vDetails.setText(ci.details);
        contactViewHolder.vRating.setText(ci.rating);
        contactViewHolder.vCaloriesCount.setText(ci.calories);
        //if(contactViewHolder.vPhoto.getDrawable() == R.id)
        contactViewHolder.vPhoto.setImageBitmap(ci.bitmap);

        contactViewHolder.vName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event
                Log.d("here","cliked!");
                Intent intent = new Intent(v.getContext(), RatingActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", ci.id); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            }
        });

        if(Double.valueOf(ci.rating) <= 2.5){
            contactViewHolder.vRating.setBackgroundColor(Color.parseColor("#C9960C"));
            //contactViewHolder.vRating.setAlpha(0.5f);
            //contactViewHolder.vRating.setColorFilter(Color.rgb(180, 180, 180), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        else{
            contactViewHolder.vRating.setBackgroundColor(Color.parseColor("#147700"));
        }
        if(Double.valueOf(ci.rating) >= 4.5){
            contactViewHolder.vBadge.setVisibility(View.VISIBLE);
        }
        else{
            contactViewHolder.vBadge.setVisibility(View.INVISIBLE);
        }

        if(Double.valueOf(ci.calories) >= 200){
            contactViewHolder.vCaloriesLabel.setVisibility(View.VISIBLE);
        }
        else{
            contactViewHolder.vCaloriesLabel.setVisibility(View.INVISIBLE);
            //contactViewHolder.vBadge.setVisibility(View.INVISIBLE);
        }

        contactViewHolder.vPhoto.setColorFilter(Color.rgb(180, 180, 235), android.graphics.PorterDuff.Mode.MULTIPLY);
        //requestAsyncTask(ci.urlString);
        //.vBadge.setColorFilter(Color.rgb(255, 0, 0), android.graphics.PorterDuff.Mode.MULTIPLY);
        //contactViewHolder.vTitle.setText(ci.name + " " + ci.surname);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vDetails;
        protected TextView vRating;
        protected ImageView vPhoto;
        protected ImageView vBadge;
        protected TextView vCaloriesCount;
        protected TextView vCaloriesLabel;
        protected ImageView vCaloriesIcon;
        protected boolean clicked = false;

        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vDetails = (TextView)  v.findViewById(R.id.details);
            vRating = (TextView)  v.findViewById(R.id.rating);
            vPhoto = (ImageView) v.findViewById(R.id.photo);
            vBadge = (ImageView) v.findViewById(R.id.badge);
            vCaloriesCount = (TextView)  v.findViewById(R.id.calories_count);
            vCaloriesLabel = (TextView)  v.findViewById(R.id.calories_label);
            vCaloriesIcon = (ImageView) v.findViewById(R.id.calories_icon);
        }

       /* @Override
        public void onClick(View view) {
            Log.d("recycler onclick test", "pawan");
        }*/
    }


    /*public void requestAsyncTask(String url){
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
    }*/

}
