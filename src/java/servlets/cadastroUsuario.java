/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ronaldo
 */
public class cadastroUsuario extends HttpServlet {

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
        
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if("".equals(nome) || "".equals(sobrenome) || "".equals(email) || "".equals(senha) ||
           nome == null || sobrenome == null || email == null || senha == null) {
            
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.setAttribute("sobrenome", sobrenome);
            request.setAttribute("senha", senha);
            
            String cadastroerror = "Todos os campos são necessários, por favor preencha corretamente.";
            request.setAttribute("cadastroerror", cadastroerror);
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
 
        }
        
        Boolean emailExiste = false;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
            Statement statement = connection.createStatement();
           
            ResultSet resultSet = statement.executeQuery("SELECT email FROM usuarios;");
            
            while(resultSet.next()) {
                
                String bd_email = resultSet.getString("email");
                
                if(bd_email.equals(email)) {
                    
                    emailExiste = true;
                    break;   
                }
            }
            
            if(emailExiste) {
                
                String emailerror = "Esse email já foi cadastrado anteriormente!";
                
                resultSet.close();
                statement.close();
                connection.close();    
                
                request.setAttribute("emailerror", emailerror);
                request.setAttribute("nome", nome);
                request.setAttribute("sobrenome", sobrenome);
                    
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
                
            } else {
            
                String insertStr = "INSERT INTO usuarios(nome, sobrenome, email, senha) VALUES (\""+nome+"\",\""+sobrenome+"\",\""+email+"\",\""+senha+"\");";            
                int delnum = statement.executeUpdate(insertStr);

                resultSet.close();
                statement.close();
                connection.close();
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(cadastroUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        String cadastrosucesso = "Cadastro realizado com sucesso!";
        
        request.setAttribute("cadastrosucesso", cadastrosucesso);
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
      
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
