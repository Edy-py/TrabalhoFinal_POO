package BancodeDados;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ConexaoUI extends Component {


    public static ArrayList<String> getInfoColuna( String nomeColuna, String tabela) throws SQLException {

        try(Connection conn = ConexaoBanco.conectar()) {

            String sql = "SELECT DISTINCT " + nomeColuna + " FROM " + tabela;
            ArrayList<String> consoles = new ArrayList<>();

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    consoles.add(rs.getString(nomeColuna));
                }

            } catch (SQLException e) {
                System.err.println("Erro ao buscar consoles: " + e.getMessage());
            }
            return consoles;
        }
    }

    public DefaultTableModel buscarDadosFiltrados(Connection conn, String sql ,Vector<String> nomesColunasAmigaveis, Object valorFiltro) throws SQLException {

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                if(valorFiltro != null){

                    pstmt.setString(1, "%" + valorFiltro + "%");
                }

                ResultSet rs = pstmt.executeQuery();

                Vector<Vector<Object>> data = new Vector<>();
                while (rs.next()) {
                    // cria um vetor com as informações de cada linha da tabela
                    Vector<Object> row = new Vector<>();
                    for (int columnIndex = 1; columnIndex <= nomesColunasAmigaveis.size(); columnIndex++) {
                        row.add(rs.getObject(columnIndex));
                    }
                    data.add(row);
                }
                return new DefaultTableModel(data, nomesColunasAmigaveis);
            }


    }

    public static Connection conectar() throws SQLException {
        try {
            return ConexaoBanco.conectar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static String buscaPorId(Connection conn, int id, String coluna, String tabela) throws SQLException {
        String sql = "SELECT " + coluna + " FROM " + tabela + " WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return null;
            }
        }
    }

    public static String buscaPorCpf(Connection conn, String cpf, String coluna, String tabela) throws SQLException {
        String sql = "SELECT " + coluna + " FROM " + tabela + " WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return null;
            }
        }

    }

    public static String buscaPrecoPorNome(Connection conn, String nome, String coluna, String tabela) throws SQLException {
        String sql = "SELECT " + coluna + " FROM " + tabela + " WHERE nome = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return null;
            }
        }
    }

}


