package hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hk.edu.cuhk.ie.iems5722.cuhkcollector.R;
import hk.edu.cuhk.ie.iems5722.cuhkcollector.persistentcloudanchor.CloudAnchorActivity;


public class ModelListAdapter extends ArrayAdapter<Model> {
    class ViewHolder{
        ImageButton modelPic;
    }
    public int resourceId;
    public Context context;
    public ModelListAdapter(@NonNull Context context, int resource, List<Model> models) {
        super(context, resource, models);
        this.context = context;
        resourceId = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        Model model = getItem(position);
        View view;
        ModelListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ModelListAdapter.ViewHolder();

            viewHolder.modelPic = (ImageButton) view.findViewById(R.id.model_pic);

            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ModelListAdapter.ViewHolder) view.getTag();
        }
        viewHolder.modelPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Model model = modelList.get(i);
                CloudAnchorActivity.modelName = model.name;
                Toast.makeText(getContext(), "Model Updated: " + CloudAnchorActivity.modelName, Toast.LENGTH_SHORT).show();
            }
        });
//        viewHolder.modelPic.setImageBitmap(getImageFromAssetsFile(this.context, model.name));
        if(model.name.equals("creeper"))
            viewHolder.modelPic.setImageResource(R.mipmap.creeper);
        else if(model.name.equals("latte"))
            viewHolder.modelPic.setImageResource(R.mipmap.latte);
        return view;
    }
    private Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        fileName = "assets://" + fileName + ".png";
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
