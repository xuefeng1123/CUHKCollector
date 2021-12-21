package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscoverViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiscoverViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is discover fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}