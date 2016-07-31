package com.citrix.xen.xenfood;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.tv.TvInputService;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by pawankumard on 7/9/2016.
 */
public class SplashLogin extends Activity{

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this);
        setContentView(R.layout.splash_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        profilepic = (ImageView) findViewById(R.id.profilepic);

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(getApplicationContext(), "in Success", Toast.LENGTH_SHORT).show();
                //setContentView(R.layout.activity_second);
                /*welcomenote.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/

                //userID = loginResult.getAccessToken().getUserId();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //Log.d("LoginActivity", response.toString());
                                /*try {
                                    Log.d("TAG", object.toString());
                                    userName = object.getString("name");
                                    username.setText(object.getString("name"));
                                    gender = object.getString("gender");
                                    email = object.getString("email");
                                    //Log.d("TAG", userID + ", " + userName + ", " + email + ", " + birthday);
                                    //requestAsyncTask();
                                } catch (JSONException e) {
                                    //requestAsyncTask();
                                    e.printStackTrace();
                                }*/
                                // Application code
                                Intent intent = new Intent(SplashLogin.this, Tabs.class);
                                startActivity(intent);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email, birthday ,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login attempt canceled.", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Login attempt failed.", Toast.LENGTH_SHORT);
            }
        });


        //Log.d("fff", "" + isLoggedIn());

        //Toast.makeText(getApplicationContext(),"inint null",Toast.LENGTH_SHORT);
        if(isLoggedIn()){
            //Log.d("fff1", "" + isLoggedIn());
            Intent intent = new Intent(SplashLogin.this, Tabs.class);
            startActivity(intent);
        }
        else{
            //Log.d("fff2", "" + isLoggedIn());
        }


        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.citrix.xen.xenfood",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }*/

    }


    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
