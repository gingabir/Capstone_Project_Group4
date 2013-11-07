package Default;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class MainSearch {

	private JFrame frame;
	private JTextField keyword;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainSearch window = new MainSearch();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainSearch() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frame = new JFrame();
		frame.setBounds(100, 100, 663, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);

		JButton btn_document = new JButton("Document Search");
		btn_document.setBounds(486, 42, 138, 23);
		desktopPane.add(btn_document);

		keyword = new JTextField();
		keyword.setBounds(210, 43, 231, 20);
		desktopPane.add(keyword);
		keyword.setColumns(10);

		JTextPane txtpnEnterTheDocument = new JTextPane();
		txtpnEnterTheDocument.setBackground(Color.LIGHT_GRAY);
		txtpnEnterTheDocument.setText("Enter the search String");
		txtpnEnterTheDocument.setBounds(25, 43, 168, 22);
		desktopPane.add(txtpnEnterTheDocument);

		JButton btn_index = new JButton("Index Search");
		btn_index.setBounds(486, 8, 138, 23);
		desktopPane.add(btn_index);

		final JTextArea result = new JTextArea();
		result.setBounds(25, 84, 599, 151);
		desktopPane.add(result);

		btn_document.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String k=keyword.getText().toString();
				SearchDocument search = new SearchDocument();
				if(search.LoadFromCache()){
					result.setText(search.find(k));
				}
			}
		});
	}
}
