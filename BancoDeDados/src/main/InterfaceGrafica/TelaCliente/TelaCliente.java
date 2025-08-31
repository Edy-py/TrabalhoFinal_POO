package InterfaceGrafica.TelaCliente;

import BancodeDados.CarregamentoDeDados;
import BancodeDados.ConexaoUI;
import Classes.ConfigLayout;
import Classes.ServicoCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.Vector;

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
    private JButton editarButton;
    private JButton atualizarButton;

    private String colunas = "id,nome,cpf,email,telefone,endereco,status";
    private String tabela = "clientes";
    private String sql = "SELECT " + colunas + " FROM " + tabela + " WHERE status LIKE ? ORDER BY nome ASC";
    private String sqlConsulta = "SELECT " + colunas + " FROM " + tabela + " WHERE cpf LIKE ? ORDER BY nome ASC";
    private String sqlSemFiltro = "SELECT " + colunas + " FROM " + tabela + " ORDER BY nome ASC";
    private String sqlCpf = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";

    public Vector<String> mudarNomeDasColunas(){
        Vector<String> nomesAmigaveis = new Vector<>();
        nomesAmigaveis.add("ID_Cliente");
        nomesAmigaveis.add("Nome");
        nomesAmigaveis.add("CPF");
        nomesAmigaveis.add("E-mail");
        nomesAmigaveis.add("Telefone");
        nomesAmigaveis.add("Endereço");
        nomesAmigaveis.add("Status");
        return nomesAmigaveis;
    }

    private void preencherComboBox() {
        filtroCombobox.removeAllItems();
        filtroCombobox.addItem("Pendente");
        filtroCombobox.addItem("Ativo");
        filtroCombobox.addItem("Inativo");
        filtroCombobox.addItem("Todos");
    }

    public TelaCliente() {
        CarregamentoDeDados threadSemfiltro = new CarregamentoDeDados(sql, "Devendo", mudarNomeDasColunas(), tabelaPanel);
        threadSemfiltro.execute();

        filtroCombobox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                String filtro = filtroCombobox.getSelectedItem().toString();
                if ("Todos".equals(filtro)) {
                    CarregamentoDeDados thread = new CarregamentoDeDados(sqlSemFiltro, null, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();
                }
                else if ("Pendente".equals(filtro)) {
                    String sqlPendente = "SELECT " + colunas + " FROM " + tabela + " WHERE status = 'Devendo' OR status = 'Alugando' ORDER BY nome ASC";
                    CarregamentoDeDados thread = new CarregamentoDeDados(sqlPendente, null, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();
                }
                else {
                    CarregamentoDeDados thread = new CarregamentoDeDados(sql, filtro, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();
                }
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroClientes telaCadastroClientes = new TelaCadastroClientes();
                telaCadastroClientes.setVisible(true);
                atualizarButton.doClick();
            }
        });

        editarButton.addActionListener(e -> {
            Window parentWindow = SwingUtilities.getWindowAncestor(this.MainCliente);
            EditarCliente editarCliente = new EditarCliente((Frame) parentWindow);
            editarCliente.setVisible(true);
            atualizarButton.doClick();
        });

        buscarButton.addActionListener(e -> {
            if ( !buscaTextField.getText().isEmpty() ) {
                if (!ServicoCliente.ehCPFValido(buscaTextField.getText())) {
                    buscaTextField.setText("Cpf inválido!");
                    buscaTextField.setForeground(Color.RED);
                    scrollerPanel.requestFocusInWindow();
                } else {
                    try {
                        String cpfFormatado = ServicoCliente.formatarCpf(buscaTextField.getText());
                        if (!ServicoCliente.ehRepetido(ConexaoUI.conectar(), cpfFormatado,sqlCpf)) {
                            buscaTextField.setText("Cpf não encontrado!");
                            buscaTextField.setForeground(Color.RED);
                            scrollerPanel.requestFocusInWindow();
                        } else {
                            buscaTextField.setForeground(Color.BLACK);
                            CarregamentoDeDados threadConsulta = new CarregamentoDeDados(sqlConsulta, cpfFormatado, mudarNomeDasColunas(), tabelaPanel);
                            threadConsulta.execute();
                            JOptionPane.showMessageDialog(null, "Consulta realizada com sucesso! Cpf: "+ buscaTextField.getText()+ " encontrado" );
                            buscaTextField.setText("");
                            scrollerPanel.requestFocusInWindow();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao realizar a consulta no banco de dados!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE );
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Digite um CPF para realizar a consulta!" );
            }
        });

        preencherComboBox();
        ConfigLayout.configurarBotaoAtualizar(atualizarButton,filtroCombobox,tabelaPanel,sql,sqlSemFiltro,mudarNomeDasColunas());
        ConfigLayout.infoBusca(buscaTextField);
        ConfigLayout.addEnterFuncao(buscarButton, buscaTextField);
    }

    public JPanel getMainCliente() {
        return MainCliente;
    }
}