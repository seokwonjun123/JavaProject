package gsmClass1;

import java.awt.EventQueue;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import gsmClass1.Convert;

public class Frame extends JFrame {
	public String input;
	public static String output;
	private JPanel contentPane;
	private static JTextField textField;
	private static JTextArea textArea;
	JScrollPane scrollPane;
	ImageIcon icon;

	
	public static void setOutput(String output) {
		Frame.output = output;
	}
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setVisible(true);
	}

	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 690);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 604, 651);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("ÇÑ¿µÅ¸º¯È¯±â");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("±¼¸²", Font.PLAIN, 30));
		label.setBounds(175, 10, 269, 83);
		panel.add(label);

		JLabel label_1 = new JLabel("¿µ¾î -> ÇÑ±Û");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("±¼¸²", Font.BOLD, 20));
		label_1.setBounds(205, 110, 200, 48);
		panel.add(label_1);

		JLabel lblNewLabel = new JLabel("ÀÔ·Â");
		lblNewLabel.setFont(new Font("±¼¸²", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(60, 212, 60, 100);
		panel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("°á°ú");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("±¼¸²", Font.BOLD, 20));
		label_2.setBounds(60, 427, 60, 100);
		panel.add(label_2);

		textField = new JTextField();
		textField.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		textField.setBounds(130, 215, 365, 100);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u2193");
		lblNewLabel_1.setFont(new Font("±¼¸²", Font.BOLD, 25));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(260, 350, 78, 36);
		panel.add(lblNewLabel_1);

		

		textArea = new JTextArea();
		textArea.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		textArea.setColumns(10);
		textArea.setBounds(130, 430, 365, 100);
		panel.add(textArea);

		JButton btnNewButton = new JButton("º¯È¯");
		btnNewButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	input = textField.getText();
            	Convert.main(input);
            }
        });
		btnNewButton.setBounds(160, 575, 110, 30);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("ÃÊ±âÈ­");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	textField.setText("");
            	textArea.setText("");
            }
        });
		btnNewButton_1.setBounds(360, 575, 110, 30);
		panel.add(btnNewButton_1);
		btnNewButton.addMouseListener(new MouseAdapter () {

            @Override

            public void mousePressed(MouseEvent e) {

                textArea.setText(output);

            }

        }  );

				
	}

}