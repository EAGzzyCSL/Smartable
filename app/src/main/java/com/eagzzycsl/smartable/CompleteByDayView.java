package com.eagzzycsl.smartable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eagzzycsl on 7/21/15.
 */
public class CompleteByDayView extends ViewPager {

    private Calendar calendar = Calendar.getInstance();


    //    private int testday = calendar.get(Calendar.DAY_OF_MONTH);
    private ArrayList<SimpleByDayView> simpleByDayViews = new ArrayList<SimpleByDayView>(3);

    class MyPagerAdapter extends PagerAdapter {
        private ArrayList<SimpleByDayView> mySimpleByDayViews;

        public MyPagerAdapter(ArrayList<SimpleByDayView> simpleByDayViews) {


            this.mySimpleByDayViews = simpleByDayViews;
        }

        @Override
        public int getCount() {
            return mySimpleByDayViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            SimpleByDayView s = mySimpleByDayViews.get(position);
            container.addView(s, 0);
            return s;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            SimpleByDayView s = mySimpleByDayViews.get(position);
            container.removeView(s);
        }
    }

    public CompleteByDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        simpleByDayViews.clear();
        simpleByDayViews.add(new SimpleByDayView(getContext(), -1, 1));
        simpleByDayViews.add(new SimpleByDayView(getContext(), 0, 1));
        simpleByDayViews.add(new SimpleByDayView(getContext(), 1, 1));
        this.setAdapter(new MyPagerAdapter(simpleByDayViews));
        this.addOnPageChangeListener(new OnPageChangeListener() {
            private boolean haveScrolled = false;

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    haveScrolled = true;
//                    testday--;
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }
                if (position == 2) {
                    haveScrolled = true;
//                    testday++;
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == 0 && haveScrolled) {
                    int scrolledY = simpleByDayViews.get(1).getScrollViewScrollY();
                    System.out.println("scrolledY:" + scrolledY);
                    simpleByDayViews.clear();

                    simpleByDayViews.add(new SimpleByDayView(getContext(), -1, scrolledY));
                    simpleByDayViews.add(new SimpleByDayView(getContext(), 0, scrolledY));
                    simpleByDayViews.add(new SimpleByDayView(getContext(), 1, scrolledY));
                    CompleteByDayView.this.setAdapter(new MyPagerAdapter(simpleByDayViews));
                    CompleteByDayView.this.setCurrentItem(1, false);
                    haveScrolled = false;
                }

            }
        });
        this.setCurrentItem(1, false);
    }

    class SimpleByDayView extends FrameLayout {
        //        public String day;
        private ScrollView scrollView;

        public SimpleByDayView(Context context, int diff, int scrollY) {
            super(context);
//            this.day = day;
            //布局：framelayout里面放了scrollview和textview，scrollview里面放了humbleByDayView
            scrollView = new ScrollView(getContext());
            scrollView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            HumbleByDayView humbleByDayView = new HumbleByDayView(getContext());
            humbleByDayView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            scrollView.addView(humbleByDayView);

            this.addView(scrollView);
//            scrollView.setScrollY(scrollY);
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            float destiny = getContext().getResources().getDisplayMetrics().density;
            int w = MyUtil.dpToPxInCode(destiny, 60);
            int h = MyUtil.dpToPxInCode(destiny, 50);

            textView.setLayoutParams(new FrameLayout.LayoutParams(w, h));
            textView.setTextAppearance(getContext(), R.style.textView_date_byDayView);


            synchronized (this) {
                //因为使用的是一个日历所以加了一个锁
                calendar.add(Calendar.DAY_OF_MONTH, diff);
                textView.setText(calendar.get(Calendar.DAY_OF_MONTH) + "\n" + MyUtil.weekEtoC(calendar.get(Calendar.DAY_OF_WEEK)));
                calendar.add(Calendar.DAY_OF_MONTH, -diff);
            }
            textView.setBackgroundColor(Color.rgb(255, 255, 255));
            this.addView(textView);
        }

        public int getScrollViewScrollY() {
            return scrollView.getScrollY();
        }


        class HumbleByDayView extends ViewGroup {
            //            final float scale = getContext().getResources().getDisplayMetrics().density;
            //view自由发挥的大小，即没有限定view大小的时候view的大小，比如wrapContent的时候
            float destiny = getContext().getResources().getDisplayMetrics().density;

            //最终经过计算后得到的view的大小
            private int myWidth;
            private int myHeight;
            //线宽和字的大小，如果线宽是奇数的话字大小宜为奇数，反之偶数
            private int lineWidth = 1;//线宽
            private int textSize = MyUtil.dpToPxInCode(destiny, 15);//文本大小，文本就是指左边的显示时间的那一个，也叫文本
            private final int topBlank = MyUtil.dpToPxInCode(destiny, 60);//顶部会空一小部分,为了美观
            private int height1h = MyUtil.dpToPxInCode(destiny, 60);//一个条的高度，不包括上下的线
            private int lineStart = topBlank + 1;//线的起始高度，即顶部空开始的下一行
            private int textStart = topBlank + lineWidth + (textSize - lineWidth) / 2;//文字的起始高度，注意文字的绘制是从左下角开始而不是左上角
            private int textPadLeft = MyUtil.dpToPxInCode(destiny, 10);//文本和文本左边的空隙
            private int linePadLeft = MyUtil.dpToPxInCode(destiny, 5);//文本和文本右边的空隙
            private int lineLeft = textPadLeft + 3 * textSize + linePadLeft;//线的左端（加入了文字占去的地）
            private int lineRight;//值为myWidth - linePadLeft,但在此处定义无效，因为myWidth还没有赋值。
            private int hpm = height1h / 60;//表示每分钟表示的高度
            private final int defaultWidth = MyUtil.dpToPxInCode(destiny, 240);   //240;
            private final int defaultHeight = 27 * height1h;
            //如果调用draw方法的时候结束坐标比起始坐标小了它依然会绘制，因为它并不区分左右先后。
            //存储事项
            private Business[] bs;
            AppCompatButton button;
            private int indicateLineY;


            public HumbleByDayView(Context context) {
                super(context);

                //构造方法
                //为这个calendarView设置一个背景色否则它无法正常显示
                setBackgroundColor(Color.rgb(255, 255, 255));

            }

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                int widthMode = MeasureSpec.getMode(widthMeasureSpec);
                int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
                int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
                //如果给定了大小的话就用给定的大小，否则用自己默认的大小
                myWidth = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                        : defaultWidth;
                myHeight = (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                        : defaultHeight;
                setMeasuredDimension(myWidth, myHeight);
//            System.out.println("onMeasure");
            }

            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                this.removeAllViews();
                if (bs != null) {
                    for (int i = 0; i < bs.length; i++) {
                        //定义事件的view并为他们添加相关属性
                        AppCompatButton businessView = new AppCompatButton(getContext());
                        businessView.setBackgroundColor(Color.rgb(60, 174, 256));
                        businessView.setText(bs[i].getName());
                        addView(businessView);
                        //计算事件被摆放的位置
                        //位置为起始加上时间折算出来的像素，每个小时的时间要多加一个横线的高度
                        //不要使用时间的差值，因为差值计算出来的小时无法判断中间有没有跨越一条横线所以不能准确绘制
                        int bt = topBlank + lineWidth + bs[i].getStart().getHour() * (height1h + lineWidth) + bs[i].getStart().getMinute() * hpm;
                        int bm = topBlank + lineWidth + bs[i].getEnd().getHour() * (height1h + lineWidth) + bs[i].getEnd().getMinute() * hpm;
                        //如果这个事件结束时间是整点的话绘制的时候减去两个像素为了美观
                        bm -= bs[i].getEnd().getMinute() == 0 ? 2 * lineWidth : 0;
                        getChildAt(i).layout(lineLeft, bt, lineRight, bm);
                    }
                }
                //先添加事件最后再添加添加事件的那个view,后添加的缘故是这样可以让它居于上面
                button = new AppCompatButton(getContext());
                button.setBackgroundColor(Color.argb(192, 30, 144, 255));
                button.setText("+");
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "show text", Toast.LENGTH_SHORT).show();
                    }
                });
//            System.out.println("constructor");
                addView(button);
                //为什么在此处声明这个view呢，和view生成时的onMeasure和onLayout有关。但是
//            System.out.println("onLayout");
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                //定义一个画笔
                Paint paint = new Paint();
                paint.setColor(Color.rgb(169, 169, 169));
                paint.setStrokeWidth(lineWidth);//线宽
                paint.setTextSize(textSize); //据说线宽和字号会冲突，但是目前没有发现
                paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
                int lineY;//线的纵坐标
                for (int i = 0; i <= 24; i++) {
                    //画日期
                    canvas.drawText(String.format("%02d", i) + ":00", textPadLeft, textStart + (height1h + lineWidth) * i, paint);
                    lineY = lineStart + (height1h + lineWidth) * i;
                    lineRight = myWidth - linePadLeft;
                    //画线
                    canvas.drawLine(lineLeft, lineY, lineRight, lineY, paint);
                }
                Calendar calendar = Calendar.getInstance();
                indicateLineY = topBlank + lineWidth + calendar.get(Calendar.HOUR_OF_DAY) * (height1h + lineWidth) + calendar.get(Calendar.MINUTE) * hpm;
                paint.setColor(Color.rgb(30, 144, 255));
                canvas.drawLine(lineLeft, indicateLineY, lineRight, indicateLineY, paint);
                paint.setColor(Color.rgb(169, 169, 169));
//            System.out.println("onDraw");
            }

            @SuppressLint("WrongCall")
            public void setBusiness(Business[] bs) {
                //一个方法，为view设置事项，应该在onStart前调用。
                this.bs = bs;
                //这儿先就凑或这么写吧，studio都怀疑怀疑我是不是调错函数了。。

                this.onLayout(false, 0, 0, 0, 0);
                System.out.println("setBusiness");
            }

            private void showAddBusiness(float eventY) {
                //显示一个类似谷歌日历的新建活动的view

                int y = (int) eventY;//点击的纵坐标
                int poor = y - lineStart;//表示点击的位置和线的起始的差值
                //当点击的范围在那24个条的范围时才显示view，最上端和最下端都不行。
                if (poor > 0 && poor < (24 * height1h + 25 * lineWidth)) {
                    //判断点击的条属于哪个条，对于每个条，上面的线属于这个条，下面的线不属于这个条。
                    //但是当那个添加的view显示的时候会把上下的线都覆盖，为了美观
                    //同样当画事件块的时候为了美观下面的条也会不属于这个事件块
                    //TODO 对于时间很短的事件块需要有一个处理，不能显示成一个细条，同样这时不能用块的宽窄来断定事件时间长度
                    int i = poor / (height1h + lineWidth);
                    //计算view该显示的位置
                    int addBusinessViewTop = lineStart + (height1h + lineWidth) * i;
                    int addBusinessViewBottom = lineStart + (height1h + lineWidth) * (i + 1) + lineWidth;
                    //系统在layout的时候坐标的计算是取坐标的左侧
                    //假设屏幕有100*100像素，则当画（20，20，80，80）的时候用掉的像素点是20-79，正好60行个点，同样，如果画2到3则粗细为1，画2-2则无结果因为粗细为0
                    button.layout(lineLeft, addBusinessViewTop, lineRight, addBusinessViewBottom);
                }
            }

            @Override
            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getEventTime() - event.getDownTime() < 200) {
                            //因为单击事件不能获取点击的位置所以用touch事件，根据手指按下和起来的时间的差值是否小于200ms来判断是不是一次点击
                            showAddBusiness(event.getY());
                        }
                        break;
                }
                return true;
                //警告：如果不return true的话会有问题，但是如果返回true了会不会对别的造成影响还不可知，默认的写法是return super.onTouchEvent(event)
            }
        }
    }
}
