package net.bxx2004.java.sql;

import it.unimi.dsi.fastutil.Hash;
import net.bxx2004.java.sql.mysql.MySQLData;
import net.bxx2004.java.sql.mysql.MySQLDataTerm;

import java.util.HashMap;
import java.util.List;

/**
 * SQL基本理念
 */
public interface SQLBase {
    /**
     * 插入数据
     * @param data 数据
     */
    public void insert(SQLData... data);
    /**
     * 删除数据
     * @param term 条件
     */
    public void delete(SQLDataTerm... term);
    /**
     * 删除数据
     * @param data 数据
     */
    public void delete(SQLData... data);

    /**
     * 更新数据
     * @param data 数据
     */
    public void update(SQLData... data);

    /**
     * 查询数据
     * @param term 条件
     * @return 查询到的数据
     */
    public List<HashMap> select(SQLDataTerm... term);
    enum Type{
        INSERT,
        DELETE,
        UPDATE,
        SELECT
    }
}