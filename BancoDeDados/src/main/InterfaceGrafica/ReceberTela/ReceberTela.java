package InterfaceGrafica.ReceberTela;

import BancodeDados.ConexaoUI;
import BancodeDados.GerenciadorBancoDados;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReceberTela {
    //declarando os atributos da interface
    private JPanel receberPanel;
    private JPanel topoPanel;
    private JPanel topoLateralPanel;
    private JComboBox<String> filtroCombobox;
    private JLabel filtrarLabel;
    private JPanel topoPanel2;
    private JTextField buscaTextfield;
    private JButton buscarButton;
    private JLabel receberLabel;
    private JTable tabelaPanel;
    private JButton receberButton;
    private JButton atualizarButton;

    //método para preencher a combobox
    private void preencherComboBox() {
        filtroCombobox.addItem("Alugando");
        filtroCombobox.addItem("Devendo");
        filtroCombobox.addItem("Todos");
    }

    private void carregarLocacoes(String filtro) {
        try {
            DefaultTableModel modelo = GerenciadorBancoDados.getDadosLocacoes(filtro);
            tabelaPanel.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar locações: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    //constructor da janela
    public ReceberTela() {

        preencherComboBox();
        carregarLocacoes("Alugando");


        receberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // 1. Verifica se existem locações pendentes ANTES de abrir a janela
                    ArrayList<String> clientesPendentes = ConexaoUI.getInfoColuna("c.nome", "locacoes l JOIN clientes c ON l.id_cliente = c.id WHERE l.status_locacao = 'Alugando' OR l.status_locacao = 'Devendo'");

                    // 2. Se a lista estiver vazia, mostra o aviso e NÃO abre a janela
                    if (clientesPendentes.isEmpty()) {
                        JOptionPane.showMessageDialog(receberPanel, "Não há locações pendentes para receber.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // 3. Se houver locações, abre a janela de recebimento
                        ReceberSubTela receberSubTela = new ReceberSubTela();
                        receberSubTela.setVisible(true);
                        // Atualiza a tabela principal depois que a subtela for fechada
                        atualizarButton.doClick();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(receberPanel, "Erro ao verificar locações pendentes: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        filtroCombobox.addActionListener(e -> {
            if (filtroCombobox.getSelectedItem() != null) {
                String filtro = filtroCombobox.getSelectedItem().toString();
                carregarLocacoes(filtro);
            }
        });

        atualizarButton.addActionListener(e -> {
            if (filtroCombobox.getSelectedItem() != null) {
                String filtro = filtroCombobox.getSelectedItem().toString();
                carregarLocacoes(filtro);
            }
        });
    }

    //getter da classe para retornar o painel principal
    public JPanel getReceberPanel() {
        return receberPanel;
    }
}