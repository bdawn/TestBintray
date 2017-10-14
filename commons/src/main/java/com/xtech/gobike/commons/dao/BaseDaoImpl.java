package com.xtech.gobike.commons.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.xtech.gobike.commons.dao.helper.DataBaseLayer;
import com.xtech.gobike.commons.dao.helper.UserInfoHelper;
import com.xtech.gobike.commons.helper.DateUtil;
import com.xtech.gobike.commons.helper.LogUtil;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * BaseDao实现类
 */
public abstract class BaseDaoImpl<T, ID> implements BaseDao<T, ID> {

    private static final String TAG = BaseDaoImpl.class.getSimpleName();

    private Dao<T, ID> dao;

    private String namespace;

    @Override
    public void setNameSpace(String nameSpace) {
        this.namespace = nameSpace;
    }

    /**
     * 是否获取当前的用户
     */
    private boolean isCurrentDaoMode = true;

    public boolean isCurrentDaoMode() {
        return this.isCurrentDaoMode;
    }

    public void setCurrentDaoMode(boolean isCurrentDaoMode) {
        this.isCurrentDaoMode = isCurrentDaoMode;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private String getCurrentNameSpace() {
        return UserInfoHelper.getCurrentUserNameSpace();
    }

    @Override
    public Dao<T, ID> getDao() throws DaoException {
        if (isCurrentDaoMode && (namespace == null || !namespace.equals(this.getCurrentNameSpace()))) {
            namespace = this.getCurrentNameSpace();
        }
        try {
            Dao<T, ID> dao = DataBaseLayer.getInstance().getDataBaseLayer(UserInfoHelper.getCurrentUserNameSpace()).getDao(this.getBeanClass());
            this.dao = dao;
            return dao;
        } catch (Exception e) {
            throw new DaoException("class:" + this.getCurrentDaoClass() + ",namespace:" + namespace + "get dao encounter error", e);
        }
    }

    /**
     * Returns the number of rows in the table associated with the data class. Depending on the size of the table and
     * the database type, this may be expensive and take a while.
     */
    @Override
    public long countOf() throws DaoException, SQLException {
        return getDao().countOf();
    }

    /**
     * Returns the number of rows in the table associated with the prepared query passed in. Depending on the size of
     * the table and the database type, this may be expensive and take a while.
     */
    protected long countOf(PreparedQuery<T> preparedQuery) throws DaoException, SQLException {

        return getDao().countOf(preparedQuery);
    }

    @Override
    public void setDao(Dao<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> queryForAll() throws SQLException, DaoException {
        return getDao().queryForAll();
    }

    @Override
    public T queryForId(ID id) throws SQLException, DaoException {
        return getDao().queryForId(id);
    }

    @Override
    public int delete(T data) throws SQLException, DaoException {
        return getDao().delete(data);
    }

    @Override
    public int deleteById(ID id) throws SQLException, DaoException {
        return getDao().deleteById(id);
    }

    @Override
    public int deleteAll() throws SQLException, DaoException {
        return getDao().deleteBuilder().delete();
    }

    /**
     * 查询老的数据表记录，获取创建时间和更新时间
     *
     * @param data 更新的记录
     * @throws SQLException
     * @throws DaoException
     */
    private void setOldDate(T data) throws SQLException, DaoException {
        if (!(data instanceof BaseData)) {
            LogUtil.i(TAG, "setOldDate not BaseData" + data);
            return;
        }
        ID id = getDao().extractId(data);
        if (id == null) {
            LogUtil.i(TAG, "setOldDate id is null" + data);
            this.setCreateTime(data);
            return;
        }
        T oldData = getDao().queryForId(id);

        if (oldData == null) {
            LogUtil.i(TAG, "setOldDate oldData is null" + data);
            this.setCreateTime(data);
            return;
        }

        LogUtil.i(TAG, "setOldDate berfore set gmtCreateTime/updatel" + data);
        BaseData oldBaseData = (BaseData) oldData;

        BaseData newData = (BaseData) data;

        newData.setGmtCreateTime(oldBaseData.getGmtCreateTime());
        newData.setGmtUpdateTime(oldBaseData.getGmtUpdateTime());

        LogUtil.i(TAG, "setOldDate after set gmtCreateTime/update" + data);
    }

    @Override
    public Dao.CreateOrUpdateStatus createOrUpdate(T data) throws SQLException, DaoException {

        this.setOldDate(data);
        Dao.CreateOrUpdateStatus createOrUpdateStatus = getDao().createOrUpdate(data);

        if (!(data instanceof BaseData)) {
//            LogUtil.i(TAG,"createOrUpdate not BaseData"+data);
            return createOrUpdateStatus;
        }

        if (createOrUpdateStatus.isCreated()) {
//            LogUtil.i(TAG,"createOrUpdate isCreated:"+data);
            this.setCreateTimeWithUpdate(data);
        }

        if (createOrUpdateStatus.isUpdated()) {
//            LogUtil.i(TAG,"createOrUpdate isUpdated:"+data);
            this.setUpdateTimeWithUpdate(data);
        }

        return createOrUpdateStatus;
    }

    /**
     * 设置创建时间／更新时间 为当前时间
     *
     * @param data 待更新数据表
     * @throws SQLException
     * @throws DaoException
     */
    private void setCreateTime(T data) throws SQLException, DaoException {
        if (data instanceof BaseData) {
            BaseData baseData = (BaseData) data;
            baseData.setGmtUpdateTime(DateUtil.getDatedayYYYY_MM_DD_HH_MM_SS());
            baseData.setGmtCreateTime(DateUtil.getDatedayYYYY_MM_DD_HH_MM_SS());
        }
    }

    /**
     * 设置更新时间  为当前时间
     *
     * @param data 待更新数据表
     * @throws SQLException
     * @throws DaoException
     */
    private void setUpdateTime(T data) throws SQLException, DaoException {
        if (data instanceof BaseData) {
            BaseData baseData = (BaseData) data;
            baseData.setGmtUpdateTime(DateUtil.getDatedayYYYY_MM_DD_HH_MM_SS());
        }
    }

    /**
     * 设置,创建，更新时间同时更新数据表
     *
     * @param data 数据表
     * @throws SQLException
     * @throws DaoException
     */
    private void setCreateTimeWithUpdate(T data) throws SQLException, DaoException {
        if (data instanceof BaseData) {
            LogUtil.i(TAG, "setCreateTimeWithUpdate:" + data);
            this.setCreateTime(data);
            this.setUpdateTime(data);
            getDao().createOrUpdate(data);
        }
    }

    /**
     * 设置更新时间同时更新数据表
     *
     * @param data 数据表
     * @throws SQLException
     * @throws DaoException
     */
    private void setUpdateTimeWithUpdate(T data) throws SQLException, DaoException {
        if (data instanceof BaseData) {
            LogUtil.i(TAG, "setUpdateTimeWithUpdate:" + data);
            this.setUpdateTime(data);
            getDao().createOrUpdate(data);
        }
    }


    @Override
    public int update(T data) throws SQLException, DaoException {

        this.setOldDate(data);

        this.setUpdateTime(data);
        return getDao().update(data);
    }

    @Override
    public int updateId(T data, ID newId) throws SQLException, DaoException {
        this.setOldDate(data);
        this.setUpdateTime(data);
        return getDao().updateId(data, newId);
    }

    @Override
    public int create(T data) throws SQLException, DaoException {
        this.setCreateTime(data);
        return this.getDao().create(data);
    }

    @Override
    public T createIfNotExists(T data) throws SQLException, DaoException {
        T t = this.getDao().queryForSameId(data);
        if (t != null) {
            this.setCreateTime(data);
        }
        return this.getDao().createIfNotExists(data);
    }

    @Override
    public int update(PreparedUpdate<T> preparedUpdate) throws SQLException, DaoException {
        return this.getDao().update(preparedUpdate);
    }

    @Override
    public int refresh(T data) throws SQLException, DaoException {
        return this.getDao().refresh(data);
    }

    @Override
    public ID extractId(T data) throws SQLException, DaoException {
        return this.getDao().extractId(data);
    }

    @Override
    public List<T> queryTodayByCreateDate() throws SQLException, DaoException {

        return this.queryByCreateDate(DateUtil.getServerTime());
    }

    @Override
    public List<T> queryByCreateDate(Date date) throws SQLException, DaoException {
        return whereCreateTimeOneDay(date).query();
    }

    @Override
    public int delete(Where<T, ID> where) throws DaoException, SQLException {
        DeleteBuilder<T, ID> deleteBuilder = this.getDao().deleteBuilder();
        deleteBuilder.setWhere(where);
        return deleteBuilder.delete();
    }

    @Override
    public Where<T, ID> where() throws DaoException {
        return this.getDao().queryBuilder().where();
    }

    public Where<T, ID> whereCountOf() throws DaoException {
        return this.getDao().queryBuilder().setCountOf(true).where();
    }

    public Where<T, ID> whereCountOfCreateTimeOneDay(Date date) throws DaoException, SQLException {
        return this.getDao().queryBuilder().setCountOf(true).where()
                .ge(BaseData.GMT_CREATE_TIME, DateUtil.getDateBeginYYYY_MM_DD_HH_MM_SS(date))
                .and().le(BaseData.GMT_CREATE_TIME, DateUtil.getDateEndYYYY_MM_DD_HH_MM_SS(date));
    }

    public Where<T, ID> whereCountOfCreateTimeToday() throws DaoException, SQLException {
        Date date = DateUtil.getServerTime();
        return this.getDao().queryBuilder().setCountOf(true).where()
                .ge(BaseData.GMT_CREATE_TIME, DateUtil.getDateBeginYYYY_MM_DD_HH_MM_SS(date))
                .and().le(BaseData.GMT_CREATE_TIME, DateUtil.getDateEndYYYY_MM_DD_HH_MM_SS(date));
    }


    protected Where<T, ID> whereCreateTimeOneDay(Date date) throws DaoException, SQLException {
        return this.getDao().queryBuilder().where()
                .ge(BaseData.GMT_CREATE_TIME, DateUtil.getDateBeginYYYY_MM_DD_HH_MM_SS(date))
                .and().le(BaseData.GMT_CREATE_TIME, DateUtil.getDateEndYYYY_MM_DD_HH_MM_SS(date));
    }

    protected List<T> queryListByWhere(Where<T, ID> where) throws DaoException, SQLException {
        QueryBuilder<T, ID> queryBuilder = this.getDao().queryBuilder();
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }

    protected List<T> queryListByWhere(Where<T, ID> where, String orderByName) throws DaoException, SQLException {
        QueryBuilder<T, ID> queryBuilder = this.getDao().queryBuilder().orderBy(orderByName, true);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }

    protected List<T> queryListGroupByWhere(Where<T, ID> where, String groupByName) throws DaoException, SQLException {
        QueryBuilder<T, ID> queryBuilder = this.getDao().queryBuilder().groupBy(groupByName);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }

    protected List<T> queryListGroupByAndOrderBy(Where<T, ID> where, String groupByName, String orderByName) throws DaoException, SQLException {
        QueryBuilder<T, ID> queryBuilder = this.getDao().queryBuilder().groupBy(groupByName).orderBy(orderByName, true);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }

    /**
     * 降序
     *
     * @param where       条件
     * @param groupByName groupBy 字段
     * @param orderByName orderBy 排序字段
     * @return 列表
     * @throws DaoException
     * @throws SQLException
     */
    protected List<T> queryListGroupByAndOrderByDesc(Where<T, ID> where, String groupByName, String orderByName) throws DaoException, SQLException {
        QueryBuilder<T, ID> queryBuilder = this.getDao().queryBuilder().groupBy(groupByName).orderBy(orderByName, false);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }

    /**
     * 降序
     *
     * @param where       条件
     * @param orderByName orderBy 排序字段
     * @return 列表
     * @throws DaoException
     * @throws SQLException
     */
    protected List<T> queryListByNameDesc(Where<T, ID> where, String orderByName) throws DaoException, SQLException {
        QueryBuilder<T, ID> queryBuilder = this.getDao().queryBuilder().orderBy(orderByName, false);
        queryBuilder.setWhere(where);
        return queryBuilder.query();
    }


    @Override
    public List<T> queryListTodayByCreateTime() throws SQLException, DaoException {
        return queryListByWhere(whereCreateTimeToday());
    }


    protected T queryFirstByWhere(Where<T, ID> where) throws DaoException, SQLException {

        List<T> list = queryListByWhere(where);

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    protected Where<T, ID> whereCreateTimeToday() throws DaoException, SQLException {

        Date date = DateUtil.getServerTime();
        return whereCreateTimeOneDay(date);
    }

    @Override
    public int deleteBeforeToday() throws SQLException, DaoException {

        return deleteBeforeDate(DateUtil.getServerTime());
    }

    @Override
    public List<T> selectBeforeTimeByUpdateTime(Date date) throws SQLException, DaoException {
        return this.getDao().queryBuilder().where()
                .le(BaseData.GMT_UPDATE_TIME, DateUtil.formatDateYYYY_MM_DD_HH_MM_SS(date))
                .query();
    }

    @Override
    public int deleteBeforeDate(Date date) throws SQLException, DaoException {

        Where<T, ID> where = this.getDao().deleteBuilder()
                .where().lt(BaseData.GMT_CREATE_TIME, DateUtil.getDateBeginYYYY_MM_DD_HH_MM_SS(date));

        DeleteBuilder<T, ID> deleteBuilder = this.getDao().deleteBuilder();
        deleteBuilder.setWhere(where);
        return deleteBuilder.delete();
    }

}
