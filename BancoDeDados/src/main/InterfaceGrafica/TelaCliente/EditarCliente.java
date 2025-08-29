package InterfaceGrafica.TelaCliente;

import javax.swing.*;

public class EditarCliente extends JDialog {
    //declarando os atributos da interface
    private JPanel editarClientePanel;
    private JLabel idClienteLabel;
    private JLabel nomeLabel;
    private JLabel cpfLabel;
    private JLabel emailLabel;
    private JLabel telefoneLabel;
    private JLabel enderecoLabel;
    private JTextField IDTextfield;
    private JTextField nomeTextfield;
    private JTextField cpfTextfield;
    private JTextField emailTextfield;
    private JTextField telefoneTextfield;
    private JTextField enderecoTextfield;
    private JButton confirmarButton;


    public EditarCliente() {
        super();

        // Define titulo da janela
        setTitle("Editar Cliente");

        // Fecha a janela quando apertamos no x
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configura o tamanho da janela
        setSize(600, 650);


        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(editarClientePanel);
    }
}
