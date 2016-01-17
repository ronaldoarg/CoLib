/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ronaldo
 */
public class mensagem extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String eu = (String)session.getAttribute("nome");
        
        if (session != null && eu != null) {
            int donoDoLivro = Integer.parseInt(request.getParameter("dono"));

            String assuntoMensagem = request.getParameter("assuntoMensagem");
            String conteudoMensagem = request.getParameter("conteudoMensagem");
            
            int id = (int)session.getAttribute("id");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
                Statement statement = conexao.createStatement();
                                
                String novaMensagem = "INSERT INTO mensagens (de_id, de_nome, para, assunto, conteudo, visivel) VALUES ("+id+",\""+eu+"\","+donoDoLivro+",\""+assuntoMensagem+"\",\""+conteudoMensagem+"\","+true+")";
                
                System.out.println(novaMensagem);
                
                int delnum = statement.executeUpdate(novaMensagem);

                response.sendRedirect("main");
            
                }  catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(cadastroLivro.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            System.out.println("Eu, "+eu+", estou mandando a mensagem \""+conteudoMensagem+"\" com o assunto \""+assuntoMensagem+"\" para o ID: "+donoDoLivro);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
