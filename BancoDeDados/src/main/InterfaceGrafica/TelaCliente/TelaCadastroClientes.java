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

    // Define o nome da tabela no banco de dados
    private String tabela = "clientes";




    //constructor da janela extra de cadastro
    public TelaCadastroClientes() {
        super();


        // Textos de exempos de como o usuário deve preencher os campos para cadastrar um cliente no banco de dados.
        String exNome = "Digite o nome do cliente: ";
        String exEndereco = "Rua, Bairro, Casa, Cidade";
        String exTelefone = "Digite o telefone do cliente: (64) 99999-9999";
        String exCpf = "Digite o CPF do cliente: 999.999.999-99";
        String exEmail = "Digite o email do cliente: exemplo@gamil.com";


        // Explicação na classe "ConfigLayout"
        ConfigLayout.exemploDeTexto(nome, exNome );
        ConfigLayout.exemploDeTexto(endereco, exEndereco);
        ConfigLayout.exemploDeTexto(telefone, exTelefone);
        ConfigLayout.exemploDeTexto(cpf, exCpf);
        ConfigLayout.exemploDeTexto(email, exEmail);



        // Função lambda para fazer o botão cadastrar funcionar.
        cadastrarButton.addActionListener(e -> {

            // Flags de verificação
            boolean nomeValido = true;
            boolean cpfValido = true;
            boolean telefoneValido = true;
            boolean emailValido = true;
            boolean enderecoValido = true;

            // Utilizando a forma try-catch-resourse para conectar ao banco de dados e inserir os dados do cliente.
            try (Connection con = ConexaoUI.conectar()) {

                // Não permite que um campo fique vazio
                if (!ConfigLayout.verificarCampoObrigatorio(nome, exNome)) {
                    nomeLabel.setText("Nome é obrigatório!");
                    nomeLabel.setForeground(Color.RED);
                    nomeValido = false;

                    // Verifica se o nome digitado é válido
                } else if (!ServicoCliente.verificarNome(nome.getText())) {
                    nomeLabel.setText("Nome inválido!");
                    nomeLabel.setForeground(Color.RED);
                    nomeValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                } else {
                    nomeLabel.setText("Nome:");
                    nomeLabel.setForeground(Color.BLACK);
                }

                // Não permite que um campo fique vazio
                if (!ConfigLayout.verificarCampoObrigatorio(cpf, exCpf)) {
                    cpfLabel.setText("CPF é obrigatório!");
                    cpfLabel.setForeground(Color.RED);
                    cpfValido = false;

                    // Verifica se o cpf digitado é válido
                } else if (!ServicoCliente.ehCPFValido(cpf.getText())) {
                    cpfLabel.setText("CPF inválido!");
                    cpfLabel.setForeground(Color.RED);
                    cpfValido = false;

                    // Verifica se o cpf não já foi cadastrado no banco de dados.
                }else if(ServicoCliente.ehCpfRepetido(con, cpf.getText())){
                        cpfLabel.setText("Cpf já cadastrado!");
                        cpfLabel.setForeground(Color.RED);
                        cpfValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                }else {
                    cpfLabel.setText("CPF:");
                    cpfLabel.setForeground(Color.BLACK);
                }


                // Não permite que um campo fique vazio
                if (!ConfigLayout.verificarCampoObrigatorio(telefone, exTelefone)) {
                    telefoneLabel.setText("Telefone é obrigatório!");
                    telefoneLabel.setForeground(Color.RED);
                    telefoneValido = false;

                    // Verifica se o telefone digitado é válido
                } else if (!ServicoCliente.ehTelefoneValido(telefone.getText())) {
                    telefoneLabel.setText("Telefone inválido!");
                    telefoneLabel.setForeground(Color.RED);
                    telefoneValido = false;

                    // Verifica se o telefone não está repetido no banco de dados.
                }else if(ServicoCliente.ehTelefoneRepetido(con, telefone.getText())){
                        telefoneLabel.setText("Telefone já cadastrado!");
                        telefoneLabel.setForeground(Color.RED);
                        telefoneValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                }else{
                    telefoneLabel.setText("Telefone:");
                    telefoneLabel.setForeground(Color.BLACK);
                }

                // // Não permite que um campo fique vazio
                if (!ConfigLayout.verificarCampoObrigatorio(email, exEmail)) {
                    emailLabel.setText("E-mail é obrigatório!");
                    emailLabel.setForeground(Color.RED);
                    emailValido = false;

                    // Verifica se o e-mail digitado é válido
                } else if (!ServicoCliente.verificarEmail(email.getText())) {
                    emailLabel.setText("E-mail inválido!");
                    emailLabel.setForeground(Color.RED);
                    emailValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                } else {
                    emailLabel.setText("E-mail:");
                    emailLabel.setForeground(Color.BLACK);
                }


                // Não permite que um campo fique vazio
                if (!ConfigLayout.verificarCampoObrigatorio(endereco, exEndereco)) {
                    enderecoLabel.setText("Endereço é obrigatório!");
                    enderecoLabel.setForeground(Color.RED);
                    enderecoValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                } else {
                    enderecoLabel.setText("Endereço:");
                    enderecoLabel.setForeground(Color.BLACK);
                }

                // Se todos os dados forem válidos, insere os dados no banco de dados.
                if (nomeValido && cpfValido && telefoneValido && emailValido && enderecoValido) {

                    String nomeCliente = nome.getText();
                    String enderecoCliente = endereco.getText();
                    String telefoneCliente = telefone.getText();
                    String cpfCliente = cpf.getText();
                    String emailCliente = email.getText();

                    // Insere os dados no banco de dados.
                    GerenciadorBancoDados.inserirDadosCliente(con, nomeCliente,emailCliente,ServicoCliente.formatarCpf(cpfCliente),enderecoCliente,ServicoCliente.formatarTelefone(telefoneCliente),null,tabela);

                    // Exibe uma mensagem de sucesso na tela.
                    JOptionPane.showMessageDialog(null, "" + nome.getText() + " cadastrado com sucesso no banco de dados!", "Cliente Cadastrado", JOptionPane.INFORMATION_MESSAGE);


                    // Fecha a janela de cadastro após cadastrado com sucesso.
                    SwingUtilities.getWindowAncestor(cadastroclientePanel).dispose();

                    // Mensagem para email inválido
                } else if (nomeValido && cpfValido && telefoneValido && !emailValido) {
                    JOptionPane.showMessageDialog(null, "E-mail inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                    // Mensagem para telefone inválido
                } else if (nomeValido && cpfValido && !telefoneValido && emailValido) {
                    JOptionPane.showMessageDialog(null, "Telefone inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                    // Mensagem para cpf inválido
                } else if (nomeValido && !cpfValido && telefoneValido && emailValido) {
                    JOptionPane.showMessageDialog(null, "CPF inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                    // Mensagem para nome inválido
                } else if (!nomeValido && cpfValido && telefoneValido && emailValido) {
                    JOptionPane.showMessageDialog(null, "Nome inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }

                // Mensagem de erro de conexão
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de dados", "Erro de conexão", JOptionPane.ERROR_MESSAGE);
            }
        }); // Fim da função lambda

        // Configura para que possamos pesquisar apertando a tecla "enter"
        ConfigLayout.addEnterFuncao(cadastrarButton, email);

        // Define titulo da janela
        setTitle("Cadastro de Clientes");

        // Fecha a janela quando apertamos no x
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configura o tamanho da janela
        setSize(600, 650);


        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(cadastroclientePanel);
    }

}
