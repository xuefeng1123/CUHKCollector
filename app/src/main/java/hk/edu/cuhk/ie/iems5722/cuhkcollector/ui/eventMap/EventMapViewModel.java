package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.eventMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}