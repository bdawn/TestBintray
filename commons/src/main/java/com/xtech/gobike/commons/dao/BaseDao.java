package com.xtech.gobike.commons.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * dao 模版类，完成基础的dao功能
 */
public interface BaseDao<T, ID> {

    <E extends BaseDao<T, ID>> Class<E> getCurrentDaoClass();

    boolean isCurrentDaoMode();

    void setCurrentDaoMode(boolean isCurrentDaoMode);

    void setNameSpace(String nameSpace);

    Class<T> getBeanClass();

    Dao<T, ID> getDao() throws DaoException;

    void setDao(Dao<T, ID> dao);

    /**
     * 获取记录数量
     *
     * @return 记录数量
     * @throws DaoException
     * @throws SQLException
     */
    long countOf() throws DaoException, SQLException;

    /**
     * Query for all of the items in the object table. For medium sized or large tables, this may load a lot of objects
     * into memory so you should consider using the {@link Dao#iterator()} method instead.
     *
     * @return A list of all of the objects in the table.
     * @throws SQLException on any SQL problems.
     */
    List<T> queryForAll() throws SQLException, DaoException;

    /**
     * Retrieves an object associated with a specific ID.
     *
     * @param id Identifier that matches a specific row in the database to find and return.
     * @return The object that has the ID field which equals id or null if no matches.
     * @throws SQLException on any SQL problems or if more than 1 item with the id are found in the database.
     */
    T queryForId(ID id) throws SQLException, DaoException;

    /**
     * Delete the database row corresponding to the id from the data parameter.
     *
     * @param data The data item that we are deleting from the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException on any SQL problems.
     */
    int delete(T data) throws SQLException, DaoException;

    /**
     * Delete an object from the database that has an id.
     *
     * @param id The id of the item that we are deleting from the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException on any SQL problems.
     */
    int deleteById(ID id) throws SQLException, DaoException;

    /**
     * 删除全部数据
     *
     * @return 删除函数
     * @throws SQLException on any SQL problems.
     * @throws DaoException on any Dao problems.
     */
    int deleteAll() throws SQLException, DaoException;

    /**
     * This is a convenience method for creating an item in the database if it does not exist. The id is extracted from
     * the data parameter and a query-by-id is made on the database. If a row in the database with the same id exists
     * then all of the columns in the database will be updated from the fields in the data parameter. If the id is null
     * (or 0 or some other default value) or doesn't exist in the database then the object will be created in the
     * database. This also means that your data item <i>must</i> have an id field defined.
     *
     * @return Status object with the number of rows changed and whether an insert or update was performed.
     */
    Dao.CreateOrUpdateStatus createOrUpdate(T data) throws SQLException, DaoException;

    /**
     * Store the fields from an object to the database row corresponding to the id from the data parameter. If you have
     * made changes to an object, this is how you persist those changes to the database. You cannot use this method to
     * update the id field -- see {@link #updateId} .
     * <p/>
     * <p>
     * NOTE: This will not save changes made to foreign objects or to foreign collections.
     * </p>
     *
     * @param data The data item that we are updating in the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException             on any SQL problems.
     * @throws IllegalArgumentException If there is only an ID field in the object. See the {@link #updateId} method.
     */
    int update(T data) throws SQLException, DaoException;

    /**
     * Update the data parameter in the database to change its id to the newId parameter. The data <i>must</i> have its
     * current (old) id set. If the id field has already changed then it cannot be updated. After the id has been
     * updated in the database, the id field of the data parameter will also be changed.
     * <p/>
     * <p>
     * <b>NOTE:</b> Depending on the database type and the id type, you may be unable to change the id of the field.
     * </p>
     *
     * @param data  The data item that we are updating in the database with the current id.
     * @param newId The <i>new</i> id that you want to update the data with.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException on any SQL problems.
     */
    int updateId(T data, ID newId) throws SQLException, DaoException;


    /**
     * Create a new row in the database from an object. If the object being created uses
     * {@link DatabaseField#generatedId()} then the data parameter will be modified and set with the corresponding id
     * from the database.
     *
     * @param data The data item that we are creating in the database.
     * @return The number of rows updated in the database. This should be 1.
     */
    int create(T data) throws SQLException, DaoException;

    /**
     * This is a convenience method to creating a data item but only if the ID does not already exist in the table. This
     * extracts the id from the data parameter, does a {@link #queryForId(Object)} on it, returning the data if it
     * exists. If it does not exist {@link #create(Object)} will be called with the parameter.
     *
     * @return Either the data parameter if it was inserted (now with the ID field set via the create method) or the
     * data element that existed already in the database.
     */
    T createIfNotExists(T data) throws SQLException, DaoException;


    /**
     * Update all rows in the table according to the prepared statement parameter. To use this, the
     * {@link UpdateBuilder} must have set-columns applied to it using the
     * {@link UpdateBuilder#updateColumnValue(String, Object)} or
     * {@link UpdateBuilder#updateColumnExpression(String, String)} methods.
     *
     * @param preparedUpdate A prepared statement to match database rows to be deleted and define the columns to update.
     * @return The number of rows updated in the database.
     * @throws SQLException             on any SQL problems.
     * @throws IllegalArgumentException If there is only an ID field in the object. See the {@link #updateId} method.
     */
    int update(PreparedUpdate<T> preparedUpdate) throws SQLException, DaoException;

    /**
     * Does a query for the data parameter's id and copies in each of the field values from the database to refresh the
     * data parameter. Any local object changes to persisted fields will be overwritten. If the database has been
     * updated this brings your local object icon_up to date.
     *
     * @param data The data item that we are refreshing with fields from the database.
     * @return The number of rows found in the database that correspond to the data id. This should be 1.
     * @throws SQLException on any SQL problems or if the data item is not found in the table or if more than 1 item is found
     *                      with data's id.
     */
    int refresh(T data) throws SQLException, DaoException;

    /**
     * Returns the ID from the data parameter passed in. This is used by some of the internal queries to be able to
     * search by id.
     */
    ID extractId(T data) throws SQLException, DaoException;

    /**
     * 查询全部今天创建的数据
     *
     * @return 查询全部今天创建的数据
     */
    List<T> queryTodayByCreateDate() throws SQLException, DaoException;

    /**
     * 查询指定日期，0点24点直接的数据 （0点包含，24点不包含）
     *
     * @param date 查询时间
     * @return 查询指定日期，0点24点直接的数据 （0点包含，24点不包含）
     */
    List<T> queryByCreateDate(Date date) throws SQLException, DaoException;

    /**
     * 删除今天0点之前数据
     *
     * @return 删除记录数量
     * @throws SQLException
     * @throws DaoException
     */
    int deleteBeforeToday() throws SQLException, DaoException;

    /**
     * 根据where条件删除
     *
     * @param where 删除条件
     * @return 删除记录数量
     * @throws DaoException
     * @throws SQLException
     */
    int delete(Where<T, ID> where) throws DaoException, SQLException;

    /**
     * 获取默认的没有条件的where
     *
     * @return 默认的没有条件的where
     * @throws DaoException
     */
    Where<T, ID> where() throws DaoException;

    /**
     * 删除指定时间前的数据
     *
     * @param date 删除指定时间前的数据
     */
    int deleteBeforeDate(Date date) throws SQLException, DaoException;

    /**
     * 查找某时间段之前的数据
     *
     * @param date 某时间段
     * @return 某时间段之前的数据
     * @throws SQLException
     * @throws DaoException
     */
    List<T> selectBeforeTimeByUpdateTime(Date date) throws SQLException, DaoException;

    /**
     * 查询今天创建的数据
     *
     * @return 查询今天创建的数据
     * @throws SQLException
     * @throws DaoException
     */
    List<T> queryListTodayByCreateTime() throws SQLException, DaoException;
}
