//Edílson Alves da Silva (GitHub: edy-py)
//Guilherme Henrique Garcia Silva (GitHub: Guigas-hgs)
//Élio Mário Soares Júnior (GitHub: BrawlerGits)

package Classes;

import BancodeDados.CarregamentoDeDados;
import BancodeDados.ConexaoUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class ConfigLayout {

    public static void exemploDeTexto(JTextField campo, String exemplo) {
        campo.setForeground(Color.gray);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setText(exemplo);

        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(exemplo)) {
                    campo.setText("");
                    campo.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setForeground(Color.gray);
                    campo.setText(exemplo);
                }
            }
        };
        campo.addFocusListener(focusListener);
    }

    public static boolean verificarCampoObrigatorio(JTextField campo, String exemplo) {
        return !campo.getText().isEmpty() && !campo.getText().equals(exemplo);
    }

    public static void addEnterFuncao(JButton botao, JTextField campo) {
        campo.addActionListener(e -> botao.doClick());
    }

    public static void infoBusca(JTextField campo) {
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals("Buscar por cpf") || campo.getText().equals("Cpf não encontrado!") || campo.getText().equals("Cpf inválido!")) {
                    campo.setText("");
                    campo.setForeground(Color.black);
                    campo.setHorizontalAlignment(JTextField.LEFT);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setForeground(Color.gray);
                    campo.setText("Buscar por cpf");
                    campo.setHorizontalAlignment(JTextField.LEFT);
                }
            }
        };
        campo.addFocusListener(focusListener);
    }

    public static void configurarBotaoAtualizar(JButton botao, JComboBox<String> filtroCombobox, JTable tabelaPanel,
                                                String sql, String sqlSemFiltro, Vector<String> nomesColunas) {
        botao.addActionListener(e -> {
            String filtro = filtroCombobox.getSelectedItem().toString();
            if ("Todos".equals(filtro)) {
                CarregamentoDeDados thread = new CarregamentoDeDados(sqlSemFiltro, null, nomesColunas, tabelaPanel);
                thread.execute();
            } else {
                CarregamentoDeDados thread = new CarregamentoDeDados(sql, filtro, nomesColunas, tabelaPanel);
                thread.execute();
            }
        });
    }

    public static void buscaNome(JTextField campo, JLabel label,JLabel labelNome, String tabela) {
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setText("");
                campo.setForeground(Color.black);
                labelNome.setForeground(Color.BLACK);
                labelNome.setText("CPF do Cliente:");
                label.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                try (Connection conn = ConexaoUI.conectar()){
                    String nome = ConexaoUI.buscaPorCpf(conn, ServicoCliente.formatarCpf(campo.getText()), "nome", tabela);
                    if (nome != null) {
                        String textoFormatado = "<html>Cliente: <font color='blue'>" + nome + "</font></html>";
                        label.setText(textoFormatado);
                        label.setFont(new Font("Arial", Font.BOLD, 16) );
                    } else {
                        if(!campo.getText().isEmpty()){
                            label.setText("<html><font color='red'>Cliente não encontrado</font></html>");
                        } else {
                            label.setText("");
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar nome do cliente!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        campo.addFocusListener(focusListener);
    }

    // --- MÉTODO CORRIGIDO ---
    public static void buscarPreco(JTextField qtdDias, JLabel labelPreco, JComboBox<String> item, String tabela) {
        Runnable calcularPreco = () -> {
            String diasStr = qtdDias.getText();
            if (item.getSelectedItem() == null || diasStr.isEmpty() || !ServicoJogo.verificarQtd(diasStr)) {
                labelPreco.setText("R$ 0,00");
                return;
            }

            try (Connection conn = ConexaoUI.conectar()) {
                String jogo = item.getSelectedItem().toString();
                String precoString = ConexaoUI.buscaPrecoPorNome(conn, jogo, "preco", tabela);

                if (precoString != null) {
                    double precoDiario = ServicoJogo.stringParaDouble(precoString);
                    int dias = Integer.parseInt(diasStr);
                    double precoTotal = precoDiario * dias;
                    labelPreco.setText(String.format("Preço total: R$ %.2f", precoTotal));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar preço do jogo!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            }
        };

        // Calcula o preço quando o jogo muda
        item.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                calcularPreco.run();
            }
        });

        // Calcula o preço enquanto o usuário digita os dias
        qtdDias.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calcularPreco.run();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calcularPreco.run();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calcularPreco.run();
            }
        });
    }
}