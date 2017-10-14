package com.xtech.gobike.commons.dao.record;


import com.xtech.gobike.commons.dao.BaseDao;
import com.xtech.gobike.commons.dao.DaoException;
import com.xtech.gobike.commons.dao.RunningRecord;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 跑步记录接口
 */
public interface RunningRecordDao extends BaseDao<RunningRecord, Object> {


    /**
     * 获取跑步记录
     * @param start 查询区间
     * @param end   查询区间
     * @return 跑步记录列表
     * @throws DaoException
     * @throws SQLException
     */
    List<RunningRecord> getRunningRecordByDate(Date start, Date end) throws DaoException, SQLException;

    /**
     * 获取全部跑步记录
     * @return 跑步记录列表
     */
    List<RunningRecord> getAllRunningRecords() throws DaoException, SQLException;
}
