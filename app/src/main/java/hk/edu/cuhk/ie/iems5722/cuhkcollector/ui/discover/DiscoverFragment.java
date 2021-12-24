package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import hk.edu.cuhk.ie.iems5722.cuhkcollector.R;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.common.helpers.DisplayRotationHelper;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.databinding.FragmentDiscoverBinding;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.persistentcloudanchor.CloudAnchorActivity;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.persistentcloudanchor.ResolveAnchorsLobbyActivity;

public class DiscoverFragment extends Fragment {

    private DiscoverViewModel discoverViewModel;
    private FragmentDiscoverBinding binding;
    private DisplayRotationHelper displayRotationHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discoverViewModel =
                new ViewModelProvider(this).get(DiscoverViewModel.class);

        binding = FragmentDiscoverBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        discoverViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        displayRotationHelper = new DisplayRotationHelper(getActivity());
        MaterialButton hostButton = binding.hostButton;
        hostButton.setOnClickListener((view) -> onHostButtonPress());
        MaterialButton resolveButton = binding.beginResolveButton;
        resolveButton.setOnClickListener((view) -> onResolveButtonPress());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayRotationHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        displayRotationHelper.onPause();
    }

    private void onHostButtonPress() {
        Intent intent = CloudAnchorActivity.newHostingIntent(this.getActivity());



        startActivity(intent);
    }

    /** Callback function invoked when the Resolve Button is pressed. */
    private void onResolveButtonPress() {
        Intent intent = ResolveAnchorsLobbyActivity.newIntent(this.getActivity());
        startActivity(intent);
    }

}