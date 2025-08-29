package InterfaceGrafica.CadastrarJogos;

import BancodeDados.CarregamentoDeDados;
import BancodeDados.ConexaoBanco;
import BancodeDados.ConexaoUI;
import BancodeDados.GerenciadorBancoDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

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

    private String colunas = "id,nome,publicacao,console,classificacao,ano_lancamento,quantidade,quantidade_disponivel,preco";
    private String tabela = "jogos";
    private String sql = "SELECT " + colunas + " FROM " + tabela + " WHERE console LIKE ? ORDER BY nome ASC";
    private String sqlSemFiltro = "SELECT " + colunas + " FROM " + tabela + " ORDER BY nome ASC";

    public Vector<String> mudarNomeDasColunas(){
        Vector<String> nomesAmigaveis = new Vector<>();
        nomesAmigaveis.add("Id_jogo");
        nomesAmigaveis.add("Nome");
        nomesAmigaveis.add("Publisher");
        nomesAmigaveis.add("Console");
        nomesAmigaveis.add("Classificacao");
        nomesAmigaveis.add("Ano_Lancamento");
        nomesAmigaveis.add("Em_Estoque");
        nomesAmigaveis.add("Disponivel");
        nomesAmigaveis.add("Preco");

        return nomesAmigaveis;
    }


    //Metodo para preencher a combobox
    private void preencherComboBox() throws SQLException {


        // busca os dados da tabela jogos na coluna consoles e os coloca na combobox
        ArrayList<String> consoles = ConexaoUI.getInfoColuna("Console", "jogos");


        // add a opção "Todos" a cobobox
        consoles.add(0, "Todos");

        // adiciona o array ao cobobox
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(consoles.toArray(new String[0]));
        filtroCombobox.setModel(modelo);
    }



    public CadastrarJogos(){

        CarregamentoDeDados threadSemfiltro = new CarregamentoDeDados(sqlSemFiltro, null, mudarNomeDasColunas(), tabelaPanel);
        threadSemfiltro.execute();


        filtroCombobox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                // Filtro de acordo com o item selecionado na combobox
                String filtro = filtroCombobox.getSelectedItem().toString();


                // Sem filtro
                if ("Todos".equals(filtro)) {
                    CarregamentoDeDados thread = new CarregamentoDeDados(sqlSemFiltro, null, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();

                }
                else {
                        // Com filtro
                        CarregamentoDeDados thread = new CarregamentoDeDados(sql, filtro, mudarNomeDasColunas(), tabelaPanel);
                        thread.execute();
                }

            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastro telaCadastro = new TelaCadastro();
                telaCadastro.setVisible(true);
            }
        });

        try {
            preencherComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //getter da classe para retornar o painel principal
    public JPanel getCadastrojogosPanel() {
        return cadastrojogosPanel;
    }

}
