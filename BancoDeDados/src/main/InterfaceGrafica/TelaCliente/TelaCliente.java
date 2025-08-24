package InterfaceGrafica.TelaCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    //Metodo para preencher a combobox
    private void preencherComboBox() {
        filtroCombobox.addItem("Devendo");
        filtroCombobox.addItem("Alugando");
        filtroCombobox.addItem("Todos");
    }

    public TelaCliente() {
        //definindo um modelo para a tabela
        String[] colunas = {"Nome", "CPF", "Telefone", "Status", "Ações"}; //configurando o nome das colunas
        Object[][] dados = {}; // vazio por enquanto, linkar com o banco de dados
        //Codigo dando erro, vou ver se arrumo depois
        //tabelaPanel.getColumnModel().getColumn(3).setPreferredWidth(80);

        //atualizando a tabela com as mudancas
        tabelaPanel.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));

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

    /**
     * Método responsável por conectar ao banco, buscar os dados e popular a JTable.
     */

    //vou comentar esse metodo por enquanto:
    /*
    private void carregarDados() {
        try (Connection conn = ConexaoBanco.conectar()) {
            // Usa o método que criamos no GerenciadorBancoDados.
            // Ele já retorna os dados no formato que a JTable precisa (DefaultTableModel).
            // Lembre-se que renomeamos a tabela para "clientes" (minúsculo).
            DefaultTableModel model = GerenciadorBancoDados.buscarDadosParaTabela(conn, "clientes");

            // Define o modelo de dados na nossa JTable. É esta linha que preenche a tabela visualmente.
            TabelaCliente.setModel(model);
            System.out.println("Dados carregados com sucesso na tabela!");

        } catch (SQLException e) {
            // Se der um erro, exibe uma mensagem amigável para o usuário.
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar dados do banco de dados: " + e.getMessage(),
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace(); // Também imprime o erro detalhado no console para o desenvolvedor.
        }
    }
    */

    //getter da classe para retornar o painel principal
    public JPanel getMainCliente() {return MainCliente;}
}
