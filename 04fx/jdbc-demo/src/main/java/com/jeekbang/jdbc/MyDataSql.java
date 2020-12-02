package com.jeekbang.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 配置 Hikari 连接池
 */
public class MyDataSql {
    static final String dbDriver = "com.mysql.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/eplat?rewriteBatchedStatements=true";
    static final String username = "july";
    static final String password = "!QAZ2wsx";


    public static DataSource hikariDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(dbDriver);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);

        return hikariDataSource;
    }


    public static void main(String[] args) throws SQLException {
        //数据库连接
        DataSource dataSource = hikariDataSource();
        Connection conn = dataSource.getConnection();

        //订单数据
        long start=System.currentTimeMillis();
        createBigOrderData(conn,1000000);
        System.out.println("生成100万条订单数据耗时："+ (System.currentTimeMillis()-start) / 1000 + " s");

    }

    /**
     * 生成数据
     * @param conn
     * @param count
     */
    private static void createBigOrderData(Connection conn, int count) {
        String sql = "insert into t_order(id,user_id,addr_id,goods_id,unit_price,quantity,amount,actice_id,snapshot_id,real_price,real_amount)" +
                " values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            int i=0;
            while (i < count) {
                for (int j=0; j<10000 && j<count-i; j++) {
                    float unitPrice = (float) (Math.random() * (1000 + 1));
                    int quantity = (int) (Math.random() * (100 + 1));
                    float amount = unitPrice * quantity;
                    pst.setInt(1,++i);
                    pst.setInt(2, (int)( Math.random () * 4 )+1);
                    pst.setInt(3, (int)( Math.random () * (100000 + 1) ));
                    pst.setInt(4, (int)( Math.random () * (100000 + 1) ));
                    pst.setFloat(5, unitPrice);
                    pst.setInt(6, quantity);
                    pst.setFloat(7, amount);
                    pst.setInt(8, (int)( Math.random () * (100000 + 1) ));
                    pst.setInt(9, (int)( Math.random () * (100000 + 1) ));
                    pst.setFloat(10, unitPrice);
                    pst.setFloat(11, amount);
                    pst.addBatch();
                }
                pst.executeBatch();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}


