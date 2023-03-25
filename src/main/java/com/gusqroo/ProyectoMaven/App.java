package com.gusqroo.ProyectoMaven;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import okhttp3.*;


public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
       
            	
                JFrame frame = new JFrame("Convertidor de Divisas API: "
                		+ "api.apilayer.com/exchangerates_data");
                
                JLabel fromLabel = new JLabel("De");
                JComboBox<String> comboboxFrom;
                String [ ] currencys = {"USD - Dollar", "EUR - Euro","MXN - "
                		+ "Peso Mexicano","CAD - Dolar Canadiense","GBP - Libra Esterlina",
                		"JPY - Yen","CNH - Yuan","SEK - Corona Sueca"};
                comboboxFrom = new JComboBox<>(currencys);
     
                JLabel toLabel = new JLabel("a:");
                JComboBox<String> comboboxTo;
                comboboxTo = new JComboBox<>(currencys);
                JLabel amountLabel = new JLabel("Monto:");
                
               
                JTextField amountTextField = new JTextField(10);
              
                JButton convertButton = new JButton("Convertir");
                

                JPanel panel = new JPanel();
                panel.add(fromLabel);
                panel.add(comboboxFrom);
                panel.add(toLabel);
                panel.add(comboboxTo);
                panel.add(amountLabel);
                panel.add(amountTextField);
                panel.add(convertButton);
              
                frame.setLocationRelativeTo(null);
                //frame.setBounds(400, 200, 400, 200);
                //panel.setBounds(400,200,400,200);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
               
                
                convertButton.addActionListener(new ActionListener() {
         	                	
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	Icon icono = new ImageIcon("src/images/icon.png");
                       
                        String from = comboboxFrom.getSelectedItem().toString();
                        String fromLen = from.toString().substring(0,3);
                        String to = comboboxTo.getSelectedItem().toString();
                        String toLen = to.toString().substring(0,3);
                        String amount = amountTextField.getText();
                        if (amount.isEmpty()) {
                        	String errorMontoVacio = "Favor de escribir un monto";
                        	JOptionPane.showMessageDialog(frame, errorMontoVacio, "Error", JOptionPane.INFORMATION_MESSAGE);
							
						} else {
						       OkHttpClient client = new OkHttpClient().newBuilder().build();

		                        Request request = new Request.Builder()
		                                .url("https://api.apilayer.com/exchangerates_data/convert?to=" + toLen + "&from=" + fromLen + "&amount=" + amount)
		                                .addHeader("apikey", "8jBsTJmf2LfpE4FEmanEluoHaFAUMOX4")
		                                .method("GET", null)
		                                .build();
		                        
		                     
		                        try {
		                        	String amount2 = amountTextField.getText();
		                        	Response response = client.newCall(request).execute();
		                            String responseBody = response.body().string();
		                            ObjectMapper objectMapper = new ObjectMapper();
		                            JsonNode jsonNode = objectMapper.readTree(responseBody);
		                            String result = jsonNode.get("result").asText();
		                            String mensaje = "El monto convertido de " + amount2 + " " +fromLen  + " es de " + result + " "+ toLen;
		                            SwingUtilities.invokeLater(new Runnable() {
		                                public void run() {
		                                    JOptionPane.showMessageDialog(frame, mensaje, "Resultado de la Conversion", JOptionPane.INFORMATION_MESSAGE, icono);
		                                    //JLabel resultLabel = new JLabel(result);
		                                    //panel.add(resultLabel);
		                                    //resultLabel.setBounds(100,200, 300,400);
		                                }
		                            });
		                        } catch (IOException ex) {
		                            JOptionPane.showMessageDialog(frame, "Error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		                        }
						}
                 
                    }
                });
            }
        });
    }
}
