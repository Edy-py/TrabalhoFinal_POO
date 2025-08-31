//Edílson Alves da Silva (GitHub: edy-py)
//Guilherme Henrique Garcia Silva (GitHub: Guigas-hgs)
//Élio Mário Soares Júnior (GitHub: BrawlerGits)

package BancodeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    public static Connection conectar() throws SQLException {

        String url = "jdbc:postgresql://db.kcpifoutnitfxowkaslv.supabase.co:5432/postgres";
        String user = "postgres"; // ou o usuário do supabase
        String password = "Edy#elio#guigas";

        return DriverManager.getConnection(url, user, password);
    }
}