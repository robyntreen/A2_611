package Frontend;

import Backend.DBOperations611;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frontend611 extends JFrame {

    private JTextArea outputArea;
    private JTextField poNumberField;

    public Frontend611() {
        // Set up the JFrame
        setTitle("Purchase Order System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Using absolute layout

        // Create buttons
        JButton listPartsButton = new JButton("List Parts");
        JButton listPOsButton = new JButton("List Purchase Orders");
        JButton poDetailsButton = new JButton("View PO Details");
        JButton submitPOButton = new JButton("Submit Purchase Order");

        // Create text area for output
        outputArea = new JTextArea();
        outputArea.setBounds(50, 200, 500, 250);
        outputArea.setEditable(false);
        add(outputArea);

        // Text field for entering PO number
        JLabel poLabel = new JLabel("PO Number:");
        poLabel.setBounds(50, 50, 100, 30);
        add(poLabel);

        poNumberField = new JTextField();
        poNumberField.setBounds(150, 50, 150, 30);
        add(poNumberField);

        // Set button bounds (position and size)
        listPartsButton.setBounds(50, 100, 150, 30);
        listPOsButton.setBounds(220, 100, 180, 30);
        poDetailsButton.setBounds(420, 100, 150, 30);
        submitPOButton.setBounds(150, 150, 250, 30);

        // Add buttons to the frame
        add(listPartsButton);
        add(listPOsButton);
        add(poDetailsButton);
        add(submitPOButton);

        // Button actions
        listPartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listParts();
            }
        });

        listPOsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPOs();
            }
        });

        poDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPODetails();
            }
        });

        submitPOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitPO();
            }
        });
    }

    // Backend method calls for listing parts
    private void listParts() {
        try {
            String partsList = DBOperations611.listParts611(); // Call backend
            outputArea.setText(partsList);
        } catch (Exception ex) {
            outputArea.setText("Error fetching parts: " + ex.getMessage());
        }
    }

    // Backend method calls for listing POs
    private void listPOs() {
        try {
            String poList = DBOperations611.listPOs611(); // Call backend
            outputArea.setText(poList);
        } catch (Exception ex) {
            outputArea.setText("Error fetching POs: " + ex.getMessage());
        }
    }

    // Fetch details for a specific PO
    private void viewPODetails() {
        try {
            int poNumber = Integer.parseInt(poNumberField.getText());
            String poDetails = DBOperations611.listPOinfo611(poNumber); // Call backend
            outputArea.setText(poDetails);
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid PO number format.");
        } catch (Exception ex) {
            outputArea.setText("Error fetching PO details: " + ex.getMessage());
        }
    }

    // Submit a new purchase order
    private void submitPO() {
        // Prompt a simple PO creation dialog (in a real-world scenario this would involve more fields)
        try {
            // Dummy submission - ideally, you'd gather partNo, qtyOrdered from a form
            String poSubmissionStatus = DBOperations611.submitPO611(/* pass PO object */);
            outputArea.setText(poSubmissionStatus);
        } catch (Exception ex) {
            outputArea.setText("Error submitting PO: " + ex.getMessage());
        }
    }

    // Main method to run the UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frontend611().setVisible(true);
            }
        });
    }
}
