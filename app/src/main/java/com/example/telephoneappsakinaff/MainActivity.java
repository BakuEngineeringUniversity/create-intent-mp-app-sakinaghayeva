package com.example.telephoneappsakinaff;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button callButton = findViewById(R.id.callButton);
        TextView linkTextView = findViewById(R.id.universityLink);
        Button playMusicButton = findViewById(R.id.playMusicButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music1);

        // Zəng funksionallığı
        callButton.setOnClickListener(v -> {
            String phoneNumber = "+994556427815";
            EditText input = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Zəng Kodu Daxil Edin")
                    .setView(input)
                    .setPositiveButton("Zəng", (dialog, which) -> {
                        if (input.getText().toString().equals("1234")) {
                            makePhoneCall(phoneNumber);
                        } else {
                            Toast.makeText(this, "Yanlış kod!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Ləğv et", null)
                    .show();
        });

        // Universitet saytı üçün link
        linkTextView.setOnClickListener(v -> {
            String url = "https://beu.edu.az/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        // Musiqi çalma funksionallığı
        playMusicButton.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    Toast.makeText(this, "Musiqi başladı", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.pause();
                    Toast.makeText(this, "Musiqi dayandı", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Zəng icazəsi verildi. Yenidən cəhd edin.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Zəng icazəsi rədd edildi!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
