package com.xbpsolutions.ceslauncher.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.xbpsolutions.ceslauncher.R;


public class LauncherActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    getSupportActionBar().hide();


  }
}