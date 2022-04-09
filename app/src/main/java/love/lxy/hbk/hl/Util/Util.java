package love.lxy.hbk.hl.Util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import love.lxy.hbk.hl.Beans.LoveProcess;
import love.lxy.hbk.hl.R;

/**
 * Created by 19216 on 2019/8/1.
 * 工具类
 */

public class Util {

    public static boolean background_music = true;
    public static boolean background_heart = false;
    public static boolean background_click_heart = true;

    public static boolean[] loveTest = {false, false, false, false, false};

    // 自定义固定的历程的条目数
    public static int CURRENT_PROCESS_LENGTH = getProcessData().size();

    public static int[] descripImgID = {R.drawable.process1, R.drawable.process2, R.drawable.process3,
            R.drawable.process4, R.drawable.process5, R.drawable.process6, R.drawable.process7,
            R.drawable.process8, R.drawable.process9, R.drawable.process10, R.drawable.process11,
            R.drawable.process12, R.drawable.process13, R.drawable.process14, R.drawable.process15,
            R.drawable.process16, R.drawable.process17, R.drawable.process18, R.drawable.process19,
            R.drawable.process20, R.drawable.process21, R.drawable.process22, R.drawable.process23,
            R.drawable.process24, R.drawable.process25, R.drawable.process26, R.drawable.process27,
            R.drawable.process27, R.drawable.process28, R.drawable.process29, R.drawable.process30,
            R.drawable.process31, R.drawable.process32};

    public static int LOVE_YEAR = 2017, LOVE_MONTH = 11, LOVE_DAY = 18;
    public static int CURRENT_YEAR = 2019, QIXI_MONTH = 8, QIXI_DAY = 7;

    public static String love_apeech1 = "        遇见你是我一生最幸福的事，我爱你，我的傻宝宝，让我一直陪着你吧，forever！";

    public static String love_speech2 = "        初三与你的相识，大学与你的重识，是命中注定的一生一世，与你的相爱，是命中注定的地老天荒。\n" +
            "        今生与你携手徜徉天地间，为你遮风挡雨，相濡以沫，并肩悠游滚滚红尘中。\n" +
            "        即使有一天你的步履变得蹒跚，青丝变成白发，红润的脸上爬满了皱纹，但我仍要携着你的手，漫步在夕阳的余晖下。\n" +
            "        你，我一生最爱的人；你，我一生最想的人；你，我一生守候的人；你，我一生唯一的人。\n" +
            "        不论你生病或是健康，富有或贫穷，始终忠於你，直到离开世界。\n" +
            "        遇见你是我一生最幸福的事，我爱你，我的傻宝宝，让我一直陪着你吧，forever！";

    // 存储登录信息文件名
    public static String LOGIN_DATA = "login_data";

    // 支持的手机号
    public static String MY_DEAR_PHONE = "15927037762"; // 15537842050  15927037762

    static public int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    static public int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    static public int dipToPx(Context context, int dip) {
        int px;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        px = dip * metrics.densityDpi / 160;
        return px;
    }

    // 开启心跳动画
    static public List<ObjectAnimator> startHeartJumpAnimator(ImageView imageView) {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageView,
                "scaleX", 1f, 2f, 1f)
                .setDuration(800);
        objectAnimatorX.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorX.start();

        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView,
                "scaleY", 1f, 2f, 1f);
        objectAnimatorY.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimatorY.setDuration(800).start();

        List<ObjectAnimator> animators = new ArrayList<>();
        animators.add(objectAnimatorX);
        animators.add(objectAnimatorY);
        return animators;

//        ScaleAnimation scaleAnimation = new ScaleAnimation(
//                1f, 2f, 1f, 2f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(1000);
//        scaleAnimation.setRepeatCount(ValueAnimator.INFINITE);
//        imageView.startAnimation(scaleAnimation);

    }

    public static void valueAlphaAnimation(final TextView textView) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                textView.setAlpha(value);
                Log.i("动画执行：", "" + value);
            }
        });
        valueAnimator.start();

    }

    // 简单判断输入的手机号是否合法
    public static boolean checkPhoneValid(String phoneNumber) {
        String expression = "((^(13|15|18|17|14)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phoneNumber);
        String phone = "[1][34578]\\d{9}";
        return matcher.matches();
    }

    // 判断今天是否是七夕节
    public static boolean isQiXi() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // month == Util.QIXI_MONTH && day == Util.QIXI_DAY
        return month == QIXI_MONTH && day == QIXI_DAY;
    }

    // 七夕弹窗提示
    public static void ToastQiXi(Context context) {
        if (isQiXi()) {
            Toast.makeText(context, "大傻傻，七夕节快乐！我不在身边也要开开心心的呦！",
                    Toast.LENGTH_LONG).show();
        }
    }

    // 判断是否到时间
    public static boolean checkIsReachTime(boolean isAhead) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        boolean realReach = false;
        if (year > CURRENT_YEAR) {
            realReach = true;
        } else if (year == CURRENT_YEAR){
            if (month > QIXI_MONTH) {
                realReach = true;
            } else if (month == QIXI_MONTH) {
                if (day >= QIXI_DAY) {
                    realReach = true;
                }
            }
        }
        if (isAhead)
            return realReach || (hour > 22)
                    || (hour == 22 && minute >= 30);
        else
            return realReach;
    }

    // 初始化爱情历程数据
    public static List<LoveProcess> getProcessData() {

        List<LoveProcess> loveProcessList = new ArrayList<>();

        LoveProcess loveProcess;

        loveProcess = new LoveProcess(R.drawable.process1, "2017年11月18日");
        loveProcess.setDescription("那一天，我怀着激动的心情做了一夜火车去了你那里，我要当面对你说“我爱你”。" +
                "傻傻的我怀里揣着一枚戒指，第一次确定关系就给了你一枚戒指，套上后，就是我的人了，以后再也不分开...");
        loveProcess.setLong_description("        那一天，我怀着激动的心情做了一夜火车去了你那里，我要当面对你说“我爱你”。" +
                "傻傻的我怀里揣着一枚戒指，第一次确定关系就给了你一枚戒指，套上后，就是我的人了，以后再也不分开。" +
                "还记得咱们当时一块吃啵啵鱼，去清明上河园玩，看各种表演节目；还有晚上在虹桥上聊天，天很冷，" +
                "但是抱着你很温暖、很温暖。");
        loveProcess.setStageImgID1(R.drawable.process1_1);
        loveProcess.setStageImgID2(R.drawable.process1_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process2, "2017年12月10日");
        loveProcess.setDescription("那一次，在我的软磨硬泡下，你第一次来武汉，为我过生日，也是你第二次一个人出远门...");
        loveProcess.setLong_description("        你第一次来武汉，有开心的也有不开心的。咱们确认关系后的第二次见面，" +
                "想想就很开心激动，我想着每天都能见到你，每天跟你不分开。那次，我第一次深深地吻了你，永不会忘记。" +
                "那天，我生日，咱们买了蛋糕，你为我唱歌，你活蹦乱跳的，超级可爱。不过，那天我也伤害了你，" +
                "对你有所隐瞒，亲爱的，我是爱你的，我会用一辈子来弥补你。");
        loveProcess.setStageImgID1(R.drawable.process2_1);
        loveProcess.setStageImgID2(R.drawable.process2_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process3, "2018年01月18日");
        loveProcess.setDescription("盼望着盼望着，终于放假了，我早已按奈不住我激动的心情，时隔一个多月，我们终于" +
                "能够再次见面了，等你一考完试，我就直接过去找你了...");
        loveProcess.setLong_description("        时隔一个多月，咱们再次见面，对彼此的爱又多了一分。正是放寒假，那次" +
                "咱们先给我办了一张年卡，为了去逛各种旅游景点。咱们去了河大老校区，爬上了城楼亲了你（其他人爬" +
                "上来又尴尬地下去了）；咱们吃了张亮麻辣烫；咱们去了万岁山，好多刺激的项目，那个吊桥，玩得很开心，" +
                "你不敢走，我在旁边扶着你一步一步走，以后我也会的，一直跟你一块走，forever...");
        loveProcess.setStageImgID1(R.drawable.process3_1);
        loveProcess.setStageImgID2(R.drawable.process3_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process4, "2018年02月14日");
        loveProcess.setDescription("那天，情人节，也是咱们在一起的第一个情人节，偷偷准备的礼物你还喜欢吗？");
        loveProcess.setLong_description("        在情人节前几天，我就在网上看了礼物，看到了情侣手链，当时就很开心，" +
                "咱们就都是彼此的人了。我偷偷买了礼物，躲着家人偷偷取快递。到了情人节那天，偷偷去你们村附近接你，" +
                "路上好像碰到了我爸妈，不过他们没看到咱们。一切都是偷偷摸摸的，咱们去了小树林，我把手链送给了你，" +
                "小傻瓜，你是不是很开心呐❤，反正我是很开心的了。");
        loveProcess.setStageImgID1(R.drawable.process4_1);
        loveProcess.setStageImgID2(R.drawable.process4_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process5, "2018年02月16日");
        loveProcess.setDescription("过年啦，亲爱哒，新年快乐！咱们才见过两天，但是大年初一我还是想见你，所以" +
                "咱们在城里约起来吧...");
        loveProcess.setLong_description("        新年快乐，大傻傻！大年初一咱们一般都没有事情，所以在我的请求下你给你" +
                "爸妈编个理由说跟一个女同学去城里玩会，所以你爸妈才放心让你出来。我还是去了你村附近接你，我骑着" +
                "电车载你去城里，路上有说有聊，很开心。咱们在公园逛了逛，吃了棉花糖（不是很好吃），玩了套圈" +
                "（我第一次玩），吃了有意思，最后咱们去了瑶瑶家里，还见了杨晗。一天下来，跟你待在一块真的很开心。");
        loveProcess.setStageImgID1(R.drawable.process5_1);
        loveProcess.setStageImgID2(R.drawable.process5_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process6, "2018年02月18日");
        loveProcess.setDescription("紧张的大年初三，才见面两天，不行，我还是很想你，一日不见，如隔三秋，不过这次" +
                "见面却发生了尴尬的事...");
        loveProcess.setLong_description("        那天你说你家里没人了，只剩下你一个人，所以我说我现在去找你，说去就去，" +
                "直接骑着电车过去了，不过出去后我才发现电车电不是很多了，但是我还是过去了。见到你后，你吵了我一顿，" +
                "但是见到你我还是很开心。没了电所以咱们不能骑车出去了，所以就去了你奶奶家里冲了电。一会有人去你奶奶家" +
                "，我自己躲在屋子里不敢出去，这感觉是不是有点怪呐（偷笑）...");
        loveProcess.setStageImgID1(R.drawable.process6_1);
        loveProcess.setStageImgID2(R.drawable.process6_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process7, "2018年02月23日");
        loveProcess.setDescription("马上就要开学了，开学后想要再见到你就不是很容易了，不开心，不如咱们临走之" +
                "前再见一次吧...");
        loveProcess.setLong_description("        转眼间，咱们相恋三个月了，有你在我感觉我是最幸福的人。不过，马上就要" +
                "开学了，回到学校后见面不是很容易，所以决定要在临走之前我要相约我的小可爱。咱们到了城里，把车子" +
                "停在了网吧门口，去里面逛了一圈，你突然发现，咦，口袋里的红色毛爷爷不见了，我就拿出来一张说在这呢，" +
                "直接放你手机壳里了。后来咱们见了你的朋友娄明月，是我第一次见你的朋友，有点不自然。咱们还去了沙滩公园，" +
                "我还欠你一块放风筝。最后咱们回去的路上，真的很不舍，因为这次分开后再见面不知道什么时候，不想跟你分开" +
                "。我回家后莫名发现你塞给我的毛爷爷也不见了，咱们俩都傻了吧...大傻傻~二傻傻");
        loveProcess.setStageImgID1(R.drawable.process7_1);
        loveProcess.setStageImgID2(R.drawable.process7_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process8, "2018年04月05日");
        loveProcess.setDescription("清明放假了，开心，计划好了你来武汉这里一块玩，开心开心，时隔一个多月咱们终于可以" +
                "再次见面了...");
        loveProcess.setLong_description("        啦啦啦，终于要见面了，安耐住激动的小心情，把我这边的事收拾好。不过早晨去" +
                "火车站接你却迟到了，对不起。不过见到你后还是抱着你，让你受伤了。先回宾馆歇一下，吃点东西。" +
                "咱们在我们学校逛了一下，拍了美美的照片，去奥山逛了商场，买了吃的；咱们去武大，逛了校园，不过樱花却没有了；" +
                "咱们去光谷逛了书店、风情街、教堂，拍了美美的照片；咱们去了长江大桥、吹着长江的风，看着长江夜景；咱们还去了" +
                "落雁峰景区，很开心。不过最不开心的就是分开，看着你进站，我一直站在火车站外面静静的看着...");
        loveProcess.setStageImgID1(R.drawable.process8_1);
        loveProcess.setStageImgID2(R.drawable.process8_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process9, "2018年05月01日");
        loveProcess.setDescription("五一假期啦，好不容易一个假期，一定要好好利用起来，约起来呐！不过却发生了不好的事...");
        loveProcess.setLong_description("        这学期的第二个假期，一定要约起来。我过去之前买了周黑鸭、给你挑了两个皮筋，" +
                "然后坐了一夜火车去了你那里，一路上都是开心的。到地方后，咱们俩第一次穿了情侣衣，粉红色的，很可爱。" +
                "然后咱们去了开元广场，看了电影，电影有点沉重，后来因为我不太会关心你咱们关系闹得有点僵。最后处于分手的" +
                "边缘，这是咱们俩的第一次大吵。到了晚上，你和你同学吃了饭，然后我过去了找你，咱们回去聊了不少，然后最后直接去了" +
                "大姐家里去，她们家没人。\n        第二天咱们俩直接一块回家了。过几天后，咱们关系一直处于不冷不热状态，为了不" +
                "让你生气，我绞尽脑汁怎样道歉才有诚意，我写了一封信，然后去了三福店买了礼品盒，买了一个耳机，买了一个小猪猪，" +
                "我还为你做了一个简单的视频（也许不是很完美），最后在礼品盒里塞满了溜溜梅。亲爱的，对不起，我当时什么都闷在心里不给你说" +
                "，以后不会这样了，我会好好守护着你，一直...");
        loveProcess.setStageImgID1(R.drawable.process9_1);
        loveProcess.setStageImgID2(R.drawable.process9_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process10, "2018年06月10日");
        loveProcess.setDescription("一个月了，最近你每天都在做兼职，特别累，有时候周末有时间了想出去走走我也" +
                "没在你身边，所以，我要过去，好好陪你...");
        loveProcess.setLong_description("        原先说的六一儿童节过去，那几天邻近高考辅导员不让走人，所以就往后推迟了几天，" +
                "过去见你真的很开心的，有点迫不及待。那天，咱们在小树林里拍了美美的照片，你超级美丽，超萌。咱们去了星光天地，" +
                "我们骑了一辆电车，你坐在篮子里，很可爱，很甜蜜；咱们抓了娃娃（没抓到，不开心）；咱们去鼓楼请你舍友吃了郭大侠；" +
                "晚上吃完后因为下雨让大姐过来把咱们送了回去。\n        第二天，咱们逛了超市，去大姐家里吃了饭，很开心，大傻傻，" +
                "我好喜欢你呦。不过晚上我不得不坐火车回去了，不开心...");
        loveProcess.setStageImgID1(R.drawable.process10_1);
        loveProcess.setStageImgID2(R.drawable.process10_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process11, "2018年08月17日");
        loveProcess.setDescription("七夕节了，不过我整个暑假都在留校复习考研，提前回家吧，给你给surprise...");
        loveProcess.setLong_description("        整个暑假你在打工，而我在准备复习考研，你有时候上夜班，咱们俩的时间正好相差开来，" +
                "所以每天视频的时间也不是很长。不过七夕节到了，我要做一个大事情，提前回家去郑州找你，给你一个惊喜。" +
                "正好那天你也休班，老天都眷顾我们，哈哈，开心。我借人手机让别人给你打电话说你的“快递”到了，看到我" +
                "后有没有很开心呐，小可爱。那天，咱们去了人民公园，去逛了地下商场，买了一个小红裙，很可爱，晚上回去" +
                "你那里，咱们在门口广场休息，我躺在你怀里，真的很开心开心开心...");
        loveProcess.setStageImgID1(R.drawable.process11_1);
        loveProcess.setStageImgID2(R.drawable.process11_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process12, "2018年08月26日");
        loveProcess.setDescription("期待着，期待着，我们终于迎来了我们的第一次旅行---青岛之行");
        loveProcess.setLong_description("        那天，你离职完后我直接在火车站等你，然后把咱们俩一块做了一夜火车去往咱" +
                "们的目的地：青岛。青岛的风景很美，有这么美的你在身边，是世界上最幸福的事。早上咱们到了地方，买了点" +
                "早饭，去了宾馆；然后下午穿上买的小红裙，带上红色的披肩，咱们一块去了金沙滩，体验大海的冲击，你一直在" +
                "喊“天在转、地在转”，十分开心。晚上，咱们去坐了摩天轮（琴岛之眼），一共13分钟14秒，听着《最浪漫的事》，" +
                "有你在，真的是很浪漫，在6分37秒最高点相吻，两人会一直在一起的，咱们就是的！");
        loveProcess.setStageImgID1(R.drawable.process12_1);
        loveProcess.setStageImgID2(R.drawable.process12_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process13, "2018年08月27日");
        loveProcess.setDescription("青岛之行第二天，打卡栈桥公园、青岛海底世界、八大关、五四广场");
        loveProcess.setLong_description("        第二天，咱们原先说去海边看日出，不过因为天气原因没有看到日出，不过早晨" +
                "在海边跟你一块吹吹海风真的是很温馨的事。咱们在海边，我写下了咱们来的记号，这里有着咱们的共同的足迹。" +
                "然后咱们去了栈桥，没有网上说的海鸥，可能时节没赶上；下午咱们先去了海底世界，见识了各种各样的海底动物，" +
                "很美丽；然后咱们去了八大关，以八大著名关隘命名；晚上咱们去了五四广场，不过却没能赶上灯光秀，不开心。");
        loveProcess.setStageImgID1(R.drawable.process13_1);
        loveProcess.setStageImgID2(R.drawable.process13_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process14, "2018年08月28日");
        loveProcess.setDescription("第三天，打开信号山、大学路、浙江路天主教堂");
        loveProcess.setLong_description("        时间好快，还没好好玩耍就已经到了最后一天了，不管了，还是先要好好玩好。" +
                "早晨也要美美哒，在火车站旁边臭美几张，开心；去了信号山，爬上了最高点，坐上了旋转观景台，可以看到" +
                "青岛的整个风景，红瓦绿树，碧海蓝天，能和你一块体验真的是超级幸福的事；咱们又去了大学路，偶尔几家" +
                "精品店，美美的吊椅，收藏屋，很有格调；出了大学路咱们打卡网红路口，大学路鱼山路；最后咱们去了天主教堂，" +
                "逛了千遇千寻，吃了九龙餐厅里的虾和海草，很开心，很幸福，买了一个狗一个猪；还有咱们的青岛老酸奶。" +
                "幸福的时光总是很快，就要坐火车回去了，有一丝不舍，分开是下次更好的再见，期待下次和你再来到这座美丽的城市," +
                "我的傻猪猪❤...");
        loveProcess.setStageImgID1(R.drawable.process14_1);
        loveProcess.setStageImgID2(R.drawable.process14_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process15, "2018年09月11日");
        loveProcess.setDescription("小可爱，生日快乐呦！不过我却没能过去陪着你，第二天，我又过去了...");
        loveProcess.setLong_description("        亲爱的，生日那天，我没有好好关心你，让你度过一个完美的生日，对不起。" +
                "第二天，我来了，咱们一块吃饭、一块去图书馆学习，然后请了马文杰吃饭，下午咱们去了西湖，不过因为" +
                "下雨咱们提前回去了；在亭子里聊了好久，她一直当咱们的电灯泡，不开心；一会她走了咱们又去了西湖，古灵精怪" +
                "的大傻傻，好喜欢你；晚上咱们回去吃了顿饭，然后去操场坐着聊天，躺在操场上，很舒服，我想一直这样下去...");
        loveProcess.setStageImgID1(R.drawable.process15_1);
        loveProcess.setStageImgID2(R.drawable.process15_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process16, "2018年10月03日");
        loveProcess.setDescription("国庆啦，假期好长呦，先回家几天，然后一起约起来呀，好开心好开心...");
        loveProcess.setLong_description("        国庆假期好几天，咱们一定要多待几天。咱们先是都回家呆了几天，然后约好3日" +
                "一块回开封。因为回去着急，你钥匙忘了带了，所以找宿管阿姨重新配了一把钥匙；然后咱们买了吃的就直接回去了。" +
                "回到学校后，咱们躺在操场上，我抱着你，看着夜空，不过我更喜欢看你，你真的好美好美，跟你的所有都好幸福。" +
                "晚上我回去了大姐家里。\n        第二天，我早早过去找你，吃了早饭，一会大姐说今天咱哥生日，让咱们过去一块吃饭，" +
                "所以咱们去超市买了一些吃的然后就过去了，很开心。下午咱们去河大逛了逛，晚上继续咱们的操场约会，跟你约会总是约不够。" +
                "\n        第三天，咱们去了西湖，去了沙滩，我在西湖那边第一次背你，你反应很大（偷笑）。晚上咱们去了河大的操场，坐着聊天，跟龙" +
                "也聊了一下。3,2,1，我追上你了，哈哈。\n        第四天，咱们去了星光天地，买了U型枕，去超市买了吃的，在门口别告知可以抽奖，" +
                "我抽中了大奖，花钱又买了一个项链，之后才发现是一个骗子，骗术。晚上，最后一个晚上，咱们最后去了操场，我一会就要走了，" +
                "坐火车回学校，不想跟你分开，好想一直这样每天跟你在一起...");
        loveProcess.setStageImgID1(R.drawable.process16_1);
        loveProcess.setStageImgID2(R.drawable.process16_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process17, "2018年11月17日");
        loveProcess.setDescription("大傻傻，咱们马上就要一周年了，很开心，我真的好爱你，我花了好长时间给你准备" +
                "的礼物希望你能喜欢呦...");
        loveProcess.setLong_description("        咱们18号一周年，所以我提前一天过来了，我来之前买了周黑鸭，不过你说我" +
                "乱花钱，我想给你买点周黑鸭给你一个小惊喜，不过却适得其反，最后惹得你有点不开心就睡觉了。你生气快消气也快，" +
                "第二天就没事了，早上我到你学校，你因为有实验，所以我就自己先去图书馆一会，然后回去了宾馆住的地方；一会去" +
                "了咱大姐家里，然后她说的给你网上买了一谈化妆品，所以我就等快递到了后下午过去你学校找你。然后咱们一块吃个饭，" +
                "你身体不舒服需要热水，然后回到宿舍后接点热水提前休息了，等待明天的到来...");
        loveProcess.setStageImgID1(R.drawable.process17_1);
        loveProcess.setStageImgID2(R.drawable.process17_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process18, "2018年11月18日");
        loveProcess.setDescription("亲爱哒，一周年快乐呦！！！傻猪猪，我好喜欢你呦！！！一年下来，咱们有开心，又难过，" +
                "发生了许多事情，但是跟你在一块真的好幸福，好喜欢你❤❤❤...");
        loveProcess.setLong_description("        今天是咱们的一周年呦，晚上做梦梦到了你，很开心。大早晨，我从宾馆出来，给你" +
                "买了瓶热的真果粒，记得上一年咱们刚开始的时候我去你学校你给我买了一瓶热的真果粒，很甜很好喝，所以今天" +
                "我也要给你买一瓶。我带着我为你准备的礼物，《恋上有你的日子》，我为你出的第一本书。你看到后很开心，觉得" +
                "我很厉害，看你开心我也真的很开心。然后咱们去逛了一些景区，龙亭、天波杨府、翰园，你身体不舒服，我一直给你接热水。" +
                "中午咱们去吃了水浒烤肉；下午咱们去了河大校园、去了铁塔公园，在草坪上晒晒太阳。累了一天你身体也不舒服，" +
                "咱们就一块回宾馆歇息了");
        loveProcess.setStageImgID1(R.drawable.process18_1);
        loveProcess.setStageImgID2(R.drawable.process18_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process19, "2018年11月19日");
        loveProcess.setDescription("一周年之后的一天，想要一直陪着你，一直...");
        loveProcess.setLong_description("        今天，因为昨天很累，咱们今天起来的很晚，回到学校后先教你弄了一下电脑里的Excel数据" +
                "，中午吃了顿饭，饭后咱们去了星光天地。咱们一块在里面的K吧里唱歌，宝宝你唱的很好听，我好喜欢，咱们俩当时" +
                "唱的歌我现在还有的呦，想听的时候就会打开听一听，很开心。晚上，咱们一块回学校上课，然后临走之前去超市买了牛奶和" +
                "吃的，你给了我你买的礼物行李箱，我拿着它很开心，但是要走了，我很不舍，我会一直想你的，这次的分开是下次更好的相见，" +
                "爱你，我的小可爱❤...");
        loveProcess.setStageImgID1(R.drawable.process19_1);
        loveProcess.setStageImgID2(R.drawable.process19_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process20, "2018年12月24日");
        loveProcess.setDescription("平安夜，圣诞节，我来啦，咱们终于有了共同的时间，我过来了，开心...");
        loveProcess.setLong_description("        距离咱们上次见面一个多月了，我还是特别想要去见面，但是因为咱们俩时间一直" +
                "赶不上，所以最后趁着这个圣诞节这两天，我过来了。24日早上，我早早的到了你的学校，外面天很冷，但是" +
                "见到你后我直接抱着你，所有的寒冷都没有了，只剩下无尽的温暖和幸福，抱着你就想拥有了全世界。咱们上午去自习室，" +
                "你上午有考试，所以我就去了图书馆，然后中午一块吃饭。下午回去休息了一下，晚上的时候你要上课；后来咱们直接去了" +
                "食堂二楼的沙发，都是小情侣，人很多，很热闹。平安夜我不应该听你的不买苹果，而我只买了一排娃哈哈。\n        第二天，咱们" +
                "先上课，做实验，然后去了星光天地，逛了一些小店，在星光天地吃了麻辣烫，逛了衣服店，没有挑到好合适的衣服，" +
                "咱们又去唱了歌，我一直在听我的麦霸小可爱唱歌，晚上咱们回去买了好多吃的，然后去了食堂沙发，又是好多小情侣（偷笑）。不过，" +
                "我希望他们不要跟咱们抢位置，因为我一会就要走了，只剩下不长的时间让咱们在一块了。时间好快，不想跟你分开，" +
                "咱们每次都是匆匆见面匆匆分开，真想快点结束这样的日子，一直跟你在一块...");
        loveProcess.setStageImgID1(R.drawable.process20_1);
        loveProcess.setStageImgID2(R.drawable.process20_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process21, "2019年03月02日");
        loveProcess.setDescription("亲爱的，咱们好长时间没有见面了，真的好想你呀，等着我，我来了...");
        loveProcess.setLong_description("        距离咱们上次见面已经有三个多月了，这是咱们最长时间没有见面。因为寒假期间" +
                "你在上海打工，没有在家，而等你回来的时候，我已经开学会学校了，所以咱们一直没有见面。好不容易等你开学" +
                "回到学校了，第一个周末，我就来到了你的学校，忍不住内心的开心，飞奔过来看你。你也是早早的起来了，在食堂等着我，" +
                "咱们一块吃了饭，然后歇一下，一块出去鼓楼逛街了，买了三个漂亮的耳坠，买了顶你喜欢的帽子，很开心。" +
                "\n        第二天，咱们收拾好后直接去吃了饭，然后下午去了河大小西门，在那里你为我买了牛仔裤、衬衫和毛衣针织衫，" +
                "很好看，型男的第一步；咱们又去星光天地逛了街，在超市买了零食，回来买了锁骨和卤菜，很好吃。晚上，咱们去了咱们的" +
                "约会打卡地点，操场，一起坐着聊天很舒服。晚上我走的时候你给我的香水火车站不让带，好烦啊，这是你给我的" +
                ",最好的东西居然不让我带（大哭）...");
        loveProcess.setStageImgID1(R.drawable.process21_1);
        loveProcess.setStageImgID2(R.drawable.process21_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process22, "2019年04月04日");
        loveProcess.setDescription("清明节啦，一起来武汉玩耍吧，我在这边租了一个新的小窝，很温馨的呦，还有咱们" +
                "美美哒的照片，想看吗？");
        loveProcess.setLong_description("        你是3日晚上下的火车，我早早在火车站等着你，等你一下来就领着我的小迷糊回去了，" +
                "先吃了份面，然后回去和我的舍友聊了好一会，直接歇息了。第二天，早上起来我为你简单做了一顿饭，熬了一锅粥。" +
                "咱们吃过后又去了华科，我去了实验室，你去了那个温馨的书屋；下午咱们去了光谷，拍了许多美美的照片，逛了世界城商场，" +
                "然后咱们去华科菜市场买了一些菜，我舍友做了一两个菜，我做了两个菜，很好吃，明天继续为我的小可爱做饭吃...");
        loveProcess.setStageImgID1(R.drawable.process22_1);
        loveProcess.setStageImgID2(R.drawable.process22_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process23, "2019年04月05日");
        loveProcess.setDescription("清明节第一天，咱们去欢乐谷吧，超级刺激的各种游戏，我想和你一块体验，一直都记得咱们" +
                "一起经历的事情...");
        loveProcess.setLong_description("        早上我为你做了早饭，亲爱的，是不是很好吃呐，以后我一直做给你吃。然后咱们直接去了" +
                "欢乐谷，因为是假期，排队的人特别多。进去后，咱们要体验的第一个项目就是过山车，最刺激的一个，拍了好长时间的队" +
                "，终于到咱们上车了，咱们在第一排，你坐里面，我在最外面，紧张激动的心情。管理员一声“发车啦”，瞬间出去了，" +
                "直接冲到最高点，又俯冲下来，旋转，全都大呼小叫，你在旁边一直喊“啊啊，不玩了，要下去”，结束后，你的眼睛都" +
                "湿了，最刺激的一分钟，永生难忘吧。之后咱们又体验了刺激的项目大摆锤、咱们玩了一个骑自行车的项目、左转盘的项目、" +
                "还有玩水的野人谷漂流、激流勇进，全身都湿透了，最后被我骗去了一个鬼屋，亲爱的你知道吗，其实我当时也有点害怕，" +
                "不过有你在我就不怕了。咱们又做了一个两层的旋转木马，咱们又去看了一个4D电影鲁滨逊漂流记，然后看了灯展巡游；" +
                "最后咱们体验了一个7D的飞跃长江，很美的风景，在峡谷中飞行、看着瀑布、看着峡谷中水面的船只，由小到大，由远及近，" +
                "绿色的风景，飞到海底看海底的世界，与各种鱼亲密接触，飞到世外桃源、大草原，与骏马飞驰...,很美丽的风景，" +
                "还想和你一块体验。今天一天玩的真的很开心，啦啦啦...");
        loveProcess.setStageImgID1(R.drawable.process23_1);
        loveProcess.setStageImgID2(R.drawable.process23_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process24, "2019年04月06日");
        loveProcess.setDescription("清明节第二天，咱们去野营吧，我背着帐篷，带上咱们的零食，向东湖进发进发...");
        loveProcess.setLong_description("        昨天一天体验了太多的东西，太累了，所以咱们今天安安静静地好好歇一天，去东湖野营。" +
                "咱们先去超市买了好多吃的，然后我背上帐篷，带上吃的，直接出发。下了地铁，等一下，日常找厕所（偷笑），先去了厕所。" +
                "扫码免费送了，“我们不要男生”，扎心了，男生不让扫，给你少了一个蝴蝶结，好美丽。咱们在东湖慢慢走，找合适的搭帐篷的地方，" +
                "看到了一个风情园，正好有位置，开始搭帐篷喽，我可是学过的呦。搭好帐篷，开吃东西，怎么能不拍照呢，当然要拍了，" +
                "看摄像头，哇，好美呀，超级可爱。咱们一直待到了下午5点，然后收拾东西回去了，先去华科买了菜，然后回家后我" +
                "为你做了美美的饭菜，开心开心...");
        loveProcess.setStageImgID1(R.drawable.process24_1);
        loveProcess.setStageImgID2(R.drawable.process24_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process25, "2019年04月07日");
        loveProcess.setDescription("清明节第三天，咱们先好好歇息一下，去逛商场，挑一下合适的衣服...");
        loveProcess.setLong_description("        清明假期的最后一天了，晚上就要和你分开了，好不舍。上午咱们就一直在一起吧" +
                "，哪里也不去了，只好好地陪着你。咱们一块看会电视，然后中午我去做饭，美美的吃了一顿，很开心。下午咱们去了" +
                "世界城广场，看了好多衣服，不过里面的衣服有点贵，你试了两个裙子都特别的好看、特别有仙气，不过看到" +
                "价格后瞬间泄气了，因为价格太贵了，现在咱们都买不起，那时候我就在想，亲爱的，我一定好好努力挣钱，以后让你" +
                "想买什么衣服就买什么衣服。然后咱们去逛了超市，随便买了一些吃的，然后咱们又买了好多廖记棒棒鸡店的虾，" +
                "就是想让你带走的，因为你很喜欢吃，而且你们那里没有卖的。最后咱们吃过饭，我把你送到火车站，依依不舍，" +
                "不想跟你分开，不想让你走，紧紧地抱着你抱着你...");
        loveProcess.setStageImgID1(R.drawable.process25_1);
        loveProcess.setStageImgID2(R.drawable.process25_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process26, "2019年04月30日");
        loveProcess.setDescription("五一假期到了，亲爱的，你期待着想要见我吗？反正我是很期待着见到你的呦~");
        loveProcess.setLong_description("        五一假期，我提前一天回去了，30号就直接到了你学校，时隔4周我们又见面了，我真的" +
                "很开心，发现跟你见面是世界上最开心幸福的事。我下了公交你接到我然后咱们吃了早饭，一块去上课，很喜欢这种感觉。" +
                "然后中午咱们去了宾馆那边，买了一些凉菜还有馒头，很好吃。午休了一会后咱们下午一块去上课。放学后，咱们买了" +
                "好多吃的，然后吃过饭后感觉没事就去开元广场看了电影《何以为家》，但是因为咱们太着急，而且刚吃过饭吃的有点多，" +
                "所以咱们看了半个小时电影后你就有点不舒服了，咱们就出去了。然后在外面呼吸一下新鲜空气，在长凳子上坐着聊聊天，" +
                "然后就一块回去了。咱们明天上午一块回家的火车...");
        loveProcess.setStageImgID1(R.drawable.process26_1);
        loveProcess.setStageImgID2(R.drawable.process26_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process27, "2019年05月03日");
        loveProcess.setDescription("在家里了，你给你妈妈说咱们要去城里约会，你妈妈同意了，开心...");
        loveProcess.setLong_description("        今天，你在周营吃过饭、我在堂哥家吃过饭，然后我就直接骑车去接你咱弟弟一块去" +
                "城里了，在涧岗会上咱们碰到了你的妈妈，然后我打了一句招呼后咱么就直接走了。咱们先去了公园，因为很少" +
                "带咱弟弟出去玩，所以他想玩什么咱们就玩什么，在公园里玩了几个游戏，买了一些玩具，然后咱们直接去了城里面去了。" +
                "咱们买了蜜雪冰城，我跟钰波吃的很开心，不过你却不能吃，因为肚子...。后来咱们逛了博雅，然后给钰波买了个短袖，" +
                "不过中间我妈她们却偷偷跟上了咱们，我很生气，回去后就说了她们一顿，把她给说哭了，亲爱的，我是不是不应该这样呐。" +
                "咱们从城里回去后，我直接把你们送到了你们庄附近，因为车子没电了所以没有把你们送到家里，我回去的路上走一段" +
                "路然后骑一段路慢慢回到了家...");
        loveProcess.setStageImgID1(R.drawable.process27_1);
        loveProcess.setStageImgID2(R.drawable.process27_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process28, "2019年05月25日");
        loveProcess.setDescription("520，我爱你，亲爱的，给你买的口红你是不是不太喜欢呐，下次再买一个你喜欢的");
        loveProcess.setLong_description("        原本520礼物我准备的相框，咱们真是心有灵犀，我刚买了相框然后你就直接在你们学校的" +
                "学姐那买了一个相框，所以我就把我买的相框放在了我这里，我用了，然后我又重新买了一只MAC口红，不过没想到你" +
                "不是很喜欢我买的这个颜色，下次再买一个其他的。到了25日早上，我到了郑州，因为这几天你直接去郑州找刘瑞洁了，" +
                "所以正好我也过去然后请她吃个饭，我是第一见她。咱们逛了逛公园，然后吃了王婆大虾；然后下午的时候咱们见了咱姐，" +
                "跟她说了一些事情，然后咱们又吃了一顿王婆大虾。之后咱们直接做高铁回了开封，休息了一下。\n        第二天，咱们说的一块" +
                "去见我妈妈，你精心收拾了后然后咱们去买了一些东西直接过去了。一块吃了顿饭，一块聊聊天，不过我妈妈做的太不好了，" +
                "她做的没有足够尊重你，而我之后也是考虑不周，伤害了你，对不起亲爱的，我不会这样了，这次后我知道了很多，我会" +
                "跟我妈妈说我的不好，让她不要觉得我很优秀，我也会夸你，说咱们在一块很开心，我只想和你在一起，一直...");
        loveProcess.setStageImgID1(R.drawable.process28_1);
        loveProcess.setStageImgID2(R.drawable.process28_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process29, "2019年06月01日");
        loveProcess.setDescription("六一儿童节，宝宝节日快乐，但是发生了这么多事我没能让你快乐，对不起...");
        loveProcess.setLong_description("        因为最近这事我一直没有做好，一直在伤害着你，所以你也一直在生气迈不过去心里的坎，" +
                "所以我今天过来了。先过儿童节，其他的不说，我在网上买了好多儿童的零食，好多咱们之前小时候经常吃的，比如" +
                "西瓜泡泡糖、大大口香糖、果丹皮、跳跳糖、娃哈哈、小鱼，好多吃的，小猪猪你喜欢吃不呐，你之前说过想吃" +
                "西瓜糖，不过一下子买了一大瓶子是不是有点多了...");
        loveProcess.setStageImgID1(R.drawable.process29_1);
        loveProcess.setStageImgID2(R.drawable.process29_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process30, "2019年06月15日");
        loveProcess.setDescription("我要毕业了，想要回去陪着你，想和你一块上课，一块学习，我这次买了一束花，却没有" +
                "达到效果...");
        loveProcess.setLong_description("        想着毕业典礼结束后直接回家好长一段时间，不过导师说项目验收，不让我回去，所以我就" +
                "临时决定提前到今天回去，我早晨的火车，然后下午到开封，我没有提前告诉你，因为你上午有六级考试，我害怕影响你" +
                "考试所以就直接在你学校里等着你。来的时候我准备了草稿，我自己也在宾馆里一个人捧着一束花说了好几次，因为我之前" +
                "都是什么都没有准备就过来了，不知道要说啥，所以我这次想好了，把自己也打扮的帅帅的。等你考完试，我就给你打电话，" +
                "你边走边说，我在你宿舍楼下等着你，我捧着花，单膝跪地，我想着我要说的话，你打断我，一直笑，让我起来，最后突然忘了" +
                "准备的话了，最后只说了“我想你，我离不开你，不想失去你，咱们重归于好好吗”。最后我让你强收下了花，然后一块去" +
                "食堂吃了饭，然后就去操场上坐着谈谈心，你给我掏耳朵，好开心，好久没有体验你给我掏耳朵了。\n" +
                "        第二天的时候你早早来了宾馆这边，然后我下去接你，一块买了早饭，然后你洗洗澡收拾一下，因为外面天热，所以咱们" +
                "一块看了几集电视，然后还看了鬼电影，你害怕不呐（偷笑）。下午的时候咱们去星光天地，去超市买了一些吃的，逛了商场，" +
                "然后打道回府学校了，咱们晚上吃了大餐大盘鸡，还有我买的剩下的虾，很丰盛。然后咱们去了操场，坐着聊天，感觉拥有你就" +
                "像拥有的全世界，亲爱的，我离不开你，想你~");
        loveProcess.setStageImgID1(R.drawable.process30_1);
        loveProcess.setStageImgID2(R.drawable.process30_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process31, "2019年07月09日");
        loveProcess.setDescription("你放假了，你是小仙女，我是大猪蹄子...");
        loveProcess.setLong_description("        你考试结束后还是买了火车票过来了，在我试探得知你来了后，我就把所有的事情都推了，" +
                "买了虾，还有其他一些零食，回去洗洗澡，把房间打扫打扫，喷喷香水。一切收拾完毕后就直接过去火车站了，等着你下车。" +
                "你一出火车站我就直接拉住了你，然后直接打车回去了，因为我知道你没有买到有座位的票。一路上我问你话你也" +
                "不回答，我知道你心里面还有点生气。咱们回去吃过饭就直接休息了。\n        第二天咱们随便吃了一点东西就直接去华科了，你让我去" +
                "实验室我没去，咱们一块去了书屋，看书，然后中午一块吃饭，然后回去后晚上和同学一块走走学校。\n        第三天，咱们去了楚河汉街，" +
                "先去了万达购物广场，咱们好长时间没有一块吃好吃的了，所以我在里面看了一家“外婆家”，环境很特别，吃的特很好；" +
                "咱们在里面买了一杯乐乐茶，然后还拍了许多美美哒的照片；然后去汉街那边逛了各种衣服店；晚上咱们就直接去火车站" +
                "坐火车回开封了...");
        loveProcess.setStageImgID1(R.drawable.process31_1);
        loveProcess.setStageImgID2(R.drawable.process31_2);
        loveProcessList.add(loveProcess);

        loveProcess = new LoveProcess(R.drawable.process32, "2019年07月12日");
        loveProcess.setDescription("一块回家，第一次一块从武汉回去...");
        loveProcess.setLong_description("        咱们坐了一夜的火车一回到开封后就直接去了宾馆，因为一晚上都没有睡好。回去休息好后咱们" +
                "晚上去了你们学校操场开始跑步，因为在华科的时候我没有跑所以这次一定要趁这个机会好好跑，跑它30圈，一圈都不能少。" +
                "亲爱的你知道吗，我最多跑的只有6圈，然后就累的要死，你一开始说30圈，我感觉应该没问题，事实证明只要还有一点" +
                "力气在，宝宝说多少我都能跑下去。最后30圈下来整个人已经累瘫了，回去的时候都是你扶着我回去的。然后回去后咱们直接" +
                "休息了。\n        第二天，因为昨天太累了所以上午一直在休息，然后下午咱们去吃了郭大侠，很开心；咱们有了咱们的“宝宝”；咱们买了衣服，" +
                "买了大大的耳环、买了乐高；\n        第三天，咱们去逛了星光天地，在里面逛了商场、排队淘金（淘到了三个珠宝）、去超市买东西；" +
                "回去学校后吃过饭在操场拍了美美的照片，最后晚上我和我们的小黄人“儿子”依依不舍的回去了，我和儿子都会想你的，小可爱~");
        loveProcess.setStageImgID1(R.drawable.process32_1);
        loveProcess.setStageImgID2(R.drawable.process32_2);
        loveProcessList.add(loveProcess);

        for (LoveProcess lp : loveProcessList) {
            lp.setChangeable(false);
        }

        return loveProcessList;
    }

    public static LoveProcess getBlankProcess() {
        LoveProcess loveProcess = new LoveProcess();
        loveProcess.setBlank_tag(true);
        loveProcess.setChangeable(false);
        return loveProcess;
    }

    // 设置View的字体
    public static void setViewTypeface(View view, Typeface typeface) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = ((ViewGroup) view);
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++)
            {
                setViewTypeface(parent.getChildAt(i), typeface);
            }
        }
        else if(view instanceof TextView){
            TextView textview = (TextView)view;
            textview.setTypeface(typeface);
        }
    }

    /**
     * 根据角度获取爱心的某一个点坐标
     * @param angle 待获取的该角度对应的点
     * @param scale 心的尺度大小
     * @param offsetX 心相对于中心的X偏移，一般取画布宽的一半
     * @param offsetY 心相对于中心的Y偏移，一般取画布高的一半
     * @return
     */
    public static Point getHeartPoint(float angle, int scale, int offsetX, int offsetY) {
        float t = (float) (angle / Math.PI);
        float x = (float) (scale * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-scale * (13 * Math.cos(t) - 5 * Math.cos(2 * t)
                - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    /**
     * 获取心形路径点集合
     * @param width 界面的宽
     * @param height 界面的高
     * @param heartRadio 心形图案的大小；值越大图案越大，可以取 30
     * @param angleSpace 心形图案的点的稀疏程度；值越大越稀疏，可以取 0.5f
     * @return
     */
    public static List<Point> getHeartPath(int width, int height, int heartRadio, float angleSpace) {

        List<Point> points = new ArrayList<>();

        int offsetX = width / 2, offsetY = height / 2;
        int scale = heartRadio * width / 1080;


        float angle = 10;
        while (angle < 180) {
            points.add(getHeartPoint(angle, scale, offsetX, offsetY));
            angle += angleSpace;
        }

        return points;

    }



}
