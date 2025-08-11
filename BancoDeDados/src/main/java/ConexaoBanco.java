import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {

    public static Connection conectar() throws IOException, SQLException {
        Properties props = new Properties();

        // Lê o db.properties de src/main/resources
        try (InputStream input = ConexaoBanco.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new IOException("Arquivo db.properties não encontrado em resources!");
            }
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
