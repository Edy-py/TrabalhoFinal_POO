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
    private JButton editarButton;
    private JButton atualizarButton;

    // Variáveis globais
    private String colunas = "id,nome,cpf,email,telefone,endereco,status";
    private String tabela = "clientes";
    private String sql = "SELECT " + colunas + " FROM " + tabela + " WHERE status LIKE ? ORDER BY nome ASC";
    private String sqlConsulta = "SELECT " + colunas + " FROM " + tabela + " WHERE cpf LIKE ? ORDER BY nome ASC";
    private String sqlSemFiltro = "SELECT " + colunas + " FROM " + tabela + " ORDER BY nome ASC";
    private String sqlCpf = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";

    // Função para alterar o nome das colunas da tabela
    public Vector<String> mudarNomeDasColunas(){
        Vector<String> nomesAmigaveis = new Vector<>();
        nomesAmigaveis.add("ID_Cliente"); // id
        nomesAmigaveis.add("Nome");// nome
        nomesAmigaveis.add("CPF");// cpf
        nomesAmigaveis.add("E-mail");// email
        nomesAmigaveis.add("Telefone");// telefone
        nomesAmigaveis.add("Endereço");// endereco
        nomesAmigaveis.add("Status");// status

        return nomesAmigaveis;
    }


    //Metodo para preencher a combobox
    private void preencherComboBox() {
        filtroCombobox.addItem("Pemdente");
        filtroCombobox.addItem("Ativo");
        filtroCombobox.addItem("Inativo");
        filtroCombobox.addItem("Todos");
    }

    // Construtor da janela principal do cliente
    public TelaCliente() {

        // Thread para carregar dados do banco de dados na tabela
        CarregamentoDeDados threadSemfiltro = new CarregamentoDeDados(sql, "Devendo", mudarNomeDasColunas(), tabelaPanel);
        threadSemfiltro.execute();

        // Função lambda que faz o cobobox funcionar
        filtroCombobox.addItemListener(e -> {

            // Captura a escolha do usuário no cobobox
            if(e.getStateChange() == ItemEvent.SELECTED){

                // ToString para garantir que seja uma string
                String filtro = filtroCombobox.getSelectedItem().toString();

                // Todos os clientes
                if ("Todos".equals(filtro)) {


                    CarregamentoDeDados thread = new CarregamentoDeDados(sqlSemFiltro, null, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();



                }

                // Mostra de acordo com o filtro selecionado
                else {

                    // Uma thread é responsável por fazer a consulta no banco de dados e carregar os dados na tabela
                    CarregamentoDeDados thread = new CarregamentoDeDados(sql, filtro, mudarNomeDasColunas(), tabelaPanel);
                    thread.execute();


                }


            }
        }); // fim da lambda





        //fazendo o botao abrir uma nova janela
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastroClientes telaCadastroClientes = new TelaCadastroClientes();
                telaCadastroClientes.setVisible(true);
            }
        });

        //fazendo o botao abrir uma nova janela
        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditarCliente editarCliente = new EditarCliente();
                editarCliente.setVisible(true);
            }
        });

        // função lambda que faz a busca de clientes pelo cpf
        buscarButton.addActionListener(e -> {
            if ( !buscaTextField.getText().isEmpty() ) {

                // Verifica se o cpf é válido
                if (!ServicoCliente.ehCPFValido(buscaTextField.getText())) {
                    buscaTextField.setText("Cpf inválido!");
                    buscaTextField.setForeground(Color.RED);
                    scrollerPanel.requestFocusInWindow();


                    } else {

                    // try para capturar a exceção lançada por ServicoCliente.ehCpfRepetido()
                    try {

                        // Verifica se o cpf está no banco de dados
                        if (!ServicoCliente.ehRepetido(ConexaoUI.conectar(), buscaTextField.getText(),sqlCpf)) {
                                buscaTextField.setText("Cpf não encontrado!");
                                buscaTextField.setForeground(Color.RED);
                                scrollerPanel.requestFocusInWindow();

                        // Se chegou aqui o cpf é válido e está no banco de dados
                        }else {

                            // define cor do texto
                            buscaTextField.setForeground(Color.BLACK);

                            // Thread para carregar dados do banco de dados na tabela
                            CarregamentoDeDados threadConsulta = new CarregamentoDeDados(sqlConsulta, buscaTextField.getText(), mudarNomeDasColunas(), tabelaPanel);
                            threadConsulta.execute();

                            // Exibe uma mensagem de sucesso na tela
                            JOptionPane.showMessageDialog(null, "Consulta realizada com sucesso! Cpf: "+ buscaTextField.getText()+ " encontrado" );

                            // Limpa o campo de busca
                            buscaTextField.setText("");

                            // Campo de busca perde o foco para a função ConfigLayout.infoBusca() funcione e coloque um valor padrão
                            scrollerPanel.requestFocusInWindow();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao realizar a consulta no banco de dados!", "Erro de Conexão", JOptionPane.ERROR_MESSAGE );

                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Digite um CPF para realizar a consulta!" );
            }
        });

        //chamando o metodo que preenche a combobox
        preencherComboBox();

        // botão atualizar
        ConfigLayout.configurarBotaoAtualizar(atualizarButton,filtroCombobox,tabelaPanel,sql,sqlSemFiltro,mudarNomeDasColunas());

        // Explicação na classe "ConfigLayout"
        ConfigLayout.infoBusca(buscaTextField);

        // Explicação na classe "ConfigLayout"
        ConfigLayout.addEnterFuncao(buscarButton, buscaTextField);
    }

    //getter da classe para retornar o painel principal
    public JPanel getMainCliente() {return MainCliente;}
}
