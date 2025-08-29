package BancodeDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class CarregamentoDeDados extends SwingWorker<DefaultTableModel, Void> {

    // Classe para carregamento de dados para evitar gargalos
    // SwingWorker é uma classe do Swing que executa uma tarefa em segundo plano (uma thread)

    private String sql;
    private Object valorFiltro;
    private Vector<String> nomesColunasAmigaveis;
    private JTable tabela;

    public CarregamentoDeDados(String sql, Object valorFiltro, Vector<String> nomesColunasAmigaveis, JTable tabela){
        this.sql = sql;
        this.valorFiltro = valorFiltro;
        this.nomesColunasAmigaveis = nomesColunasAmigaveis;
        this.tabela = tabela;
    }



    // Busca os dados da tabela no banco de dados e retorna um modelo de tabela
    protected DefaultTableModel doInBackground() throws Exception {
        return new ConexaoUI().buscarDadosFiltrados(ConexaoBanco.conectar(), sql, nomesColunasAmigaveis, valorFiltro);
    }

    @Override
    // semelhante ao método run de um thread, ensina para thread
    protected void done() {
        try {
            DefaultTableModel modelo = get();

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);

            tabela.setRowSorter(sorter);

            tabela.setModel(modelo);



        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Erro na execução, tente novamente mais tarde!", "Erro de execução", JOptionPane.ERROR_MESSAGE);

        }
    }

}
