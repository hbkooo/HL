package love.lxy.hbk.hl.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import love.lxy.hbk.hl.Activity.LoveProcessActivity;
import love.lxy.hbk.hl.Activity.LoveProcessItemActivity;
import love.lxy.hbk.hl.Beans.LoveProcess;
import love.lxy.hbk.hl.R;

/**
 * Created by 19216 on 2019/8/3.
 * 爱情历程数据适配器
 */

public class LoveProcessAdapter extends RecyclerView.Adapter<LoveProcessAdapter.ViewHolder> {

    private Typeface typeface = null;
    private Context context;
    private List<LoveProcess> loveProcessList;

    protected boolean isScrolling = false;  // 是否正在滑动

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView no_over_tv;
        LinearLayout root_layout;
        ImageView description_iv;
        TextView node_tv, description_tv, time_tv;
        LinearLayout description_linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            no_over_tv = itemView.findViewById(R.id.process_item_no_over_tv);
            root_layout = itemView.findViewById(R.id.process_item_root_layout);
            description_iv = itemView.findViewById(R.id.process_item_description_iv);
            node_tv = itemView.findViewById(R.id.process_item_node_tv);
            description_tv = itemView.findViewById(R.id.process_item_description_tv);
            time_tv = itemView.findViewById(R.id.process_item_time_tv);
            description_linearLayout = itemView.findViewById(R.id.process_item_description_linear_layout);
        }
    }

    public LoveProcessAdapter(List<LoveProcess> loveProcessList, Context context) {
        this.loveProcessList = loveProcessList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.love_process_item1, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        if (typeface != null) {
            viewHolder.description_tv.setTypeface(typeface);
            viewHolder.time_tv.setTypeface(typeface);
            viewHolder.no_over_tv.setTypeface(typeface);
        }

        viewHolder.node_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                LoveProcess loveProcess = loveProcessList.get(position);
//                showLoveProcess(loveProcess);
                startActivity(loveProcess);
//                Toast.makeText(context, "点击node \n" + loveProcess.toString(),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.description_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                LoveProcess loveProcess = loveProcessList.get(position);
//                showLoveProcess(loveProcess);
                startActivity(loveProcess);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        LoveProcess loveProcess = loveProcessList.get(position);
        if (loveProcess.isBlank_tag()) {
            holder.root_layout.setVisibility(View.INVISIBLE);
            holder.no_over_tv.setVisibility(View.VISIBLE);
        } else {
            holder.no_over_tv.setVisibility(View.INVISIBLE);
            holder.root_layout.setVisibility(View.VISIBLE);
            holder.time_tv.setText(loveProcess.getTime());
            holder.description_tv.setText(loveProcess.getDescription());
            if (!isScrolling) {
//                try {
//                    holder.description_iv.setImageResource(loveProcess.getDescription_imgID());
//                } catch (Exception e) {
//                    holder.description_iv.setImageResource(Util.descripImgID[
//                            (int) (Math.random() * 100 % Util.descripImgID.length)]);
//                }
                holder.description_iv.setImageResource(Util.descripImgID[
                        (int) (Math.random() * 100 % Util.descripImgID.length)]);
            }
        }


    }

    @Override
    public int getItemCount() {
        return loveProcessList.size();
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    // 点击条目界面跳转
    private void startActivity(LoveProcess process) {
        Intent intent = new Intent(context, LoveProcessItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("process", process);
        intent.putExtra("bundle", bundle);
        ((LoveProcessActivity) context).startActivityForResult(intent, LoveProcessActivity.REQUEST_CHANGE);
//        context.startActivity(intent);
    }

    private AlertDialog dialog = null;
    private TextView content_tv, time_tv;
    private ImageView stage_iv1, stage_iv2;

    @SuppressLint("ResourceAsColor")
    private void showLoveProcess(final LoveProcess loveProcess) {


        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_love_process_item,
                    null, false);
            builder.setView(view);
            builder.setCancelable(false);
            dialog = builder.show();

            content_tv = view.findViewById(R.id.love_process_item_content_tv);
            content_tv.setTypeface(typeface);

            time_tv = view.findViewById(R.id.love_process_item_time_tv);
            time_tv.setTypeface(typeface);

            stage_iv1 = view.findViewById(R.id.love_process_item_iv1);
            stage_iv2 = view.findViewById(R.id.love_process_item_iv2);

//            HeartPathView heartPathView = view.findViewById(R.id.love_process_item_heart_path_view);
//            heartPathView.setBackgroundColor(R.color.myWhite);


            // 飞心背景效果
//        HeartView heartView = view.findViewById(R.id.love_process_item_heart_view);
//        heartView.start(context);

            Button confirm_btn = view.findViewById(R.id.love_process_item_confirm_btn);
            confirm_btn.setTypeface(typeface);
            confirm_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
//                dialog.hide();
                }
            });

        } else {
            dialog.show();
        }


        time_tv.setText(loveProcess.getTime());
        content_tv.setText(loveProcess.getLong_description());
        stage_iv1.setImageResource(loveProcess.getStageImgID1());
        stage_iv2.setImageResource(loveProcess.getStageImgID2());


    }

//    private void showLoveProcess(LoveProcess loveProcess) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_love_process_item,
//                null, false);
//        builder.setView(view);
//        builder.setCancelable(false);
//        final AlertDialog dialog = builder.show();
//
//        TextView content_tv = view.findViewById(R.id.love_process_item_content_tv);
//        content_tv.setTypeface(typeface);
//        content_tv.setText(loveProcess.getLong_description());
//
//        TextView time_tv = view.findViewById(R.id.love_process_item_time_tv);
//        time_tv.setTypeface(typeface);
//        time_tv.setText(loveProcess.getTime());
//
//        ImageView stage_iv1 = view.findViewById(R.id.love_process_item_iv1);
//        ImageView stage_iv2 = view.findViewById(R.id.love_process_item_iv2);
//
//        stage_iv1.setImageResource(loveProcess.getStageImgID1());
//        stage_iv2.setImageResource(loveProcess.getStageImgID2());
//
//        // 飞心背景效果
////        HeartView heartView = view.findViewById(R.id.love_process_item_heart_view);
////        heartView.start(context);
//
//        Button confirm_btn = view.findViewById(R.id.love_process_item_confirm_btn);
//        confirm_btn.setTypeface(typeface);
//        confirm_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
////                dialog.hide();
//            }
//        });
//
//
//    }


}
