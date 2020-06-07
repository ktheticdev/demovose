package com.gontones.demovo;

import javax.swing.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class MainWidget extends JFrame{
    private JPanel pane;
    private JTabbedPane tabbedPane1;
    private JButton PASTEButton;
    private JTextField input;
    private JButton ENCODEButton;
    private JButton DECODEButton;
    private JTextArea output;
    private JButton COPYButton;
    private JPasswordField salt;
    private JPasswordField key1;
    private JPasswordField key2;
    private JButton SAVESETTINGSButton;

    public MainWidget() {
        setContentPane(pane);
        setTitle("DEMOVO");
        pack();
        setMinimumSize(getSize());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final Preferences prefs = Preferences.userNodeForPackage(MainWidget.class);
        salt.setText(prefs.get("salt", "defaultsalt"));
        key1.setText(prefs.get("key1", "228"));
        key2.setText(prefs.get("key2", "1488"));
        final int echo = this.salt.getEchoChar();
        salt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                super.focusGained(e);
                MainWidget.this.salt.setEchoChar('\0');
            }
        });
        salt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                super.focusLost(e);
                MainWidget.this.salt.setEchoChar((char)echo);
            }
        });
        key1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                super.focusGained(e);
                MainWidget.this.key1.setEchoChar('\0');
            }
        });
        key1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                super.focusLost(e);
                MainWidget.this.key1.setEchoChar((char)echo);
            }
        });
        key2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(final FocusEvent e) {
                super.focusGained(e);
                MainWidget.this.key2.setEchoChar('\0');
            }
        });
        key2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                super.focusLost(e);
                MainWidget.this.key2.setEchoChar((char)echo);
            }
        });
        SAVESETTINGSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                prefs.put("salt", String.valueOf(salt.getPassword()));
                prefs.put("key1", String.valueOf(key1.getPassword()));
                prefs.put("key2", String.valueOf(key2.getPassword()));
            }
        });
        PASTEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    input.setText(TextTransfer.getData());
                }
                catch (IOException | UnsupportedFlavorException ex) {
                    ex.printStackTrace();
                }
            }
        });
        COPYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextTransfer.setData(output.getText());
            }
        });
        ENCODEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText(CipherUtils.base64encode(CipherUtils.aescipher(prefs.get("key2", "1488"), CipherUtils.caesarEncode(MainWidget.this.input.getText(), prefs.get("key1", "228")), prefs.get("salt", "defaultsalt"))));
                pack();
            }
        });
        DECODEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    output.setText(CipherUtils.caesarDecode(CipherUtils.aesdecipher(prefs.get("key2", "1488"), CipherUtils.base64decode(MainWidget.this.input.getText()), prefs.get("salt", "defaultsalt")), prefs.get("key1", "228")));
                }
                catch (Exception ex) {
                    output.setText("Input is not a ciphertext, or keys you entered are wrong.");
                }
                pack();
            }
        });
    }

    public static void main(String[] args) {
        try {
            for (final UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWidget.class.getName()).log(Level.SEVERE, null, ex);
        }
        new MainWidget();
    }
}
