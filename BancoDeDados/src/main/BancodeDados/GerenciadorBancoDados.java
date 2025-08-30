package BancodeDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GerenciadorBancoDados {

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
                System.out.println("Inserido: " + nome); // mudar para um JOpitionpane
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


    public static void atualizarDadosCliente(Connection conn,int id, String novoNome, String novoEmail, String novoCPF,String novoEndereco,String novoTelefone, String tabela) throws SQLException {

            String sql = "UPDATE " + tabela + " SET Nome = ?, email = ?, cpf = ?, endereco = ?, telefone = ? WHERE Id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, novoNome);
                pstmt.setString(2, novoEmail);
                pstmt.setString(3, novoCPF);
                pstmt.setString(4, novoEndereco);
                pstmt.setString(5, novoTelefone);
                pstmt.setDouble(6, id);


            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao atualizar dados do cliente: ", "Erro de atualização", JOptionPane.ERROR_MESSAGE);
            }

    }


    public static void deletarDados(int id, String tabela) throws SQLException {

        try (Connection conn = ConexaoBanco.conectar()){

            String sql = "DELETE FROM " + tabela + " WHERE Id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);

            }
            }
        }


}
