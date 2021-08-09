package net.bxx2004.java.sql.mysql;

import net.bxx2004.java.sql.SQL;
import net.bxx2004.java.sql.SQLBase;
import net.bxx2004.java.sql.SQLData;
import net.bxx2004.java.sql.SQLPlatForm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL基本操作
 */
public class MySQLBase implements SQLBase{
    private MySQLConnection connection;
    private String tableName;
    /**
     * 根据连接构造
     * @param connection 连接
     */
    public MySQLBase(MySQLConnection connection,String tableName){
        this.tableName = tableName;
        this.connection = connection;
    }

    @Override
    @SQL(platform = SQLPlatForm.MYSQL,type = Type.INSERT)
    public void insert(SQLData... data) {
        String namesql = "";
        String valueSize = "";
        for (SQLData arg : data){
            MySQLData mySQLData = (MySQLData) arg;
            namesql += mySQLData.row + ", ";
            valueSize += mySQLData.valueAsString() + ",";
        }
        namesql = namesql.substring(0,namesql.length() -2);
        valueSize = valueSize.substring(0,valueSize.length() -1);
        String sql = "insert into " + tableName + "(" + namesql + ") values("+ valueSize +")";
        run(sql);
    }

    @Override
    @SQL(platform = SQLPlatForm.MYSQL,type = Type.DELETE)
    public void delete(SQLData... data) {
        for (SQLData arg : data){
            String terms = "";
            MySQLData mySQLData = (MySQLData) arg;
            MySQLDataTerm[] term = mySQLData.terms;
            for (MySQLDataTerm t : term){
                terms += t.rowName + "='" + t.value + "' and ";
            }
            terms = terms.substring(0,terms.length() - 4);
            String sql = "delete from " + tableName + " where " + terms;
            run(sql);
        }
    }

    @Override
    @SQL(platform = SQLPlatForm.MYSQL,type = Type.UPDATE)
    public void update(SQLData... data) {
        String terms = "";
        for (SQLData arg : data){
            MySQLData data1 = (MySQLData) arg;
            MySQLDataTerm[] term = data1.terms;
            for (MySQLDataTerm t : term){
                terms += t.rowName + "='" + t.value + "' and ";
            }
            terms = terms.substring(0,terms.length() - 4);
            String sql = "update " + tableName + " set " + data1.row + "='" + data1.value + "' where " +  terms;
            run(sql);
        }
    }

    @Override
    @SQL(platform = SQLPlatForm.MYSQL,type = Type.SELECT)
    public List<HashMap> select(SQLData data) {
        try {
            String terms = "";
            MySQLData mySQLData = (MySQLData) data;
            MySQLDataTerm[] term = mySQLData.terms;
            for (MySQLDataTerm t : term){
                terms += t.rowName + "='" + t.value + "' and ";
            }
            terms = terms.substring(0,terms.length() - 4);
            String sql = "select * from " + tableName + "where "+terms;
            ResultSet rs = runQuery(sql);
            return asList(rs);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private void run(String sql){
        try {
            connection.getConnection().createStatement().execute(sql);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private ResultSet runQuery(String sql){
        try {
            return connection.getConnection().createStatement().executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private List<HashMap> asList(ResultSet rs){
        try {
            List<HashMap> list = new ArrayList();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                HashMap rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
