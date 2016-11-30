package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import client.*;
import sale.*;
import transaction.Purchase;
import transaction.Sale;
import transaction.TransactionCollection;

/**
 * Creates and sets up the user interface for the Transaction tab. Handles
 * all functionality dealing with transactions.
 * @author concox
 *
 */
public class TransactionGUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 6894451317975428869L;
	
	private JButton btnPurchase, btnSale, btnConfPurchase, btnConfSale;
	private JPanel pnlSale, pnlPurchase, pnlButtons, pnlContent;
	
	@SuppressWarnings("rawtypes")
	private JComboBox cmbSaleItems, cmbSaleClients;
	private JTextField[] txfSale = new JTextField[2];
	private JLabel[] lblSale;
	
	@SuppressWarnings("rawtypes")
	private JComboBox cmbPurchaseItems, cmbPurchaseClients;
	private JTextField[] txfPurchase = new JTextField[2];
	private JLabel[] lblPurchase;
	
	/**
	 * Constructor
	 */
	public TransactionGUI() {
		setLayout(new BorderLayout());
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}
	
	/**
	 * Creates the components for the gui.
	 */
	private void createComponents() {
		pnlContent = new JPanel();
		pnlButtons = new JPanel();
		
		btnPurchase = new JButton("Purchase");
		btnPurchase.addActionListener(this);
		
		btnSale = new JButton("Sale");
		btnSale.addActionListener(this);
		
		pnlButtons.add(btnSale);
		pnlButtons.add(btnPurchase);
		add(pnlButtons, BorderLayout.NORTH);
		
		createSalePnl();

		createPurchasePnl();
		
		add(pnlContent, BorderLayout.CENTER);
	}
	
	/**
	 * Make the buttons work!
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnPurchase) {
			pnlContent.removeAll();
			createPurchasePnl();
			pnlContent.add(pnlPurchase);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnSale) {
			pnlContent.removeAll();
			createSalePnl();
			pnlContent.add(pnlSale);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnConfPurchase) {
			performPurchase();
		} else if (e.getSource() == btnConfSale) {
			performSale();
		}
	}
	
	/**
	 * Handles the logic behind making the sale button work. 
	 */
	private void performSale() {
		if (txfSale[0].getText().length() > 0 && txfSale[1].getText().length() > 0) {
			double price = Double.parseDouble(txfSale[0].getText());
			double commission = Double.parseDouble(txfSale[1].getText());
			List<Item> item = ItemCollection.search(cmbSaleItems.getSelectedItem().toString());
			int itemId = Integer.parseInt(item.get(0).getId());
			String[] clientName = cmbPurchaseClients.getSelectedItem().toString().split(" ");
			List<Client> client = ClientCollection.search(clientName[0], clientName[1]);
			int clientId = Integer.parseInt(client.get(0).getId());

			Sale sale = new Sale(commission, price, itemId, clientId);
			TransactionCollection.addSale(sale);
			System.out.println("remove: " + item.get(0).getName());
			ItemCollection.removeItem(item.get(0).getName());
			
			JOptionPane.showMessageDialog(null, "Sale to " + 
					client.get(0).getFirstName() + " was successful!");
			for (int i = 0; i < txfSale.length; i++) {
				if (txfSale[i].getText().length() != 0) {
					txfSale[i].setText("");
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Fill out the fields!");
		}
		
	}
	
	/**
	 * Handles the logic behind making the purchase button work.
	 */
	private void performPurchase() {
		if (txfPurchase[0].getText().length() > 0 && txfPurchase[1].getText().length() > 0){
			double cost = Double.parseDouble(txfPurchase[0].getText());
			String condition = txfPurchase[1].getText();
			List<Item> item = ItemCollection.search(cmbPurchaseItems.getSelectedItem().toString());
			int itemId = Integer.parseInt(item.get(0).getId());
			String[] clientName = cmbPurchaseClients.getSelectedItem().toString().split(" ");
			List<Client> client = ClientCollection.search(clientName[0], clientName[1]);
			int clientId = Integer.parseInt(client.get(0).getId());
			
			Purchase purchase = new Purchase(condition, cost, itemId, clientId);
			TransactionCollection.addPurchase(purchase);
			
			JOptionPane.showMessageDialog(null, "Purchase from " + 
					client.get(0).getFirstName() + " was successful!");
			
			for (int i = 0; i < txfPurchase.length; i++) {
				if (txfPurchase[i].getText().length() != 0) {
					txfPurchase[i].setText("");
				}
			}	
		} else {
			JOptionPane.showMessageDialog(null, "Fill out fields!");
		}
	}
	
	/**
	 * Sets up and creates the sale panel. Handles the field generaction and 
	 * text handling. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createSalePnl() {
		pnlSale = new JPanel();
		pnlSale.setLayout(new GridLayout(8, 0));
		String saleNames[] = {"Original Price: ","Added Commission: "};
		lblSale = new JLabel[saleNames.length];
		for (int i = 0; i < saleNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfSale[i] = new JTextField(25);
			lblSale[i] = new JLabel(saleNames[i]);
			panel.add(lblSale[i]);
			panel.add(txfSale[i]);
			pnlSale.add(panel);
		}
		JPanel comboItemPanelSale = new JPanel();
		comboItemPanelSale.setLayout(new GridLayout(1, 1));
		List<Item> itemsSale =ItemCollection.getItems();
		String itemNamesSale[] = new String[itemsSale.size()];
		for (int i = 0; i < itemNamesSale.length; i++) {
			itemNamesSale[i] = itemsSale.get(i).getName();
		}
		if (itemsSale != null) {
			cmbSaleItems = new JComboBox(itemNamesSale);
			cmbSaleItems.setSelectedItem(0);
			comboItemPanelSale.add(new JLabel("Select Item: "));
			comboItemPanelSale.add(cmbSaleItems);
			pnlSale.add(comboItemPanelSale);
		}
		
		JPanel comboClientPanelSale = new JPanel();
		comboItemPanelSale.setLayout(new GridLayout(1, 1));
		List<Client> clientsSale = ClientCollection.getClients();
		String clientNamesSale[] = new String[clientsSale.size()];
		for (int i = 0; i < clientNamesSale.length; i++) {
			clientNamesSale[i] = clientsSale.get(i).getFirstName() 
					+ " " + clientsSale.get(i).getLastName();
		}
		if (clientsSale != null) {
			cmbSaleClients = new JComboBox(clientNamesSale);
			cmbSaleClients.setSelectedItem(0);
			comboClientPanelSale.add(new JLabel("Select Client to sell to: "));
			comboClientPanelSale.add(cmbSaleClients);
			pnlSale.add(comboClientPanelSale);
		}
		JPanel pnlSaleBtn = new JPanel();
		btnConfSale = new JButton("Sale");
		btnConfSale.addActionListener(this);
		pnlSaleBtn.add(btnConfSale);
		pnlSale.add(pnlSaleBtn);
		
	}
	
	/**
	 * Sets up and creates the purchase panel. Handles the field generaction and 
	 * text handling. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createPurchasePnl() {
		pnlPurchase = new JPanel();
		pnlPurchase.setLayout(new GridLayout(8, 0));
		String purchaseNames[] = {"Cost of item:", "Condition of item:"};
		lblPurchase = new JLabel[purchaseNames.length];
		for (int i = 0; i < purchaseNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfPurchase[i] = new JTextField(25);
			lblPurchase[i] = new JLabel(purchaseNames[i]);
			panel.add(lblPurchase[i]);
			panel.add(txfPurchase[i]);
			pnlPurchase.add(panel);
		}
		
		JPanel comboItemPanel = new JPanel();
		comboItemPanel.setLayout(new GridLayout(1, 1));
		List<Item> items = ItemCollection.getItems();
		String itemNames[] = new String[items.size()];
		for (int i = 0; i < itemNames.length; i++) {
			itemNames[i] = items.get(i).getName();
		}
		if (items != null) {
			cmbPurchaseItems = new JComboBox(itemNames);
			cmbPurchaseItems.setSelectedItem(0);
			comboItemPanel.add(new JLabel("Select Item: "));
			comboItemPanel.add(cmbPurchaseItems);
			pnlPurchase.add(comboItemPanel);
		}
		
		JPanel comboClientPanel = new JPanel();
		comboItemPanel.setLayout(new GridLayout(1, 1));
		List<Client> clients = ClientCollection.getClients();
		String clientNames[] = new String[clients.size()];
		for (int i = 0; i < clientNames.length; i++) {
			clientNames[i] = clients.get(i).getFirstName() + " " + clients.get(i).getLastName();
		}
		if (clients != null) {
			cmbPurchaseClients = new JComboBox(clientNames);
			cmbPurchaseClients.setSelectedItem(0);
			comboClientPanel.add(new JLabel("Select Client to buy item from: "));
			comboClientPanel.add(cmbPurchaseClients);
			pnlPurchase.add(comboClientPanel);
		}
		JPanel pnlPurchaseBtn = new JPanel();
		btnConfPurchase = new JButton("Purchase");
		btnConfPurchase.addActionListener(this);
		pnlPurchaseBtn.add(btnConfPurchase);
		pnlPurchase.add(pnlPurchaseBtn);
	}
}
