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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ronaldo
 */
public class login extends HttpServlet {

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
        
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String lembrar = request.getParameter("lembrar");
        
        System.out.println("lembrar ------------ \n"+lembrar);
        
        int idEmail = 0;
        Boolean emailExiste = false;
        Boolean senhaOk = false;
        String nome = null;
        
        if("".equals(email) || "".equals(senha)) {
            
            request.setAttribute("email", email);
            
            String loginError = "Todos os campos são necessários, por favor preencha corretamente.";
            request.setAttribute("loginError", loginError);
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);    
        }
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/colib?zeroDateTimeBehavior=convertToNull", "root", "admin");
            Statement statement = connection.createStatement();
           
            ResultSet consultaEmail = statement.executeQuery("SELECT email, id FROM usuarios;");
            
            while(consultaEmail.next()) {
                String bdEmail = consultaEmail.getString("email");
                if(bdEmail.equals(email)) {
                    //Email existe no banco
                    emailExiste = true;
                    idEmail = consultaEmail.getInt("id");
                    
                    break;
                }
            }
            
            consultaEmail.close();
            
            if(!emailExiste) {

                statement.close();
                connection.close();
                
                String loginError = "Email não cadastrado! <br/> Cadastre-se no formulário ao lado ou insira um email válido.";
                request.setAttribute("loginError", loginError);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            } else {
                
                ResultSet consultaSenha = statement.executeQuery("SELECT * FROM usuarios WHERE id = "+idEmail+";");

                while(consultaSenha.next()) {
                    String bdSenha = consultaSenha.getString("senha");
                    nome = consultaSenha.getString("nome");
                    if(bdSenha.equals(senha)) {
                        senhaOk = true;
                    }
                }

                consultaSenha.close();
            
                if(!senhaOk) {

                    statement.close();
                    connection.close();

                    String loginError = "Senha inválida! <br/> Tente novamente.";
                    request.setAttribute("loginError", loginError);
                    request.setAttribute("email", email);
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);

                } else {

                    statement.close();
                    connection.close();

                    HttpSession session = request.getSession(true);
                    if(lembrar != null) {
                        Cookie cookieEmail = new Cookie("colib.login.email", email);
                        Cookie cookieSenha = new Cookie("colib.login.senha", senha);
                        cookieEmail.setMaxAge(2629743);
                        cookieSenha.setMaxAge(2629743);
                        response.addCookie(cookieEmail);
                        response.addCookie(cookieSenha);
                    }
                    session.setAttribute("nome", nome);
                    session.setAttribute("dataLogin", new Date());
                    session.setAttribute("id", idEmail);
                    
                    response.sendRedirect("main");
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
