package love.lxy.hbk.hl.Beans;

import java.io.Serializable;

import love.lxy.hbk.hl.R;

/**
 * Created by 19216 on 2019/8/3.
 * 每一个恋爱时期描述
 */

public class LoveProcess implements Serializable {

    private int id;
    private boolean blank_tag = false;  // 是否是空白，“未完待续”

    private int description_imgID = R.drawable.boy1;
    private String description, time, long_description;

    private int stageImgID1 = R.drawable.ours, stageImgID2 = R.drawable.ours1;
    private String img_path1 = "", img_path2 = "";

    private boolean changeable = true;  // 是否可以修改该历程

    public LoveProcess() {}

    public LoveProcess(String time) {
        this.time = time;
        this.blank_tag = false;
    }

    public LoveProcess(int description_imgID, String time) {
        this.description_imgID = description_imgID;
        this.time = time;
        this.blank_tag = false;
    }

    public LoveProcess(int description_imgID, String description, String time) {
        this.description_imgID = description_imgID;
        this.description = description;
        this.time = time;
        this.blank_tag = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescription_imgID() {
        return description_imgID;
    }

    public void setDescription_imgID(int description_imgID) {
        this.description_imgID = description_imgID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public int getStageImgID1() {
        return stageImgID1;
    }

    public void setStageImgID1(int stageImgID1) {
        this.stageImgID1 = stageImgID1;
    }

    public int getStageImgID2() {
        return stageImgID2;
    }

    public void setStageImgID2(int stageImgID2) {
        this.stageImgID2 = stageImgID2;
    }

    public boolean isBlank_tag() {
        return blank_tag;
    }

    public void setBlank_tag(boolean blank_tag) {
        this.blank_tag = blank_tag;
    }

    public String getImg_path1() {
        return img_path1;
    }

    public void setImg_path1(String img_path1) {
        this.img_path1 = img_path1;
    }

    public String getImg_path2() {
        return img_path2;
    }

    public void setImg_path2(String img_path2) {
        this.img_path2 = img_path2;
    }

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    @Override
    public String toString() {
        return "LoveProcess{" +
                "id=" + id +
                ", blank_tag=" + blank_tag +
                ", description_imgID=" + description_imgID +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", long_description='" + long_description + '\'' +
                ", stageImgID1=" + stageImgID1 +
                ", stageImgID2=" + stageImgID2 +
                ", img_path1='" + img_path1 + '\'' +
                ", img_path2='" + img_path2 + '\'' +
                ", changeable=" + changeable +
                '}';
    }

}
