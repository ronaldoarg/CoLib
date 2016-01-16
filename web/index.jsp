<%-- 
    Document   : index.jsp
    Created on : Jan 6, 2016, 9:02:28 PM
    Author     : ronaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="bootstrap.jsp" %>
        <title>CoLib</title>
    </head>
    
    <%  
        String nome = (String)request.getAttribute("nome");   
        String sobrenome = (String)request.getAttribute("sobrenome");
        String email = (String)request.getAttribute("email");   
        String senha = (String)request.getAttribute("senha");
        String cadastroSucesso = (String)request.getAttribute("cadastrosucesso");
        String cadastroError = (String)request.getAttribute("cadastroerror");
        String loginError = (String)request.getAttribute("loginError");
        String emailError = (String)request.getAttribute("emailerror"); 

        String emailCookie = "";
        String senhaCookie = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("colib.login.email")) {
                    emailCookie = c.getValue();
                } if (c.getName().equals("colib.login.senha")) {
                    senhaCookie = c.getValue();
                }
            }
        }
    %>
    
    <body>
        <div class="container">
            <div class="page-header text-center">
                <h1>CoLib <small>Um sistema colaborativo de empréstimos de livros</small></h1>
            </div>
            
            <!-- Login -->
            <form class="col-lg-3 col-lg-offset-2" action="login" method="POST">
                <h3>Login</h3>
                <% if (loginError != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= loginError %>
                </div>
                <%}%>
                <div class="form-group" id="emailLogin">
                    <label for="exampleInputEmail1">Email</label>
                    <input type="email" class="form-control" id="exampleInputEmail1" <%if(email!=null){%>value="<%=email%>"<%} else {%>value="<%= emailCookie%>"<%}%> name="email" placeholder="Email">
                </div>
                <div class="form-group" id="senhaLogin">
                    <label for="exampleInputPassword1">Senha</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" name="senha" value="<%= senhaCookie%>" placeholder="Senha">
                </div>
                <div class="checkbox">
                  <label>
                    <input type="checkbox" name="lembrar">Lembrar-me
                  </label>
                </div>
                <button type="submit" class="btn btn-primary pull-right">Entrar</button>
            </form>
            
            <!-- Cadastro -->
            <form class="col-lg-3 col-lg-offset-2" action="cadastroUsuario" method="POST">
                <h3>Cadastre-se</h3>
                <% if (cadastroError != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= cadastroError %>
                </div>
                  
                <%}%>
                
                <div class="form-group" id="cadastronome">
                    <label for="inputNome">Nome</label>
                    <input type="text" class="form-control" id="InputNome" name="nome" <%if(nome!=null){%>value="<%=nome%>"<%}%> placeholder="Nome">
                </div>
                <div class="form-group" id="cadastrosobrenome">
                    <label for="inputSobrenome">Sobrenome</label>
                    <input type="text" class="form-control" id="InputSobrenome" name="sobrenome" <%if(sobrenome!=null){%>value="<%=sobrenome%>"<%}%> placeholder="Sobrenome">
                </div>
                
                <% if(emailError != null) { %>
                
                <div class="alert alert-danger" role="alert">
                    <%= emailError %>
                </div>
                <%}%>
                  
                <div class="form-group" id="cadastroemail">
                    <label for="inputEmail1">Email</label>
                    <input type="email" class="form-control" id="InputEmailCadastro" name="email" placeholder="Email">
                </div>
                <div class="form-group" id="cadastrosenha">
                    <label for="inputPassword1">Senha</label>
                    <input type="password" class="form-control" id="InputSenhaCadastro" name="senha" placeholder="Senha">
                </div>
                <button type="submit" class="btn btn-primary pull-right">Enviar</button>
            </form>
        
            <% if (cadastroSucesso != null) {%> 
           
            <!-- Modal -->
            <div class="modal fade bg-success" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Cadastro realizado com sucesso! Faça login para continuar.</h4>
                        </div>
                        <div class="modal-footer">
                            
                            <form class="col-lg-6 col-lg-offset-3 text-left" action="login" method="POST">

                                <div class="form-group">
                                    <label for="exampleInputEmail1">Email</label>
                                    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Email">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Senha</label>
                                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                                </div>
                                <button type="submit" class="btn btn-primary pull-right">Entrar</button>
                            </form> <br/><br/><br/>
                            
                        </div>
                    </div>
                </div>
            </div>
           
            <%}%>
            
            <script>
                $('#myModal').modal('show');
            
                <% if (emailError != null) { %>
                
                    $("#cadastroemail").addClass('has-error');
                
                <% } if (cadastroError != null) { %>
                    
                    $("#cadastronome").addClass('has-error');
                    $("#cadastrosobrenome").addClass('has-error');
                    $("#cadastroemail").addClass('has-error');
                    $("#cadastrosenha").addClass('has-error');
                   
                <%} 
                if (loginError != null) {
                    if (email == null) { %>
                        $("#emailLogin").addClass('has-error');
                    <%}%>
                    $("#senhaLogin").addClass('has-error');
                <%}%>
            </script>
                       
        </div>
    </body>
</html>
