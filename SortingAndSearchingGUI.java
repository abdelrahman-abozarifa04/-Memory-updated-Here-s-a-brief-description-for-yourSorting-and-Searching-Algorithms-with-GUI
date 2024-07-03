package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import static project.SortingAlgorithms.*;

public class SortingAndSearchingGUI extends JFrame {

    private JComboBox<String> sortingComboBox;
    private JComboBox<String> searchingComboBox;
    private JTextField numOfElementsField;
    private JTextField elementsField;
    private JTextArea outputTextArea;
    private JButton executeButton, clearButton, saveButton, loadButton;
    private JRadioButton sortOnlyRadioButton, searchOnlyRadioButton, bothRadioButton;

    public SortingAndSearchingGUI() {
        setTitle("Sorting and Searching Algorithms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 10));

        ButtonGroup choiceGroup = new ButtonGroup();
        sortOnlyRadioButton = new JRadioButton("Sort Only", true);
        searchOnlyRadioButton = new JRadioButton("Search Only");
        bothRadioButton = new JRadioButton("Sort and Search");
        sortOnlyRadioButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        searchOnlyRadioButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        bothRadioButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size

        choiceGroup.add(sortOnlyRadioButton);
        choiceGroup.add(searchOnlyRadioButton);
        choiceGroup.add(bothRadioButton);

        JPanel choicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        choicePanel.add(sortOnlyRadioButton);
        choicePanel.add(searchOnlyRadioButton);
        choicePanel.add(bothRadioButton);

        JLabel chooseLabel = new JLabel("Choose:");
        chooseLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size for "Choose:" label

        // Set font size for radio buttons
        sortOnlyRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
        searchOnlyRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));
        bothRadioButton.setFont(new Font("Arial", Font.PLAIN, 18));

        inputPanel.add(chooseLabel);
        inputPanel.add(choicePanel);

        JLabel numOfElementsLabel = new JLabel("Enter the number of elements:");
        numOfElementsLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        numOfElementsField = new JTextField(20);
        inputPanel.add(numOfElementsLabel);
        inputPanel.add(numOfElementsField);

        JLabel elementLabel = new JLabel("Enter the elements separated by spaces:");
        elementLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        elementsField = new JTextField(40);
        inputPanel.add(elementLabel);
        inputPanel.add(elementsField);

        JLabel sortingLabel = new JLabel("Select Sorting Algorithm:");
        sortingLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        sortingComboBox = new JComboBox<>(new String[]{"Bubble Sort", "Insertion Sort", "Merge Sort", "Selection Sort", "Quick Sort", "Heap Sort"});
        inputPanel.add(sortingLabel);
        inputPanel.add(sortingComboBox);

        JLabel searchingLabel = new JLabel("Select Searching Algorithm:");
        searchingLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        searchingComboBox = new JComboBox<>(new String[]{"Linear Search", "Binary Search"});
        inputPanel.add(searchingLabel);
        inputPanel.add(searchingComboBox);

        executeButton = new JButton("Execute");
        executeButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        executeButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeAlgorithms();
            }
        });
        inputPanel.add(executeButton);

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        clearButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        inputPanel.add(clearButton);

        saveButton = new JButton("Save Results");
        saveButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        saveButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveResults();
            }
        });
        inputPanel.add(saveButton);

        loadButton = new JButton("Load Results");
        loadButton.setPreferredSize(new Dimension(200, 100)); // Set preferred size
        loadButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadResults();
            }
        });
        inputPanel.add(loadButton);

        outputTextArea = new JTextArea();
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font size
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(outputLabel, BorderLayout.WEST);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Set font size for input fields
        numOfElementsField.setFont(new Font("Arial", Font.PLAIN, 18));
        elementsField.setFont(new Font("Arial", Font.PLAIN, 18));

        setSize(1400, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executeAlgorithms() {
        try {
            outputTextArea.setText("");

            int numOfElements = Integer.parseInt(numOfElementsField.getText());
            String[] elementsStr = elementsField.getText().split("\\s+");
            if (numOfElements != elementsStr.length) {
                JOptionPane.showMessageDialog(this, "Number of elements entered does not match the specified number.");
                return;
            }

            int[] arr = new int[numOfElements];
            for (int i = 0; i < numOfElements; i++) {
                arr[i] = Integer.parseInt(elementsStr[i]);
            }

            String sortingAlgorithm = (String) sortingComboBox.getSelectedItem();
            String searchingAlgorithm = (String) searchingComboBox.getSelectedItem();

            boolean sorted = false;

            if (sortOnlyRadioButton.isSelected() || bothRadioButton.isSelected()) {
                if (sortingAlgorithm != null) {
                    sorted = true;
                    switch (sortingAlgorithm) {
                        case "Bubble Sort":
                            bubbleSort(arr);
                            break;
                        case "Insertion Sort":
                            insertionSort(arr);
                            break;
                        case "Merge Sort":
                            mergeSort(arr);
                            break;
                        case "Selection Sort":
                            selectionSort(arr);
                            break;
                        case "Quick Sort":
                            quickSort(arr, 0, arr.length - 1);
                            break;
                        case "Heap Sort":
                            heapSort(arr);
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Invalid sorting algorithm selected.");
                            sorted = false;
                            break;
                    }
                }
            }

            if (searchOnlyRadioButton.isSelected() || bothRadioButton.isSelected()) {
                if (searchingAlgorithm != null) {
                    int target = Integer.parseInt(JOptionPane.showInputDialog("Enter the target element: "));
                    int index = -1; // Initialize index to -1
                    switch (searchingAlgorithm) {
                        case "Linear Search":
                            index = project.SearchingAlgorithms.linearSearch(arr, target);
                            break;
                        case "Binary Search":
                            if (!sorted) {
                                JOptionPane.showMessageDialog(this, "Please ensure the array is sorted for binary search.");
                                return;
                            }
                            index = project.SearchingAlgorithms.binarySearch(arr, target);
                            break;
                    }
                    if (index != -1) {
                        outputTextArea.append("Element " + target + " found at index " + index + " using " + searchingAlgorithm + "\n");
                    } else {
                        outputTextArea.append("Element " + target + " not found using " + searchingAlgorithm + "\n");
                    }
                }
            }

            // Print sorted array only if sorting option is selected
            if ((sortOnlyRadioButton.isSelected() || bothRadioButton.isSelected()) && sorted) {
                outputTextArea.append("Sorted Array using " + sortingAlgorithm + ": ");
                for (int num : arr) {
                    outputTextArea.append(num + " ");
                }
                outputTextArea.append("\n");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
        }
    }

    private void clearFields() {
        numOfElementsField.setText("");
        elementsField.setText("");
        outputTextArea.setText("");
    }

    private void saveResults() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(outputTextArea.getText());
                JOptionPane.showMessageDialog(this, "Results saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while saving results.");
            }
        }
    }

    private void loadResults() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (Scanner scanner = new Scanner(file)) {
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append("\n");
                }
                outputTextArea.setText(sb.toString());
                JOptionPane.showMessageDialog(this, "Results loaded successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error occurred while loading results.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SortingAndSearchingGUI();
            }
        });
    }
}