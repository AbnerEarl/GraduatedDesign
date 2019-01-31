package com.lifeng.layoutmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifeng.layoutmanager.R;
import com.lifeng.layoutmanager.been.Been;
import com.lifeng.layoutmanager.callback.MoveTouchCallback;

import java.util.Collections;
import java.util.List;

/**
 * 项目名称：LayoutManager
 * 类描述：自定义的recycler的适配器
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements MoveTouchCallback.ItemTouchAdapter {

    private Context context;
    private int src;
    private List<Been> results;

    public RecyclerAdapter(int src,List<Been> results){
        this.results = results;
        this.src = src;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(src, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.imageView.setImageResource(results.get(position).getSrc());
        holder.textView.setText(results.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    //在这里可以设置最后一位或者特定位置不可拖动，实际需求可能会用到
    @Override
    public void onMove(int fromPosition, int toPosition) {
        //注释部分即为设置最后一个位置不可拖动改变
//        if (fromPosition==results.size()-1 || toPosition==results.size()-1){
//            return;
//        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = width/4;
            itemView.setLayoutParams(layoutParams);
            textView = (TextView) itemView.findViewById(R.id.item_text);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
