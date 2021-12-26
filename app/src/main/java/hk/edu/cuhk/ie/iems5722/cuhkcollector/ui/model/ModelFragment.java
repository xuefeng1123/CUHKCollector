package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.cuhkcollector.R;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.databinding.FragmentModelBinding;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.persistentcloudanchor.CloudAnchorActivity;

public class ModelFragment extends Fragment {

    private ModelViewModel modelViewModel;
    private FragmentModelBinding binding;

    private ModelListAdapter modelListAdapter;
    private ListView modelListView;

    private List<Model> modelList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        modelViewModel =
                new ViewModelProvider(this).get(ModelViewModel.class);

        binding = FragmentModelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        modelListView = binding.modelPicList;
        modelList = new ArrayList<>();
        modelListAdapter = new ModelListAdapter(getActivity(), R.layout.model_item, modelList);
        modelListView.setAdapter(modelListAdapter);

//        modelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Model model = modelList.get(i);
//                CloudAnchorActivity.modelName = model.name;
//                Toast.makeText(getActivity(), "Model Updated: " + CloudAnchorActivity.modelName, Toast.LENGTH_SHORT).show();
//            }
//        });

        mockData();
        return root;
    }

    private void mockData(){
        Model model1 = new Model("anchor");
        Model model2 = new Model("creeper");
        Model model3 = new Model("latte");

        modelList.add(model1);
        modelList.add(model2);
        modelList.add(model3);

        modelListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}