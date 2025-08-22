import java.sql.*;

public class TesteConexao {
    public static void main(String[] args) {

        try (Connection conn = ConexaoBanco.conectar()) {
            System.out.println("Conexão com Supabase/Postgres estabelecida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
