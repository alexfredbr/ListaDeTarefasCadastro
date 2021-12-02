
package dao;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import model.Usuario;


public class UsuarioDAO {
    
    public static void inserirUsuario(Usuario u) {
        try (Connection conexao = Conexao.abrirConexao();PreparedStatement stmt = conexao.prepareStatement("INSERT INTO cadastro.usuario (email, senha) VALUES (?,?)");){

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getSenha());

            int up = stmt.executeUpdate();

//            if (up > 0) {
//                System.out.println("Usuario cadastrado com sucesso");
//            } else {
//                System.out.println("Algo deu errado");
//            }
//            System.out.println("pressione enter para continuar");
//            Scanner scan = new Scanner(System.in);
//            scan.nextLine();
            

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
    
    public static int atualizarUsuario(Usuario u, String email, String senha) {
        if(u.getEmail().equals(email) || checarUsuarioPorEmail(email)== null){
        try (Connection conexao = Conexao.abrirConexao();PreparedStatement stmt = conexao.prepareStatement("Update cadastro.usuario SET email = ?, senha =? WHERE id = ? ");){
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);       

            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            stmt.setInt(3, u.getId());

            int up = stmt.executeUpdate();
            return up;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // problema na atualização
        }else
        return -1; // Outro usuário tem o email registrado
    }
        
    
    
    public static void deletarUsuario(Usuario u) {
        try (Connection conexao = Conexao.abrirConexao();PreparedStatement stmt = conexao.prepareStatement("DELETE FROM cadastro.usuario WHERE email = ?");
            ) {


            stmt.setString(1, u.getEmail());

            int up = stmt.executeUpdate();

//            if (up > 0) {
//                System.out.println("Usuario deletado com sucesso!");
//            } else {
//                System.out.println("Algo deu errado!");
//            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Usuario checarUsuarioPorEmail(String email) {
        try (Connection conexao = Conexao.abrirConexao();
               PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM cadastro.usuario WHERE email =? ");){
            Usuario u = null;

            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                u = new Usuario();
                
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setId(rs.getInt("id"));
                return u;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
