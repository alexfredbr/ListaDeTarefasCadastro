/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integracao;

import java.util.ArrayList;
import java.util.Scanner;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author frede
 */
public class Menu {

    public static void main(String[] args) {

        String email, senha;

        ArrayList<Usuario> Usuarios = new ArrayList();

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("|| INDEX ||");
            System.out.println("============");
            System.out.println("[1] fazer cadastro");
            System.out.println("[2] fazer login");
            System.out.println("[3] deletar usuario");
            System.out.println("[4] encerrar o programa");
            System.out.print("Digite a opcao: ");
            switch (scan.nextInt()) {

                case 1:
                    scan.nextLine();
                    System.out.println("|| Pagina de cadastro ||");
                    System.out.print("Digite o email: ");
                    email = scan.nextLine();
                    System.out.print("Digite a senha: ");
                    senha = scan.nextLine();
                    if (!(email.contains("@")) || senha.length() <= 5 || senha.length() >= 16) {
                        System.out.println("Email ou senha incorretos");
                        System.out.println("Email deve ter um '@'");
                        System.out.println("A senha deve ter mais de 5 caracteres e menos de 16");
                        System.out.println("pressione enter para continuar");
                        scan.nextLine();

                    } else {
                        Usuario user = new Usuario();
                        user.setEmail(email);
                        user.setSenha(senha);
                        Usuarios.add(user);
                        inserirUsuario(user);

                    }
                    break;

                case 2:
                    scan.nextLine();
                    System.out.println("|| Pagina de login ||");
                    int idUsuario = checarUsuario();
                    if (idUsuario == 0) {
                        System.out.println("Email ou senha incorretos. Redirecionando para Index...");
                        System.out.println("Pressione enter para continuar");
                        scan.nextLine();

                    } else {

                        System.out.println("Login feito com sucesso!\n");
                        while (true) {

                            System.out.println("|| Home Page ||");
                            System.out.println("===============");
                            System.out.println("[1] Listar tarefas");
                            System.out.println("[2] Listar tarefas finalizadas");
                            System.out.println("[3] Listar tarefas nao finalizadas");
                            System.out.println("[4] Adicionar nova tarefa");
                            System.out.println("[5] finalizar tarefa");
                            System.out.println("[6] remover tarefa");
                            System.out.println("[7] logout");
                            System.out.println("Digite a opcao: ");

                            switch (scan.nextInt()) {
                                case 1:
                                    scan.nextLine();
                                    Tarefa.imprimirTarefas(idUsuario);
                                    break;

                                case 2:
                                    scan.nextLine();
                                    System.out.println("|| Tarefas Finalizadas ||");
                                    Tarefa.imprimirTarefas(idUsuario, 1);
                                    break;
                                case 3:
                                    scan.nextLine();
                                    System.out.println("|| Tarefas em Aberto ||");
                                    Tarefa.imprimirTarefas(idUsuario, 0);
                                    break;
                                case 4:
                                    scan.nextLine();

                                    System.out.println("|| Nova Tarefa ||");
                                    System.out.println("=================");
                                    Tarefa.inserirTarefa(idUsuario);

                                    break;
                                case 5:
                                    scan.nextLine();
                                    System.out.println("|| Finalizar tarefa ||");

                                    while (true) {
                                        Tarefa.imprimirTarefas(idUsuario, 0);
                                        if (Tarefa.finalizarTarefa(idUsuario));
                                        {
                                            break;
                                        }
                                    }

                                    break;
                                case 6:
                                    scan.nextLine();
                                    while (true) {
                                        System.out.println("|| Remover Tarefa ||");
                                        System.out.println("====================");

                                        Tarefa.imprimirTarefas(idUsuario);
                                        if (Tarefa.removerTarefa(idUsuario)) {
                                            break;
                                        }
                                    }
                                    break;
                                case 7:
                                    scan.nextLine();
                                    System.out.println("Fazendo logout...");
                                    return;
                            }
                        }
                    }

                    break;
                case 3:
                    scan.nextLine();
                    deletarUsuario();
                    break;
                case 4:
                    scan.nextLine();
                    System.out.println("Encerrando o programa.");
                    return;
            }
        }

    }

    public static int checarUsuario() {
        try {
            Scanner scan = new Scanner(System.in);
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            System.out.print("Digite o email: ");
            String email = scan.nextLine();
            System.out.print("Digite a senha: ");
            String senha = scan.nextLine();

            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cadastro.usuario WHERE email =? and senha =?");
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void atualizarUsuario() {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            System.out.println("Digite o ID do usuario a ser atualizado");
            Scanner scan = new Scanner(System.in);
            int id = scan.nextInt();
            scan.nextLine();
            System.out.println("Digite o novo email");
            String email = scan.nextLine();
            System.out.println("Digite a nova senha");
            String senha = scan.nextLine();

            PreparedStatement stmt = conexao.prepareStatement("Update cadastro.usuario SET email = ?, senha =? WHERE id = ? ");

            stmt.setString(1, email);
            stmt.setString(2, senha);
            stmt.setInt(3, id);

            int up = stmt.executeUpdate();

            if (up > 0) {
                System.out.println("Atualização concluida com sucesso!");
            } else {
                System.out.println("Algo deu errado!");
            }
            System.out.println("pressione enter para continuar");
            scan.nextLine();

            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarUsuario() {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            System.out.println("Digite o email do usuario a ser deletado");
            Scanner scan = new Scanner(System.in);
            String email = scan.nextLine();

            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM cadastro.usuario WHERE email = ?");
            stmt.setString(1, email);

            int up = stmt.executeUpdate();

            if (up > 0) {
                System.out.println("Usuario deletado com sucesso!");
            } else {
                System.out.println("Algo deu errado!");
            }

            System.out.println("pressione enter para continuar");
            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void imprimirBanco() {
        try {

            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cadastro.usuario");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("\n id: " + rs.getInt("id"));
                System.out.println("email: " + rs.getString("email"));
                System.out.println("senha: " + rs.getString("senha"));
            }

            System.out.println("pressione enter para continuar");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirUsuario(Usuario u) {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);

            Connection conexao = DriverManager.getConnection("jdbc:mySQL://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "");

            PreparedStatement stmt = conexao.prepareStatement("INSERT INTO cadastro.usuario (email, senha) VALUES (?,?)");
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getSenha());

            int up = stmt.executeUpdate();

            if (up > 0) {
                System.out.println("Usuario cadastrado com sucesso");
            } else {
                System.out.println("Algo deu errado");
            }
            System.out.println("pressione enter para continuar");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            conexao.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
}
