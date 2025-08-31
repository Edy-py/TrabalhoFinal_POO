package BancodeDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class GerenciadorBancoDados {

    public static void avancarDia() throws SQLException {
        String sql = "SELECT id_locacao, id_cliente, data_locacao, dias_alugados FROM locacoes WHERE status_locacao = 'Alugando'";
        try (Connection conn = ConexaoBanco.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            long hoje = System.currentTimeMillis();

            while (rs.next()) {
                int idLocacao = rs.getInt("id_locacao");
                int idCliente = rs.getInt("id_cliente");
                Date dataLocacao = rs.getDate("data_locacao");
                int diasAlugados = rs.getInt("dias_alugados");

                long diffEmMillis = hoje - dataLocacao.getTime();
                long diasPassados = TimeUnit.DAYS.convert(diffEmMillis, TimeUnit.MILLISECONDS);

                if (diasPassados > diasAlugados) {
                    atualizarStatusCliente(idCliente, "Devendo");
                }
            }
        }
    }

    public static void inserirDadosCliente(Connection conn, String nome, String email, String cpf,String endereco,String telefone,String status, String tabela) throws SQLException {
        String sql = "INSERT INTO " + tabela + " (nome, endereco, telefone, cpf, email, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, endereco);
            pstmt.setString(3, telefone);
            pstmt.setString(4, cpf);
            pstmt.setString(5, email);
            pstmt.setString(6, status);
            pstmt.executeUpdate();
        }
    }

    public static void inserirDadosJogos(String nome, String publicacao, String console, String classificacao, Integer anoLancamento, Integer estoque, Integer disponivel, Double preco, String tabela) throws SQLException {
        try(Connection conn = ConexaoBanco.conectar()){
            String sql = "INSERT INTO " + tabela + " (nome, publicacao,console, classificacao, ano_lancamento, quantidade, quantidade_disponivel, preco) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, publicacao);
                pstmt.setString(3, console);
                pstmt.setString(4, classificacao);
                pstmt.setInt(5, anoLancamento);
                pstmt.setInt(6, estoque);
                pstmt.setInt(7, disponivel);
                pstmt.setDouble(8, preco);
                pstmt.executeUpdate();
            }
        }
    }

    public static void inserirDadosLocacao(int idCliente, int idJogo, int diasAlugados) throws SQLException {
        try (Connection conn = ConexaoBanco.conectar()) {
            String sql = "INSERT INTO locacoes (id_cliente, id_jogo, data_locacao, dias_alugados, status_locacao) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idCliente);
                pstmt.setInt(2, idJogo);
                pstmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                pstmt.setInt(4, diasAlugados);
                pstmt.setString(5, "Alugando");
                pstmt.executeUpdate();
            }
        }
    }

    public static void atualizarDadosCliente(Connection conn,int id, String novoNome, String novoEmail, String novoCPF,String novoEndereco,String novoTelefone, String tabela) throws SQLException {
        String sql = "UPDATE " + tabela + " SET nome = ?, email = ?, cpf = ?, endereco = ?, telefone = ? WHERE Id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, novoCPF);
            pstmt.setString(4, novoEndereco);
            pstmt.setString(5, novoTelefone);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dados do cliente: " + e.getMessage(), "Erro de Operação", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }

    public static void atualizarStatusCliente(int idCliente, String novoStatus) throws SQLException {
        try (Connection conn = ConexaoBanco.conectar()) {
            String sql = "UPDATE clientes SET status = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, novoStatus);
                pstmt.setInt(2, idCliente);
                pstmt.executeUpdate();
            }
        }
    }

    public static void atualizarQuantidadeDisponivelJogo(int idJogo, int quantidade) throws SQLException {
        try (Connection conn = ConexaoBanco.conectar()) {
            String sql = "UPDATE jogos SET quantidade_disponivel = quantidade_disponivel + ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, quantidade);
                pstmt.setInt(2, idJogo);
                pstmt.executeUpdate();
            }
        }
    }

    public static void deletarDados(int id, String tabela) throws SQLException {
        try (Connection conn = ConexaoBanco.conectar()){
            String sql = "DELETE FROM " + tabela + " WHERE Id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        }
    }

    public static DefaultTableModel getDadosLocacoes(String statusFiltro) throws SQLException {
        String sql;
        if (statusFiltro.equals("Todos")) {
            sql = "SELECT c.nome, j.nome, j.preco, l.data_locacao, l.dias_alugados, l.status_locacao " +
                    "FROM locacoes l " +
                    "JOIN clientes c ON l.id_cliente = c.id " +
                    "JOIN jogos j ON l.id_jogo = j.id";
        } else {
            sql = "SELECT c.nome, j.nome, j.preco, l.data_locacao, l.dias_alugados, l.status_locacao " +
                    "FROM locacoes l " +
                    "JOIN clientes c ON l.id_cliente = c.id " +
                    "JOIN jogos j ON l.id_jogo = j.id " +
                    "WHERE l.status_locacao = ?";
        }

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (!statusFiltro.equals("Todos")) {
                pstmt.setString(1, statusFiltro);
            }

            ResultSet rs = pstmt.executeQuery();

            Vector<String> colunas = new Vector<>();
            colunas.add("Cliente");
            colunas.add("Jogo");
            colunas.add("Preço Diário");
            colunas.add("Data de Locação");
            colunas.add("Dias Alugados");
            colunas.add("Status");

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString(1));
                linha.add(rs.getString(2));
                linha.add(rs.getDouble(3));
                linha.add(rs.getDate(4));
                linha.add(rs.getInt(5));
                linha.add(rs.getString(6));
                dados.add(linha);
            }
            return new DefaultTableModel(dados, colunas);
        }
    }

    public static ResultSet getLocacaoDetalhes(int idCliente) throws SQLException {
        String sql = "SELECT j.nome, j.preco, l.dias_alugados, l.data_locacao, c.status, l.id_locacao, j.id as id_jogo " +
                "FROM locacoes l " +
                "JOIN jogos j ON l.id_jogo = j.id " +
                "JOIN clientes c ON l.id_cliente = c.id " +
                "WHERE l.id_cliente = ? AND (l.status_locacao = 'Alugando' OR l.status_locacao = 'Devendo')";
        Connection conn = ConexaoBanco.conectar();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idCliente);
        return pstmt.executeQuery();
    }

    public static void finalizarLocacao(int idLocacao) throws SQLException {
        String sql = "UPDATE locacoes SET status_locacao = 'Concluída' WHERE id_locacao = ?";
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLocacao);
            pstmt.executeUpdate();
        }
    }

    public static boolean verificarLocacoesPendentes(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM locacoes WHERE id_cliente = ? AND (status_locacao = 'Alugando' OR status_locacao = 'Devendo')";
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }
}