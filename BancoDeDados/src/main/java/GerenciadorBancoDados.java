import java.sql.*;

public class GerenciadorBancoDados {

    public static void criarTabelaJogos(Connection conn, String nomeTabela, String tipoBanco) throws SQLException {
        String sql;

        if ("mysql".equalsIgnoreCase(tipoBanco)) {
            sql = "CREATE TABLE IF NOT EXISTS " + nomeTabela + " (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT," +
                    "Nome VARCHAR(100) NOT NULL," +
                    "Ano INT NOT NULL," +
                    "QuantidadeDisponivel INT NOT NULL," +
                    "Console VARCHAR(20) NOT NULL," +
                    "Classificacao VARCHAR(20) NOT NULL" +
                    ");";
        } else { // SQLite
            sql = "CREATE TABLE IF NOT EXISTS " + nomeTabela + " (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Nome TEXT NOT NULL," +
                    "Ano INTEGER NOT NULL," +
                    "QuantidadeDisponivel INTEGER NOT NULL," +
                    "Console TEXT NOT NULL," +
                    "Classificacao TEXT NOT NULL" +
                    ");";
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela '" + nomeTabela + "' verificada/criada com sucesso.");
        }
    }

    public static void criarTabelaClientes(Connection conn, String nomeTabela, String tipoBanco) throws SQLException {
        String sql;

        if ("mysql".equalsIgnoreCase(tipoBanco)) {
            sql = "CREATE TABLE IF NOT EXISTS " + nomeTabela + " (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT," +
                    "Nome VARCHAR(100) NOT NULL," +
                    "Idade INT NOT NULL," +
                    "Email VARCHAR(100) NOT NULL," +
                    "CPF VARCHAR(20) NOT NULL" +
                    ");";
        } else { // SQLite
            sql = "CREATE TABLE IF NOT EXISTS " + nomeTabela + " (" +
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Nome TEXT NOT NULL," +
                    "Idade INTEGER NOT NULL," +
                    "Email TEXT NOT NULL," +
                    "CPF TEXT NOT NULL" +
                    ");";
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela '" + nomeTabela + "' verificada/criada com sucesso.");
        }
    }

    public static void inserirDadosCliente(Connection conn, String nome, String email, String cpf, String tabela) throws SQLException {
        String sql = "INSERT INTO " + tabela + " (Nome, Email, CPF) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, cpf);
            pstmt.executeUpdate();
            System.out.println("Inserido: " + nome);
        }
    }

    public static void inserirDadosJogos(Connection conn, String nome, int ano, int quantidade, String console,String classificacao, String tabela) throws SQLException {
        String sql = "INSERT INTO " + tabela + " (Nome, Ano, QuantidadeDisponivel, Console, Classificacao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, ano);
            pstmt.setInt(3, quantidade);
            pstmt.setString(4, console);
            pstmt.setString(5, classificacao);
            pstmt.executeUpdate();
            System.out.println("Inserido: " + nome);
        }
    }

    public static void consultarDadosCliente(Connection conn, String tabela) throws SQLException {
        String sql = "SELECT Id, Nome, Email, CPF FROM " + tabela;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Id") + "\t" +
                        "Nome: " + rs.getString("Nome") + "\t" +
                        "E-mail: " + rs.getString("Email") +
                        "\tCPF: " + rs.getString("CPF"));
            }
        }
    }

    public static void consultarDadosJogos(Connection conn, String tabela) throws SQLException {
        String sql = "SELECT *  FROM " + tabela;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Id") + "\t" +
                        "Nome: " + rs.getString("Nome") + "\t" +
                        "Ano de fabricação: " + rs.getInt("Ano") +
                        "\tConsole: " + rs.getString("Console") +
                        "\tQuantidade em estoque: " + rs.getInt("QuantidadeDisponivel"));
            }
        }
    }

    public static void atualizarDadosCliente(Connection conn, int id, String novoNome, String novoCPF, String novoEmail, String tabela) throws SQLException {
        String sql = "UPDATE " + tabela + " SET Nome = ?, CPF = ?, Email = ? WHERE Id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoCPF);
            pstmt.setString(3, novoEmail);
            pstmt.setInt(4, id);

            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Registro com ID " + id + " atualizado com sucesso.");
            } else {
                System.out.println("Nenhum registro encontrado com ID " + id + ".");
            }
        }
    }

    public static void deletarDados(Connection conn, int id, String tabela) throws SQLException {
        String sql = "DELETE FROM " + tabela + " WHERE Id = ?";
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

    public static void deletarTabela(Connection conn, String nomeTabela) throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + nomeTabela;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela '" + nomeTabela + "' deletada com sucesso.");
        }
    }

    public static void consultarTabela(Connection conn, String nomeTabela, String filtro) throws SQLException {
        // Monta a query com parâmetro
        String sql = "SELECT * FROM " + nomeTabela + " WHERE Console = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define o valor do filtro
            stmt.setString(1, filtro);

            // Executa a query
            try (ResultSet rs = stmt.executeQuery()) {
                // Percorre e exibe os resultados
                while (rs.next()) {
                    // Exemplo: imprimir todas as colunas conhecidas
                    int id = rs.getInt("Id");
                    String nome = rs.getString("Nome");
                    String console = rs.getString("Console");
                    int ano = rs.getInt("Ano");

                    System.out.println(id + " - " + nome + " - " + console + " - " + ano);
                }
            }
        }
    }

}
