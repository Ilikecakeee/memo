package com.example.memo.otherclass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.memo.MemosActivity;
import com.example.memo.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private List<image1> mImgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View imgView;
        ImageView imagename;

        public ViewHolder(View view){
            super(view);
            imgView=view;
            imagename=(ImageView) view.findViewById(R.id.img_name);
        }
    }

    public ImageAdapter(List<image1> ImgList){
        mImgList=ImgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view=LayoutInflater.from(mContext)
                .inflate(R.layout.img_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.imgView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                image1 image12=mImgList.get(position);//有问题
                String data=image12.getPath();


            }
        });
        //return new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position){
        image1 image11=mImgList.get(position);
        Glide.with(mContext).load(image11.getPath()).into(holder.imagename);
        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

    }
    @Override
    public int getItemCount(){
        return mImgList.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    //  删除数据
    public void removeData(int position) {
        mImgList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


}

