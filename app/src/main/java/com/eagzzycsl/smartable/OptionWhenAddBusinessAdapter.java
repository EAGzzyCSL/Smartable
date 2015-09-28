package com.eagzzycsl.smartable;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class OptionWhenAddBusinessAdapter extends RecyclerView.Adapter<OptionWhenAddBusinessAdapter.ViewHolder> implements OptionType {
    private ArrayList<String> items;
    private Context context;
    private RecyclerView container;
    private int containerHeight = 0;
    private boolean haveGetContainerHeight = false;
    private String type;
    private int selectedItemPos = -1;
    private RecyclerView.LayoutManager layoutManager;

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public OptionWhenAddBusinessAdapter(ArrayList<String> items, Context context, RecyclerView container, String type) {
        this.items = items;
        this.context = context;
        this.container = container;
        this.type = type;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setChecked(false);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.add_option, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!haveGetContainerHeight) {
            containerHeight = container.getMeasuredHeight();
            haveGetContainerHeight = true;
        }
        if (position == selectedItemPos) {
            holder.setChecked(true);
        }
        holder.setText(items.get(position));


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatCheckBox textView;

        public ViewHolder(final View itemView) {
            super(itemView);
            textView = (AppCompatCheckBox) itemView.findViewById(R.id.add_option_checkBox);
/*
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addData(getAdapterPosition());
                    int tmp=getItemCount();
                    if(tmp<=3){

                        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, containerHeight));
                    }else if(tmp<=6){

                        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, containerHeight* 2));

                    }else{
                        container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, containerHeight* 3));

                    }

                }
            });

*/
            textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectedItemPos = getAdapterPosition();
                        for (int i = 0; layoutManager != null && i < layoutManager.getChildCount(); i++) {
                            AppCompatCheckBox aCb = (AppCompatCheckBox) (layoutManager.getChildAt(i));
                            if (aCb != (AppCompatCheckBox) buttonView) {
                                aCb.setChecked(false);
                            }
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
                case OptionType.ALERT: {
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
    }
}

interface OptionType {
    String LABEL = "label";
    String POS = "pos";
    String ALERT = "alert";
}