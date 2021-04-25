package technomint.app.arbanClap.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;

import technomint.app.arbanClap.CategoryDetail;
import technomint.app.arbanClap.Config.Config;
import technomint.app.arbanClap.Model.BestModel;
import technomint.app.arbanClap.Model.CategoryModel;
import technomint.app.arbanClap.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    private List<CategoryModel> mData ;


    public CategoryAdapter(Context mContext, List lst) {


        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_baseline_error_outline_24);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.category_item,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //ye h category ka click listner
                Intent i = new Intent(mContext, CategoryDetail.class);

                i.putExtra("category_id",mData.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("category_name",mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("category_image",mData.get(viewHolder.getAdapterPosition()).getImageUrl());

                mContext.startActivity(i);
            }
        });
        // click listener here
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvname.setText(mData.get(position).getName());
        // holder.tv_rate.setText(mData.get(position).getRating());
        // holder.tvstudio.setText(mData.get(position).getStudio());
        // holder.tvcat.setText(mData.get(position).getCategorie());

        // load image from the internet using Glide
        Glide.with(mContext).load(Config.assests + mData.get(position).getImageUrl()).apply(options).into(holder.AnimeThumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvname;
        ImageView AnimeThumbnail;


        public MyViewHolder(View itemView) {
            super(itemView);
            AnimeThumbnail = itemView.findViewById(R.id.categoryimage);
            tvname = itemView.findViewById(R.id.category_name);

        }
    }


}