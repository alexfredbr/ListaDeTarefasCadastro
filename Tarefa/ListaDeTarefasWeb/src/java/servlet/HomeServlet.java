package servlet;

import dao.TarefaDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Tarefa;
import model.Usuario;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Usuario usuarioLogado = (Usuario)sessao.getAttribute("Usuario");

        String acao = request.getParameter("acao");

        
        if(acao.equals("adicionar")){
            if(TarefaDAO.inserirTarefa(usuarioLogado, request.getParameter("tituloTarefa")) >0){
            request.setAttribute("atualizado", "Tarefa adicionada com sucesso!");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            return;
            }else{
                request.setAttribute("atualizado", "Ocorreu um erro ao adicionar a tarefa!");
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);
            return;
            }
                
        }
        if(acao.equals("listar")){
            ArrayList<Tarefa> tarefas = TarefaDAO.listarTarefas(usuarioLogado);
            request.setAttribute("listado", tarefas);
            
            RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            rd.forward(request, response);

            return;
        }
        
        if (acao.equals("alterar")) {
            String novoEmail = request.getParameter("novoEmail");
            String novaSenha = request.getParameter("novaSenha");
            if (novaSenha.length() < 5 || novaSenha.length() > 20) {
                request.setAttribute("atualizado","Ocorreu um erro ao alterar os dados");
                request.setAttribute("erroSenha", "A senha deve ter entre 5 e 20 caracteres");
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
                return;
            }
            else if (UsuarioDAO.atualizarUsuario(usuarioLogado, novoEmail, novaSenha) > 0) {
                request.setAttribute("atualizado", "Email e senha atualizados com sucesso!");
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
                return;
            } else if (UsuarioDAO.atualizarUsuario((Usuario) sessao.getAttribute("Usuario"), novoEmail, novaSenha) == -1) {
                request.setAttribute("atualizado", "Email registrado por outro usuário!");
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
                return;

            } else {
                request.setAttribute("atualizado", "Atualização não pôde ser concluída.");
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
                return;

            }
            
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
