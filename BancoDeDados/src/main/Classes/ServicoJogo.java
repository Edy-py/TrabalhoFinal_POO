package Classes;

import javax.swing.*;
import java.awt.event.ItemEvent;


public class ServicoJogo {
    //Transforma uma string numérica para inteiro
    public static int stringParaInt(String string){

            return Integer.parseInt(string);

    }

    // Verifica se ano é válido
    public static boolean verificarAno(String ano){
       String regex = "^\\d+$"; // permite somente valores numéricos

       if (!ano.matches(regex)){
            return false;
       }

       int anoInt = stringParaInt(ano);

       // Retorna falso se o ano não estiver no intervalo
       if(anoInt < 1952 || anoInt > 2025){
           // 1952 foi quando o primeiro jogo foi lançãdo
           // 2025 ano atual
           return false;
       }
       return true;
    }

    // Verifica se quantidade é um valor numérico
    public static boolean verificarQtd(String qtd){
        String regex = "^\\d+$"; // Só permite caracteres numérico
        return qtd.matches(regex);
    }

    // Validação de preço
    public static boolean verificarPreco(String qtd){

        String regex = "^\\d+([.,]\\d{1,2})?$"; // Permite caracteres numéricos separados por pontos ou por vírgula

        return qtd.matches(regex);
    }

    // Transforma string em duoble
    public static double stringParaDouble(String string){


        try {
            // Remove espaços e caracteres especiais
            string = string.trim().replaceAll("[R$\\s]", "");

            // Substitui vírgula por ponto
            string = string.replace(",", ".");

            // Verifica se o formato é válido usando regex
            if (!string.matches("^\\d*\\.?\\d+$")) {
                JOptionPane.showMessageDialog(null, "Valor inválido. Use apenas números e vírgula ou ponto como separador decimal.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            // Converte para double
            return Math.round(Double.parseDouble(string) * 100.0) / 100.0;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido. Use apenas números e vírgula ou ponto como separador decimal.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
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
