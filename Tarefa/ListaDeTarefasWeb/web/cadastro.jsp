<%-- 
    Document   : cadastro
    Created on : 11/01/2020, 13:33:48
    Author     : Aluno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro </title>
    </head>
    <body>
        <form method="POST" action="cadastro">
            
            <p>
                <label>E-mail:</label>
                <input type="email" name="email"/>
            </p>
            <p>
                <label>Senha:</label>
                <input type="password" name="senha"/>
            </p>
            <p>
                <label>Repetir Senha:</label>
                <input type="password" name="senhaRepetida"/>
            </p>
            
            <input type="submit" value="Cadastrar"  />
            <p>${erroJaCadastrado}</p>
            <p>${erroSenha}</p>
            
        </form>
    </body>
</html>
