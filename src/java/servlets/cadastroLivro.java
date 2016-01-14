/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
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
public class cadastroLivro extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false);        
        String nome = (String)session.getAttribute("nome");
        int id = (int)session.getAttribute("id");
     
        if (session != null && nome != null) {

            String nomeLivro = request.getParameter("nomeLivro");
            String autorLivro = request.getParameter("autorLivro");
            String edicaoLivro = request.getParameter("edicaoLivro");

            System.out.println("Nome: "+nomeLivro);
            System.out.println("Autor: "+autorLivro);
            System.out.println("Edicao: "+edicaoLivro);

            if("".equals(nomeLivro) || "".equals(autorLivro) || "".equals(edicaoLivro)) {

                session.setAttribute("nomeLivro", nomeLivro);
                session.setAttribute("autorLivro", autorLivro);
                session.setAttribute("edicaoLivro", edicaoLivro);

                String cadastroLivroError = "Todos os campos são necessários, por favor preencha corretamente.";
                session.setAttribute("cadastroLivroError", cadastroLivroError);

                System.out.println(cadastroLivroError);

                response.sendRedirect("main");
            } else {
                try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
                Statement statement = conexao.createStatement();
                                
                String insertLivros = "INSERT INTO livros (nome, autor, edicao, dono, disponivel) VALUES (\""+nomeLivro+"\",\""+autorLivro+"\",\""+edicaoLivro+"\","+id+","+true+")";
                
                System.out.println(insertLivros);
                
                int delnum = statement.executeUpdate(insertLivros);

                response.sendRedirect("main");
            
                }  catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(cadastroLivro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
