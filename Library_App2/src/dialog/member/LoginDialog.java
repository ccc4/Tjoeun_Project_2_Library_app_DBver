package dialog.member;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

		add(idLabel = new JLabel("아이디"));
		idLabel.setBounds(10, 0, 60, 30);
		add(idField = new JTextField());
		idField.setBounds(70, 0, 100, 30);
		
		add(pwLabel = new JLabel("비밀번호"));
		pwLabel.setBounds(10, 30, 60, 30);
		add(pwField = new JPasswordField());
		pwField.setBounds(70, 30, 100, 30);
		
		add(loginBtn = new JButton("로그인"));
		loginBtn.setBounds(90, 65, 80, 30);
		
		this.setSize(190, 130);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		generateEvents();
		
	}
	
	private void generateEvents() {
		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					idField.requestFocus();
					return;
				}
				if (pwField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
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
