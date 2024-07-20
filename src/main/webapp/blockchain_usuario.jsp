
<%@page import="Modelo.SocketUsuario"%>
<%@page import="utils.DadosUsuarioSessao"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.util.Base64"%>
<%@page import="Sockets.BlockchainPeer"%>
<%@page import="utils.Blowfish"%>
<%@page import="java.util.Date"%>
<%@page import="Modelo.Bloco"%>
<%@page import="utils.BlockchainUtil"%>
<%@page import="Modelo.Blockchain"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">

    <head>
        <title>Blockchain</title>
        <link rel="stylesheet" href="css/estilo.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body>
        <div class="main-usuario">
            <h1>UCAN Blockchain</h1>
            <div style="display: flex; justify-content: space-between;">

                <h4 style="width: 200px;">
                    <%
                        SocketUsuario socketUsuario = (SocketUsuario) request.getSession().getAttribute( "socketUsuario" );
                        String usuario = ( String ) request.getSession().getAttribute( "usuario" );
                        String email = ( String ) request.getSession().getAttribute( "email" );

                        if ( usuario != null ) {
                    %>
                    <%= usuario %>
                    <%
                        }
                    %>

                </h4>
                <input type="button" value="Editar" class="btt-editar-info" id="botao-editar">
                <a href="Logout">sair</a>
                <div>
                    <form class="campo-pesquisa" method="get" action="Pesquisar">
                        <input type="search" name="pesquisar" placeholder="Pesquisar na Blockchain" style="width: 350px;">
                        <input type="submit" value="Pesquisar" style="width: 80px; margin-left: 10px;">
                    </form>
                </div>

            </div>

            <hr>

            <div style="display: flex;">
                <h4>Ledger Blockchain</h4>
                <input type="button" value="Adicionar Informação" class="btt-add-info" id="botao-modal">
            </div>

            <div id="modalBox" class="modal">
                <div class="modal-content">
                    <span class="fechar">&times;</span>

                    <p>Texto simples:</p>
                    <form method="post" action="CarregarFicheiro">
                        <input type="hidden" name="texto" value="true">
                        <textarea name="texto-simples" placeholder="Escreva aqui" maxlength="100" required></textarea>
                        <input type="submit" value="Adicionar" class="btt-add-info2">
                    </form>

                    <p>Arquivo:</p>
                    <form method="post" action="CarregarFicheiro" enctype="multipart/form-data">
                        <input type="hidden" name="texto" value="false">
                        <input type="file" name="arquivo" placeholder="Carregar um arquivo" required>
                        <input type="submit" value="Adicionar" class="btt-add-info2">
                    </form>
                </div>
            </div>

            <div id="modalBoxEdit" class="modal">
                <div class="modal-content">
                    <span class="fechar-editar">&times;</span>

                    <p>Atualizar Dados do Socket do usuário logado:</p>
                    <form method="post" action="EditarSocketUsuario">
                        <input type="text" name="endereco" value="<%= socketUsuario.getEndereco() %>" placeholder="Novo Endereço" required>
                        <input type="text" name="porta" value="<%= socketUsuario.getPorta() %>" placeholder="Nova Porta" required>

                        <input type="submit" value="Atualizar" class="btt-add-info2">
                    </form>
                </div>
            </div>

            <div class="area-blockchain">
                <%
                    String diretorio = "/Users/valdemar/apache-tomcat-9.0.71/bin/";
                    Blockchain blockchain = BlockchainUtil.carregarBlockchain( diretorio + email + "_blockchain.ucan" );
                    Blowfish blowfish = new Blowfish();

                    if ( DadosUsuarioSessao.blocosEncontrados.size() == 0 ) {

                        for ( Bloco bloco : blockchain.chain ) {

                            String conteudoDoBloco = blowfish.desencriptar( bloco.getConteudoDoBloco() );

                            if ( conteudoDoBloco.startsWith( "FILE:" ) ) {
                                String filePath = conteudoDoBloco.substring( 5 );
                                String fileName = new File( filePath ).getName();
                %>
                <div class="area-bloco" style="overflow: scroll; height: 500px;">
                    <span>Hash do bloco: <%= bloco.getHash() %></span><br>
                    <span>Hash do bloco anterior: <%= bloco.getHashDoBlocoAnterior() %></span>
                    <p class="conteudo-bloco">
                        <a href="Download?path=<%= URLEncoder.encode( filePath, "UTF-8" ) %>" download>Baixar o arquivo: <%= fileName %></a>
                    </p>
                </div>
                <%
                }
                else {
                %>
                <div class="area-bloco" style="overflow: scroll; height: 500px;">
                    <span>Hash do bloco: <%= bloco.getHash() %></span><br>
                    <span>Hash do bloco anterior: <%= bloco.getHashDoBlocoAnterior() %></span>
                    <p class="conteudo-bloco"><%= conteudoDoBloco %></p>
                </div>
                <%
                        }
                    }
                }
                else {

                    for ( Bloco bloco : DadosUsuarioSessao.blocosEncontrados ) {

                        String conteudoDoBloco = blowfish.desencriptar( bloco.getConteudoDoBloco() );

                        if ( conteudoDoBloco.startsWith( "FILE:" ) ) {
                            String filePath = conteudoDoBloco.substring( 5 );
                            String fileName = new File( filePath ).getName();
                %>
                <div class="area-bloco" style="overflow: scroll; height: 500px;">
                    <span>Hash do bloco: <%= bloco.getHash() %></span><br>
                    <span>Hash do bloco anterior: <%= bloco.getHashDoBlocoAnterior() %></span>
                    <p class="conteudo-bloco">
                        <a href="Download?path=<%= URLEncoder.encode( filePath, "UTF-8" ) %>" download>Baixar o arquivo: <%= fileName %></a>
                    </p>
                </div>
                <%
                }
                else {
                %>
                <div class="area-bloco" style="overflow: scroll; height: 500px;">
                    <span>Hash do bloco: <%= bloco.getHash() %></span><br>
                    <span>Hash do bloco anterior: <%= bloco.getHashDoBlocoAnterior() %></span>
                    <p class="conteudo-bloco"><%= conteudoDoBloco %></p>
                </div>

                <%
                            }
                        }
                    }
                %>
            </div>

        </div>

        <script>
            // Get the modal
            var modal = document.getElementById("modalBox");
            var modal2 = document.getElementById("modalBoxEdit");

            // Get the button that opens the modal
            var btn = document.getElementById("botao-modal");
            var btn2 = document.getElementById("botao-editar");

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("fechar")[0];
            var span2 = document.getElementsByClassName("fechar-editar")[0];

            // When the user clicks on the button, open the modal 
            btn.onclick = function () {
                modal.style.display = "block";
            };

            btn2.onclick = function () {
                modal2.style.display = "block";
            };

            // When the user clicks on <span> (x), close the modal
            span.onclick = function () {
                modal.style.display = "none";
            };

            span2.onclick = function () {
                modal2.style.display = "none";
            };

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            };

            window.onclick = function (event) {
                if (event.target == modal2) {
                    modal2.style.display = "none";
                }
            };
        </script>

    </div>
</body>

</html>
