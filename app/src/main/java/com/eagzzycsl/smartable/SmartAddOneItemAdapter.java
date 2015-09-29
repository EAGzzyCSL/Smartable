package com.eagzzycsl.smartable;


import android.content.Context;
import android.media.Image;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import common.MyTime;

public class SmartAddOneItemAdapter extends RecyclerView.Adapter<SmartAddOneItemAdapter.ViewHolder> {
    private ArrayList<Integer> itemId = new ArrayList<Integer>();
    private Context context;
    private RecyclerView recyclerView_container;
    private RecyclerView.LayoutManager layoutManager;

    public SmartAddOneItemAdapter(ArrayList<Integer> itemId, Context context, RecyclerView recyclerView_container) {
        this.itemId = itemId;
        this.context = context;
        this.recyclerView_container = recyclerView_container;
        this.layoutManager = recyclerView_container.getLayoutManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.smart_add_one_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView_label;
        private RecyclerView recyclerView_pos;
        private RecyclerView recyclerView_alert;
        private AppCompatRadioButton RadioButton_noAlert;
        private AppCompatRadioButton RadioButton_noLabel;
        private AppCompatRadioButton RadioButton_noPos;
        private AppCompatEditText editText_title;
        private LinearLayout linearLayout_setTime;
        private LinearLayout linearLayout_setLabel;
        private LinearLayout linearLayout_setPos;
        private LinearLayout linearLayout_setAlert;
        private ImageButton imageButton_done;
        private ImageButton imageButton_delete;
        private AppCompatTextView textView_startDate;
        private AppCompatTextView textView_startTime;
        private AppCompatTextView textView_endDate;
        private AppCompatTextView textView_endTime;
        private MyTime timeStart = new MyTime();
        private MyTime timeEnd = new MyTime();

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView_label = (RecyclerView) itemView.findViewById(R.id.recyclerView_label);
            recyclerView_pos = (RecyclerView) itemView.findViewById(R.id.recyclerView_pos);
            recyclerView_alert = (RecyclerView) itemView.findViewById(R.id.recyclerView_alert);

            RadioButton_noLabel = (AppCompatRadioButton) itemView.findViewById(R.id.RadioButton_noLabel);
            RadioButton_noPos = (AppCompatRadioButton) itemView.findViewById(R.id.RadioButton_noPos);
            RadioButton_noAlert = (AppCompatRadioButton) itemView.findViewById(R.id.RadioButton_noAlert);
            recyclerView_label.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
            recyclerView_label.setItemAnimator(new DefaultItemAnimator());
            recyclerView_label.setAdapter(new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
                {
                    this.add("作业");
                    this.add("与人为乐");
                    this.add("与己为乐");
                }
            }, context, recyclerView_label, OptionType.LABEL, RadioButton_noLabel));
            RadioButton_noLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ((OptionWhenAddBusinessAdapter) (recyclerView_label.getAdapter())).clearSelected();
                    }
                }
            });
            recyclerView_pos.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
            recyclerView_pos.setItemAnimator(new DefaultItemAnimator());
            recyclerView_pos.setAdapter(new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
                {
                    this.add("宿舍");
                    this.add("教室");
                    this.add("其他");

                    this.add("+");
                }
            }, context, recyclerView_pos, OptionType.POS, RadioButton_noPos));
            RadioButton_noPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ((OptionWhenAddBusinessAdapter) (recyclerView_pos.getAdapter())).clearSelected();
                    }
                }
            });

            editText_title = (AppCompatEditText) itemView.findViewById(R.id.editText_title);
            linearLayout_setTime = (LinearLayout) itemView.findViewById(R.id.linearLayout_setTime);
            linearLayout_setLabel = (LinearLayout) itemView.findViewById(R.id.linearLayout_setLabel);
            linearLayout_setPos = (LinearLayout) itemView.findViewById(R.id.linearLayout_setPos);
            linearLayout_setAlert = (LinearLayout) itemView.findViewById(R.id.linearLayout_setAlert);
            editText_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {

                        setFold(false);
                        for (int i = 0; i < recyclerView_container.getChildCount(); i++) {
                            SmartAddOneItemAdapter.ViewHolder vH = (SmartAddOneItemAdapter.ViewHolder) recyclerView_container.getChildViewHolder(layoutManager.getChildAt(i));
                            if (ViewHolder.this != vH) {
                                vH.setFold(true);
                            }
                        }
                    }
                }
            });
            imageButton_done = (ImageButton) itemView.findViewById(R.id.imageButton_done);
            imageButton_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFold(true);
                    addItem(getItemCount());
                }
            });
            imageButton_delete = (ImageButton) itemView.findViewById(R.id.imageButton_delete);
            imageButton_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getAdapterPosition());
                }
            });

            textView_startDate = (AppCompatTextView) itemView.findViewById(R.id.textView_startDate);
            textView_startTime = (AppCompatTextView) itemView.findViewById(R.id.textView_startTime);
            textView_endDate = (AppCompatTextView) itemView.findViewById(R.id.textView_endDate);
            textView_endTime = (AppCompatTextView) itemView.findViewById(R.id.textView_endTime);
            textView_startDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyDatePickerDialog(context, textView_startDate, timeStart)
                            .show();
                }
            });
            textView_endDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyDatePickerDialog(context, textView_endDate, timeEnd)
                            .show();
                }
            });
            textView_startTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyTimePickerDialog(context, textView_startTime, timeStart)
                            .show();
                }
            });
            textView_endTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MyTimePickerDialog(context, textView_endTime, timeEnd)
                            .show();
                }
            });
        }

        public void setFold(boolean isFold) {
            if (isFold) {
                linearLayout_setTime.setVisibility(View.GONE);
                linearLayout_setLabel.setVisibility(View.GONE);
                linearLayout_setPos.setVisibility(View.GONE);
                linearLayout_setAlert.setVisibility(View.GONE);
            } else {
                linearLayout_setTime.setVisibility(View.VISIBLE);
                linearLayout_setLabel.setVisibility(View.VISIBLE);
                linearLayout_setPos.setVisibility(View.VISIBLE);
                linearLayout_setAlert.setVisibility(View.VISIBLE);
            }
        }

        private void addItem(int position) {
            itemId.add(position, position * 2);
            notifyItemInserted(position);
        }

        private void deleteItem(int position) {
            itemId.remove(position);
            notifyItemRemoved(position);
        }
    }
}
