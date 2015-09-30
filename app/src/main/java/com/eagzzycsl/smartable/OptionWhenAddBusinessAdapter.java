package com.eagzzycsl.smartable;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.zip.CheckedInputStream;

import common.Affair;


public class OptionWhenAddBusinessAdapter extends RecyclerView.Adapter<OptionWhenAddBusinessAdapter.ViewHolder> implements OptionType {
    private ArrayList<String> items;
    private Context context;
    private RecyclerView container;
    private int containerHeight = 0;
    private boolean haveGetContainerHeight = false;
    private String type;
    private int selectedItemPos = -1;
    private AppCompatRadioButton headCheckedBox;
    private RecyclerView.LayoutManager layoutManager;
//    private Affair mLinkedAffair;
//    public void setMLinkedAffair(Affair mLinkedAffair){
//        this.mLinkedAffair=mLinkedAffair;
//    }

    public int getSelectedItemPos() {
        return selectedItemPos;
    }

    public void clearSelected() {
        this.selectedItemPos = -1;
        for (int i = 0; i < layoutManager.getChildCount(); i++) {
            AppCompatRadioButton aCR = (AppCompatRadioButton) (layoutManager.getChildAt(i));
            aCR.setChecked(false);
        }
    }

    public OptionWhenAddBusinessAdapter(ArrayList<String> items, Context context, RecyclerView container, String type, AppCompatRadioButton headCheckedBox) {
        this.items = items;
        this.context = context;
        this.container = container;
        this.layoutManager = container.getLayoutManager();
        this.type = type;
        this.headCheckedBox = headCheckedBox;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setChecked(false);//回收前全部设置为不选中到了展示时再决定要不要选中

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_option, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!haveGetContainerHeight) {
            containerHeight = container.getMeasuredHeight();
            adjustHeight();
            haveGetContainerHeight = true;

        }
        if (position == selectedItemPos) {
            holder.setChecked(true);
        }
        if (position == getItemCount() - 1) {
            holder.setIsAdd(true);
        }
        if (type == OptionType.TAKE) {
            if (position == 0) {
                holder.setChecked(true);
            }
        }

        holder.setText(items.get(position));


    }

    private void adjustHeight() {
        int tmp = getItemCount();
        int oneLineCount;
        if (type == OptionType.LABEL) {
            oneLineCount = 3;
        } else {
            oneLineCount = 4;
        }
        if (tmp <= oneLineCount) {

            container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, containerHeight));
        } else if (tmp <= 2 * oneLineCount) {

            container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (containerHeight * 2)));
        } else {
            container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (containerHeight * 3)));

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatRadioButton textView;
        private boolean isAdd = false;

        public ViewHolder(final View itemView) {
            super(itemView);
            textView = (AppCompatRadioButton) itemView.findViewById(R.id.add_option_checkBox);
            if (type == OptionType.ALERT || type == OptionType.POS || type == OptionType.TAKE) {


                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isAdd) {
                            addData(getAdapterPosition());
                            adjustHeight();
                        }
                    }
                });
            }


            textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        selectedItemPos = getAdapterPosition();
                        //因为设置持续时间的那一块head是不可以选的所以要这样做
                        if(type==OptionType.TAKE){
                            headCheckedBox.setChecked(true);
                        }
                        headCheckedBox.setChecked(false);
                        for (int i = 0; i < layoutManager.getChildCount(); i++) {
                            AppCompatRadioButton aCR = (AppCompatRadioButton) (layoutManager.getChildAt(i));
                            if (aCR != (AppCompatRadioButton) buttonView) {
                                aCR.setChecked(false);
                            }
                        }
                    }
                    if (type == OptionType.ALERT || type == OptionType.POS || type == OptionType.TAKE) {
                        if (isAdd) {
                            buttonView.setChecked(!isChecked);
                        }
                    }

                }
            });

            switch (type) {
                case OptionType.LABEL: {
                    textView.setBackgroundResource(R.drawable.add_option_bkg_label);
                    break;
                }
                case OptionType.POS: {
                    textView.setBackgroundResource(R.drawable.add_option_bkg_pos);
                    break;
                }
                case OptionType.ALERT:
                case OptionType.TAKE: {
                    textView.setBackgroundResource(R.drawable.add_option_bkg_alert);
                    break;
                }
            }
        }

        public void setText(String text) {
            textView.setText(text);
        }

        public void addData(int position) {
            items.add(position, "java");
            notifyItemInserted(position);
        }

        public void setChecked(boolean isChecked) {
            textView.setChecked(isChecked);
        }

        public void setIsAdd(boolean isAdd) {
            this.isAdd = isAdd;
        }
    }
}

interface OptionType {
    String LABEL = "label";
    String POS = "pos";
    String ALERT = "alert";
    String TAKE = "take";
}