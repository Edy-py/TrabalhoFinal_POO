package InterfaceGrafica.CadastrarJogos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastrarJogos {

    //declarando os componentes do panel
    private JPanel cadastrojogosPanel;
    private JPanel topoPanel;
    private JComboBox<String> filtroCombobox;
    private JPanel topolateralPanel;
    private JLabel jogosLabel;
    private JScrollPane scrollerPanel;
    private JTable tabelaPanel;
    private JButton cadastrarButton;

    //Metodo para preencher a combobox
    private void preencherComboBox() {
        filtroCombobox.addItem("Nome");
        filtroCombobox.addItem("Console");
    }

    public CadastrarJogos() {
        //definindo um modelo para a tabela
        String[] colunas = {"Nome", "Console", "Quantidade Disp.", "Preço", "Ações"}; //configurando o nome das colunas
        Object[][] dados = {}; // vazio por enquanto, linkar com o banco de dados
        //Codigo dando erro, vou ver se arrumo depois
        //tabelaPanel.getColumnModel().getColumn(3).setPreferredWidth(80);

        //atualizando a tabela com as mudancas
        tabelaPanel.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));

        //fazendo o botao abrir uma nova janela TelaCadastro
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastro telaCadastro = new TelaCadastro();
                telaCadastro.setVisible(true);
            }
        });

        //chamando o metodo que preenche a combobox
        preencherComboBox();
    }

    public JPanel getCadastrojogosPanel() {
        return cadastrojogosPanel;
    }

}
