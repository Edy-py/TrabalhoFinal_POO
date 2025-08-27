package InterfaceGrafica.TelaCliente;

import Classes.ConfigLayout;
import Classes.ServicoCliente;

import javax.swing.*;
import java.awt.*;

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



    //constructor da janela extra de cadastro
    public TelaCadastroClientes() {
        super();

        String exNome = "Digite o nome do cliente: ";
        String exEndereco = "Rua, Bairro, Casa, Cidade";
        String exTelefone = "Digite o telefone do cliente: (64) 99999-9999";
        String exCpf = "Digite o CPF do cliente: 999.999.999-99";
        String exEmail = "Digite o email do cliente: exemplo@gamil.com";

        ConfigLayout.exemploDeTexto(nome, exNome );
        ConfigLayout.exemploDeTexto(endereco, exEndereco);
        ConfigLayout.exemploDeTexto(telefone, exTelefone);
        ConfigLayout.exemploDeTexto(cpf, exCpf);
        ConfigLayout.exemploDeTexto(email, exEmail);


        cadastrarButton.addActionListener(e -> {
            String comando = e.getActionCommand();
            if(comando.equals("Cadastrar")){

                if (!ConfigLayout.verificarCampoObrigatorio(nome, exNome) && !ServicoCliente.verificarNome(nome.getText())) {
                    nomeLabel.setText("Nome inválido!");
                    nomeLabel.setForeground(Color.RED);
                    return; // Interrompe a execução
                }

                // Se chegou até aqui, o nome é válido. Podemos limpar a mensagem de erro.
                nomeLabel.setText("Nome:");
                nomeLabel.setForeground(Color.BLACK); // ou a cor padrão


                if (!ConfigLayout.verificarCampoObrigatorio(cpf, exNome) && !ServicoCliente.ehCPFValido(cpf.getText())) {
                    cpfLabel.setText("Cpf inválido!");
                    cpfLabel.setForeground(Color.RED);
                    return; // Interrompe a execução
                }

                // Se chegou até aqui, o nome é válido. Podemos limpar a mensagem de erro.
                cpfLabel.setText("CPF:");
                cpfLabel.setForeground(Color.BLACK); // ou a cor padrão


                if (!ConfigLayout.verificarCampoObrigatorio(telefone, exNome) && !ServicoCliente.ehTelefoneValido(telefone.getText())) {
                    telefoneLabel.setText("Telefone inválido!");
                    telefoneLabel.setForeground(Color.red);
                    return;
                }

                telefoneLabel.setText("Telefone:");
                telefoneLabel.setForeground(Color.black);


                if (!ConfigLayout.verificarCampoObrigatorio(email, exNome) && !ServicoCliente.verificarEmail(email.getText())) {

                    emailLabel.setText("Telefone inválido!");
                    emailLabel.setForeground(Color.red);
                    return;
                }
                emailLabel.setText("");
                emailLabel.setForeground(Color.black);
                email.setText("");

                if (!ConfigLayout.verificarCampoObrigatorio(endereco, exNome)) {
                    return;
                }

                // Fecha a janela de cadastro após cadastrado com sucesso.
                SwingUtilities.getWindowAncestor(cadastroclientePanel).dispose();

                JOptionPane.showMessageDialog(null, "" + nome.getText() + " cadastrado com sucesso!", "Cliente Cadastrado", JOptionPane.INFORMATION_MESSAGE);

            }else{

                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }

                String nomeCliente = nome.getText();
                String enderecoCliente = endereco.getText();
                String telefoneCliente = telefone.getText();
                String cpfCliente = cpf.getText();
                String emailCliente = email.getText();




        });




        ConfigLayout.addEnterFuncao(cadastrarButton, email);
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(cadastroclientePanel);
    }

}
