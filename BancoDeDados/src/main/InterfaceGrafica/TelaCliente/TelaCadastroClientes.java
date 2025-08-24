package InterfaceGrafica.TelaCliente;

import javax.swing.*;

public class TelaCadastroClientes extends JDialog {
    private JPanel cadastroclientePanel;
    private JLabel nomeLabel;
    private JLabel enderecoLabel;
    private JLabel telefoneLabel;
    private JLabel cpfLabel;
    private JLabel emailLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton cadastrarButton;

    //constructor da janela extra de cadastro
    public TelaCadastroClientes() {
        super();
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(cadastroclientePanel);
    }

}
