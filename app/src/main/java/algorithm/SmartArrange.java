package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import common.Affair;

class time {
    int begin;// 某一天，几点开始的begin
    int len;

    time(int x, int y) {
        this.begin = x;
        this.len = y;
    }
}

class freetime {
    ArrayList<time> fre;
    int all;// 总闲时

    freetime() {
        fre = new ArrayList<time>();
        all = 0;
    }

    int id;// 每天的编号

    int find(int b) {// 查找这天里是否有空闲时间塞下这个启示时间为a长度为b的时间。
        int s = -1;
        int w = 0, q = 0;
        for (int i = 0; i < fre.size(); i++) {
            time tt = fre.get(i);
            if (tt.begin >= 19)
                continue;
            if (tt.len >= b) {
                if (s == -1) {
                    w = tt.len;
                    s = tt.begin;
                    q = i;
                } else {
                    if (w > tt.len) {
                        w = tt.len;
                        s = tt.begin;
                        q = i;
                    }
                }
            }
        }
        if (s != -1) {
            time tt = new time(s + b, fre.get(q).len - b);
            all -= b;
            fre.remove(q);
            fre.add(tt);
        }
        return s;
    }

    int find1(int b) {// 查找这天里是否有空闲时间塞下这个启示时间为a长度为b的时间。
        int s = -1;
        int w = 0, q = 0;
        for (int i = 0; i < fre.size(); i++) {
            time tt = fre.get(i);
            if (tt.begin < 19)
                continue;
            if (tt.len >= b) {
                if (s == -1) {
                    w = tt.len;
                    s = tt.begin;
                    q = i;
                } else {
                    if (w > tt.len) {
                        w = tt.len;
                        s = tt.begin;
                        q = i;
                    }
                }
            }
        }
        if (s != -1) {
            time tt = new time(s + b, fre.get(q).len - b);
            all -= b;
            fre.remove(q);
            fre.add(tt);
        }
        return s;
    }

    void set_secret(int begin, int last) {
        for (int i = 0; i < fre.size(); i++) {
            time pp = fre.get(i);
            if(last>=pp.begin&&pp.len+pp.begin>=begin){
                fre.remove(pp);
                time pp1;
                if (begin > pp.begin) {
                    pp1 = new time(pp.begin, begin - pp.begin);
                    fre.add(pp1);
                }
                if (pp.len + pp.begin > last) {
                    pp1 = new time(last, pp.len + pp.begin - last);
                    fre.add(pp1);
                }
                all -= Math.min(pp.len+pp.begin,last)-Math.max(pp.begin,begin);
            }
        }
    }
}

class daysort implements Comparator<freetime> {
    public int compare(freetime x, freetime y) {
        freetime a = (freetime) x;
        freetime b = (freetime) y;
        if (a.all != b.all)
            return a.all < b.all ? 1 : -1;
        return a.id > b.id ? 1 : -1;
    }
}

class ji {// 最终返回的结果
    int yes;// 是否能够成功安排
    int day;// 安排到哪一天
    int s;// 安排到几点
}

class item {
    int id;// 第几个安排
    int loca;// 1代表宿舍，0代表其他
    int cla;// 类型0代表作业，1代表与人为乐，2代表与己为乐，3代表bull
    int begin, end;// 起始日期，截止日期。
    int len;
    ji dana;// 最后返回的安排的日期和这个日期的时间

    item(int a, int b, int c, int e, int d, int f) {
        id = a;
        loca = b;
        cla = c;
        len = e;
        begin = d;
        end = f;
    }
}

class itemsort implements Comparator<item> {// 正向排序

    public int compare(item x, item y) {
        item a = (item) x;
        item b = (item) y;
        if (a.end != b.end)
            return a.end > b.end ? 1 : -1;
        if (a.begin != b.begin)
            return a.begin < b.begin ? 1 : -1;
        return a.len > b.len ? 1 : -1;
    }
}

class itemsort2 implements Comparator<item> {// 反向排序

    public int compare(item x, item y) {
        item a = (item) x;
        item b = (item) y;
        if (a.end != b.end)
            return a.end < b.end ? 1 : -1;
        if (a.begin != b.begin)
            return a.begin > b.begin ? 1 : -1;
        return a.len < b.len ? 1 : -1;
    }
}

public class SmartArrange {

    freetime day[];// 所有天数
    freetime ff[];
    int cnt;// 总天数
    item all[];// 这个变量有歧义，在这边表示某一件事情相关的所有属性
    item a[];
    item b[];
    item c[];
    item d[];
    item f[];
    int tot;// 总项目数
    ji jieguo[];// c从0~tot-1这些事件对应的安排结果

    void init(int cnt) {
        day = new freetime[cnt];
        for (int i = 0; i < cnt; i++) {
            day[i] = new freetime();
            day[i].fre.add(new time(7, 5));
            day[i].fre.add(new time(12, 7));
            day[i].fre.add(new time(19, 5));
            day[i].all += 17;
            day[i].id = i;
        }
    }

    void solve() {
        jieguo = new ji[tot];
        int x = 0, y = 0, z = 0, w = 0, q = 0;
        for (int i = 0; i < tot; i++) {
            if (all[i].loca == 1) {
                x++;
            } else {
                if (all[i].cla == 0) {
                    y++;
                } else if (all[i].cla == 1) {
                    z++;
                } else if (all[i].cla == 2) {
                    w++;
                } else {
                    q++;
                }
            }
        }
        a = new item[x];
        b = new item[y];
        c = new item[z];
        d = new item[w];
        f = new item[q];
        x = y = z = w = q = 0;
        for (int i = 0; i < tot; i++) {
            if (all[i].loca == 1) {
                a[x++] = all[i];
            } else {
                if (all[i].cla == 0) {
                    b[y++] = all[i];
                } else if (all[i].cla == 1) {
                    c[z++] = all[i];
                } else if (all[i].cla == 2) {
                    d[w++] = all[i];
                } else {
                    f[q++] = all[i];
                }
            }
        }
        Arrays.sort(a, new itemsort());
        Arrays.sort(b, new itemsort());
        Arrays.sort(c, new itemsort2());
        Arrays.sort(d, new itemsort2());
        Arrays.sort(f, new itemsort2());
        for (int i = 0; i < x; i++) {
            ff = new freetime[a[i].end - a[i].begin + 1];
            for (int j = a[i].begin; j <= a[i].end; j++) {
                ff[j - a[i].begin] = day[j];
            }
            Arrays.sort(ff, new daysort());
            int f = 0;
            ji da = new ji();
            for (int j = 0; j < cnt; j++) {
                int ww = ff[j].find1(a[i].len);
                if (ww != -1) {
                    f = 1;
                    da.yes = 1;
                    da.day = ff[j].id;
                    da.s = ww;
                    day[ff[j].id] = ff[j];
                    break;
                }
            }
            if (f == 0) {
                da.yes = 0;
            }
            jieguo[a[i].id] = da;
        }
        for (int i = 0; i < y; i++) {
            ff = new freetime[b[i].end - b[i].begin + 1];
            for (int j = b[i].begin; j <= b[i].end; j++) {
                ff[j - b[i].begin] = day[j];
            }
            Arrays.sort(ff, new daysort());
            int f = 0;
            ji da = new ji();
            for (int j = 0; j < cnt; j++) {
                int ww = ff[j].find(b[i].len);
                if (ww != -1) {
                    f = 1;
                    da.yes = 1;
                    da.day = ff[j].id;
                    da.s = ww;
                    day[ff[j].id] = ff[j];
                    break;
                }
            }
            if (f == 0) {
                da.yes = 0;
            }
            jieguo[b[i].id] = da;
        }
        while (z > 0 || w > 0 || q > 0) {
            if (z > 0) {
                ff = new freetime[c[z - 1].end - c[z - 1].begin + 1];
                for (int j = c[z - 1].begin; j <= c[z - 1].end; j++) {
                    ff[j - c[z - 1].begin] = day[j];
                }
                Arrays.sort(ff, new daysort());
                int f = 0;
                ji da = new ji();
                for (int j = 0; j < cnt; j++) {
                    int ww = ff[j].find(c[z - 1].len);
                    if (ww != -1) {
                        f = 1;
                        da.yes = 1;
                        da.day = ff[j].id;
                        da.s = ww;
                        day[ff[j].id] = ff[j];
                        break;
                    }
                }
                if (f == 0) {
                    da.yes = 0;
                }
                jieguo[c[z - 1].id] = da;
                z--;
            }
            if (w > 0) {
                ff = new freetime[d[w - 1].end - d[w - 1].begin + 1];
                for (int j = d[w - 1].begin; j <= d[w - 1].end; j++) {
                    ff[j - d[w - 1].begin] = day[j];
                }
                Arrays.sort(ff, new daysort());
                int f = 0;
                ji da = new ji();
                for (int j = 0; j < cnt; j++) {
                    int ww = ff[j].find(d[w - 1].len);
                    if (ww != -1) {
                        f = 1;
                        da.yes = 1;
                        da.day = ff[j].id;
                        da.s = ww;
                        day[ff[j].id] = ff[j];
                        break;
                    }
                }
                if (f == 0) {
                    da.yes = 0;
                }
                jieguo[d[w - 1].id] = da;
                w--;
            }
            if (q > 0) {
                ff = new freetime[f[q - 1].end - f[q - 1].begin + 1];
                for (int j = f[q - 1].begin; j <= f[q - 1].end; j++) {
                    ff[j - f[q - 1].begin] = day[j];
                }
                Arrays.sort(ff, new daysort());
                int fff = 0;
                ji da = new ji();
                for (int j = 0; j < cnt; j++) {
                    int ww = ff[j].find(f[q - 1].len);
                    if (ww != -1) {
                        fff = 1;
                        da.yes = 1;
                        da.day = ff[j].id;
                        da.s = ww;
                        day[ff[j].id] = ff[j];
                        break;
                    }
                }
                if (fff == 0) {
                    da.yes = 0;
                }
                jieguo[f[q - 1].id] = da;
                q--;
            }
        }


        for (int i = 0; i < tot; i++) {
            if (jieguo[i].yes == 0)
                as.get(i).setMeet(false);
//				System.out.println("No");
            else {
                as.get(i).setMeet(true);
                as.get(i).setDay(jieguo[i].day);
                as.get(i).setHour(jieguo[i].s);
//				System.out.println("Yes" + " " + jieguo[i].day + " " + jieguo[i].s);
            }
        }
    }

    private ArrayList<Affair> as;

    public SmartArrange(int allFreeDays, ArrayList<Affair> as) {
        this.cnt = allFreeDays;
        this.as = as;
    }

    public ArrayList<Affair> doArrange() {
        // cnt既是一个全局的静态变量也是init的参数！！！！
        //cnt已经通过参数传递
        cnt = 7;//允许的时间范围，默认为一周


        init(cnt);
        day[0].set_secret(7, 12);
        day[0].set_secret(14, 16);
        day[0].set_secret(17, 18);
        day[0].set_secret(19, 21);


        day[1].set_secret(0, 9  );
        day[1].set_secret(11, 12);
        day[1].set_secret(14, 16);
        day[1].set_secret(14, 18);
        day[1].set_secret(22, 24);
        day[2].set_secret(0, 8  );
        day[2].set_secret(11, 12);
        day[2].set_secret(14, 18);
        day[2].set_secret(19, 21);
        day[3].set_secret(0, 8  );
        day[3].set_secret(11, 12);
        day[3].set_secret(14, 18);
        day[4].set_secret(0, 7  );
        day[4].set_secret(9, 14 );
        day[4].set_secret(17, 18);
        day[4].set_secret(22, 24);
        day[5].set_secret(0, 12 );
        day[5].set_secret(17, 18);
        day[5].set_secret(19, 21);
        day[6].set_secret(0, 12 );
        day[6].set_secret(17, 18);


        tot = as.size();
        all = new item[tot];
        for (int i = 0; i < tot; i++) {
            all[i] = new item(as.get(i).getNo(), as.get(i).getLocation(), as.get(i).getKind(), as.get(i).getTakes(),
                    as.get(i).getStartDay(), as.get(i).getEndDay());
        }
        solve();
        return this.as;
    }

}