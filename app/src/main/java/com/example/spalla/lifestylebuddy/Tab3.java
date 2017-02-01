package com.example.spalla.lifestylebuddy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab3 extends Fragment {

    Datarecipe datarecipe = null;
    private View myFragmentView;

    TextView labelLunch, labelDinner, urlDinner, urlLunch;
    Button SHAKE;
    static ImageView lunchimage, dinnerimage;
    ProgressDialog progressdialog;

    private receiveRecipe mAuthTask = null;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        myFragmentView = inflater.inflate(R.layout.tab3, container, false);
        //I'm taking the instance of Datalogin
        datarecipe = datarecipe.getInstance();

        // Creo la textView dove inserirò la ragione sociale del Locale---------------
        lunchimage = (ImageView) myFragmentView.findViewById(R.id.imageLunch);
        dinnerimage = (ImageView) myFragmentView.findViewById(R.id.imageDinner);

        /*new DownloadImageTask((ImageView) myFragmentView.findViewById(R.id.imageLunch))
                .execute(datarecipe.getImagel());
        new DownloadImageTask((ImageView) myFragmentView.findViewById(R.id.imageDinner))
                .execute(datarecipe.getImaged());*/

        // Creo la textView dove inserirò la ragione sociale del Locale---------------
        labelLunch = (TextView) myFragmentView.findViewById(R.id.labelLunch);
        labelLunch.setText(datarecipe.getLabell());
        labelDinner = (TextView) myFragmentView.findViewById(R.id.labelDinner);
        labelDinner.setText(datarecipe.getLabeld());
        urlLunch = (TextView) myFragmentView.findViewById(R.id.urllunch);
        urlLunch.setText(datarecipe.getUrll());
        urlDinner = (TextView) myFragmentView.findViewById(R.id.urlDinner);
        urlDinner.setText(datarecipe.getUrld());


        // Creo il bottone cerca con il quale andrò a cercare le consegne-------------
        SHAKE = (Button) myFragmentView.findViewById(R.id.shake_button);
        SHAKE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new receiveRecipe().execute();
                progressdialog = ProgressDialog.show(getActivity(), "",
                        "Searching for Recipes...", true);
            }
        });


        return myFragmentView;
    }


    /*public void FillJson() {

        String result;
        String edit = editmeasure.getText().toString();
        result = edit.replaceAll(" ", "");
        String scelta = dropdown.getSelectedItem().toString();

        //Prepare hash map for Json
        HashMap params = new HashMap();
        params.put(new String("type"), scelta);
        params.put(new String("value"), result);
        params.put(new String("valueType"), "double");

        //convert parameters into JSON object

        JSONObject holder = new JSONObject(params);
        System.out.println(holder);


    }*/

    public class receiveRecipe extends AsyncTask<String,String,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {

            String result = "";

            // TODO: attempt authentication against a network service.

            String ENDPOINT = "https://fathomless-beyond-31009.herokuapp.com/introsde/blogic/recipe";
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(ENDPOINT);
            String json = "";
            JSONObject jObj = null;
            try{
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if(statusCode == 200){
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                        //System.out.println(line);
                        json = builder.toString();
                    }

                    result = "ok";
                } else {
                    Log.e(MainActivity.class.toString(),"Failed JSON object");

                    //MOSTRARE MESSAGGIO "nessuna ricetta disponibile al momento"
                    progressdialog.dismiss();
                    return false;
                }
            }catch(ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

            try {
                jObj = new JSONObject(json);
                System.out.println(jObj);

                datarecipe.setImagel(jObj.getJSONObject("recipe").getJSONObject("lunchRecipe").getString("image"));
                datarecipe.setLabell(jObj.getJSONObject("recipe").getJSONObject("lunchRecipe").getString("label"));
                datarecipe.setUrll(jObj.getJSONObject("recipe").getJSONObject("lunchRecipe").getString("url"));

                datarecipe.setImaged(jObj.getJSONObject("recipe").getJSONObject("dinnerRecipe").getString("image"));
                datarecipe.setLabeld(jObj.getJSONObject("recipe").getJSONObject("dinnerRecipe").getString("label"));
                datarecipe.setUrld(jObj.getJSONObject("recipe").getJSONObject("dinnerRecipe").getString("url"));

                System.out.println(datarecipe.getImaged());
                System.out.println(datarecipe.getLabell());



            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {


            if(success){
                progressdialog.dismiss();
                createToastOK();

            }else{
                createToastNO();
            }


        }
    }


    public void createToastNO(){
        Toast.makeText(getActivity(), "NO RECIPES ARE READY! TRY AGAIN!",
                Toast.LENGTH_LONG).show();

    }
    public void createToastOK() {

        // show The Image in a ImageView
        new DownloadImageTask((ImageView) myFragmentView.findViewById(R.id.imageLunch))
                .execute(datarecipe.getImagel());
        new DownloadImageTask((ImageView) myFragmentView.findViewById(R.id.imageDinner))
                .execute(datarecipe.getImaged());

        labelLunch.setText(datarecipe.getLabell());
        labelDinner.setText(datarecipe.getLabeld());
        urlLunch.setText(datarecipe.getUrll());
        urlDinner.setText(datarecipe.getUrld());

    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }





}