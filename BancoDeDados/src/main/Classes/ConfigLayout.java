package Classes;

import BancodeDados.CarregamentoDeDados;
import BancodeDados.ConexaoBanco;
import BancodeDados.ConexaoUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class ConfigLayout {




    public static void exemploDeTexto(JTextField campo, String exemplo) {

        /*
         * Essa função é responsável por configurar os JTextField
         * com textos de exemplo para mostrar para o usuário como deve ser o preenchimento
         * */

        // Define configuração inicial do JTextField
        campo.setForeground(Color.gray);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));

        campo.setText(exemplo);

        // FocusListener é uma interface que pode ser intanciada diretamente em um método
        FocusListener focusListener = new FocusListener() {

            @Override
            // quando o usuário clica no JTextField
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(exemplo) ||
                        campo.getText().equals(exemplo + "-> Campo Obrigatório!!") ||
                        campo.getText().equals("ID inválido!") ||
                        campo.getText().equals("ID é obrigatório!")
                ) {
                    campo.setText("");
                    campo.setForeground(Color.black);
                    campo.setHorizontalAlignment(JTextField.LEFT);
                }
            }

            @Override
            // Quando o usuário passa muda de campo
            public void focusLost(FocusEvent e) {

                // Se o Jtext estiver vazio
                if (campo.getText().isEmpty()) {

                    // Verifica se o Jtext.getText.equals(exemplo)
                    if (verificarCampoObrigatorio(campo, exemplo)) {
                        campo.setText(exemplo);

                        // indica o capo como obrigatório
                    } else {
                        campo.setText(exemplo + "-> Campo Obrigatório!!");
                    }
                    campo.setHorizontalAlignment(JTextField.CENTER);
                }

            }
        };
        // add a mágica ao campo
        campo.addFocusListener(focusListener);
    }


    // método auxiliar
    public static boolean validarCampoObrigatorio(JTextField campo, String exemplo) {

        if (campo.getText().isEmpty() || campo.getText().equals(exemplo) || campo.getText().equals(exemplo + "-> Campo Obrigatório!!")) {
            return false;
        }
        return true;
    }


    // Garante que o campo seja preenchido
    public static boolean verificarCampoObrigatorio(JTextField campo, String exemplo) {
        boolean campoValido = validarCampoObrigatorio(campo, exemplo);
        if (!campoValido) {
            campo.setForeground(Color.red);

            return false;
        } else {
            campo.setForeground(Color.gray);
            return true;
        }
    }


    // Fução para setar a ação de clicar "enter" e interagir com a UI
    public static void addEnterFuncao(JButton botao, JTextField campo) {
        campo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botao.doClick();
                campo.setText("");
                campo.setForeground(Color.black);
                campo.setHorizontalAlignment(JTextField.LEFT);
            }
        });
    }


    // Segue a mesma lógica de ExemploDeTexto, mas para o jtext de busca
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
                    campo.setForeground(Color.BLACK);
                    campo.setText("Buscar por cpf");
                    campo.setHorizontalAlignment(JTextField.LEFT);
                }
            }
        };
        campo.addFocusListener(focusListener);
    }

    // Atualiza a tabela na UI
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

                    String nome = ConexaoUI.buscaPorCpf(conn,campo.getText(), "nome",tabela);

                    if (nome != null) {

                        String textoFormatado = "<html>Esse cpf pertence ao cliente <font color='blue'>" + nome + "</font></html>";
                        label.setText(textoFormatado);
                        label.setFont(new Font("Arial", Font.BOLD, 22) );

                    }else{

                        label.setText("");
                        label.setForeground(Color.BLACK);

                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar nome do cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println(ex.getMessage() );
                }
            }
        };
        campo.addFocusListener(focusListener);
    }

    public static void buscarPreco(JTextField qtdDias ,JLabel labelPreco,JComboBox<String> item, String tabela) {



            FocusListener focusListener = new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                    labelPreco.setText("");

                }

                public void focusLost(FocusEvent e) {

                    try (Connection conn = ConexaoBanco.conectar()) {
                    item.addItemListener(e1 -> {

                        if(e1.getStateChange() == ItemEvent.SELECTED){
                            String jogo = item.getSelectedItem().toString();

                            try {
                                String precoString = ConexaoUI.buscaPrecoPorNome(conn,jogo, "preco",tabela);
                                double preco = ServicoJogo.stringParaDouble(precoString);
                                int dia = Integer.parseInt(qtdDias.getText());
                                labelPreco.setText("Preço total: R$" + preco * dia);

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }


                        }

                    });
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao buscar preço do jogo!", "Erro", JOptionPane.ERROR_MESSAGE);

                    }
                }
            };
            item.addFocusListener(focusListener);
    }
}
