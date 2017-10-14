package com.xtech.gobike.commons.api;

import com.xtech.gobike.commons.dao.DaoException;
import com.xtech.gobike.commons.dao.RunningRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * 跑步记录API
 */
public interface RunningRecordApi {

    /**
     * 插入跑步记录
     *
     * @param runningRecord 跑步记录
     * @return 记录id
     * @throws DaoException
     * @throws SQLException
     */
    int saveRunningRecord(RunningRecord runningRecord);

    /**
     * 删除跑步记录
     *
     * @return 记录改变条数
     * @throws DaoException
     * @throws SQLException
     */
    int deleteRunningRecord(RunningRecord runningRecord);

    /**
     * 获取跑步记录
     *
     * @param start 查询区间
     * @param end   查询区间
     * @return 跑步记录列表
     * @throws DaoException
     * @throws SQLException
     */
    List<RunningRecord> getRunningRecordByDate(long start, long end);

}
