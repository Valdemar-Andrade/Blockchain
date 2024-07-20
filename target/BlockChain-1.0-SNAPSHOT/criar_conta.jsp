<%-- 
    Document   : criar_conta
    Created on : 25/06/2024, 20:02:32
    Author     : valdemar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">

    <head>
        <title>Criar Conta</title>
        <link rel="stylesheet" href="css/estilo.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>
        <div class="main">
            <h1>Crie uma conta</h1>
            <h3>Para usar a blockchain</h3>

            <form method="post" action="CadastrarConta">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" placeholder="Nome do Usuário" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="Seu email" required>

                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" placeholder="Sua senha" required>
                
                <label for="socket">Informações do Socket:</label>
                <input type="text" id="socket" name="endereco" placeholder="Endereço" required>
                <input type="text" id="socket" name="porta" placeholder="Porta" required>

                <div class="wrap">
                    <button type="submit" onclick="">
                        Criar
                    </button>
                </div>
            </form>

            <a href="index.jsp" style="text-decoration: none;">
                voltar
            </a>
        </div>
    </body>

</html>
