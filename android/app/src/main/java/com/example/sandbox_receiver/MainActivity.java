package com.example.sandbox_receiver;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity  {
    private static final String CHANNEL = "com.example.keyevents/receiveKeyEvent";
    private static final String TAG = "MainActivity"; // Tag for logging

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            if (call.method.equals("receiveKeyEvent")) {
                                // Handle platform method call if needed
                                Log.d(TAG, "Platform method receiveKeyEvent called");
                                result.success("Received key event method call");
                            }else if(call.method.equals("sendKeyEvent")) {
                                try {
                                    int keyCode = Integer.parseInt(call.argument("keyCode").toString());
                                    sendKeyEvent(keyCode);
                                    Log.d(TAG, "Success"); // Log success message
                                    result.success("Success"); // Send success message back to Flutter
                                } catch (NumberFormatException e) {
                                    Log.e(TAG, "Error parsing keyCode", e); // Log error
                                    result.error("ERROR_PARSING_KEYCODE", "Failed to parse keyCode", null);
                                }
                            } else {
                                result.notImplemented();
                            }
                        }
                );
    }

    private void sendKeyEvent(int keyCode) {
        // Implement the logic to send a key event here
        // This might involve sending a broadcast or using an accessibility service
        Log.d(TAG, "sendKeyEvent called with keyCode: " + keyCode);
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
        Intent intent = new Intent("com.example.keyevents.KEY_EVENT");
        intent.putExtra("keyCode", keyCode);
        sendBroadcast(intent);// Log method call
    }

}

