package com.example.spalla.lifestylebuddy;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
public class Tab2 extends Fragment {

    Datalogin Datalogin = null;
    private View myFragmentView;
    private static final String[] paths = {"weight", "sleep", "wtime", "steps"};

    TextView name, lastname, height, weight, steps, sleep, wtime, hrate;
    Button SUBMIT , IMAGEBUTTON;
    static EditText editmeasure;
    static Spinner dropdown;
    ProgressDialog progressdialog;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        myFragmentView = inflater.inflate(R.layout.tab2, container, false);
        //I'm taking the instance of Datalogin
        Datalogin = Datalogin.getInstance();

        dropdown = (Spinner) myFragmentView.findViewById(R.id.Spinner01);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dropdown.setAdapter(adapter);

        // Creo la textView dove inserirò la ragione sociale del Locale---------------
        editmeasure = (EditText) myFragmentView.findViewById(R.id.ETvalue);


        // Creo la textView dove inserirò la ragione sociale del Locale---------------

        weight = (TextView) myFragmentView.findViewById(R.id.weightvalue);
        weight.setText(Datalogin.getWeightg());

        steps = (TextView) myFragmentView.findViewById(R.id.stepsvalue);
        steps.setText(Datalogin.getStepsg());

        sleep = (TextView) myFragmentView.findViewById(R.id.sleepvalue);
        sleep.setText(Datalogin.getSleepg());

        wtime = (TextView) myFragmentView.findViewById(R.id.walkvalue);
        wtime.setText(Datalogin.getWtimeg());

        hrate = (TextView) myFragmentView.findViewById(R.id.heartratevalue);
        hrate.setText(Datalogin.getHrateg());

        // Creo il bottone cerca con il quale andrò a cercare le consegne-------------
        SUBMIT = (Button) myFragmentView.findViewById(R.id.measuremod);
        SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result;
                String edit = editmeasure.getText().toString();
                result = edit.replaceAll(" ", "");
                if(result.equals("")){

                    Toast.makeText(getActivity(), "Please insert a value!",
                            Toast.LENGTH_SHORT).show();

                }else {
                    FillJson();
                    progressdialog = ProgressDialog.show(getActivity(), "",
                            "Setting new Goal...", true);
                }
            }
        });


        return myFragmentView;
    }


    public void FillJson() {

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
        new AsyncT().execute(String.valueOf(holder));


    }

    class AsyncT extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String result = "";

            try{

                URI uri = new URI("https://evening-plateau-90939.herokuapp.com/introsde/pcentric/newGoal/" + Datalogin.getIdPerson());
                URL url = uri.toURL();
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                //set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
                // json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
                //input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    System.out.println("Stream vuoto! My god");
                    return null;
                }else {
                    //Stampo il Json di risposta:
                    JsonResponse = buffer.toString();
                    Log.i("JsonResponse: ",JsonResponse);



                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Errore: ", "Errore nel chiudere il reader/stream", e);
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            progressdialog.dismiss();
            createToast();

        }
    }

    public void createToast(){
        Toast.makeText(getActivity(), "Goal correctly updated!",
                Toast.LENGTH_LONG).show();

    }



}