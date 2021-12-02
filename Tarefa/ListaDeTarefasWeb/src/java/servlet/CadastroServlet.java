package servlet;

import dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

@WebServlet(name="CadastroServlet", urlPatterns={"/cadastro"})
public class CadastroServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Usuario u = new Usuario();
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String senhaRepetida = request.getParameter("senhaRepetida");
        u.setEmail(email);
        u.setSenha(senha);
        if(!(senhaRepetida.equals(senha))){
            request.setAttribute("erroSenha", "As senhas não coincidem!");
            RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
            rd.forward(request, response);
        }
        else if(senha.length()<5 || senha.length()>20){
            request.setAttribute("erroSenha", "A senha deve ter entre 5 e 20 caracteres");
            RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
            rd.forward(request, response);
        }else if(UsuarioDAO.checarUsuarioPorEmail(email)==(null)){
        
            UsuarioDAO.inserirUsuario(u);
            HttpSession sessao = request.getSession();
            sessao.setAttribute("Usuario", u);
            
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            
        }else{
            request.setAttribute("erroJaCadastrado","E-mail já cadastrado, tente com outro");
            
            RequestDispatcher rd = request.getRequestDispatcher("cadastro.jsp");
            rd.forward(request, response);
        }
        
        
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
