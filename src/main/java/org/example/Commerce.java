//
// Auteur : Filipe Dias Morais
// Projet : ExerciceCommerce
// Date   : 31.01.2023
// 


package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Properties;

public class Commerce extends JPanel {

    static JTextField item;
    static JTextField prenom;
    static JButton acheter;
    static JTextArea logs;
    static JTextArea lastLine;

    static Database database;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Commerce");
        Commerce commerce = new Commerce();
        frame.add(commerce);
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Commerce");

        InputStream input = Commerce.class.getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();

        try {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        database = new Database(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        logs.setText(database.getItems());
    }

    public Commerce() {
        setLayout(new BorderLayout());
        JPanel nord = new JPanel();
        add(nord, BorderLayout.NORTH);
        nord.setLayout(new GridLayout(1, 5));

        JLabel label1 = new JLabel("Item:");
        nord.add(label1);

        item = new JTextField();
        nord.add(item);

        JLabel label2 = new JLabel("Nom:");
        nord.add(label2);

        prenom = new JTextField();
        nord.add(prenom);

        acheter = new JButton("Achete");
        nord.add(acheter);
        acheter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    database.achete(Integer.parseInt(item.getText()), prenom.getText());
                    lastLine.setText(prenom.getText() + " a acheté l'item " + item.getText() + ". " + database.getSolde("Il lui reste " + database.getSolde(prenom.getText())));
                    logs.setText(database.getItems());

                } catch (Exception ex) {
                    lastLine.setText(ex.getMessage());
                }
            }
        });

        JPanel center = new JPanel();
        add(center, BorderLayout.CENTER);
        center.setLayout(new GridLayout(1, 1));

        logs = new JTextArea();
        logs.setEditable(false);
        center.add(logs);

        JPanel sud = new JPanel();
        add(sud, BorderLayout.SOUTH);
        sud.setLayout(new GridLayout(1, 1));

        lastLine = new JTextArea();
        lastLine.setEditable(false);
        sud.setBorder(BorderFactory.createLineBorder(Color.black));
        sud.add(lastLine);

    }


}
