package com.example.wildcat.customkeyboard;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by wildcat on 11/16/2016.
 */
public class MyStorage {

    //String sdCardRoot = Environment.getExternalStorageDirectory().toString();
    public void listRaw(File sdCardRoot){
        String path_name = sdCardRoot.toString();
        File f = new File(path_name);
        File files[] = f.listFiles();
        if (files != null){
            for(int i = 0; i < files.length; i++){
                Log.d("Files", "filename: " + files[i].getName());
                listRaw(files[i]);
            }
        }

        /*
        File yourDir = new File(sdCardRoot, "Alarms");
        for (File f : yourDir.listFiles()) {
            if (f.isFile())
            {
                String name = f.getName();
                Log.i("file names", name);

            }

        }*/
    }


}
