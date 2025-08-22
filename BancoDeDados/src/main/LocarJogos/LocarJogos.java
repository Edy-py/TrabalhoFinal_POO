import javax.swing.*;

public class LocarJogos {
    private JPanel controlPane;
    private JTextField findCPF_TextField;
    private JComboBox findJogo_ComboBox;
    private JSpinner setDias_Spinner;
    private JLabel getPreco_Label;
    private JButton confirmar_Button;
    private JLabel info1_Label;
    private JLabel info2_Label;
    private JLabel info3_Label;
    private JLabel info5_Label;
    private JLabel info4_Label;
    private JLabel getNome_Label;

    //um getter para "mandar" esta interface para a principal.
    public JPanel getControlPane() {
        return controlPane;
    }

}