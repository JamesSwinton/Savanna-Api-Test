package com.zebra.jamesswinton.savannaapitest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

  // Debugging
  private static final String TAG = "HomeActivity";

  // Constants


  // Static Variables


  // Non-Static Variables


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    Intent lookupActivity = new Intent(this, BarcodeLookupActivity.class);
    startActivity(lookupActivity);
  }
}
