import javax.swing.*;
import java.awt.*;

public class InterfacePrincipal {

    //Declarando os atributos da interface
    private JPanel mainPanel;
    private JPanel panelBotoes;
    private JButton clientesButton;
    private JButton locarButton;
    private JButton jogosButton;
    private JButton receberButton;
    private JPanel panelFuncoes;
    private JPanel clientesPanel;
    private JPanel locarPanel;
    private JPanel jogosPanel;
    private JPanel receberPanel;

    //funcao que realiz a navegacao entre os paineis das funcoes:
    private void mostrarPainel(String nomePainel){
        CardLayout cl = (CardLayout)panelFuncoes.getLayout();
        cl.show(panelFuncoes, nomePainel);
    }

    //construtor da interface
    //atribuindo uma funcao mostrarPainel para cada botao
    //atribuindo outras interfaces aos cards do panelFuncoes
    public InterfacePrincipal(){

        //criando instancias das outras interfaces:
        LocarJogos locarJogosInter = new LocarJogos();

        locarPanel.setLayout(new BorderLayout());
        locarPanel.add(locarJogosInter.getControlPane(), BorderLayout.CENTER);

        clientesButton.addActionListener(e -> mostrarPainel("Card1"));
        locarButton.addActionListener(e -> mostrarPainel("Card2"));
        jogosButton.addActionListener(e -> mostrarPainel("Card3"));
        receberButton.addActionListener(e -> mostrarPainel("Card4"));
    }

    //criando a funcao main para iniciar o JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Locadora");
        frame.setContentPane(new InterfacePrincipal().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

}
