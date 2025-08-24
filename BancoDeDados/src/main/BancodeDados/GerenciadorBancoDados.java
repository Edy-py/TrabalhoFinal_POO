import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class GerenciadorBancoDados {

    public static void inserirDadosCliente(Connection conn, String nome, String email, String cpf,String endereco,String telefone, String tabela) throws SQLException {
        String sql = "INSERT INTO " + tabela + " (nome, endereco, telefone, cpf, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, endereco);
            pstmt.setString(3, telefone);
            pstmt.setString(4, cpf);
            pstmt.setString(5, email);
            pstmt.executeUpdate();
            System.out.println("Inserido: " + nome);
        }
    }

    public static void inserirDadosJogos(Connection conn, String nome, int publicacao, String console, String classificacao, int anoLancamento, int estoque, int disponivel,  String tabela) throws SQLException {
        String sql = "INSERT INTO " + tabela + " (nome, publicacao,console, classificacao, ano_lancamento, quantidade, quantidade_disponivel) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, publicacao);
            pstmt.setString(3, console);
            pstmt.setString(4, classificacao);
            pstmt.setInt(5, anoLancamento);
            pstmt.setInt(6, estoque);
            pstmt.setInt(7, disponivel);
            pstmt.executeUpdate();
            System.out.println("Inserido: " + nome);
        }
    }

    public static void consultarDadosCliente(Connection conn, String tabela) throws SQLException {
        String sql = "SELECT * FROM " + tabela;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "\t" +
                        "Nome: " + rs.getString("nome") + "\t" +
                        "Endereço: " + rs.getString("endereco") +
                        "\tTelefone: " + rs.getInt("telefone") +
                        "\tCPF: " + rs.getString("cpf") +
                        "\tE-mail: " + rs.getString("email"));
            }
        }
    }


    //Nome, Publicação,Console, Classificacao, Ano de Lançamento, Quantidade em Estoque, Quantidade Disponível
    public static void consultarDadosJogos(Connection conn, String tabela) throws SQLException {
        String sql = "SELECT *  FROM " + tabela;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "\t" +
                        "Nome: " + rs.getString("nome") + "\t" +
                        "Publicação: " + rs.getInt("publicacao") +
                        "\tConsole: " + rs.getString("console") +
                        "\tClassificacao: " + rs.getString("classificacao") +
                        "\tAno de Lançamento: "+ rs.getInt("ano_lancamento") +
                        "\tQuantidade em Estoque: " + rs.getInt("quantidade") +
                        "\tQuantidade Disponível: " + rs.getInt("quantidade_disponivel"));
            }
        }
    }

    public static void atualizarDadosCliente(Connection conn,int id, String novoNome, String novoEmail, String novoCPF,String novoEndereco,String novoTelefone, String tabela) throws SQLException {
        String sql = "UPDATE " + tabela + " SET Nome = ?, email = ?, cpf = ?, endereco = ?, telefone = ? WHERE Id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, novoCPF);
            pstmt.setString(4, novoEndereco);
            pstmt.setString(5, novoTelefone);
            pstmt.setInt(6, id);

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

    public static DefaultTableModel buscarDadosParaTabela(Connection conn, String nomeTabela) throws SQLException {
        String sql = "SELECT * FROM " + nomeTabela;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Pega os metadados do resultado (informações sobre as colunas)
            ResultSetMetaData metaData = rs.getMetaData();

            // Nomes das colunas
            Vector<String> columnNames = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {

                columnNames.add(metaData.getColumnName(column));
            }

            // Dados das linhas
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    row.add(rs.getObject(columnIndex));
                }
                data.add(row);
            }

            // Retorna um modelo de tabela padrão com os dados e nomes de colunas
            return new DefaultTableModel(data, columnNames);

        }
    }

}
