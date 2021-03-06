<%-- 
    Document   : main
    Created on : Jan 11, 2016, 9:21:31 PM
    Author     : ronaldo
--%>

<%@page import="classes.Livro"%>
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

    <% String nome = (String)request.getAttribute("nome");
       Date data = (Date)request.getAttribute("data");
       String cadastroLivroError = (String)request.getAttribute("cadastroLivroError");
    %>
    
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
                        <span class="glyphicon glyphicon glyphicon-envelope" aria-hidden="true"> Minhas Mensagens</span>                       
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
                                <button type="button" class="btn btn-default pull-right" data-dismiss="modal">Cancelar</button>
                            </form>       
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- Fim modal novo livro -->
             
            <!-- Meus livros -->
            <div class="col-lg-12" id="meuslivros">
                
                <br/><br/>
                    
                <% List<Livro> livros = (List<Livro>) request.getAttribute("livrosLista");
                    int cont = 0;
                    if (livros != null && livros.size() > 0) { %>
                    
                    
                <h3>Seus livros</h3>
                
                <div class="row">
                
                    <%for (Livro a : livros) { %>
                    <% cont++; %>        
                    <div class="col-lg-3 text-right">
                        <img src="http://static7.depositphotos.com/1238677/724/i/950/depositphotos_7243722-Gray-book-isolated-on-white-.-Clean-cover.jpg" class="img-responsive" alt="<%=a.getNome() %>" title="<%=a.getNome() %>"/>
                        <h3><%=a.getNome() %></h3>
                        <p><%= a.getAutor() %></p>
                        <p><%= a.getEdicao() %>ª Edição</p>
                        <% if(a.isDisponivel() == false) { %>
                        <div class="alert alert-warning text-center" role="alert">
                            Esse livro está emprestado
                        </div><%}%>
                    </div>
                    <%if(cont % 4 == 0) {%></div><div class="row"> <%}%>
                    <% } %> 
                    </div>
                <%} else { %>
                    <h3>Você não possui nenhum livro cadastrado, utilize o botão ao lado para começar a usar o CoLib</h3>
                <% } %>
            </div>
            
            <!-- Resultado da busca -->
            <div class="col-lg-12">
                
                <br/><br/>
                    
                <% if(request.getAttribute("busca") != null) {
                    List<Livro> livrosBusca = (List<Livro>) request.getAttribute("resultadoBusca");
                    if (livrosBusca != null && livrosBusca.size() > 0) { %>
                    
                <h3>Livros encontrados</h3>
                
                <%for (Livro a : livrosBusca) { %>
                        
                <div class="col-lg-3 text-right">
                    <img src="http://static7.depositphotos.com/1238677/724/i/950/depositphotos_7243722-Gray-book-isolated-on-white-.-Clean-cover.jpg" class="img-responsive" alt="<%=a.getNome() %>" title="<%=a.getNome() %>"/>
                    <h3><%=a.getNome() %></h3>
                    <p><%= a.getAutor() %></p>
                    <p><%= a.getEdicao() %>ª Edição</p>
                    <p>DonoID: <%= a.getDono() %></p>
                    <% if(a.isDisponivel() == false) { %>
                    <div class="alert alert-warning text-center" role="alert">
                        Esse livro já está emprestado
                    </div><%} else {%>
                    <div>
                        <button class="btn btn-primary"  data-toggle="modal" data-target="#modalMensagem" data-id="<%= a.getDono() %>" id="queroLivro">Quero esse livro</button>
                    </div>
                    <% } %>
                </div>
                        <% }
                    } else { %>
                    
                    <h3>A busca não encontrou livros relacionados.</h3>
                    <% } }%>
            </div>
            
            <!-- Modal mensagem -->
            <div class="modal fade" id="modalMensagem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">Escreva sua mensagem abaixo</h4>
                        </div>
                        <div class="modal-body col-lg-10 col-lg-offset-1">
                            
                            <!-- Mensagem -->
                            <form action="mensagem" method="POST">
                                
                                <div class="form-group">
                                    <label for="inputAssunto">Assunto</label>
                                    <input type="text" class="form-control" name="assuntoMensagem" placeholder="Empréstimo de livro">
                                </div>
                                
                                <div class="form-group">
                                    <label for="inputMensagem">Mensagem</label>
                                    <textarea class="form-control" rows="4"  name="conteudoMensagem" placeholder="Quero o seu livro emprestado, como posso pegar..."></textarea>
                                </div>

                                <button type="submit" class="btn btn-success pull-right" id="enviarMensagem" name="para">Enviar</button>
                                <button type="button" class="btn btn-default pull-right" data-dismiss="modal">Cancelar</button>
                            </form>       
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- Fim modal mensagem -->
            
        </div>
        
        <!-- Scripts -->
        <script> 
            <% if(cadastroLivroError != null) {%>
                $("#modalNovoLivro").modal('show');
                $("#cadastrolivronome").addClass('has-error');
                $("#cadastrolivroautor").addClass('has-error');
                $("#cadastrolivroedicao").addClass('has-error');
                
            <% } if(request.getAttribute("busca") != null) { %>
                $("#meuslivros").addClass('hidden');
            <% } %>
            
            $('#modalMensagem').on('show.bs.modal', function() {

                var livroID = $('#queroLivro').data('id');
                $('#enviarMensagem').val(livroID);

            });
                
                
        </script>
        
    </body>

</html>
