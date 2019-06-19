package com.zebra.jamesswinton.savannaapitest;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.ScannerException;
import com.zebra.jamesswinton.savannaapitest.databinding.ActivityBarcodeLookupBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarcodeLookupActivity extends AppCompatActivity implements Scanner.DataListener {

  // Debugging
  private static final String TAG = "BarcodeLookupActivity";

  // Constants


  // Static Variables
  private static UpcProductAdapter mUpcProductAdapter;

  // Non-Static Variables
  private ActivityBarcodeLookupBinding mDataBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Init DataBinding
    mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_barcode_lookup);

    // Init RecyclerView
    mUpcProductAdapter = new UpcProductAdapter(this,null);
    mDataBinding.productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mDataBinding.productRecyclerView.setAdapter(mUpcProductAdapter);

    // Set Click Listener
    mDataBinding.searchButton.setOnClickListener(v -> {
      // Validate Barcode Entered
      if (TextUtils.isEmpty(mDataBinding.barcodeText.getText())) {
        Toast.makeText(this, "Please enter a barcode", Toast.LENGTH_SHORT).show();
        return;
      }

      // TODO: Trigger Update
      BarcodeLookupAPI barcodeLookupAPI = RetrofitInstance.getInstance().create(BarcodeLookupAPI.class);
      String barcode = mDataBinding.barcodeText.getText().toString().trim();
      Call<UpcProduct> barcodeLookup = barcodeLookupAPI.barcodeLookup(barcode);

      barcodeLookup.enqueue(new Callback<UpcProduct>() {
        @Override
        public void onResponse(Call<UpcProduct> call, Response<UpcProduct> response) {
          if(response.isSuccessful()) {
            Log.i(TAG, "onResponse: Successful - " + response.body().items.get(0).getTitle());

            // Handle Response
            mUpcProductAdapter.showNewProduct(response.body());
          } else {
            Log.e(TAG, "onResponse: Unsuccessful - " + response.code(), null);
          }
        }

        @Override
        public void onFailure(Call<UpcProduct> call, Throwable t) {
          Log.e(TAG, "onResponse: Unsuccessful - " + t.getMessage(), t);
        }
      });
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Enable Scanner
    mDataBinding.barcodeText.postDelayed(this::enableScanner, 100);
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Disable Scanner
    disableScanner();
  }

  @Override
  public void onData(ScanDataCollection scanDataCollection) {
    // Get Scanner Data as []
    ScanDataCollection.ScanData[] scannedData = scanDataCollection.getScanData().toArray(
        new ScanDataCollection.ScanData[scanDataCollection.getScanData().size()]);

    // Debugging
    for (ScanDataCollection.ScanData scanData : scannedData) {
      Log.i(TAG, "Label Type: " + scanData.getLabelType().name());
      Log.i(TAG, "Barcode: " + scanData.getData());
      Log.i(TAG, "Label Type: " + scanData.getLabelType().toString());
    }

    // Set Text
    mDataBinding.barcodeText.setText(scannedData[0].getData());
  }

  private void enableScanner() {
    final Scanner.DataListener dataListener = this;
    try {
      ((App) getApplicationContext()).enableScanner(dataListener);
    } catch (ScannerException e) {
      Log.e(TAG, "ScannerException: " + e.getMessage());
    }
  }

  private void disableScanner() {
    try {
      ((App) getApplicationContext()).disableScanner(this);
    } catch (ScannerException e) {
      Log.e(TAG, "ScannerException: " + e.getMessage());
    }
  }
}
