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
        String regex = "^[0-9.]+$";
        return qtd.matches(regex);
    }

    public static double stringParaDouble(String string){

        double valor = Double.parseDouble(string);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(valor));

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
