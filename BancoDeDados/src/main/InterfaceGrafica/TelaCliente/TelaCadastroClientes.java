//Edílson Alves da Silva (GitHub: edy-py)
//Guilherme Henrique Garcia Silva (GitHub: Guigas-hgs)
//Élio Mário Soares Júnior (GitHub: BrawlerGits)

package InterfaceGrafica.TelaCliente;

import BancodeDados.ConexaoUI;
import BancodeDados.GerenciadorBancoDados;
import Classes.ConfigLayout;
import Classes.ServicoCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaCadastroClientes extends JDialog {
    private JPanel cadastroclientePanel;
    private JLabel nomeLabel;
    private JLabel enderecoLabel;
    private JLabel telefoneLabel;
    private JLabel cpfLabel;
    private JLabel emailLabel;
    private JTextField nome;
    private JTextField endereco;
    private JTextField telefone;
    private JTextField cpf;
    private JTextField email;
    private JButton cadastrarButton;

    private String tabela = "clientes";
    private String sqlCpf = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";
    private String sqlTelefone = "SELECT COUNT(*) FROM clientes WHERE telefone = ?";

    public TelaCadastroClientes(Frame pai, boolean modal) {
        super(pai, modal);

        String exNome = "Digite o nome do cliente: ";
        String exEndereco = "Rua, Bairro, Casa, Cidade";
        String exTelefone = "Digite o telefone do cliente: (64) 99999-9999";
        String exCpf = "Digite o CPF do cliente: 999.999.999-99";
        String exEmail = "Digite o email do cliente: exemplo@gamil.com";

        ConfigLayout.exemploDeTexto(nome, exNome);
        ConfigLayout.exemploDeTexto(endereco, exEndereco);
        ConfigLayout.exemploDeTexto(telefone, exTelefone);
        ConfigLayout.exemploDeTexto(cpf, exCpf);
        ConfigLayout.exemploDeTexto(email, exEmail);

        cadastrarButton.addActionListener(e -> {
            boolean nomeValido = true;
            boolean cpfValido = true;
            boolean telefoneValido = true;
            boolean emailValido = true;
            boolean enderecoValido = true;

            try (Connection con = ConexaoUI.conectar()) {

                if (!ConfigLayout.verificarCampoObrigatorio(nome, exNome)) {
                    nomeLabel.setText("Nome é obrigatório!");
                    nomeLabel.setForeground(Color.RED);
                    nomeValido = false;
                } else if (!ServicoCliente.verificarNome(nome.getText())) {
                    nomeLabel.setText("Nome inválido!");
                    nomeLabel.setForeground(Color.RED);
                    nomeValido = false;
                } else {
                    nomeLabel.setText("Nome:");
                    nomeLabel.setForeground(Color.BLACK);
                }

                if (!ConfigLayout.verificarCampoObrigatorio(cpf, exCpf)) {
                    cpfLabel.setText("CPF é obrigatório!");
                    cpfLabel.setForeground(Color.RED);
                    cpfValido = false;
                } else if (!ServicoCliente.ehCPFValido(cpf.getText())) {
                    cpfLabel.setText("CPF inválido!");
                    cpfLabel.setForeground(Color.RED);
                    cpfValido = false;
                } else if (ServicoCliente.ehRepetido(con, ServicoCliente.formatarCpf(cpf.getText()), sqlCpf)) { // CORREÇÃO AQUI
                    cpfLabel.setText("Cpf já cadastrado!");
                    cpfLabel.setForeground(Color.RED);
                    cpfValido = false;
                } else {
                    cpfLabel.setText("CPF:");
                    cpfLabel.setForeground(Color.BLACK);
                }

                if (!ConfigLayout.verificarCampoObrigatorio(telefone, exTelefone)) {
                    telefoneLabel.setText("Telefone é obrigatório!");
                    telefoneLabel.setForeground(Color.RED);
                    telefoneValido = false;
                } else if (!ServicoCliente.ehTelefoneValido(telefone.getText())) {
                    telefoneLabel.setText("Telefone inválido!");
                    telefoneLabel.setForeground(Color.RED);
                    telefoneValido = false;
                } else if (ServicoCliente.ehRepetido(con, ServicoCliente.formatarTelefone(telefone.getText()), sqlTelefone)) { // CORREÇÃO AQUI
                    telefoneLabel.setText("Telefone já cadastrado!");
                    telefoneLabel.setForeground(Color.RED);
                    telefoneValido = false;
                } else {
                    telefoneLabel.setText("Telefone:");
                    telefoneLabel.setForeground(Color.BLACK);
                }

                if (!ConfigLayout.verificarCampoObrigatorio(email, exEmail)) {
                    emailLabel.setText("E-mail é obrigatório!");
                    emailLabel.setForeground(Color.RED);
                    emailValido = false;
                } else if (!ServicoCliente.verificarEmail(email.getText())) {
                    emailLabel.setText("E-mail inválido!");
                    emailLabel.setForeground(Color.RED);
                    emailValido = false;
                } else {
                    emailLabel.setText("E-mail:");
                    emailLabel.setForeground(Color.BLACK);
                }

                if (!ConfigLayout.verificarCampoObrigatorio(endereco, exEndereco)) {
                    enderecoLabel.setText("Endereço é obrigatório!");
                    enderecoLabel.setForeground(Color.RED);
                    enderecoValido = false;
                } else {
                    enderecoLabel.setText("Endereço:");
                    enderecoLabel.setForeground(Color.BLACK);
                }

                if (nomeValido && cpfValido && telefoneValido && emailValido && enderecoValido) {
                    String nomeCliente = nome.getText();
                    String enderecoCliente = endereco.getText();
                    String telefoneCliente = telefone.getText();
                    String cpfCliente = cpf.getText();
                    String emailCliente = email.getText();

                    GerenciadorBancoDados.inserirDadosCliente(con, nomeCliente, emailCliente, ServicoCliente.formatarCpf(cpfCliente), enderecoCliente, ServicoCliente.formatarTelefone(telefoneCliente), "Ativo", tabela);
                    JOptionPane.showMessageDialog(null, "'" + nome.getText() + "' cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.getWindowAncestor(cadastroclientePanel).dispose();
                } else if (!cpfValido || !telefoneValido) {
                    // Não mostra popup de erro de validação se for CPF ou Telefone duplicado, pois o label já informa
                }
                else {
                    JOptionPane.showMessageDialog(null, "Por favor, corrija os campos inválidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de dados: " + ex.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Mostra o erro detalhado no console
            }
        });

        ConfigLayout.addEnterFuncao(cadastrarButton, email);
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(pai);
        this.add(cadastroclientePanel);
    }
}