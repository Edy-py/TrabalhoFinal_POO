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
    private JPanel excluirJogosPanel;
    private JTextField idJogoTextField;
    private JLabel IDJogoLabel;
    private JLabel nomeDoJogoLabel;
    private JButton confirmarExclusaoButton;
    private String tabela = "jogos";

    public ExcluirJogos() {
        super();
        String exId = "Digite o ID do jogo: ";
        ConfigLayout.exemploDeTexto(idJogoTextField, exId);

        confirmarExclusaoButton.addActionListener(e -> {
            boolean idValido = true;
            try (Connection con = ConexaoUI.conectar()) {
                if (!ConfigLayout.verificarCampoObrigatorio(idJogoTextField, exId)) {
                    IDJogoLabel.setText("ID é obrigatório!");
                    IDJogoLabel.setForeground(Color.RED);
                    idValido = false;
                } else if (!ServicoCliente.ehIdValido(idJogoTextField.getText())) {
                    IDJogoLabel.setText("ID inválido!");
                    IDJogoLabel.setForeground(Color.RED);
                    idValido = false;
                } else {
                    IDJogoLabel.setText("Digite o ID:");
                    IDJogoLabel.setForeground(Color.BLACK);
                }

                if(idValido){
                    int id = Integer.parseInt(idJogoTextField.getText());
                    String nomeJogo = ConexaoUI.buscaPorId(con, id, "nome", tabela);

                    if(nomeJogo != null){
                        int resposta = JOptionPane.showConfirmDialog(null, "Confirmar exclusão do jogo '" + nomeJogo + "'?", "Confirmar Ação", JOptionPane.YES_NO_OPTION);

                        if(resposta == JOptionPane.YES_OPTION){
                            GerenciadorBancoDados.deletarDados(id,tabela);
                            JOptionPane.showMessageDialog(null, "Jogo '" + nomeJogo + "' excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            SwingUtilities.getWindowAncestor(excluirJogosPanel).dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,"ID do jogo não encontrado no banco de dados.", "Erro de Operação", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao Banco de dados.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
            }
        });

        ConfigLayout.addEnterFuncao(confirmarExclusaoButton,idJogoTextField);
        setTitle("Excluir Jogo");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        this.add(excluirJogosPanel);
    }
}