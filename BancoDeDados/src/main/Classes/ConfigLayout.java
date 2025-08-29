package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ConfigLayout {

    private String filtro;

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }


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
                if (campo.getText().equals(exemplo) || campo.getText().equals(exemplo + "-> Campo Obrigatório!!")) {
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
                    campo.setHorizontalAlignment(JTextField.CENTER);
                }
            }
        };
        campo.addFocusListener(focusListener);
    }
}
