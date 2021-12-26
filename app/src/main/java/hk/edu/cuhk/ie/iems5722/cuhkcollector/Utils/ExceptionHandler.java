package hk.edu.cuhk.ie.iems5722.cuhkcollector.Utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ExceptionHandler {
    public static void handleIllegalChatroomIDException(Context context){
        ((Activity)context).findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "The chat room has been disbanded", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void handleGetInfoException(Context context){
        ((Activity)context).findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Network Error!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void handleLoadedAllMsgException(Context context){
        ((Activity) context).findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "No more messages!", Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void handlePostInfoException(Context context){
        ((Activity) context).findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Failed to send messages!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
