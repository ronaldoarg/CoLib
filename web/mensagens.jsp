<%-- 
    Document   : mensagens
    Created on : Jan 16, 2016, 10:34:50 PM
    Author     : ronaldo
--%>

<%@page import="classes.Mensagem"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="bootstrap.jsp" %>
        <title>CoLib</title>
        
        <style>
            .white, .white a {
                color: #fff;
            }
        </style>
        
    </head>

    <% String nome = (String)request.getAttribute("nome");%>
    <% Date data = (Date)request.getAttribute("data");%>
    <% String cadastroLivroError = (String)request.getAttribute("cadastroLivroError");%>

    <body>
        <!-- Menu Superior -->
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">

                <a href="main">
                    <h4 class="navbar-text"><span class="glyphicon glyphicon-home white" aria-hidden="true"></span>  Olá,  <%=nome%></h4>
                </a>
                <a href="logout">
                    <button type="button" class="btn btn-default navbar-btn pull-right" style="margin-left:20px;">
                        <span class="glyphicon glyphicon-log-out" aria-hidden="true"> Sair</span>                       
                    </button>
                </a>
                
                <a href="verMensagem">
                    <button type="button" class="btn btn-default navbar-btn pull-right">
                        <span class="glyphicon glyphicon glyphicon-envelope" aria-hidden="true"> Mensagens</span>                       
                    </button>
                </a>
                
            </div>
        </nav>
        
        <div class="container" style="margin-top: 70px;">
            <div class="row">
                <!-- Buscar Livro -->
                <div class="col-lg-8">
                    <h3>Encontre um livro novo</h3>
                    <form action="pesquisa" class="form-inline col-lg-12">
                        <div class="form-group">
                            <input type="search" class="form-control" name="nomeDoLivro" placeholder="Digite aqui o nome do livro">
                        </div>
                        <button type="submit" class="btn btn-primary">Pesquisar</button>
                    </form>
                </div>
                
                <!-- Cadastrar livro -->
                <div class="col-lg-4">
                    <h3>Cadastre um livro novo</h3>
                    <form class="form-inline col-lg-12">
                        <button type="button" class="btn btn-primary col-lg-8" data-toggle="modal" data-target="#modalNovoLivro">Cadastrar</button>
                    </form>
                </div>
            </div>
           
            <!-- Modal de cadastro novo livro -->
            <div class="modal fade" id="modalNovoLivro" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Você está adicionando um novo livro à sua coleção</h4>
                        </div>
                        <div class="modal-body col-lg-10 col-lg-offset-1">
                            
                            <!-- Cadastro -->
                            <form action="cadastroLivro" method="POST">
                                
                                <% if (cadastroLivroError != null) { %>
                                <div class="alert alert-danger" role="alert">
                                    <%= cadastroLivroError %>
                                </div>
                                <% } %>    
                                <div class="form-group" id="cadastrolivronome">
                                    <label for="inputNome">Nome do livro</label>
                                    <input type="text" class="form-control" id="InputNomeLivro" name="nomeLivro" placeholder="Nome">
                                </div>
                                <div class="form-group" id="cadastrolivroautor">
                                    <label for="inputSobrenome">Autor</label>
                                    <input type="text" class="form-control" id="InputAutorLivro" name="autorLivro" placeholder="Autor">
                                </div>

                                <div class="form-group" id="cadastrolivroedicao">
                                    <label for="inputEmail1">Edição</label>
                                    <input type="text" class="form-control" id="InputEdicaoLivro" name="edicaoLivro" placeholder="Edição">
                                </div>

                                <button type="submit" class="btn btn-success pull-right">Enviar</button>
                                <button type="button" class="btn btn-dafault pull-right" data-dismiss="modal">Cancelar</button>
                            </form>       
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- Fim modal novo livro -->    
        </div>
        
                                <h1><%=nome%></h1>                        
                                
        <div class="container">
         <% List<Mensagem> mensagens = (List<Mensagem>) request.getAttribute("mensagens");
            if (mensagens != null && mensagens.size() > 0) { %>

                <h4>Minhas mensagens</h4>

                <div class="table-responsive">
                    <table class="table">
                        <thead>sa.kjdhf</thead>
                    </table>
                </div>
          <%}%>
        
        </div>
                                
    </body>

</html>
