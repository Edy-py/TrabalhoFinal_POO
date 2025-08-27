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
            boolean nomeValido = true;
            boolean cpfValido = true;
            boolean telefoneValido = true;
            boolean emailValido = true;
            boolean enderecoValido = true;



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
                }else {
                    enderecoLabel.setText("E-mail:");
                    enderecoLabel.setForeground(Color.BLACK);
                }

                if(nomeValido && cpfValido && telefoneValido && emailValido && enderecoValido){



                    JOptionPane.showMessageDialog(null, "" + nome.getText() + " cadastrado com sucesso!", "Cliente Cadastrado", JOptionPane.INFORMATION_MESSAGE);
                    // Fecha a janela de cadastro após cadastrado com sucesso.
                    SwingUtilities.getWindowAncestor(cadastroclientePanel).dispose();
                }
                else if(!nomeValido && !cpfValido && !telefoneValido && !emailValido && !enderecoValido){
                    JOptionPane.showMessageDialog(null, "Nenhum dado válido foi inserido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                }
                else if(nomeValido && cpfValido && telefoneValido && !emailValido){
                    JOptionPane.showMessageDialog(null, "E-mail inválido!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }
                else if(nomeValido && cpfValido && !telefoneValido && emailValido){
                    JOptionPane.showMessageDialog(null, "Telefone inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }
                else if(nomeValido && !cpfValido && telefoneValido && emailValido){
                    JOptionPane.showMessageDialog(null, "CPF inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }
                else if(!nomeValido && cpfValido && telefoneValido && emailValido){
                    JOptionPane.showMessageDialog(null, "Nome inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
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
