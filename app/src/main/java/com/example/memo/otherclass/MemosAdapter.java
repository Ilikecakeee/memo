package com.example.memo.otherclass;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.memo.MemosActivity;
import com.example.memo.R;
import com.example.memo.ShowmemoActivity;
import com.example.memo.db.memos;

import java.util.List;

public class MemosAdapter extends RecyclerView.Adapter<MemosAdapter.ViewHolder>{
    private List<memos1> mMemo;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View memoView;
        TextView title;
        TextView texts;
        TextView date;

        public ViewHolder(View view){
            super(view);
            memoView=view;
            title=(TextView) view.findViewById(R.id.title);
            texts=(TextView) view.findViewById(R.id.texts);
            date=(TextView) view.findViewById(R.id.date);
        }
    }

    public MemosAdapter(List<memos1> MemoList){
        mMemo=MemoList;
    }

    @Override
    public MemosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_item,parent,false);
        final MemosAdapter.ViewHolder holder=new MemosAdapter.ViewHolder(view);
        holder.memoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position=holder.getAdapterPosition();
                memos1 memos12=mMemo.get(position);
                Intent intent=new Intent(v.getContext(),ShowmemoActivity.class);
                int data=memos12.getId();
                Log.d("SubAdapter","qqqqqqqqqqqqqq"+data);
                intent.putExtra("memoid",data);
                v.getContext().startActivity(intent);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MemosAdapter.ViewHolder holder, int position){
        memos1 memo11=mMemo.get(position);
        holder.title.setText(memo11.getTitle());
        holder.texts.setText(memo11.getTexts());
        holder.date.setText(memo11.getDate());

    }
    @Override
    public int getItemCount(){
        return mMemo.size();
    }
}
