package com.tayyipgoren.yuru.yuru;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

public class ClassStorageHandler
{
    public static String LOG_TAG = "CLASS_STORAGE_HANDLER";

    /**
     * Class name factory
     * @param class_name Class name to convert
     * @return String Converted class name
     */
    public static String getClassFileName(String class_name)
    {
        return class_name + ".bin";
    }

    /**
     * Saves a object to internal storage
     * @param context Context
     * @param o Object to save
     */
    public static void saveObject(Context context, Object o){
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(o.getClass().getName() + ".bin",Context.MODE_PRIVATE)); //Select where you wish to save the file...
            oos.writeObject(o); // write the class as an 'object'
            oos.flush(); // flush the stream to insure all of the information was written to 'class_name.bin'
            oos.close();// close the stream
            Log.v(LOG_TAG, "Object " + o.getClass().getName() + " saved succesfully");
        }
        catch(Exception ex)
        {
            Log.v(LOG_TAG,"Save error :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Loads a saved object
     * @param file Input stream for the file to read
     * @return  Object
     */
    public static Object loadSerializedObject(FileInputStream file)
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(file);
            Object o = ois.readObject();
            Log.v(LOG_TAG, "Class succesfully readed : " + o.getClass().getName());
            return o;
        }
        catch(Exception ex)
        {
            Log.v(LOG_TAG,"Save error :" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
