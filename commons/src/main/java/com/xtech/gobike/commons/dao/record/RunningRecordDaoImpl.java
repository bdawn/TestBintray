package com.xtech.gobike.commons.dao.record;


import com.xtech.gobike.commons.dao.BaseDaoImpl;
import com.xtech.gobike.commons.dao.DaoException;
import com.xtech.gobike.commons.dao.RunningRecord;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 跑步记录实现类
 */

public class RunningRecordDaoImpl extends BaseDaoImpl<RunningRecord, Object> implements RunningRecordDao {

    @Override
    public List<RunningRecord> getRunningRecordByDate(Date start, Date end) throws DaoException, SQLException {
        return getDao().queryBuilder().where().ge("record_time",start).and().le("record_time",end).query();
    }

    @Override
    public List<RunningRecord> getAllRunningRecords() throws DaoException, SQLException {

        return getDao().queryForAll();
    }

    @Override
    public Class<RunningRecordDao> getCurrentDaoClass() {
        return RunningRecordDao.class;
    }

    @Override
    public Class<RunningRecord> getBeanClass() {
        return RunningRecord.class;
    }
}
