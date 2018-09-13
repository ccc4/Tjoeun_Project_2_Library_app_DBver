package dialog.member;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import frame.Home;

public class MemCheckRemove extends JDialog {
	
	private boolean value = false;
	
	JLabel pwLabel;
	JPasswordField pwField;
	JButton removeBtn; 
	
	public MemCheckRemove(Home frame, String title) {
		super(frame, title, true);
		
		getContentPane().setLayout(null);
		
		add(pwLabel = new JLabel("��й�ȣ"));
		pwLabel.setBounds(10, 0, 85, 30);
		add(pwField = new JPasswordField());
		pwField.setBounds(100, 0, 120, 30);
		
		add(removeBtn = new JButton("Ż��"));
		removeBtn.setBounds(160, 35, 60, 30);
		
		this.setSize(235, 100);
//		setResizable(false);
		this.setLocationRelativeTo(null);
		
		generateEvent();
	}
	
	private void generateEvent() {
		removeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(getPwField().length() == 0) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �ۼ��ؾ� �մϴ�.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
					pwField.requestFocus();
					return;
				}
				
				int check = JOptionPane.showConfirmDialog(null, "���� ȸ��Ż�� �����Ͻðڽ��ϱ�?\n���̵� ������ �Ұ����մϴ�.", "ȸ��Ż��", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
//					setVisible(false);
				
				value = true;
				setVisible(false);
			}
		});
	}

	public boolean check() {
		return this.value;
	}

	public String getPwField() {
		return pwField.getText().trim();
	}

	public void setPwField(JPasswordField pwField) {
		this.pwField = pwField;
	}
}
