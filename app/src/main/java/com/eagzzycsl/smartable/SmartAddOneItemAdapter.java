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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import common.Affair;
import common.MyTime;

public class SmartAddOneItemAdapter extends RecyclerView.Adapter<SmartAddOneItemAdapter.ViewHolder> {
    private ArrayList<Affair> affairs = new ArrayList<Affair>();
    private Context context;
    private RecyclerView recyclerView_container;
    private RecyclerView.LayoutManager layoutManager;
    private boolean hasEmpty = true;

    public ArrayList<Affair> getAffairs() {
        return this.affairs;
    }

    public SmartAddOneItemAdapter(ArrayList<Affair> affairs, Context context, RecyclerView recyclerView_container) {
        this.affairs = affairs;
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
        if (position == getItemCount() - 1) {
            holder.setDefaultChecked();
            //为了解决recyclerView的getAdapterPosition问题
            //http://stackoverflow.com/questions/29156172/viewholder-getadapterposition-always-returns-1
            //当第一次被加载的时候设置默认选中而不是创建对象的时候设置默认选中
            holder.setAutoFocus();
            layoutManager.scrollToPosition(layoutManager.getItemCount() - 1);
        }

    }

    @Override
    public int getItemCount() {
        return affairs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView_label;
        private RecyclerView recyclerView_pos;
        private RecyclerView recyclerView_alert;
        private RecyclerView recyclerView_take;
        private AppCompatRadioButton radioButton_take;
        private AppCompatRadioButton radioButton_noLabel;
        private AppCompatRadioButton radioButton_noPos;
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
        private MyTime timeStart;
        private MyTime timeEnd;
        private OptionWhenAddBusinessAdapter labelAdapter;
        private OptionWhenAddBusinessAdapter posAdapter;
        private OptionWhenAddBusinessAdapter alertAdapter;
        private OptionWhenAddBusinessAdapter takeAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            final Calendar calendar = Calendar.getInstance();
            timeStart = new MyTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), 0);
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            //如果这儿是7的话在宦神那边会有下标的问题
            timeEnd = new MyTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), 0);

            recyclerView_label = (RecyclerView) itemView.findViewById(R.id.recyclerView_label);
            recyclerView_pos = (RecyclerView) itemView.findViewById(R.id.recyclerView_pos);
            recyclerView_alert = (RecyclerView) itemView.findViewById(R.id.recyclerView_alert);
            recyclerView_take = (RecyclerView) itemView.findViewById(R.id.recyclerView_take);

            radioButton_noLabel = (AppCompatRadioButton) itemView.findViewById(R.id.radioButton_noLabel);
            radioButton_noPos = (AppCompatRadioButton) itemView.findViewById(R.id.radioButton_noPos);
            radioButton_take = (AppCompatRadioButton) itemView.findViewById(R.id.radioButton_take);
            recyclerView_label.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
            recyclerView_label.setItemAnimator(new DefaultItemAnimator());
            labelAdapter = new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
                {
                    this.add("作业");
                    this.add("与人为乐");
                    this.add("与己为乐");
                }
            }, context, recyclerView_label, OptionType.LABEL, radioButton_noLabel);
            recyclerView_label.setAdapter(labelAdapter);

            radioButton_noLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        affairs.get(getAdapterPosition()).setKind(3);
                        labelAdapter.clearSelected();
                    } else {
                        affairs.get(getAdapterPosition()).setKind(labelAdapter.getSelectedItemPos());
                    }
                }
            });
            recyclerView_pos.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
            recyclerView_pos.setItemAnimator(new DefaultItemAnimator());
            posAdapter = new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
                {
                    this.add("宿舍");
                    this.add("教室");
                    this.add("其他");
                    this.add("+");
                }
            }, context, recyclerView_pos, OptionType.POS, radioButton_noPos);
            recyclerView_pos.setAdapter(posAdapter);
            radioButton_noPos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        affairs.get(getAdapterPosition()).setLocation(0);
                        posAdapter.clearSelected();
                    } else {
                        if (posAdapter.getSelectedItemPos() == 0) {
                            affairs.get(getAdapterPosition()).setLocation(1);
                        } else {
                            affairs.get(getAdapterPosition()).setLocation(0);
                        }
                    }
                }
            });
            recyclerView_take.setLayoutManager(new StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL));
            recyclerView_take.setItemAnimator(new DefaultItemAnimator());

            takeAdapter = new OptionWhenAddBusinessAdapter(new ArrayList<String>() {
                {
                    this.add("1h");
                    this.add("2h");
                    this.add("3h");
                    this.add("+");
                }
            }, context, recyclerView_take, OptionType.TAKE, radioButton_take);
            recyclerView_take.setAdapter(takeAdapter);
            radioButton_take.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    affairs.get(getAdapterPosition()).setTakes(takeAdapter.getSelectedItemPos() + 1);
                    //因为分别是1，2,3小时，所以这块就先这么写了
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
                        highlightMe();

                    }
                }
            });
            editText_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    highlightMe();
                }
            });
            imageButton_done = (ImageButton) itemView.findViewById(R.id.imageButton_done);
            imageButton_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFold(true);
                    if (!editText_title.getText().toString().equals("")) {
                        hasEmpty = false;
                    }
                    if (!hasEmpty) {
                        addItem(getItemCount());
                        hasEmpty = true;
                    }

                    Calendar c = Calendar.getInstance();
                    affairs.get(getAdapterPosition()).setStartDay(timeStart.toCalendar().get(Calendar.DAY_OF_YEAR) - c.get(Calendar.DAY_OF_YEAR));
                    affairs.get(getAdapterPosition()).setEndDAy(timeEnd.toCalendar().get(Calendar.DAY_OF_YEAR) - c.get(Calendar.DAY_OF_YEAR));
                    affairs.get(getAdapterPosition()).setTitle(editText_title.getText().toString());

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
            textView_startDate.setText(MyPickerDialog.getDate(timeStart.getYear(),
                    timeStart.getMonth(), timeStart.getDay()));
            textView_endDate.setText(MyPickerDialog.getDate(timeEnd.getYear(),
                    timeEnd.getMonth(), timeEnd.getDay()));
            textView_startTime.setText(MyPickerDialog.getMoment(timeStart.getHour(), timeStart.getMinute()));
            textView_endTime.setText(MyPickerDialog.getMoment(timeEnd.getHour(), timeEnd.getMinute()));

        }

        private void highlightMe() {
            setFold(false);
            for (int i = 0; i < recyclerView_container.getChildCount(); i++) {
                SmartAddOneItemAdapter.ViewHolder vH = (SmartAddOneItemAdapter.ViewHolder) recyclerView_container.getChildViewHolder(layoutManager.getChildAt(i));
                if (ViewHolder.this != vH) {
                    vH.setFold(true);
                }
            }
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
            affairs.add(position, new Affair());
            notifyItemInserted(position);
        }

        private void deleteItem(int position) {
            affairs.remove(position);
            notifyItemRemoved(position);
        }

        private void setDefaultChecked() {
            radioButton_noLabel.setChecked(true);
            radioButton_noPos.setChecked(true);
            radioButton_take.setChecked(true);
        }

        private void setAutoFocus() {
            editText_title.requestFocus();
        }
    }
}
