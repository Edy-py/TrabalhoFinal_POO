package InterfaceGrafica.CadastrarJogos;

// import classes
import BancodeDados.GerenciadorBancoDados;
import Classes.ConfigLayout;
import Classes.ServicoJogo;

// imports libs
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class TelaCadastro extends JDialog{

    //declarando os componentes do panel
    private JPanel cadastroPanel;
    private JLabel nomeLabel;
    private JLabel publicacaoLabel;
    private JLabel consoleLabel;
    private JLabel classificacaoLabel;
    private JLabel anoLabel;
    private JLabel quantidadeLabel;
    private JLabel precoLabel;
    private JTextField nomeJogo;
    private JTextField publisher;
    private JComboBox<String> classificacaoCombobox;
    private JTextField anoLancamento;
    private JTextField quantidade;
    private JTextField preco;
    private JTextField console;
    private JButton cadastrarButton;

    private String tabela = "jogos";

    private String exNome = "Digite o nome do jogo: ";
    private String exConsole = "Digite o console do jogo: ";
    private String exAno = "Digite o ano de lancamento do jogo: ";
    private String exQuantidade = "Digite a quantidade de jogos disponiveis: ";
    private String exPreco = "Digite o preco do jogo (R$): ";
    private String exPublicacao = "Digite o publisher do jogo: ";



    //Metodo para preencher a combobox
    private void preencherComboBox() {
        classificacaoCombobox.addItem("L");
        classificacaoCombobox.addItem("10+");
        classificacaoCombobox.addItem("12+");
        classificacaoCombobox.addItem("14+");
        classificacaoCombobox.addItem("16+");
        classificacaoCombobox.addItem("18+");
    }

    //constructor da janela extra de cadastro
    public TelaCadastro() {
        super();

        // Explicação na classe de configlayout
        ConfigLayout.exemploDeTexto(nomeJogo, exNome);
        ConfigLayout.exemploDeTexto(console, exConsole);
        ConfigLayout.exemploDeTexto(publisher, exPublicacao);
        ConfigLayout.exemploDeTexto(anoLancamento, exAno);
        ConfigLayout.exemploDeTexto(quantidade, exQuantidade);
        ConfigLayout.exemploDeTexto(preco, exPreco);

        // Faz o botão de cadastro funcionar
        cadastrarButton.addActionListener(e -> {

            // flags
            boolean nomeValido = true;
            boolean consoleValido = true;
            boolean anoValido = true;
            boolean quantidadeValida = true;
            boolean precoValido = true;



            // Verifica se o campo de nome do jogo está vazio
            if (!ConfigLayout.verificarCampoObrigatorio(nomeJogo,exNome)){
                nomeLabel.setText("Nome obrigatorio!");
                nomeLabel.setForeground(Color.RED);
                nomeValido = false;
            }

            // Caso contrario seta valores e cor padrão
            else {
                nomeLabel.setText("Nome do Jogo: ");
                nomeLabel.setForeground(Color.BLACK);
            }

            // Verifica se o campo de publisher do jogo está vazio
            if (!ConfigLayout.verificarCampoObrigatorio(publisher,exPublicacao)){
                publicacaoLabel.setText("Publisher obrigatorio!");
                publicacaoLabel.setForeground(Color.RED);
                consoleValido = false;
            }

            // Caso contrario seta valores e cor padrão
            else {
                publicacaoLabel.setText("Publisher do Jogo: ");
                publicacaoLabel.setForeground(Color.BLACK);
            }

            // Verifica se o campo de console do jogo está vazio
            if (!ConfigLayout.verificarCampoObrigatorio(console,exConsole)){
                consoleLabel.setText("Console obrigatorio!");
                consoleLabel.setForeground(Color.RED);
                consoleValido = false;
            }

            // Caso contrario seta valores e cor padrão
            else {
                consoleLabel.setText("Console do Jogo: ");
                consoleLabel.setForeground(Color.BLACK);
            }

            // Verifica se o campo de ano de lançamento do jogo está vazio
            if (!ConfigLayout.verificarCampoObrigatorio(anoLancamento,exAno)){
                anoLabel.setText("Ano obrigatorio!");
                anoLabel.setForeground(Color.RED);
                anoValido = false;

                // verifica se é numérico
            }else if (!ServicoJogo.verificarAno(anoLancamento.getText())){
                anoLabel.setText("Ano inválido!");
                anoLabel.setForeground(Color.RED);
                anoValido = false;

                // Caso contrario seta valores e cor padrão
            }else {
                anoLabel.setText("Ano do Jogo: ");
                anoLabel.setForeground(Color.BLACK);
            }

            // Verifica se o campo de nome do jogo está vazio
            if (!ConfigLayout.verificarCampoObrigatorio(quantidade,exQuantidade)){
                quantidadeLabel.setText("Quantidade obrigatoria!");
                quantidadeLabel.setForeground(Color.RED);
                quantidadeValida = false;

                // Verifica se é numérico
            }else if (!ServicoJogo.verificarQtd(quantidade.getText())){
                quantidadeLabel.setText("Somente números!");
                quantidadeLabel.setForeground(Color.RED);
                quantidadeValida = false;
            }

            // Caso contrario seta valores e cor padrão
            else {
                quantidadeLabel.setText("Quantidade do Jogo: ");
                quantidadeLabel.setForeground(Color.BLACK);
            }

            // Verifica se o campo de nome do jogo está vazio
            if(!ConfigLayout.verificarCampoObrigatorio(preco,exPreco)){
                precoLabel.setText("Peço é obigatório!");
                precoLabel.setForeground(Color.RED);
                precoValido = false;

                // Verifica se é numérico
            } else if (!ServicoJogo.verificarPreco(preco.getText())){
                precoLabel.setText("Somente números!");
                precoLabel.setForeground(Color.RED);
                precoValido = false;

                // Caso contrario seta valores e cor padrão
            }else {
                precoLabel.setText("Preço Diário: R$");
                precoLabel.setForeground(Color.BLACK);
            }



            // Se todos os dados forem válidos, insere os dados no banco de dados.
            if (nomeValido && consoleValido && anoValido&& quantidadeValida   && precoValido) {

                String nome = nomeJogo.getText();
                String publisherJogo = publisher.getText();
                String consoleJogo = console.getText().toUpperCase();
                int ano = ServicoJogo.stringParaInt(anoLancamento.getText());
                int estoque = ServicoJogo.stringParaInt(quantidade.getText());
                double precoDiario = ServicoJogo.stringParaDouble(preco.getText());
                String classificacao = ServicoJogo.getItemSelecionado(classificacaoCombobox);


                // Insere os dados no banco de dados.
                try {
                    GerenciadorBancoDados.inserirDadosJogos(nome, publisherJogo, consoleJogo,classificacao, ano, estoque,estoque, precoDiario, tabela);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na conexão com o banco de dados", "Erro de Conexão!", JOptionPane.ERROR_MESSAGE);
                }

                // Exibe uma mensagem de sucesso na tela.
                JOptionPane.showMessageDialog(null, "" + nomeJogo.getText() + " cadastrado com sucesso no banco de dados!", "Cliente Cadastrado", JOptionPane.INFORMATION_MESSAGE);


                // Fecha a janela de cadastro após cadastrado com sucesso.
                SwingUtilities.getWindowAncestor(cadastroPanel).dispose();

                // Mensagem para quantidade inválido
            } else if (nomeValido && consoleValido && anoValido && !quantidadeValida  ) {
                JOptionPane.showMessageDialog(null, "Valor não numérico!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                // Mensagem para ano inválido
            } else if (nomeValido && consoleValido && !anoValido && quantidadeValida   ) {
                JOptionPane.showMessageDialog(null, "Ano inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                // Mensagem para console inválido
            } else if (nomeValido && !consoleValido && anoValido&& quantidadeValida  ) {
                JOptionPane.showMessageDialog(null, "Console inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);

                // Mensagem para nome inválido
            } else if (!nomeValido && consoleValido && anoValido&& quantidadeValida  ) {
                JOptionPane.showMessageDialog(null, "Nome inválido", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }


            });

        // Configura para que possamos pesquisar apertando a tecla "enter"
        ConfigLayout.addEnterFuncao(cadastrarButton, preco);

        // Titulo
        setTitle("Cadastro de Jogos");

        // Fechar no X
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Definir tamanho
        setSize(600, 650);


        setLocationRelativeTo(null);

        //chamando o metodo que preenche a combobox
        preencherComboBox();

        //adicionando o conteudo da panel cadastroPanel a nova janela que será criada
        this.add(cadastroPanel);
    }
}
