#NoteCatcher

class project for cosi153
Team BandBangBang
Members: Christina Feng, Jiaqi Li, Shuangchen Shen, Qifu Yin

Important Note: Our application only works well for API under 23 for the use of AudioRecord.

Introduction
The application will allow user to record any sound/note in daily life to match a set of notes and use them to form a tune. The idea is to find as many interesting sounds as possible to form several amazing musical combination.
The application will include an icon for user to record whatever the sound they want. After pressing the record button, users will record and save a short clip of audio and the application will automatically detect their pitches and generate several notes from the clip. This will be the main feature of this application. Any note that has been detected will be automatically saved to the SD card. Once all the notes required by a piece of music have been detected and saved, the user will be able to play the music.
Our goal is to encourage people to go outside, discover and catch any interesting sound in their real life to formulate a song. We also aim to motivate people who does not sing well to practice singing and become a better singer. These two kinds of people will become our potential users.

1 AudioRecord
In order to achieve our ultimate goal, the first step is to record a clip and we used AudioRecord to do that. The AudioRecord class manages the audio resources for Java
applications to record audio from the audio input hardware of the platform. This is achieved by ”pulling” (reading) the data from the AudioRecord object. The application is responsible for polling the AudioRecord object in time using one of the following three methods: read(byte[], int, int), read(short[], int, int) or read(ByteBuffer, int).

2 Pitch Detection
Pitch detection is an important part of our project in which case we need to analyze the clip that we have recorded and detect the pitches from the clip. The tricky part of pitch detection is to change the time domain to frequency domain, which could be solved by fast Fourier Transform. We adopted Yin’s algorithm as an external resource for pitch detection.

3 AudioTrack
From AudioRecord, we could use read method to store the data for a pitch. In order to playback the pitch, AudioTrack is needed. The AudioTrack class manages and plays a single audio resource for Java applications. It allows streaming of PCM audio buffers to the audio sink for playback. In our project, we use AudioTrack.write(byte[],int, int) method to audio sink to playback the audio we want. In our projects, we store each note as our audio data to a byte array and call write(byte[],int, int) on it.

4 Pitch Conversion and Comparision
While we are recording, we are detecting pitches/notes at the same time. The pitch we detect is a float which will later be converted into a character notes, e.g. D4, F5. The character notes will be shown on the screen and we compare the note with the notes within the song. If the note we detect also belongs to part of the song, then it means that we have found one note of the song. If all the notes of the song are captured by our recordings, then the song will be played.

5 Digit sounds
The application will simulate digital sounds based on the notes on the sheet music. One frequency will become one digital sound. After combining all the notes on the sheet music , it will become a piece of digital music.

6 Future improvement
1. Print out singing notes, then help singers to compare their singing accuracy with music score. 2. Limit the music storage. Feature to download music and save their scores. 3. Extract recorded sound clips according to their notes. Concatenate the clips to play specific songs.
