import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Jogos jogo = new Jogos();
        jogo.setAno(2000);
        jogo.setNome("Jogo de tiro");
        jogo.setConsole("PS4");
        jogo.setQtdDisponivel(5);
        jogo.setEsrb("+16");


        try (Connection conn = ConexaoBanco.conectar()) {

            Properties props = new Properties();
            try (InputStream input = Main.class.getResourceAsStream("/db.properties")) {
                props.load(input);
            }
            String tipoBanco = props.getProperty("db.type");
            String tabela = "jogos";
            String tabela2 = "clientes";


            //GerenciadorBancoDados.deletarTabela(conn, tabela);
            //GerenciadorBancoDados.deletarTabela(conn, tabela2);

            //GerenciadorBancoDados.criarTabelaJogos(conn, tabela, tipoBanco);
            //GerenciadorBancoDados.criarTabelaClientes(conn, tabela2, tipoBanco);

            // add jogos
            //GerenciadorBancoDados.inserirDadosJogos(conn,jogo.getNome(),jogo.getAno(),jogo.getQtdDisponivel(),jogo.getConsole(), jogo.getEsrb(),tabela );
            //GerenciadorBancoDados.inserirDadosJogos(conn,jogo.getNome(),jogo.getAno(),jogo.getQtdDisponivel(),"Xbox", jogo.getEsrb(),tabela );
            //GerenciadorBancoDados.consultarDadosCliente(conn, tabela);

            //GerenciadorBancoDados.deletarDados(conn, 2, tabela);
            GerenciadorBancoDados.consultarDadosJogos(conn,tabela);
            System.out.println();
            GerenciadorBancoDados.consultarTabela(conn, tabela, "PS4");



        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
