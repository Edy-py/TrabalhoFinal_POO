package InterfaceGrafica.LocarJogos;

import javax.swing.*;

public class LocarJogos {
    //declarando os atributos da tela
    private JPanel ControlPanel;
    private JPanel informacoesPanel;
    private JButton confirmarLocacaoButton;
    private JLabel cpfLabel;
    private JTextField cpfTextField;
    private JLabel nomeparaaparecerLabel;
    private JLabel nomeclienteLabel;
    private JComboBox<String> jogoCombobox;
    private JSpinner diasSpinner;
    private JLabel jogoLabel;
    private JLabel diasLabel;
    private JLabel precototalLabel;
    private JLabel inserirprecoLabel;

    //getter da classe para mandar o panel principal para a tela principal
    public JPanel getControlPanel() {
        return ControlPanel;
    }
}
