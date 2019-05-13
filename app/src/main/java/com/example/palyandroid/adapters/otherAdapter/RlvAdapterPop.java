package com.example.palyandroid.adapters.otherAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.palyandroid.R;

import java.util.ArrayList;

/**
 * Created by 孤辟 on 2019/5/2.
 */

public class RlvAdapterPop extends RecyclerView.Adapter {


    public ArrayList<String> mText;

    public RlvAdapterPop(ArrayList<String> text) {

        mText = text;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
        return new VHheard(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VHheard holder1= (VHheard) holder;
        holder1.mTe.setText(mText.get(position));
        holder1.mIv.setImageResource(R.drawable.house);
    }

    @Override
    public int getItemCount() {
        return mText.size();
    }
    class VHheard extends RecyclerView.ViewHolder {
        private final TextView mTe;
        private final ImageView mIv;

        public VHheard(View itemView) {
            super(itemView);
            mTe = itemView.findViewById(R.id.tv);
            mIv = itemView.findViewById(R.id.iv);

        }
    }
}
