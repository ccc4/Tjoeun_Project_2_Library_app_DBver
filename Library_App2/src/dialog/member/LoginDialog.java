package dialog.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import frame.Home;

public class LoginDialog extends JDialog {
private boolean value = false;
	
	JLabel idLabel, pwLabel;
	JTextField idField, pwField;
	JButton loginBtn, exitBtn;
	
	public LoginDialog(Home owner, String title) {
		super(owner, title, true);
		
		getContentPane().setLayout(null);

		add(idLabel = new JLabel("���̵�"));
		idLabel.setBounds(10, 10, 60, 30);
		add(idField = new JTextField());
		idField.setBounds(70, 10, 100, 30);
		
		add(pwLabel = new JLabel("��й�ȣ"));
		pwLabel.setBounds(10, 40, 60, 30);
		add(pwField = new JPasswordField());
		pwField.setBounds(70, 40, 100, 30);
		
		add(loginBtn = new JButton("�α���"));
		loginBtn.setBounds(90, 75, 80, 30);
		
		this.setSize(195, 150);
		this.setLocationRelativeTo(null);
		
		generateEvents();
		
	}
	
	private void generateEvents() {
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���", "ERROR", JOptionPane.WARNING_MESSAGE);
					idField.requestFocus();
					return;
				}
				if (pwField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���", "ERROR", JOptionPane.WARNING_MESSAGE);
					pwField.requestFocus();
					return;
				}

				value = true;
				setVisible(false);
			}
		});
	}

	public boolean check() {
		return this.value;
	}

	public String getIdField() {
		return idField.getText().trim();
	}

	public void setIdField(JTextField idField) {
		this.idField = idField;
	}

	public String getPwField() {
		return pwField.getText().trim();
	}

	public void setPwField(JTextField pwField) {
		this.pwField = pwField;
	}
}
