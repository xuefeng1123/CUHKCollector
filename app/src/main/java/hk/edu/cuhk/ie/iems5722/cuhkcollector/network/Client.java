package hk.edu.cuhk.ie.iems5722.cuhkcollector.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.cuhkcollector.Entity.MyEvent;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.MainActivity;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.R;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.Utils.ExceptionHandler;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.Utils.ExceptionUtil;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.eventMap.EventMapFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client {
    final static OkHttpClient client = new OkHttpClient();

    static public void getEventRequest(Context context) {
        final Request request = new Request.Builder()
                .get()
                .url(API.GET_MAP_EVENTS)
                .build();
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i("response Succeed!", response.body().toString());
                        String res = response.body().string();
                        JSONObject responseJson = JSON.parseObject(res);
                        if (responseJson.get("status").equals("OK")) {
                            String dataString = responseJson.getString("data");
                            List<MyEvent> events = JSON.parseArray(dataString, MyEvent.class);
                            ((MainActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager();
                                    EventMapFragment eventMapFragment = (EventMapFragment) fragmentManager.getPrimaryNavigationFragment();
                                    eventMapFragment.updateEventMarker(events);
                                }
                            });
                        } else {
                            Log.e("error", responseJson.toString());
                            throw new ExceptionUtil.GetInfoException();
                        }
                    } else {
                        throw new IOException("failed to receive response!" + response);
                    }
                } catch (IOException e) {
                    Log.e("get Request Error", e.toString());
                } catch (ExceptionUtil.GetInfoException e) {
                    Log.e("network error", e.toString());
                    ExceptionHandler.handleGetInfoException(context);
                }
                return null;
            }
        };
        asyncTask.execute(null,null);
    }
}