package Classes;

public class ServicoCliente {

    // verificar se nome válido
    public static boolean verificarNome(String nome){
        String regex = "[a-zA-ZáàâãéèêíïóôõöúüçÁÀÂÃÉÈÊÍÏÓÔÕÖÚÜÇ\\s]+"; // expressão regular que permite assentos, letras maiusculas e minúculas e não permite valores numéricos.

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
        String regex = "^([a-zA-Z0-9_\\\\-\\\\.]+)@([a-zA-Z0-9_\\\\-\\\\.]+)\\\\.([a-zA-Z]{2,5})$"; // expressão regular para email

        return email.matches(regex); // Retorna true se estiver no formato da String regex

    }

}
