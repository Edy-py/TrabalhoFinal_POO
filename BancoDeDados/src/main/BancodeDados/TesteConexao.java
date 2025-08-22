import java.sql.*;

public class TesteConexao {
    public static void main(String[] args) {

        try (Connection conn = ConexaoBanco.conectar()) {
            System.out.println("Conexão com Supabase/Postgres estabelecida com sucesso!");
            GerenciadorBancoDados.inserirDadosCliente(conn,"edilson", "edils@gmail.com","08919197816","cs 01", "61992939498","clientes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
