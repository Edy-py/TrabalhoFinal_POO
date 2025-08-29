package InterfaceGrafica.CadastrarJogos;

import javax.swing.*;

public class ExcluirJogos extends JDialog {
    //declarando os atributos da interface
    private JPanel excluirJogosPanel;
    private JTextField idJogoTextField;
    private JLabel insiraIDJogoLabel;
    private JLabel nomedojogoselecionadoLabel;
    private JLabel nomeDoJogoLabel;
    private JButton confirmarExclusãoButton;

    public  ExcluirJogos() {
        super();

        // Define titulo da janela
        setTitle("Exlcuir Jogo");

        // Fecha a janela quando apertamos no x
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configura o tamanho da janela
        setSize(600, 500);

        // Fazendo a tela aparecer no centro da tela
        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(excluirJogosPanel);
    }
}
