<%-- 
    Document   : index
    Created on : 18/01/2020, 09:51:18
    Author     : Aluno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Tarefas</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1> Lista de Tarefas </h1>
        <form method="POST" action="login">
            
            <p>
                <label>E-mail:</label>
                <input type="email" name="email"/>
            </p>
            <p>
                <label>Senha:</label>
                <input type="password" name="senha"/>
            </p>
            
            <input type="submit" value="Fazer login" />
            
            <p>${NoUser}</p>
            <p>${WrongPassword}</p>
            
            <a href ="cadastro.jsp"> Cadastrar novo usuário</a>
            
        </form>
    </body>
</html>