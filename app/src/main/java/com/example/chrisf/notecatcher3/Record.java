package com.example.chrisf.notecatcher3;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.util.Iterator;

public class Record extends AppCompatActivity {

    private ImageButton record;

    AudioRecord audioRec;
    Thread recThread;
    float pitch;

    private static final int RECORDER_SAMPLERATE = 8000;
    private final int duration = 1;
    private final int numSamples = duration * RECORDER_SAMPLERATE;
    private double sample[] = new double[numSamples];
    private final byte generatedSnd[] = new byte[2 * numSamples];
    private AudioTrack audioTrack;
    ArrayList<Float> listPitch = new ArrayList<Float>();
    HashSet<String> hash1;
    HashSet<String> hash2;
    ArrayList<String> list1;
    ArrayList<String> list2;

    TextView pitches;
    String file_contents;

    //for real-time...
    int bufferRec = 1024;
    int bytesPerElement = 2;

    private String pathForNotes = "/"+Environment.getExternalStorageDirectory().getPath()+"/1.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        list1 = new ArrayList<String>();
        list2 = new ArrayList<String>();
        hash2 = new HashSet<String>();
        /*File happyBday = new File("/"Environment.getExternalStorageDirectory().getPath()+"/HappyBirthday.txt");//// FIXME: 12/1/16
        if (!f.exists() && f.isDirectory()) {
            String contents = "D4 E4 F#4 G4 ";
            createFile(contents);
        }*/
        File notesCaught = new File(pathForNotes);//// FIXME: 12/1/16
        if (!notesCaught.exists() && notesCaught.isDirectory()) {
            String contents = "";
            createFile(contents);
            // TODO: 12/1/16 call a intent to show the caught notes??? 
        }
        //file_contents = readFile();
        //System.out.println("FILE CONTENTS :  " + file_contents);
       /* String[] notes = file_contents.split(" ");
        for (String note : notes) {
            list2.add(note);
        }*/


        hash1 = new HashSet<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, duration * RECORDER_SAMPLERATE,
                AudioTrack.MODE_STREAM);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pitches = (TextView) findViewById(R.id.pitches);
        pitches.setText(String.valueOf(pitch));

        final int bufferSize = AudioRecord.getMinBufferSize(40000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioRec = new AudioRecord(MediaRecorder.AudioSource.MIC, 40000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        record = (ImageButton) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            int i = 1;

            @Override
            public void onClick(View v) {
                if (i % 2 == 0) {
                    record.setBackgroundResource(android.R.drawable.presence_audio_online);
                    stopRecord(v);
                    //genTone(listPitch);
                    //savedMenu(v);
                    addNote(hash1);
                    Intent i = new Intent(Record.this, ListSong.class);
                    startActivity(i);//// TODO: 12/1/16 jump to a new page showing what have captured???

                } else {
                    if (audioRec.getState() == AudioRecord.STATE_INITIALIZED) {
                        audioRec.setPositionNotificationPeriod(44100 / 2); // should make sure the buffer is a multiple of this

                        audioRec.setRecordPositionUpdateListener(
                                new AudioRecord.OnRecordPositionUpdateListener() {
                                    public void onPeriodicNotification(final AudioRecord recorder) {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                double lhalfSteps = getHalfStepsFromA4((float) pitch);
                                                String lout = halfStepsToString(lhalfSteps);
                                                hash1.add(lout);
                                                listPitch.add(pitch);
                                                pitches.setText(lout);
                                                //compare(lout, list2);
                                            }
                                        });
                                    }
                                    public void onMarkerReached(AudioRecord recorder) {
                                        //Log.d(_tag, "onMarkerReached");
                                    }
                                }
                        );
                    }
                    Toast.makeText(getApplicationContext(), "Recording..",
                            Toast.LENGTH_SHORT).show();
                    record.setBackgroundResource(android.R.drawable.presence_audio_busy);
                }
                i++;
            }
        });

    }

    public void createFile(String contents) {
        FileWriter fWriter;
        try {
            fWriter = new FileWriter(pathForNotes, true);
            fWriter.write(contents);
            fWriter.flush();
            fWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNote(HashSet notes) {
        FileWriter fWriter;
        try {
            fWriter = new FileWriter(pathForNotes, true);
            fWriter.write(convertToString(notes));
            fWriter.flush();
            fWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertToString(HashSet set){
        Iterator iter = set.iterator();
        String res = "";
        while(iter.hasNext()){
            res += iter.next().toString()+" ";
        }
        System.out.println(res);
        return res.trim();
    }

    public double getHalfStepsFromA4(float aFrequency) {
        return 12 * (log(aFrequency / 440.0, 2));
    }

    public String halfStepsToString(double aHalfStepsFromA4) {
        String retval = "";

        int lintHalfStepsFromC0 = (int) aHalfStepsFromA4 + 57;

        int loctave = (lintHalfStepsFromC0 / 12);

        int lnoteInOctave = lintHalfStepsFromC0 % 12;

        switch (lnoteInOctave) {
            case 0:
                retval += "C";
                break;
            case 1:
                retval += "C#";
                break;
            case 2:
                retval += "D";
                break;
            case 3:
                retval += "D#";
                break;
            case 4:
                retval += "E";
                break;
            case 5:
                retval += "F";
                break;
            case 6:
                retval += "F#";
                break;
            case 7:
                retval += "G";
                break;
            case 8:
                retval += "G#";
                break;
            case 9:
                retval += "A";
                break;
            case 10:
                retval += "A#";
                break;
            case 11:
                retval += "B";
                break;
        }
        retval = String.format("%s%d", retval, loctave);
        return retval;
    }

    static double log(double x, double base) {
        return (Math.log(x) / Math.log(base));
    }

    protected void onResume() {
        super.onResume();
        if (audioRec.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRec.startRecording();

            Thread lrecorder = new Thread(new Runnable() {
                public void run() {
                    int lcounter = 0;
                    while (audioRec.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED) {
                        final short[] lshortArray = new short[bufferRec / 2];

                        audioRec.read(lshortArray, 0, bufferRec / 2);

                        if (lcounter == 0) {
                            Yin lyin = new Yin(audioRec.getSampleRate());
                            double linstantaneousPitch = lyin.getPitch(lshortArray);
                            if (linstantaneousPitch >= 0) {
                                pitch = (float) ((2.0 * pitch + linstantaneousPitch) / 3.0);
                            }
                        }
                        lcounter = (lcounter + 1) % 5;
                    }
                }
            });
            lrecorder.start();
        }
    }

    public void stopRecord(View v) {
        if (audioRec != null) {
            audioRec.stop();
            audioRec.release();
            recThread = null;
        }

        Toast.makeText(getApplicationContext(), "Finishing..",
                Toast.LENGTH_SHORT).show();
    }

    /*public boolean compare(String s, ArrayList<String> set2) {
        int count;
        if (set2.contains(s)) {
            for (int i = set2.size() - 1; i >= 0; i--) {
                if (set2.get(i).equals(s)) {
                    count = i;
                    set2.remove(count);
                }
            }
        }
        String contents = "";
        for (int j = 0; j < set2.size(); j++) {
            contents = contents + set2.get(j) + " ";
        }
        createFile(contents);
        String results = readFile();
        //System.out.println(results);
        if (set2.isEmpty()) {
            return true;
        }
        return false;
    }*/
}

