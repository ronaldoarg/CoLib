/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Livro;
import classes.Mensagem;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class verMensagem extends HttpServlet {

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
        
        String nome = (String)session.getAttribute("nome");
        int id = (int)session.getAttribute("id");
        
        System.out.println("#################\nid: "+id);
        System.out.println("#################\nnome: "+nome);
     
        if (session != null && nome != null) {
    
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
                Statement statement = conexao.createStatement();
    
                String queryMensagens = "SELECT * from mensagens;";
                
                ResultSet consultaMensagens = statement.executeQuery(queryMensagens);
                
                List<Mensagem> mensagens = new ArrayList<Mensagem>();
                
                while(consultaMensagens.next()) {
                
                    int mensagemPara = consultaMensagens.getInt("para");
                    boolean mensagemVisivel = consultaMensagens.getBoolean("visivel");
                    
                    if(mensagemPara == id && mensagemVisivel) {
                        
                        Mensagem mensagem = new Mensagem();
                        
                        mensagem.setId(consultaMensagens.getInt("id"));
                        mensagem.setDe_id(consultaMensagens.getInt("de_id"));
                        mensagem.setDe_nome(consultaMensagens.getString("de_nome"));
                        mensagem.setPara(consultaMensagens.getInt("para"));
                        mensagem.setAssunto(consultaMensagens.getString("assunto"));
                        mensagem.setConteudo(consultaMensagens.getString("conteudo"));
                        mensagem.setVisivel(true);
                        
                        mensagens.add(mensagem);
                    }
                }
                
                consultaMensagens.close();
                statement.close();
                conexao.close();
         
                System.out.println("##############\nTamanho da lista de mensagens: "+mensagens.size());
                
                //session.setAttribute("listaMensagens", mensagens);
                //session.setAttribute("verMensagens", "true");
                
                request.setAttribute("listaMensagens", mensagens);
                request.setAttribute("verMensagens", "true");
                request.setAttribute("nome", nome);

                RequestDispatcher rd = request.getRequestDispatcher("/mensagens.jsp");
                rd.forward(request, response);
                                
                }  catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(pesquisa.class.getName()).log(Level.SEVERE, null, ex);
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
