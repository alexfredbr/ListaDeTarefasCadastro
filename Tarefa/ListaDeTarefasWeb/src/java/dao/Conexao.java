/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Aluno
 */
public class Conexao {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Driver driver = null;

    private static void registrarDriver() {

        if (driver == null) {
            try {
                Driver d = new Driver();
                DriverManager.registerDriver(d);
                driver = d;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    protected static Connection abrirConexao() {
        registrarDriver();
        try {
            Connection c = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
