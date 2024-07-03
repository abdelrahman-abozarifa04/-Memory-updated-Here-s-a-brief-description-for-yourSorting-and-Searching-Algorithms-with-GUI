package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JCheckBox showPasswordCheckBox;

    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "password";

    private static final String USER_INFO_FILE = "userinfo.txt";

    public LoginGUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome, Please Log In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setForeground(Color.GRAY);
        usernameField.setText("Enter Username");
        usernameField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Enter Username");
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Show placeholder text
        passwordField.setText("Enter Password");
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Enter Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Enter Password");
                    passwordField.setEchoChar((char) 0); // Show placeholder text
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(passwordField, gbc);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('•');
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(showPasswordCheckBox, gbc);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 24));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (checkCredentials(username, password)) {
                    dispose();
                    openSortingAndSearchingGUI();
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password.");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        inputPanel.add(loginButton, gbc);

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.PLAIN, 24));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUp();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(signUpButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        setSize(600, 400); // Increase the size of the JFrame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean checkCredentials(String username, String password) {
        if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
            return true;
        }

        try (Scanner scanner = new Scanner(new File(USER_INFO_FILE))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void signUp() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("Enter Username") || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty.");
            return;
        }

        if (password.equals("Enter Password") || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            return;
        }

        try (FileWriter writer = new FileWriter(USER_INFO_FILE, true)) {
            writer.write(username + ":" + password + "\n");
            JOptionPane.showMessageDialog(this, "Sign up successful! You can now log in.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save user info.");
            e.printStackTrace();
        }
    }

    private void openSortingAndSearchingGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SortingAndSearchingGUI();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI();
            }
        });
    }
}
