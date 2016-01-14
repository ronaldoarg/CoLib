/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Livro;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
public class pesquisa extends HttpServlet {

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
    
            String busca = request.getParameter("nomeDoLivro");
            CharSequence b = busca;
            
            System.out.println("busca: "+busca);
            System.out.println("b: "+b);
            
            if("".equals(busca)) {

                session.setAttribute("busca", busca);
                
                String buscaError = "Preencha o formulário para realizar uma busca";
                session.setAttribute("buscaError", buscaError);

                response.sendRedirect("main");
            } else {
                try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
                Statement statement = conexao.createStatement();
    
                String queryLivros = "SELECT * from livros;";
                
                System.out.println(queryLivros);
                
                ResultSet consultaLivros = statement.executeQuery(queryLivros);
                
                List<Livro> livros = new ArrayList<Livro>();
                
                //Boolean possui = false;
                
                while(consultaLivros.next()) {
                
                    String nomeLivro = consultaLivros.getString("nome");
                    Boolean possui = nomeLivro.contains(b);
                    
                    System.out.println("b: "+b);
                    System.out.println("nome livro: "+nomeLivro);
                    System.out.println(possui);
                    
                    if(possui) {
                        
                        Livro livro = new Livro();
                        livro.setId(consultaLivros.getInt("id"));
                        livro.setNome(consultaLivros.getString("nome"));
                        livro.setAutor(consultaLivros.getString("autor"));
                        livro.setEdicao(consultaLivros.getInt("edicao"));
                        livro.setDisponivel(consultaLivros.getBoolean("disponivel"));

                        livros.add(livro);
                    }
                }
         
                session.setAttribute("resultadoBusca", livros);
         
                response.sendRedirect("main");
                
                }  catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(pesquisa.class.getName()).log(Level.SEVERE, null, ex);
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
