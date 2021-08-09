package net.bxx2004.java.sql;

/**
 * SQL操作实现
 */
public interface SQLHandler {
    /**
     * 建立SQL连接
     */
    public void set(SQLConnection connection);

    /**
     * 建表
     */
    public void createTable(String name, boolean primary_key_ID, SQLData... data);

    /**
     * 删表
     */
    public void deleteTable(String name);
}
