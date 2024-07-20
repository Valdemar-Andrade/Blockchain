<%-- 
    Document   : index
    Created on : 25/06/2024, 20:00:52
    Author     : valdemar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">

    <head>
        <title>Login</title>
        <link rel="stylesheet" href="css/estilo.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>
        <div class="main">
            <h1>UCAN Blockchain</h1>
            <h3>Entre com as suas credenciais</h3>
            <%
                String mensagemErro = request.getParameter("mensagemErro");
                if(mensagemErro != null)
                {
            %>
            <h4 style="color: red"><%= mensagemErro%></h4>
            <%
                }
            %>
            <form method="post" action="FazerLogin">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="Seu Email" required>

                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" placeholder="Sua senha" required>

                <div class="wrap">
                    <button type="submit" onclick="">Entrar</button>
                </div>
            </form>

            <p>Não tem uma conta?
                <a href="criar_conta.jsp" style="text-decoration: none;">
                    Criar Conta
                </a>
            </p>
        </div>
    </body>

</html>
