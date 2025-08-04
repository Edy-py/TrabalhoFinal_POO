import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GerenciadorBancoDados {

    public static void criarTabela(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS pessoas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "idade INTEGER" +
                ");";
        // Usa try-with-resources para garantir que o Statement seja fechado.
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'pessoas' verificada/criada com sucesso.");
        }
    }

    /**
     * Insere uma nova pessoa na tabela. Usa PreparedStatement para segurança.
     */
    public static void inserirDados(Connection conn, String nome, int idade) throws SQLException {
        String sql = "INSERT INTO pessoas(nome, idade) VALUES(?, ?)";
        // Usa try-with-resources para garantir que o PreparedStatement seja fechado.
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.executeUpdate();
            System.out.println("Inserido: " + nome);
        }
    }

    /**
     * Consulta e exibe todas as pessoas cadastradas na tabela.
     */
    public static void consultarDados(Connection conn) throws SQLException {
        String sql = "SELECT id, nome, idade FROM pessoas";
        // Usa try-with-resources para garantir que Statement e ResultSet sejam fechados.
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Itera sobre o resultado da consulta
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "\t" +
                        "Nome: " + rs.getString("nome") + "\t" +
                        "Idade: " + rs.getInt("idade"));
            }
        }
    }

    /**
     * Atualiza o nome e a idade de uma pessoa com base no seu ID.
     */
    public static void atualizarDados(Connection conn, int id, String novoNome, int novaIdade) throws SQLException {
        String sql = "UPDATE pessoas SET nome = ? , idade = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setInt(2, novaIdade);
            pstmt.setInt(3, id);
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Registro com ID " + id + " atualizado com sucesso.");
            } else {
                System.out.println("Nenhum registro encontrado com ID " + id + ".");
            }
        }
    }

    /**
     * Deleta uma pessoa da tabela com base no seu ID.
     */
    public static void deletarDados(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM pessoas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Registro com ID " + id + " deletado com sucesso.");
            } else {
                System.out.println("Nenhum registro encontrado com ID " + id + ".");
            }
        }
    }
}

