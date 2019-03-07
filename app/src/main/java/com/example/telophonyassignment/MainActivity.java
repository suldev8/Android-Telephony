package com.example.telophonyassignment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String details = "";
    TextView telephonyView;
    static final int PERMISSION_READ_STATE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void start(View view){
        int getPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (getPermissions == PackageManager.PERMISSION_GRANTED){
            showTelephony();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_PHONE_STATE},
            PERMISSION_READ_STATE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case PERMISSION_READ_STATE:
                if(grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showTelephony();
                } else {
                    Toast.makeText(this, "You permission denied", Toast.LENGTH_LONG).show();
                }
        }
    }
    private void showTelephony() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int intPhoneType = manager.getPhoneType();
        String phoneType = "";
        switch (intPhoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneType = "CMDA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneType = "NONE";
                break;

        }


        int intCallState = manager.getCallState();
        String callState = "";
        switch (intCallState) {
            case(TelephonyManager.CALL_STATE_IDLE):
                callState = "No Activity";
                break;
            case (TelephonyManager.CALL_STATE_OFFHOOK):
                callState = "Off-hook";
                break;
            case (TelephonyManager.CALL_STATE_RINGING):
                callState = "Ringing";
                break;
        }
        int intSimState = manager.getSimState();
        String simState = "";
        switch (intSimState) {
            case (TelephonyManager.SIM_STATE_ABSENT):
                simState = "no SIM card is available in the device";
                break;
            case (TelephonyManager.SIM_STATE_CARD_IO_ERROR):
                simState = "SIM Card Error, present but faulty";
                break;
            case (TelephonyManager.SIM_STATE_CARD_RESTRICTED):
                simState = "SIM Card restricted, present but no usable due to carrier restrictions";
                break;
            case (TelephonyManager.SIM_STATE_NETWORK_LOCKED):
                simState = "Locked: requires a network PIN to unlock";
                break;
            case (TelephonyManager.SIM_STATE_NOT_READY):
                simState = "SIM Card is NOT READY";
                break;
            case (TelephonyManager.SIM_STATE_PERM_DISABLED):
                simState = "SIM card state: SIM Card Error, permanently disabled";
                break;
            case (TelephonyManager.SIM_STATE_READY):
                simState = "Ready";
                break;
            case (TelephonyManager.SIM_STATE_UNKNOWN):
                simState = "SIM card state: no SIM card is available in the device";
                break;
            case (TelephonyManager.SIM_STATE_PIN_REQUIRED):
                simState = "Locked: requires the user's SIM PIN to unlock";
                break;
            case (TelephonyManager.SIM_STATE_PUK_REQUIRED):
                simState = "Locked: requires the user's SIM PUK to unlock";
                break;
        }
        String IMEIorMEID = manager.getDeviceId();
        String lineNumber = manager.getLine1Number();

        details = "Phone Details \n" +
                "\nThe IMEI/MEID: "+ IMEIorMEID +
                "\nLine Number: "+ lineNumber +
                "\nPhone Network Type: "+ phoneType +
                "\nCall State: "+ callState +
                "\nSim State: "+ simState;
        telephonyView = (TextView) findViewById(R.id.telephonyView);
        telephonyView.setText(details);

    }
}
