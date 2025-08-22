import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = ConexaoBanco.conectar()) {
            System.out.println("Conexão com Supabase/Postgres estabelecida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}