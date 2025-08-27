package InterfaceGrafica.ReceberTela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceberSubTela extends JDialog {
    private JPanel recebersubtelaPanel;
    private JLabel clienteJLabel;
    private JComboBox clientesCombobox;
    private JLabel jogoLabel;
    private JLabel precoLabel;
    private JLabel atrasoLabel;
    private JLabel precototalLabel;
    private JLabel diasLabel;
    private JButton receberButton;
    private JLabel mostrarjogolabel;
    private JLabel mostrarprecoLabel;
    private JLabel mostrardiasLabel;
    private JLabel mostraratrasoLabel;
    private JLabel mostrartotalLabel;

    public void preencherCombobox(){
        //Edilson, faz sua mágica dos banco de dados aqui
    }

    public ReceberSubTela() {
        super();

        receberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Inserir funcao
            }
        });

        setTitle("Receber");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(recebersubtelaPanel);

    }
}
