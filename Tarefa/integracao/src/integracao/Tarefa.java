/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracao;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author frede
 */
public class Tarefa {

    private String titulo;
    private boolean finalizada = false;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public static void imprimirTarefas(int id) {
        try {

            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cadastro.tarefa WHERE id_usuario =?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n id: " + rs.getInt("id"));
                System.out.println("titulo: " + rs.getString("titulo"));
                System.out.println("finalizada: " + rs.getBoolean("finalizada"));
            } else {
                System.out.println("Nao ha tarefas para exibir!");
            }
            while (rs.next()) {
                System.out.println("\n id: " + rs.getInt("id"));
                System.out.println("titulo: " + rs.getString("titulo"));
                System.out.println("finalizada: " + rs.getBoolean("finalizada"));
            }

            System.out.println("pressione enter para continuar");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirTarefas(int id, int f) {
        try {

            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cadastro.tarefa WHERE id_usuario =? and finalizada =?");
            stmt.setInt(1, id);
            stmt.setInt(2, f);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n id: " + rs.getInt("id"));
                System.out.println("titulo: " + rs.getString("titulo"));
                System.out.println("finalizada: " + rs.getBoolean("finalizada"));
            } else {
                System.out.println("Nao ha tarefas para exibir!");
            }
            while (rs.next()) {
                System.out.println("\n id: " + rs.getInt("id"));
                System.out.println("titulo: " + rs.getString("titulo"));
                System.out.println("finalizada: " + rs.getBoolean("finalizada"));
            }

            System.out.println("pressione enter para continuar");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirTarefa(int id) {
        try {
            Scanner scan = new Scanner(System.in);
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO cadastro.tarefa (titulo, finalizada,id_usuario) VALUES (?,?,?)");
            System.out.println("Digite o titulo: ");

            stmt.setString(1, scan.nextLine());
            stmt.setInt(2, 0);
            stmt.setInt(3, id);

            int up = stmt.executeUpdate();

            if (up > 0) {
                System.out.println("Tarefa adicionada com sucesso!");
            } else {
                System.out.println("Algo deu errado");
            }
            System.out.println("Pressione enter para voltar para Home Page");

            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public static boolean finalizarTarefa(int id) {
        try {
            
            Scanner scan = new Scanner(System.in);
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            System.out.println("Digite o id da tarefa a ser finalizada");
            

            PreparedStatement stmt = conexao.prepareStatement("Update cadastro.tarefa SET finalizada = ? WHERE id_usuario = ? and id = ? ");

            stmt.setInt(1, 1);
            stmt.setInt(2, id);
            stmt.setInt(3, scan.nextInt());
            scan.nextLine();

            int up = stmt.executeUpdate();

            if (up > 0) {
                System.out.println("Tarefa finalizada com sucesso");
                System.out.println("pressione enter para continuar");
                scan.nextLine();
                conexao.close();
                return true;
            } else {
                System.out.println("Algo deu errado!");
                
            }
            System.out.println("pressione enter para continuar");
            scan.nextLine();

            conexao.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean removerTarefa(int id) {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            System.out.println("Digite o id da tarefa a ser deletada");
            Scanner scan = new Scanner(System.in);
            

            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM cadastro.tarefa WHERE id_usuario = ? and id = ?");
            stmt.setInt(1, id);
            stmt.setInt(2, scan.nextInt());
            scan.nextLine();
            

            int up = stmt.executeUpdate();

            if (up > 0) {
                System.out.println("Tarefa deletada com sucesso!");
                System.out.println("pressione enter para continuar");
                scan.nextLine();
                conexao.close();
                return true;
            } else {
                System.out.println("Algo deu errado! Tente outra tarefa");
            }

            System.out.println("pressione enter para continuar");
            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
