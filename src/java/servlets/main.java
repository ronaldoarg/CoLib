/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Livro;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ronaldo
 */
public class main extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException {
        
        HttpSession session = request.getSession(false);
        
        int id = (int)session.getAttribute("id");
        String nome = (String)session.getAttribute("nome");
        if (session != null && nome != null) {
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
                Statement statement = conexao.createStatement();
                
                String queryLivros = "SELECT * from livros WHERE dono = \""+id+"\";";
                
                ResultSet consultaLivros = statement.executeQuery(queryLivros);
                
                List<Livro> livros = new ArrayList<Livro>();
                
                while(consultaLivros.next()) {
                    Livro livro = new Livro();
                    livro.setId(consultaLivros.getInt("id"));
                    livro.setNome(consultaLivros.getString("nome"));
                    livro.setAutor(consultaLivros.getString("autor"));
                    livro.setEdicao(consultaLivros.getInt("edicao"));
                    livro.setDisponivel(consultaLivros.getBoolean("disponivel"));
                    
                    livros.add(livro);
                }
         
                request.setAttribute("livrosLista", livros);
         
                consultaLivros.close();
                statement.close();
                conexao.close();
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }   
     
            String cadastroLivroError = (String)session.getAttribute("cadastroLivroError");
            
            request.setAttribute("cadastroLivroError", cadastroLivroError);         
            
            request.setAttribute("resultadoBusca", session.getAttribute("resultadoBusca"));
            request.setAttribute("busca", session.getAttribute("busca"));
            request.setAttribute("listaMensagens", session.getAttribute("listaMensagens"));
            request.setAttribute("verMensagens", session.getAttribute("verMensagens"));
           
            request.setAttribute("nome", (String)session.getAttribute("nome"));
            request.setAttribute("data", session.getAttribute("dataLogin"));
            RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
            rd.forward(request, response);
        
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
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
