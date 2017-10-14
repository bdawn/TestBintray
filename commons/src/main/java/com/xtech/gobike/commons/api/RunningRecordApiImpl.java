package com.xtech.gobike.commons.api;

import com.xtech.gobike.commons.dao.DaoFactory;
import com.xtech.gobike.commons.dao.RunningRecord;
import com.xtech.gobike.commons.dao.record.RunningRecordDao;

import java.util.List;

public class RunningRecordApiImpl implements  RunningRecordApi {

    public RunningRecordApiImpl() {
    }

    @Override
    public int saveRunningRecord(RunningRecord runningRecord) {
        try {
            RunningRecordDao deviceDao = DaoFactory.getDao(RunningRecordDao.class);
            deviceDao.createOrUpdate(runningRecord);
            return runningRecord.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteRunningRecord(RunningRecord runningRecord) {
        return 0;
    }

    @Override
    public List<RunningRecord> getRunningRecordByDate(long start, long end) {
        return null;
    }
}
