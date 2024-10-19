package Frontend;

import Backend.DBOperations611;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Frontend611 extends JFrame {

    private JTextArea outputArea;  // For the "View Info" tab
    private JTextArea submitOutputArea;  // For the "Submit PO" tab
    private JTextField poNumberField;
    private JTextField clientIDField;
    private JTextField partNumberField;
    private JTextField qtyOrderedField;
    private JTextField priceOrderedField;
    private ArrayList<PurchaseOrderLine> poLines; // To hold lines for the PO

    public Frontend611() {
        // Set up the JFrame
        setTitle("Purchase Order System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the list to hold PO lines
        poLines = new ArrayList<>();

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab for viewing information
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(null);

        // Tab for submitting purchase order
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(null);

        // Adding both tabs to the tabbed pane
        tabbedPane.addTab("View Info", viewPanel);
        tabbedPane.addTab("Submit PO", submitPanel);
        tabbedPane.setBounds(0, 0, 600, 600);
        add(tabbedPane);

        // ----- View Info Tab -----
        // Create buttons for listing parts and POs, and viewing PO details
        JButton listPartsButton = new JButton("List Parts");
        JButton listPOsButton = new JButton("List Purchase Orders");
        JButton poDetailsButton = new JButton("View PO Details");

        // Output area for showing results
        outputArea = new JTextArea();
        outputArea.setBounds(50, 300, 500, 250);
        outputArea.setEditable(false);
        viewPanel.add(outputArea);

        JLabel poNumberLabel = new JLabel("PO No:");
        poNumberLabel.setBounds(320, 50, 100, 30);
        viewPanel.add(poNumberLabel);

        poNumberField = new JTextField();
        poNumberField.setBounds(420, 50, 100, 30);
        viewPanel.add(poNumberField);

        // Set button bounds for the view tab
        listPartsButton.setBounds(50, 200, 150, 30);
        listPOsButton.setBounds(220, 200, 180, 30);
        poDetailsButton.setBounds(420, 200, 150, 30);

        // Add buttons to the view panel
        viewPanel.add(listPartsButton);
        viewPanel.add(listPOsButton);
        viewPanel.add(poDetailsButton);

        // ----- Submit PO Tab -----
        // Text fields for entering PO information in the submit tab
        JLabel clientLabel = new JLabel("Client ID:");
        clientLabel.setBounds(50, 50, 100, 30);
        submitPanel.add(clientLabel);

        clientIDField = new JTextField();
        clientIDField.setBounds(150, 50, 150, 30);
        submitPanel.add(clientIDField);

        JLabel partLabel = new JLabel("Part No:");
        partLabel.setBounds(50, 100, 100, 30);
        submitPanel.add(partLabel);

        partNumberField = new JTextField();
        partNumberField.setBounds(150, 100, 150, 30);
        submitPanel.add(partNumberField);

        JLabel qtyLabel = new JLabel("Quantity Ordered:");
        qtyLabel.setBounds(50, 150, 120, 30);
        submitPanel.add(qtyLabel);

        qtyOrderedField = new JTextField();
        qtyOrderedField.setBounds(170, 150, 130, 30);
        submitPanel.add(qtyOrderedField);

        JLabel priceLabel = new JLabel("Price Ordered:");
        priceLabel.setBounds(320, 150, 100, 30);
        submitPanel.add(priceLabel);

        priceOrderedField = new JTextField();
        priceOrderedField.setBounds(420, 150, 100, 30);
        submitPanel.add(priceOrderedField);

        // Create output area for showing the added PO lines and submission status
        submitOutputArea = new JTextArea();
        submitOutputArea.setBounds(50, 300, 500, 250);
        submitOutputArea.setEditable(false);
        submitPanel.add(submitOutputArea);

        // Create buttons for adding a PO line and submitting the PO
        JButton addLineButton = new JButton("Add PO Line");
        JButton submitPOButton = new JButton("Submit Purchase Order");

        // Set button bounds for the submit tab
        addLineButton.setBounds(50, 250, 150, 30);
        submitPOButton.setBounds(250, 250, 250, 30);

        // Add buttons to the submit panel
        submitPanel.add(addLineButton);
        submitPanel.add(submitPOButton);

        // ----- Button Actions -----
        listPartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listParts611();
            }
        });

        listPOsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPOs611();
            }
        });

        poDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPODetails611();
            }
        });

        addLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPOLine611();
            }
        });

        submitPOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitPO611();
            }
        });
    }

    // Add PO Line method
    private void addPOLine611() {
        try {
            int partNo = Integer.parseInt(partNumberField.getText());
            int qty = Integer.parseInt(qtyOrderedField.getText());
            double price = Double.parseDouble(priceOrderedField.getText());

            // Add the new line to the list
            poLines.add(new PurchaseOrderLine(partNo, qty, price));

            // Display the added line in the Submit PO tab
            submitOutputArea.append("PO Line added: Part No " + partNo + ", Quantity: " + qty + ", Price: " + price + "\n");
        } catch (NumberFormatException ex) {
            submitOutputArea.setText("Invalid input. Please enter valid numbers.\n");
        }
    }

    // Submit PO to the backend
    private void submitPO611() {
        try {
            int clientID = Integer.parseInt(clientIDField.getText());

            // Call backend method to submit PO
            String poSubmissionStatus = DBOperations611.submitPO611(clientID, poLines); // Pass client ID and lines

            // Show the submission result in the Submit PO tab
            submitOutputArea.append(poSubmissionStatus + "\n");

            // Clear PO lines after submission
            poLines.clear();
        } catch (Exception ex) {
            submitOutputArea.setText("Error submitting PO: " + ex.getMessage() + "\n");
        }

    }

    // Backend method calls for listing parts
    private void listParts611() {
        try {
            String partsList = DBOperations611.listParts611(); // Call backend
            outputArea.setText(partsList);
        } catch (Exception ex) {
            outputArea.setText("Error fetching parts: " + ex.getMessage());
        }
    }

    // Backend method calls for listing POs
    private void listPOs611() {
        try {
            String poList = DBOperations611.listPOs611(); // Call backend
            outputArea.setText(poList);
        } catch (Exception ex) {
            outputArea.setText("Error fetching POs: " + ex.getMessage());
        }
    }

    // Fetch details for a specific PO
    private void viewPODetails611() {
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
