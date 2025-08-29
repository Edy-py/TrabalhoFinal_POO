package Classes;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;

public class ServicoJogo {

    public static int stringParaInt(String string){

            return Integer.parseInt(string);

    }



    public static boolean verificarAno(String ano){
       String regex = "^\\d+$";

       if (!ano.matches(regex)){
            return false;
       }

       int anoInt = stringParaInt(ano);

       if(anoInt < 1952 || anoInt > 2021){
           return false;
       }
       return true;


    }

    public static boolean verificarQtd(String qtd){
        String regex = "^\\d+$";
        return qtd.matches(regex);
    }

    public static boolean verificarPreco(String qtd){
        String regex = "^\\d+([.,]\\d{1,2})?$";

        return qtd.matches(regex);
    }

    public static double stringParaDouble(String string){

        try {
            // Remove espaços e caracteres especiais
            string = string.trim().replaceAll("[R$\\s]", "");

            // Substitui vírgula por ponto
            string = string.replace(",", ".");

            // Verifica se o formato é válido usando regex mais flexível
            if (!string.matches("^\\d*\\.?\\d+$")) {
                throw new NumberFormatException("Valor inválido. Use apenas números e vírgula ou ponto como separador decimal.");
            }

            // Converte para double
            return Math.round(Double.parseDouble(string) * 100.0) / 100.0;

        } catch (NumberFormatException e) {
            throw new NumberFormatException("Valor inválido. Use apenas números e vírgula ou ponto como separador decimal.");
        }



    }

    public static String getItemSelecionado(JComboBox comboBox) {

        comboBox.addItemListener(e1 -> {
            if(e1.getStateChange() == ItemEvent.SELECTED){
                if (comboBox.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null, "Selecione um item na lista!");

                }
            }
        });
        return comboBox.getSelectedItem().toString();
    }



}
