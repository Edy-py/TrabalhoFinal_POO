package InterfaceGrafica.LocarJogos;

import BancodeDados.ConexaoUI;
import Classes.ConfigLayout;
import Classes.ServicoCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class LocarJogos {
    //declarando os atributos da tela
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

    private String tabela = "clientes";
    private String sqlCpf = "SELECT COUNT(*) FROM "+tabela+" WHERE cpf = ?";


    public Vector<String> mudarNomeDasColunas(){
        Vector<String> nomesAmigaveis = new Vector<>();
        nomesAmigaveis.add("Id_jogo");
        nomesAmigaveis.add("Nome");
        nomesAmigaveis.add("Publisher");
        nomesAmigaveis.add("Console");
        nomesAmigaveis.add("Classificacao");
        nomesAmigaveis.add("Ano_Lancamento");
        nomesAmigaveis.add("Em_Estoque");
        nomesAmigaveis.add("Disponivel");
        nomesAmigaveis.add("Preco");

        return nomesAmigaveis;
    }


    //Metodo para preencher a combobox
    private void preencherComboBox(JComboBox<String> comboBox, String coluna, String tabela) {
        try (Connection con = ConexaoUI.conectar()) {

            // busca os dados da tabela jogos na coluna consoles e os coloca na combobox
            ArrayList<String> items = ConexaoUI.getInfoColuna(coluna, tabela);


            // adiciona o array ao cobobox
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(items.toArray(new String[0]));
            comboBox.setModel(modelo);

        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados","Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }

    }


    public LocarJogos() {
        super();

        String exCpf = "Digite o CPF do cliente: 999.999.999-99";

        ConfigLayout.infoBusca(cpfTextField);

        preencherComboBox(jogoCombobox,"nome","jogos");

        confirmarLocacaoButton.addActionListener(e -> {

            boolean cpfValido = true;

            try (Connection con = ConexaoUI.conectar()) {

                // Não permite que um campo fique vazio
                if (!ConfigLayout.verificarCampoObrigatorio(cpfTextField, exCpf)) {
                    cpfLabel.setText("CPF é obrigatório!");
                    cpfLabel.setForeground(Color.RED);
                    cpfTextField.setText("");
                    cpfValido = false;

                    // Verifica se o cpf digitado é válido
                } else if (!ServicoCliente.ehCPFValido(cpfTextField.getText())) {
                    cpfLabel.setText("CPF inválido!");
                    cpfLabel.setForeground(Color.RED);
                    cpfTextField.setText("");
                    cpfValido = false;

                    // Verifica se o cpf não já foi cadastrado no banco de dados.
                }else if(!ServicoCliente.ehRepetido(con, cpfTextField.getText(), sqlCpf)){ // se não é repetido o cpf não foi cadastrado no banco de dados.
                    cpfLabel.setText("Cpf não cadastrado!");
                    cpfLabel.setForeground(Color.RED);
                    cpfTextField.setText("");
                    cpfValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                }else {
                    cpfLabel.setText("Digite o CPF:");
                    cpfLabel.setForeground(Color.BLACK);
                }

                if(cpfValido){



                    String nome = nomeparaaparecerLabel.getText();

                    int resposta = JOptionPane.showConfirmDialog(null, "Nome: " + nome, "Confirmar Cliente", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        nomeparaaparecerLabel.setText(nome);

                    }else {
                        JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário","Aviso", JOptionPane.WARNING_MESSAGE);
                    }


                }

            }catch (
                    SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de dados", "Erro de conexão", JOptionPane.ERROR_MESSAGE);
            }

        });

        ConfigLayout.buscarPreco(textField1,precototalLabel,jogoCombobox,tabela);

        ConfigLayout.buscaNome(cpfTextField,nomeparaaparecerLabel,cpfLabel,tabela);

    }


    //getter da classe para mandar o panel principal para a tela principal
    public JPanel getControlPanel() {
        return ControlPanel;
    }


}
