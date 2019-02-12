package com.example.memo.otherclass;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memo.MainActivity;
import com.example.memo.MemosActivity;
import com.example.memo.R;
import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    private List<subject1> mSubList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View subView;
        TextView subjectname;

        public ViewHolder(View view){
            super(view);
            subView=view;
            subjectname=(TextView) view.findViewById(R.id.sub_name);
        }
    }

    public SubAdapter(List<subject1> SubList){
        mSubList=SubList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.subView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                subject1 subject12=mSubList.get(position);
                Intent intent=new Intent(v.getContext(),MemosActivity.class);
                int data=subject12.getId();
                Log.d("SubAdapter","qqqqqqqqqqqqqq"+data);
                intent.putExtra("subid",data);
                v.getContext().startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        subject1 subject11=mSubList.get(position);
        holder.subjectname.setText(subject11.getName());

    }
    @Override
    public int getItemCount(){
        return mSubList.size();
    }


}
