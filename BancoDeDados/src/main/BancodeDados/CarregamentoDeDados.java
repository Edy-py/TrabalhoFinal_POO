package BancodeDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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



    @Override
    protected DefaultTableModel doInBackground() throws Exception {
        return new ConexaoUI().buscarDadosFiltrados(ConexaoBanco.conectar(), sql, nomesColunasAmigaveis, valorFiltro);
    }

    @Override
    // semelhante ao método run de um thread
    protected void done() {
        try {
             tabela.setModel(get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
