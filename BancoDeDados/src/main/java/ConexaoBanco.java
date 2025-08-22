import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    public static Connection conectar() throws SQLException {
        

        String url = "postgresql://postgres:Edy#elio#guigas@db.kcpifoutnitfxowkaslv.supabase.co:5432/postgres";
        String user = "postgres"; // ou o usuário do supabase
        String password = "Edy#elio#guigas";

        return DriverManager.getConnection(url, user, password);
    }
}