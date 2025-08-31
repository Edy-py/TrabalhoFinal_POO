//Edílson Alves da Silva (GitHub: edy-py)
//Guilherme Henrique Garcia Silva (GitHub: Guigas-hgs)
//Élio Mário Soares Júnior (GitHub: BrawlerGits)

package InterfaceGrafica.ReceberTela;

import BancodeDados.ConexaoUI;
import BancodeDados.GerenciadorBancoDados;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReceberSubTela extends JDialog {
    private JPanel recebersubtelaPanel;
    private JLabel clienteJLabel;
    private JComboBox<String> clientesCombobox;
    private JLabel jogoLabel;
    private JLabel precoLabel;
    private JLabel atrasoLabel;
    private JLabel precototalLabel;
    private JLabel diasLabel;
    private JButton receberButton;
    private JLabel mostrarjogolabel;
    private JLabel mostrarprecoLabel;
    private JLabel mostrardiasLabel;
    private JLabel mostraratrasoLabel;
    private JLabel mostrartotalLabel;

    private int idLocacaoAtual;
    private int idJogoAtual;
    private int idClienteAtual;

    public void preencherCombobox(){
        try {
            // Usamos um alias para evitar ambiguidade no nome da coluna
            ArrayList<String> clientes = ConexaoUI.getInfoColuna("c.nome", "locacoes l JOIN clientes c ON l.id_cliente = c.id WHERE l.status_locacao = 'Alugando' OR l.status_locacao = 'Devendo'");
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(clientes.toArray(new String[0]));
            clientesCombobox.setModel(modelo);

            if (clientes.isEmpty()) {
                receberButton.setEnabled(false);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a lista de clientes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarDetalhesLocacao() {
        if (clientesCombobox.getSelectedItem() == null) {
            // Limpa os campos se não houver cliente selecionado
            mostrarjogolabel.setText("");
            mostrarprecoLabel.setText("");
            mostrardiasLabel.setText("");
            mostraratrasoLabel.setText("");
            mostrartotalLabel.setText("");
            return;
        }

        try {
            String nomeCliente = clientesCombobox.getSelectedItem().toString();
            idClienteAtual = ConexaoUI.getIdPorNome(nomeCliente, "clientes");

            ResultSet rs = GerenciadorBancoDados.getLocacaoDetalhes(idClienteAtual);

            if (rs.next()) {
                idLocacaoAtual = rs.getInt("id_locacao");
                idJogoAtual = rs.getInt("id_jogo");

                String nomeJogo = rs.getString("nome");
                double precoDiario = rs.getDouble("preco");
                int diasAlugados = rs.getInt("dias_alugados");
                Date dataLocacao = rs.getDate("data_locacao");

                mostrarjogolabel.setText(nomeJogo);
                mostrarprecoLabel.setText("R$ " + String.format("%.2f", precoDiario));
                mostrardiasLabel.setText(diasAlugados + " dias");

                long diff = new Date().getTime() - dataLocacao.getTime();
                long diasPassados = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                double multa = 0;
                if (diasPassados > diasAlugados) {
                    long diasAtraso = diasPassados - diasAlugados;
                    multa = diasAtraso * (precoDiario * 0.10);
                    mostraratrasoLabel.setText("R$ " + String.format("%.2f", multa) + " (" + diasAtraso + " dias de atraso)");
                } else {
                    mostraratrasoLabel.setText("Sem atraso");
                }

                double precoTotal = (precoDiario * diasAlugados) + multa;
                mostrartotalLabel.setText("R$ " + String.format("%.2f", precoTotal));

            } else {
                carregarDetalhesLocacao();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar detalhes da locação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public ReceberSubTela(Frame pai, boolean modal) {
        super(pai, modal);
        preencherCombobox();
        carregarDetalhesLocacao();

        clientesCombobox.addActionListener(e -> carregarDetalhesLocacao());

        receberButton.addActionListener(e -> {
            try {
                GerenciadorBancoDados.finalizarLocacao(idLocacaoAtual);
                GerenciadorBancoDados.atualizarQuantidadeDisponivelJogo(idJogoAtual, 1);

                if (!GerenciadorBancoDados.verificarLocacoesPendentes(idClienteAtual)) {
                    GerenciadorBancoDados.atualizarStatusCliente(idClienteAtual, "Ativo");
                }

                JOptionPane.showMessageDialog(this, "Jogo recebido com sucesso!");


                dispose();


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar o recebimento: " + ex.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        setTitle("Receber Jogo");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(pai);
        this.add(recebersubtelaPanel);
    }
}