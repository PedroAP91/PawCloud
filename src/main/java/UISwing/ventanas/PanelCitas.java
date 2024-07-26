package UISwing.ventanas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PanelCitas extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PanelCitas() {
		setOpaque(false);
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(1026, 630, 89, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(0, 630, 89, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(1026, 0, 89, 23);
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(463, 304, 89, 23);
		add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setBounds(0, 0, 89, 23);
		add(btnNewButton_4);

	}
}
