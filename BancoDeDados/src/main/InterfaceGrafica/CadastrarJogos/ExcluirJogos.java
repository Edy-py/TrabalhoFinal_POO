package InterfaceGrafica.CadastrarJogos;

import BancodeDados.ConexaoUI;
import BancodeDados.GerenciadorBancoDados;
import Classes.ConfigLayout;
import Classes.ServicoCliente;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ExcluirJogos extends JDialog {
    //declarando os atributos da interface
    private JPanel excluirJogosPanel;
    private JTextField idJogoTextField;
    private JLabel IDJogoLabel;
    private JLabel nomedojogoselecionadoLabel;
    private JLabel nomeDoJogoLabel;
    private JButton confirmarExclusaoButton;

    private String tabela = "jogos";
    private String sqlID = "SELECT COUNT(*) FROM clientes WHERE id = ?";

    public  ExcluirJogos() {
        super();

        String exId = "Digite o ID do cliente: ";

        ConfigLayout.exemploDeTexto(idJogoTextField, exId);

        confirmarExclusaoButton.addActionListener(e -> {

            boolean idValido = true;

            try (Connection con = ConexaoUI.conectar()) {

                if (!ConfigLayout.verificarCampoObrigatorio(idJogoTextField, exId)) {
                    IDJogoLabel.setText("ID é obrigatório!");
                    IDJogoLabel.setForeground(Color.RED);
                    idValido = false;

                    // Verifica se o cpf digitado é válido
                } else if (!ServicoCliente.ehIdValido(idJogoTextField.getText())) {
                    IDJogoLabel.setText("ID inválido!");
                    IDJogoLabel.setForeground(Color.RED);
                    idValido = false;

                    // se passar pelas verificação volta para os valores iniciais para o campo.
                }else {
                    IDJogoLabel.setText("Digite o ID:");
                    IDJogoLabel.setForeground(Color.BLACK);
                }

                if(idValido){

                    int id = Integer.parseInt(idJogoTextField.getText());

                    if(ConexaoUI.buscaPorId(con, id, "nome", tabela) != null){

                        int resposta = JOptionPane.showConfirmDialog(null, "Confirmar exclusão do jogo " + ConexaoUI.buscaPorId(con, id, "nome",tabela) + "?", "Excluir Jogo", JOptionPane.YES_NO_OPTION);
                        nomedojogoselecionadoLabel.setText("Jogo " + ConexaoUI.buscaPorId(con, id, "nome",tabela));

                        if(resposta == JOptionPane.YES_OPTION){

                            JOptionPane.showMessageDialog(null, "Jogo " + ConexaoUI.buscaPorId(con,id,"nome",tabela) + " excluido do banco de dados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            GerenciadorBancoDados.deletarDados(id,tabela);

                        }else {
                            JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário");
                        }


                        // Fecha a janela excluirJogosPanel
                        SwingUtilities.getWindowAncestor(excluirJogosPanel).dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,"Id do jogo não encontrado no banco de dados", "Id não encontrado", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de dados", "Erro de conexão", JOptionPane.ERROR_MESSAGE);
            }

        });

        // Define titulo da janela
        setTitle("Exlcuir Jogo");

        // Fecha a janela quando apertamos no x
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Configura o tamanho da janela
        setSize(600, 500);

        // Fazendo a tela aparecer no centro da tela
        setLocationRelativeTo(null);

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(excluirJogosPanel);
    }
}
