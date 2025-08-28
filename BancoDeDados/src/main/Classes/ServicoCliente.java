package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicoCliente {

    // explicação das strings regex: "StringRegex.md"

    // verificar se nome válido
    public static boolean verificarNome(String nome){
        String regex = "[a-zA-ZáàâãéèêíïóôõöúüçÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇ\\s]+"; // expressão regular que permite assentos, letras maiusculas e minúculas e não permite valores numéricos.

        if(nome.length() < 3){
            return false;
        }
        return nome.matches(regex); // Retorna true se estiver no formato da String regex

    }

    public static boolean ehCPFValido(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos e se os numeros não são iguais (000-000-000-00)
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return true;
    }

    public static boolean ehTelefoneValido(String telefone) {
        // Remove caracteres não numéricos
        telefone = telefone.replaceAll("\\D", "");

        // Verifica se o telefone tem 11 dígitos e se os numeros não são iguais ((00) 0 0000-0000)
        if (telefone.length() != 11 || telefone.matches("(\\d)\\1{10}")) {
            return false;
        }

        return true;
    }

    public static boolean verificarEmail(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // expressão regular para email

        return email.matches(regex); // Retorna true se estiver no formato da String regex

    }

    // Método que verifica se o cpf já foi cadastrado no banco de dados
    public static boolean ehCpfRepetido(Connection conn, String cpf) {
            String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cpf);
                try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int qtd = rs.getInt(1);
                        if (qtd > 0) {

                            return true;
                        }
                    }

                return false;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }// Método que verifica se o telefone já foi cadastrado no banco de dados
    public static boolean ehTelefoneRepetido(Connection conn, String telefone) {
            String sql = "SELECT COUNT(*) FROM clientes WHERE telefone = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, telefone);
                try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int qtd = rs.getInt(1);
                        if (qtd > 0) {

                            return true;
                        }
                    }

                return false;
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }




}
