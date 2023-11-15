package com.ispl.ekalarogya.training.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.models.ImageList;
import com.ispl.ekalarogya.training.utils.ExtraUtils;
import com.ispl.ekalarogya.training.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<ImageList> imageList;
    private static final String TAG = "ImageListAdapter";
    private ImageAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(ImageList item);
    }

    public ImageAdapter(Context context, List<ImageList> imageList) {
        this.mContaxt = context;
        this.imageList=imageList;
        this.listener = listener;

        Log.d(TAG, "ImageAdapter: +++ "+imageList.size());
        Log.d(TAG, "ImageAdapter: to string  "+imageList.toString());

    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_images, null);

        return new ImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageAdapter.ViewHolder holder, int position) {
        final ImageList baseResponse = imageList.get(position);
//        Log.d(TAG, "onBindViewHolder: "+baseResponse.getImages().toString());

        if (baseResponse.getImages()!=null&&baseResponse.getImages().contains(".jpeg")){
            Picasso.with(mContaxt).load(ExtraUtils.baseImg+baseResponse.getImages()).placeholder(R.drawable.add_image).error(R.drawable.add_image).into(holder.iMageJagaran);
        }else if(baseResponse.getImages()==null){

        }
        else {
            holder.iMageJagaran.setImageBitmap(ImageUtils.convertBase64ToBitmap(baseResponse.getImages()));
        }

        holder.bind(imageList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+imageList.size());
        return imageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView editpen,imagedlt,iMageJagaran;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iMageJagaran=itemView.findViewById(R.id.iMageJagaran);
            imagedlt=itemView.findViewById(R.id.image_delete);



        }


        public void bind(ImageList imageLis, ImageAdapter.OnItemClickListener listener, int position) {

            Log.d(TAG, "bind: onimage click url "+imageLis.getImages());
            imagedlt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageList.remove(position);
                    notifyDataSetChanged();
                }
            });

        }
    }



}

