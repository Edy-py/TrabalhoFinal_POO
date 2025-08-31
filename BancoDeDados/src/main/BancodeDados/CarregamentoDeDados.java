package BancodeDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

public class CarregamentoDeDados extends SwingWorker<DefaultTableModel, Void> {

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

    protected DefaultTableModel doInBackground() throws Exception {
        try {
            // A consulta original permanece aqui
            return new ConexaoUI().buscarDadosFiltrados(ConexaoBanco.conectar(), sql, nomesColunasAmigaveis, valorFiltro);
        } catch (Exception e) {
            // --- ADIÇÃO PARA DEBUG ---
            // Se um erro acontecer durante a consulta, ele será impresso no console.
            System.err.println("### ERRO NA THREAD DE CARREGAMENTO DE DADOS ###");
            System.err.println("SQL com problema: " + sql);
            e.printStackTrace(); // Isso vai nos mostrar o erro exato do SQL.
            // --- FIM DA ADIÇÃO ---
            throw e; // Lança a exceção para que o método `done()` saiba que algo deu errado.
        }
    }

    @Override
    protected void done() {
        try {
            DefaultTableModel modelo = get();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
            tabela.setRowSorter(sorter);
            tabela.setModel(modelo);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (ExecutionException e) {
            // Esta mensagem é a que você está vendo. A causa raiz será impressa no console agora.
            JOptionPane.showMessageDialog(null, "Erro na execução, tente novamente mais tarde!", "Erro de execução", JOptionPane.ERROR_MESSAGE);
        }
    }
}