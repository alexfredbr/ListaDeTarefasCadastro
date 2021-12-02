package cadastrodeusuariosmysql;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastroDeUsuariosMYSQL {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("======= BANCO DE DADOS ========");
        while (true) {
            System.out.println("Selecione a opção desejada:");
            System.out.println("[1]Inserir novo usuário");
            System.out.println("[2]Atualizar usuário existente");
            System.out.println("[3]Deletar usuário existente");
            System.out.println("[4]Imprimir Banco de Dados");
            System.out.println("[5]Sair do programa");
            switch (scanner.nextInt()) {
                case 1:
                    executarInserirUsuario();
                    break;
                case 2:
                    atualizarUsuario();
                    break;
                case 3:
                    deletarUsuario();
                    break;
                case 4:
                    imprimirBanco();
                    break;
                case 5:
                    return;
                    
            }

        }
    }

    public static void executarInserirUsuario() {
        Usuario u = new Usuario();
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o email");
        u.setEmail(scan.nextLine());
        System.out.println("Digite a senha");
        u.setSenha(scan.nextLine());
        inserirUsuario(u);
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
}
