package InterfaceGrafica.CadastrarJogos;

import javax.swing.*;

public class TelaCadastro extends JDialog{

    //declarando os componentes do panel
    private JPanel cadastroPanel;
    private JLabel nomeLabel;
    private JLabel publicacaoLabel;
    private JLabel consoleLabel;
    private JLabel classificacaoLabel;
    private JLabel anoLabel;
    private JLabel quantidadeLabel;
    private JLabel precoLabel;
    private JTextField nomeTextfield;
    private JTextField publisherTextfield;
    private JComboBox<String> classificacaoCombobox;
    private JTextField anolancamentoTextfield;
    private JTextField quantidadeTextfield;
    private JTextField precoTextfield;
    private JTextField consoleTextfield;
    private JButton cadastrarButton;

    //Metodo para preencher a combobox
    private void preencherComboBox() {
        classificacaoCombobox.addItem("Livre");
        classificacaoCombobox.addItem("10+");
        classificacaoCombobox.addItem("12+");
        classificacaoCombobox.addItem("14+");
        classificacaoCombobox.addItem("16+");
        classificacaoCombobox.addItem("18+");
    }

    //constructor da janela extra de cadastro
    public TelaCadastro() {
        super();
        setTitle("Cadastro de Jogos");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        //chamando o metodo que preenche a combobox
        preencherComboBox();

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(cadastroPanel);
    }

}
