package InterfaceGrafica.LocarJogos;

import BancodeDados.ConexaoUI;
import BancodeDados.GerenciadorBancoDados;
import Classes.ConfigLayout;
import Classes.ServicoCliente;
import Classes.ServicoJogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocarJogos {
    private JPanel ControlPanel;
    private JPanel informacoesPanel;
    private JButton confirmarLocacaoButton;
    private JLabel cpfLabel;
    private JTextField cpfTextField;
    private JLabel nomeparaaparecerLabel;
    private JLabel nomeclienteLabel;
    private JComboBox<String> jogoCombobox;
    private JLabel jogoLabel;
    private JLabel diasLabel;
    private JLabel precototalLabel;
    private JLabel inserirprecoLabel;
    private JTextField textField1;
    private JComboBox<String> filtroCombobox;
    private JLabel filtrarLabel;

    private String tabela = "clientes";
    private String sqlCpf = "SELECT COUNT(*) FROM "+tabela+" WHERE cpf = ?";

    private void preencherComboBox(JComboBox<String> comboBox, String coluna, String tabela) {
        try {
            ArrayList<String> items = ConexaoUI.getInfoColuna(coluna, tabela);
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(items.toArray(new String[0]));
            comboBox.setModel(modelo);
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao popular a combobox: " + ex.getMessage(),"Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LocarJogos() {
        super();

        preencherComboBox(filtroCombobox, "console", "jogos");
        filtroCombobox.insertItemAt("Todos", 0);
        filtroCombobox.setSelectedIndex(0);

        preencherComboBox(jogoCombobox, "nome", "jogos WHERE quantidade_disponivel > 0");

        filtroCombobox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String consoleSelecionado = (String) filtroCombobox.getSelectedItem();
                if ("Todos".equals(consoleSelecionado)) {
                    preencherComboBox(jogoCombobox, "nome", "jogos WHERE quantidade_disponivel > 0");
                } else {
                    preencherComboBox(jogoCombobox, "nome", "jogos WHERE console = '" + consoleSelecionado + "' AND quantidade_disponivel > 0");
                }
            }
        });

        String exCpf = "Digite o CPF do cliente: 999.999.999-99";
        ConfigLayout.infoBusca(cpfTextField);

        confirmarLocacaoButton.addActionListener(e -> {
            boolean cpfValido = true;
            boolean diasValido = true;

            try {
                if (jogoCombobox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Nenhum jogo disponível para locação com o filtro selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String cpfInput = cpfTextField.getText();

                if (!ConfigLayout.verificarCampoObrigatorio(cpfTextField, exCpf)) {
                    cpfLabel.setText("CPF é obrigatório!");
                    cpfLabel.setForeground(Color.RED);
                    cpfTextField.setText("");
                    cpfValido = false;
                } else if (!ServicoCliente.ehCPFValido(cpfInput)) {
                    cpfLabel.setText("CPF inválido!");
                    cpfLabel.setForeground(Color.RED);
                    cpfTextField.setText("");
                    cpfValido = false;
                } else {
                    String cpfFormatado = ServicoCliente.formatarCpf(cpfInput);
                    if(!ServicoCliente.ehRepetido(ConexaoUI.conectar(), cpfFormatado, sqlCpf)){
                        cpfLabel.setText("Cpf não cadastrado!");
                        cpfLabel.setForeground(Color.RED);
                        cpfTextField.setText("");
                        cpfValido = false;
                    } else {
                        cpfLabel.setText("Digite o CPF:");
                        cpfLabel.setForeground(Color.BLACK);
                    }
                }

                if (!ServicoJogo.verificarQtd(textField1.getText())) {
                    diasLabel.setText("Dias inválidos!");
                    diasLabel.setForeground(Color.RED);
                    textField1.setText("");
                    diasValido = false;
                } else {
                    diasLabel.setText("Dias a Locar:");
                    diasLabel.setForeground(Color.BLACK);
                }

                if(cpfValido && diasValido){
                    String nomeJogo = jogoCombobox.getSelectedItem().toString();
                    int idCliente = ConexaoUI.getIdPorCpf(ServicoCliente.formatarCpf(cpfInput));
                    int idJogo = ConexaoUI.getIdPorNome(nomeJogo, "jogos");
                    int dias = Integer.parseInt(textField1.getText());

                    int resposta = JOptionPane.showConfirmDialog(null, "Confirmar locação do jogo " + nomeJogo + " por " + dias + " dias?", "Confirmar Ação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        GerenciadorBancoDados.inserirDadosLocacao(idCliente, idJogo, dias);
                        GerenciadorBancoDados.atualizarQuantidadeDisponivelJogo(idJogo, -1);
                        GerenciadorBancoDados.atualizarStatusCliente(idCliente, "Alugando");
                        JOptionPane.showMessageDialog(null, "Locação realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário.","Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de dados.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            }
        });


        ConfigLayout.buscarPreco(textField1, inserirprecoLabel, jogoCombobox, "jogos");
        ConfigLayout.buscaNome(cpfTextField, nomeparaaparecerLabel, cpfLabel, tabela);
        ConfigLayout.addEnterFuncao(confirmarLocacaoButton,textField1);
    }

    public JPanel getControlPanel() {
        return ControlPanel;
    }
}