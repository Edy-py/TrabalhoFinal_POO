package InterfaceGrafica.ReceberTela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceberTela {
    //declarando os atributos da interface
    private JPanel receberPanel;
    private JPanel topoPanel;
    private JPanel topoLateralPanel;
    private JComboBox<String> filtroCombobox;
    private JLabel filtrarLabel;
    private JPanel topoPanel2;
    private JTextField buscaTextfield;
    private JButton buscarButton;
    private JLabel receberLabel;
    private JTable tabelaPanel;
    private JButton receberButton;
    private JButton atualizarButton;

    private String colunas = "id,nome,cpf,jogo_alugado,status";


    //método para preencher a combobox
    private void preencherComboBox() {
        filtroCombobox.addItem("Dia Limite");
        filtroCombobox.addItem("Devendo");
        filtroCombobox.addItem("Alugando");
        filtroCombobox.addItem("Todos");
    }

    //constructor da janela
    public ReceberTela() {


        //fazendo o botao abrir uma nova janela
        receberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReceberSubTela receberSubTela = new ReceberSubTela();
                receberSubTela.setVisible(true);
            }
        });

        //chamando o método para preencher a combobox
        preencherComboBox();
    }

    //getter da classe para retornar o painel principal
    public JPanel getReceberPanel() {
        return receberPanel;
    }
}
