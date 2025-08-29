package InterfaceGrafica.CadastrarJogos;

import BancodeDados.CarregamentoDeDados;
import BancodeDados.ConexaoUI;
import Classes.ConfigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
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

    // Strings para consulta no banco de dados
    private String colunas = "id,nome,publicacao,console,classificacao,ano_lancamento,quantidade,quantidade_disponivel,preco";
    private String tabela = "jogos";
    private String sql = "SELECT " + colunas + " FROM " + tabela + " WHERE console LIKE ? ORDER BY nome ASC";
    private String sqlSemFiltro = "SELECT " + colunas + " FROM " + tabela + " ORDER BY nome ASC";

    // Essa função é usada para mudar o nome das colunas da tabela, só para mostrar na UI
    private Vector<String> mudarNomeDasColunas(){

        // Declaração de um objeto do tipo Vector para armazenar os nomes das colunas da tabela
        Vector<String> nomesAmigaveis = new Vector<>();

        nomesAmigaveis.add("Id_jogo");
        nomesAmigaveis.add("Nome");
        nomesAmigaveis.add("Publisher");
        nomesAmigaveis.add("Console");
        nomesAmigaveis.add("Classificação");
        nomesAmigaveis.add("Ano_Lançamento");
        nomesAmigaveis.add("Em_Estoque");
        nomesAmigaveis.add("Disponivel");
        nomesAmigaveis.add("Preço");

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

        // faz o filto funcionar
        filtroCombobox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                // Filtro de acordo com o item selecionado na combobox
                String filtro = filtroCombobox.getSelectedItem().toString();

                ConfigLayout setFiltro = new ConfigLayout();
                setFiltro.setFiltro(filtro);


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

        // faz o botão de cadastro funcionar
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastro telaCadastro = new TelaCadastro();
                telaCadastro.setVisible(true);
            }
        });

        try {
            preencherComboBox();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro na execução, tente novamente mais tarde!", "Erro de execução", JOptionPane.ERROR_MESSAGE);
        }
    }

    //getter da classe para retornar o painel principal
    public JPanel getCadastrojogosPanel() {
        return cadastrojogosPanel;
    }

}
