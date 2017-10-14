package com.xtech.gobike.commons.helper.eventbus;

/**
 * 上传跑步记录之后返回跑步排名
 * Created by GX on 2017/9/19.
 */

public class RankEvent {

    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
