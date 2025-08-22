import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String endereco;
    private String email;
    private int telefone;
    private ArrayList<Locacao> locacoes;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getTelefone() {
        return telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    public ArrayList<Locacao> getLocacoes() {
        return locacoes;
    }
    public void setLocacoes(ArrayList<Locacao> locacoes) {
        this.locacoes = locacoes;
    }

    public class ValidadorCPF {

        public static boolean isCPFValido(String cpf) {
            // Remove caracteres não numéricos
            cpf = cpf.replaceAll("\\D", ""); //

            // Verifica se o CPF tem 11 dígitos
            if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
                return false;
            }

    // cadastrarCliente() : void
    // editarCliente() : void
    // locarJogo() : void
    // devolverJogo() : void
    // isCPF() : boolean
    // isTelefone() : boolean
    // isEmail() : boolean
}