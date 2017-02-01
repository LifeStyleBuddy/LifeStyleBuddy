package com.example.spalla.lifestylebuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab4 extends Fragment implements View.OnClickListener{

    Datalogin Datalogin = null;
    private View myFragmentView;
    private Button buttonStartTime, buttonStopTime;
    private EditText edtTimerValue;
    private TextView textViewShowTime; // will show the time
    private CountDownTimer countDownTimer; // built in android class
    // CountDownTimer
    private long totalTimeCountInMilliseconds; // total count down time in
    // milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    TextView lastwtime;
    EditText workfor;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        myFragmentView = inflater.inflate(R.layout.tab4, container, false);

        Datalogin = Datalogin.getInstance();

        buttonStartTime = (Button) myFragmentView.findViewById(R.id.btnStartTime);
        buttonStopTime = (Button) myFragmentView.findViewById(R.id.btnStopTime);
        textViewShowTime = (TextView) myFragmentView.findViewById(R.id.tvTimeCount);
        edtTimerValue = (EditText) myFragmentView.findViewById(R.id.edtTimerValue);

        lastwtime = (TextView) myFragmentView.findViewById(R.id.lastwtime);
        lastwtime.setText(Datalogin.getWtime());
        workfor = (EditText) myFragmentView.findViewById(R.id.edtTimerValue);

        String b = Datalogin.getWtime();

        if(b.isEmpty()){
            b = "0";
        }
        int a = Integer.parseInt(b);
        a = a + 5;
        String s = String.valueOf(a).toString();
        workfor.setText(s);

        buttonStartTime.setOnClickListener(this);
        buttonStopTime.setOnClickListener(this);

        return myFragmentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStartTime) {
            textViewShowTime.setTextAppearance(getActivity().getApplicationContext(),
                    R.style.normalText);
            setTimer();
            buttonStopTime.setVisibility(View.VISIBLE);
            buttonStartTime.setVisibility(View.GONE);
            edtTimerValue.setVisibility(View.GONE);
            edtTimerValue.setText("");
            startTimer();


            new DownloadImageTask((ImageView) myFragmentView.findViewById(R.id.imagemot))
                    .execute(Datalogin.getPicture_url());

        } else if (v.getId() == R.id.btnStopTime) {
            countDownTimer.cancel();
            buttonStartTime.setVisibility(View.VISIBLE);
            buttonStopTime.setVisibility(View.GONE);
            edtTimerValue.setVisibility(View.VISIBLE);
        }
    }

    private void setTimer() {
        int time = 0;
        if (!edtTimerValue.getText().toString().equals("")) {
            time = Integer.parseInt(edtTimerValue.getText().toString());
        } else
            Toast.makeText(getActivity(), "Please Enter Minutes...",
                    Toast.LENGTH_LONG).show();

        totalTimeCountInMilliseconds = 60 * time * 1000;

        timeBlinkInMilliseconds = 30 * 1000;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime.setTextAppearance(getActivity().getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                        textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                textViewShowTime.setText("Time up!");
                textViewShowTime.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.GONE);
                edtTimerValue.setVisibility(View.VISIBLE);
            }

        }.start();

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