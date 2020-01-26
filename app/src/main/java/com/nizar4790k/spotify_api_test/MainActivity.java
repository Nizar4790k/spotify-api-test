package com.nizar4790k.spotify_api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nizar4790k.spotify_api_test.connectors.SongService;
import com.nizar4790k.spotify_api_test.model.Song;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private TextView userView;
        private TextView songView;
        private Button addBtn;
        private Song song;

        private SongService songService;
        private ArrayList<Song> recentlyPlayedTracks;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            songService = new SongService(getApplicationContext());
            userView = (TextView) findViewById(R.id.user);
            songView = (TextView) findViewById(R.id.song);
            addBtn = (Button) findViewById(R.id.add);

            SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
            userView.setText(sharedPreferences.getString("userid", "No User"));

            addBtn.setOnClickListener(addListener);
            getTracks();
        }


        private void getTracks() {
            songService.getRecentlyPlayedTracks(() -> {
                recentlyPlayedTracks = songService.getSongs();
                updateSong();
            });
        }

        private View.OnClickListener addListener = (v) -> {
        songService.addSongToLibrary(this.song);
        if (recentlyPlayedTracks.size() > 0) {
            recentlyPlayedTracks.remove(0);
        }
        updateSong();
      };

        private void updateSong() {
            if (recentlyPlayedTracks.size() > 0) {
                songView.setText(recentlyPlayedTracks.get(0).getName());
                song = recentlyPlayedTracks.get(0);
            }
        }

    }

