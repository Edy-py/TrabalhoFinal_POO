//Edílson Alves da Silva (GitHub: edy-py)
//Guilherme Henrique Garcia Silva (GitHub: Guigas-hgs)
//Élio Mário Soares Júnior (GitHub: BrawlerGits)

package InterfaceGrafica.InterfacePrincipal;

import BancodeDados.GerenciadorBancoDados;
import InterfaceGrafica.CadastrarJogos.CadastrarJogos;
import InterfaceGrafica.LocarJogos.LocarJogos;
import InterfaceGrafica.ReceberTela.ReceberTela;
import InterfaceGrafica.TelaCliente.TelaCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InterfacePrincipal {

    //declarando os componentes do panel
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

    //funcao que realiza a navegacao entre os paineis das funcoes:
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
        CadastrarJogos cadastrarJogosInter = new CadastrarJogos();
        TelaCliente telaClienteInter = new TelaCliente();
        ReceberTela telaReceberInter = new ReceberTela();

        //atribuindo outras interfaces aos cards que estão dentro da panelFuncoes
        locarPanel.setLayout(new BorderLayout());
        locarPanel.add(locarJogosInter.getControlPanel(), BorderLayout.CENTER);

        jogosPanel.setLayout(new BorderLayout());
        jogosPanel.add(cadastrarJogosInter.getCadastrojogosPanel(), BorderLayout.CENTER);

        clientesPanel.setLayout(new BorderLayout());
        clientesPanel.add(telaClienteInter.getMainCliente(),  BorderLayout.CENTER);

        receberPanel.setLayout(new BorderLayout());
        receberPanel.add(telaReceberInter.getReceberPanel(), BorderLayout.CENTER);

        //fazendo com que cada botão troque entre os cards do panelFuncoes
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
        frame.setSize(1200, 720);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}