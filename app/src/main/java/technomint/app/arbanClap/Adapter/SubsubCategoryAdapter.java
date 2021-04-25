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

import java.util.List;

import technomint.app.arbanClap.DetailPage;
import technomint.app.arbanClap.Model.CategorySubModel;
import technomint.app.arbanClap.Model.SubsubCategoryModel;
import technomint.app.arbanClap.R;
import technomint.app.arbanClap.SubsubCategory;

public class SubsubCategoryAdapter extends RecyclerView.Adapter<SubsubCategoryAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    private List<SubsubCategoryModel> mData ;


    public SubsubCategoryAdapter(Context mContext, List lst) {

        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_baseline_error_outline_24);


    }

    // Clcik listner kaha he

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.subsubcategory_itemlayout,parent,false);

        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailPage.class);
                i.putExtra("subcategory_id",mData.get(viewHolder.getAdapterPosition()).getId());
                i.putExtra("service_name",mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("service_amount",mData.get(viewHolder.getAdapterPosition()).getService_amount());
                i.putExtra("service_desc",mData.get(viewHolder.getAdapterPosition()).getService_desc());
                i.putExtra("service_type",mData.get(viewHolder.getAdapterPosition()).getService_type());
                i.putExtra("service_image",mData.get(viewHolder.getAdapterPosition()).getImageUrl());

                mContext.startActivity(i);

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.subitem_text.setText(mData.get(position).getName());
        // holder.tv_rate.setText(mData.get(position).getRating());
        // holder.tvstudio.setText(mData.get(position).getStudio());
        // holder.tvcat.setText(mData.get(position).getCategorie());

        // load image from the internet using Glide
        Glide.with(mContext).load("http://urbanclap.laarizona.com"+mData.get(position).getImageUrl()).apply(options).into(holder.subitem_image);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subitem_text;
        ImageView subitem_image;


        public MyViewHolder(View itemView) {
            super(itemView);
            subitem_text = itemView.findViewById(R.id.subitem_text);
            subitem_image = itemView.findViewById(R.id.subitem_image);

        }
    }


}