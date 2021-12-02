/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import dao.TarefaDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Tarefa;
import model.Usuario;


@WebServlet(name="FinalizarServlet", urlPatterns={"/finalizar"})
public class FinalizarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String[] tarefas = request.getParameterValues("tarefas");
        HttpSession sessao= request.getSession();
        Usuario usuarioLogado = (Usuario)sessao.getAttribute("Usuario");
        
        /*para cada tarefa, o for guarda a atual na variável t*/
        for (String t : tarefas) {
            int id = Integer.parseInt(t);
            
            Tarefa tarefa = new Tarefa();
            tarefa.setId(id);
            TarefaDAO.finalizarTarefa(usuarioLogado, id);
        }
        
        response.sendRedirect("home.jsp");
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
