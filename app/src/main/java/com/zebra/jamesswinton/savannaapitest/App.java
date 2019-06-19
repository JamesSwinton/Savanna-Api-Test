package com.zebra.jamesswinton.savannaapitest;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKManager.EMDKListener;
import com.symbol.emdk.EMDKManager.FEATURE_TYPE;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.EMDKResults.STATUS_CODE;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.barcode.Scanner.DataListener;
import com.symbol.emdk.barcode.Scanner.StatusListener;
import com.symbol.emdk.barcode.ScannerConfig;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.StatusData;
import java.util.ArrayList;
import java.util.List;

public class App extends Application implements EMDKListener, StatusListener, DataListener {

  // Debugging
  private static final String TAG = "ApplicationClass";

  // Constants
  public static final Handler mUiThread = new Handler(Looper.getMainLooper());

  // Static Variables
  private static boolean mIsScanning = false;

  public static Scanner mScanner;

  private static EMDKManager mEmdkManager;
  private static BarcodeManager mBarcodeManager;
  private static List<DataListener> mDataListeners;

  // Non-Static Variables


  @Override
  public void onCreate() {
    super.onCreate();

    // Init DataListener Array
    mDataListeners = new ArrayList<>();

    // Init EMDK
    EMDKResults emdkManagerResults = EMDKManager.getEMDKManager(this, this);

    // Verify EMDK Manager
    if (emdkManagerResults == null || emdkManagerResults.statusCode != STATUS_CODE.SUCCESS) {
      // Log Error
      Log.e(TAG, "onCreate: Failed to get EMDK Manager -> " + emdkManagerResults.statusCode);
    }
  }

  @Override
  public void onOpened(EMDKManager emdkManager) {
    // Log Results
    Log.i(TAG, "onOpened: EMDK Manager Initialised");

    // Assign EMDK Reference
    mEmdkManager = emdkManager;

    // Get Barcode Manager
    mBarcodeManager = (BarcodeManager) mEmdkManager.getInstance(FEATURE_TYPE.BARCODE);

    // Init Scanner
    try {
      initScanner();
    } catch (ScannerException e) {
      Log.e(TAG, "onOpened: Scanner Exception - " + e.getMessage(), e);
    }
  }

  @Override
  public void onClosed() {
    // Log EMDK Closed
    Log.i(TAG, "onClosed: EMDK Closed");

    // Release EMDK Manager
    if (mEmdkManager != null) {
      mEmdkManager.release();
      mEmdkManager = null;
    }
  }

  /*
   * This is the callback method upon data availability.
   */
  @Override
  public void onData(ScanDataCollection scanDataCollection) {
    mUiThread.post(() -> {
      // Handle Data
      for (DataListener dataListener : mDataListeners) {
        dataListener.onData(scanDataCollection);
      }

      // Restart Scanner
      if (mScanner != null) {
        try {
          if (!mScanner.isReadPending()) mScanner.read();
        } catch (ScannerException e) {
          Log.e(TAG, "onData: ScannerException: " + e.getMessage(), e);
        }
      }
    });
  }

  /*
   * This is the callback method upon scan status event occurs.
   */
  @Override
  public void onStatus(StatusData statusData) {
    switch (statusData.getState()) {
      case IDLE:
        try {
          try { Thread.sleep(100); }
          catch (InterruptedException e) { e.printStackTrace(); }
          mScanner.read();
        } catch (ScannerException e) {
          Log.e(TAG, "onStatus: ScannerException - " + e.getMessage(), e);
        }
        break;
      case WAITING:
        Log.i(TAG, "onStatus: Scanner Waiting...");
        break;
      case SCANNING:
        Log.i(TAG, "onStatus: Scanner Scanning...");
        break;
      case DISABLED:
        Log.i(TAG, "onStatus: Scanner Disabled...");
        break;
      case ERROR:
        Log.i(TAG, "onStatus: Scanner Error!");
        break;
    }
  }

  void initScanner() throws ScannerException {
    // Init Scanner
    mScanner = mBarcodeManager.getDevice(BarcodeManager.DeviceIdentifier.DEFAULT);
    // Set Scanner Listeners
    mScanner.addDataListener(this);
    mScanner.addStatusListener(this);
    // Enable Scanner if needed
    if (mIsScanning) {
      enableScanner(null);
    }
  }

  public void enableScanner(DataListener dataListener) throws ScannerException {
    Log.i(TAG, "enableScanner: Enabling Scanner...");

    // Add DataListener to List if Exists
    if (dataListener != null && !mDataListeners.contains(dataListener)) {
      mDataListeners.add(dataListener);
    }

    // Enable Scanner
    mScanner.enable();
    mIsScanning = true;

    // Build & Set Scanner Meta (Can only be done after Scanner is Enabled)
    ScannerConfig config = mScanner.getConfig();
    config.readerParams.readerSpecific.imagerSpecific.pickList = ScannerConfig.PickList.ENABLED;
    config.scanParams.decodeAudioFeedbackUri = "system/media/audio/notifications/decode-short.wav";
    config.scanParams.decodeHapticFeedback = true;
    config.decoderParams.upce0.enabled = true;
    config.decoderParams.upce1.enabled = true;
    config.decoderParams.upca.enabled = true;
    mScanner.setConfig(config);
  }

  public void disableScanner(DataListener dataListener) throws ScannerException {
    Log.i(TAG, "disableScanner: Disabling Scanner...");

    // Remove DataListener from List if Exists
    if (mDataListeners.contains(dataListener)) {
      mDataListeners.remove(dataListener);
      mScanner.removeDataListener(dataListener);
    }

    // Disable Scanner
    mScanner.disable();
    mIsScanning = false;
  }

}
