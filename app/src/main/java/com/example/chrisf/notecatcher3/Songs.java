/*package com.example.chrisf.notecatcher3;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by jiaqi on 12/1/16.


public class Songs extends AppCompatActivity {
    final float noteD=293.7f;
    final float noteE=329.6f;
    final float noteF=370.0f;
    final float noteG=392.0f;
    final float noteA=440.0f;
    final float noteB=493.9f;
    final float noteC5=523.3f;
    final float noteD5=587.3f;
    protected String[] notesList;
    //// TODO: 12/1/16 consider making it a hashmap???
    ArrayList<Float> theTune;
    private static final int RECORDER_SAMPLERATE = 8000;
    private final int duration = 1;
    private final int numSamples=duration * RECORDER_SAMPLERATE;
    private double sample[]=new double[numSamples];
    private final byte generatedSnd[] = new byte[2 * numSamples];
    private AudioTrack audioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_birthday);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT, duration*RECORDER_SAMPLERATE,
                AudioTrack.MODE_STREAM);
        setSupportActionBar(toolbar);

        //birthdaySong=new ArrayList<Float>();
        //createBirthdaySong();

        final boolean readyToPlay = compareNset();

        ImageButton play = (ImageButton) findViewById(R.id.playSong);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(readyToPlay){
                    playSound();
                    genTone(theTune);
                }
                else{
                    Toast.makeText(getApplicationContext(), "You haven't collected them all yet, keep working!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean compareNset(){
        //compare what we caught with what we need
        //check the corresponding boxes
        //and decided if good to go!
        boolean go = true;
        String whatWeHave = readFile();

        for(String note : notesList){
            String noteN = note.replace('#', 's');
            CheckBox box = (CheckBox) findViewById(getResources().getIdentifier("note_"+noteN, "id", this.getPackageName()));
            if(whatWeHave.contains(note))
                box.setChecked(true);
            else {
                box.setChecked(false);
                go = false;
            }
        }
        return go;
    }

    public String readFile() {
        char buf[] = new char[512];
        FileReader rdr;
        String contents = "";
        try {
            rdr = new FileReader("/"+ Environment.getExternalStorageDirectory().getPath()+"/NotesCuaght.txt");
            int s = rdr.read(buf);
            for (int k = 0; k < s; k++) {
                contents += buf[k];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }


    public void genTone(ArrayList<Float> pitchList){
        for (float pitch: pitchList){
            for (int i = 0; i < numSamples; ++i) {
                sample[i] = Math.sin(2 * Math.PI * i / (RECORDER_SAMPLERATE/pitch));
            }
            int idx = 0;
            for (double dVal : sample) {
                short val = (short) (dVal * 32767);
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }
            audioTrack.write(generatedSnd, 0, numSamples);
        }
    }

    void playSound(){
        audioTrack.play();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                Intent i = new Intent(HappyBirthday.this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.action_backTune:
                Intent g = new Intent(HappyBirthday.this, ListSong.class);
                startActivity(g);
                return true;
            case R.id.action_backRecord:
                Intent m = new Intent(HappyBirthday.this, Record.class);
                startActivity(m);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
*/