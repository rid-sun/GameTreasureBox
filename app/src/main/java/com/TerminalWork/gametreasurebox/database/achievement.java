package com.TerminalWork.gametreasurebox.database;

import org.litepal.crud.LitePalSupport;

/*
 * 作者：JiaTai Sun
 * 时间：20-5-26 下午5:27
 * 类名：achievement
 * 功能：用来存储游戏成就
 * 补充：这部分限于时间本人并没有被实现
 */

public class achievement extends LitePalSupport {
    private long ID;
    private String name;
    private String game_type;
    private int score_2048;
    private int checkpointID;
    private int steps;
    private String time;

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public int getScore_2048() {
        return score_2048;
    }

    public void setScore_2048(int score_2048) {
        this.score_2048 = score_2048;
    }

    public int getCheckpointID() {
        return checkpointID;
    }

    public void setCheckpointID(int checkpointID) {
        this.checkpointID = checkpointID;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
