package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.eventMap;

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

import hk.edu.cuhk.ie.iems5722.cuhkcollector.databinding.FragmentEventMapBinding;

public class EventMapFragment extends Fragment {

    private EventMapViewModel eventMapViewModel;
    private FragmentEventMapBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventMapViewModel =
                new ViewModelProvider(this).get(EventMapViewModel.class);

        binding = FragmentEventMapBinding .inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        eventMapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}