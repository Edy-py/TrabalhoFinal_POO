package InterfaceGrafica.TelaCliente;

import BancodeDados.CarregamentoDeDados;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Vector;

/**
 * Esta classe representa a janela principal que exibe os clientes em uma tabela.
 */


public class TelaCliente {
    private JPanel MainCliente;
    private JPanel topoPanel;
    private JPanel topolateralPanel;
    private JLabel clientesLabel;
    private JComboBox<String> filtroCombobox;
    private JLabel filtrarLabel;
    private JScrollPane scrollerPanel;
    private JTable tabelaPanel;
    private JButton cadastrarButton;
    private JPanel topo2Panel;
    private JTextField buscaTextField;
    private JButton buscarButton;

    private String colunas = "id,nome,cpf,email,telefone,endereco,status";
    private String tabela = "clientes";
    private String sql = "SELECT " + colunas + " FROM " + tabela + " WHERE status LIKE ? ";
    private String sqlSemFiltro = "SELECT " + colunas + " FROM " + tabela;

    public Vector<String> mudarNomeDasColunas(){
        Vector<String> nomesAmigaveis = new Vector<>();
        nomesAmigaveis.add("ID_Cliente");
        nomesAmigaveis.add("Nome");
        nomesAmigaveis.add("CPF");
        nomesAmigaveis.add("E-mail");
        nomesAmigaveis.add("Telefone");
        nomesAmigaveis.add("Endereço");
        nomesAmigaveis.add("Status");

        return nomesAmigaveis;
    }


    //Metodo para preencher a combobox
    private void preencherComboBox() {
        filtroCombobox.addItem("Devendo");
        filtroCombobox.addItem("Alugando");
        filtroCombobox.addItem("Todos");
    }

    public TelaCliente() {

        CarregamentoDeDados threadSemfiltro = new CarregamentoDeDados(sqlSemFiltro, null, mudarNomeDasColunas(), tabelaPanel);
        threadSemfiltro.execute();

        filtroCombobox.addItemListener(e -> {

            if(e.getStateChange() == ItemEvent.SELECTED){

                String filtro = filtroCombobox.getSelectedItem().toString();

                if ("Todos".equals(filtro)) {
                    CarregamentoDeDados thread = new CarregamentoDeDados(sqlSemFiltro, null, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();

                }
                else {
                    CarregamentoDeDados thread = new CarregamentoDeDados(sql, filtro, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();
                }
            }
        });

        //fazendo o botao abrir uma nova janela
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroClientes telaCadastroClientes = new TelaCadastroClientes();
                telaCadastroClientes.setVisible(true);
            }
        });

        //chamando o metodo que preenche a combobox
        preencherComboBox();
    }


    //getter da classe para retornar o painel principal
    public JPanel getMainCliente() {return MainCliente;}
}
