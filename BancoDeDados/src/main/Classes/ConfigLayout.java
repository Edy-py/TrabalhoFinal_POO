package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ConfigLayout {


    public static void exemploDeTexto(JTextField campo, String exemplo){

        /*
        * Essa função é responsável
        *
        * */

        // Define configuração inicial do JTextField
        campo.setForeground(Color.gray);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));

        campo.setText(exemplo);

        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(campo.getText().equals(exemplo) || campo.getText().equals(exemplo + "-> Campo Obrigatório!!")){
                    campo.setText("");
                    campo.setForeground(Color.black);
                    campo.setHorizontalAlignment(JTextField.LEFT);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(campo.getText().isEmpty()){
                    if (verificarCampoObrigatorio(campo, exemplo)){
                        campo.setText(exemplo);
                    }else {
                        campo.setText(exemplo + "-> Campo Obrigatório!!");
                    }
                    campo.setHorizontalAlignment(JTextField.CENTER);
                }

            }
        };
        campo.addFocusListener(focusListener);
    }

    public static boolean validarCampoObrigatorio(JTextField campo, String exemplo){
        if(campo.getText().isEmpty() || campo.getText().equals(exemplo) || campo.getText().equals(exemplo + "-> Campo Obrigatório!!")){
            return false;
        }
        return true;
    }


    public static boolean verificarCampoObrigatorio(JTextField campo, String exemplo){
        boolean campoValido = validarCampoObrigatorio(campo, exemplo);
        if(!campoValido){
            campo.setForeground(Color.red);

            return false;
        }else{
            campo.setForeground(Color.gray);
            return true;
        }
    }

    public static void addEnterFuncao(JButton botao, JTextField campo){
        campo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botao.doClick();
            }
        });
    }


    public static void infoBusca(JTextField campo){

        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(campo.getText().equals("Buscar por cpf") || campo.getText().equals("Cpf não encontrado!") || campo.getText().equals("Cpf inválido!")){
                    campo.setText("");
                    campo.setForeground(Color.black);
                    campo.setHorizontalAlignment(JTextField.LEFT);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(campo.getText().isEmpty()){
                    campo.setForeground(Color.BLACK);
                    campo.setText("Buscar por cpf");
                    campo.setHorizontalAlignment(JTextField.CENTER);
                }
            }
        };
        campo.addFocusListener(focusListener);
    }




}
