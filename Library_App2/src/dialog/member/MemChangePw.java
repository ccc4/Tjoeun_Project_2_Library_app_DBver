package dialog.member;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import frame.Home;

public class MemChangePw extends JDialog {
	
	private boolean value = false;
	
	JLabel beforePwLabel, afterPwLabel, afterPwCheckLabel;
	JPasswordField beforePwField, afterPwField, afterPwCheckField;
	JButton changeBtn; 
	
	public MemChangePw(Home frame, String title) {
		super(frame, title, true);
		
		getContentPane().setLayout(null);
		
		add(beforePwLabel = new JLabel("���� ��й�ȣ"));
		beforePwLabel.setBounds(10, 0, 85, 30);
		add(beforePwField = new JPasswordField());
		beforePwField.setBounds(100, 0, 120, 30);
		
		add(afterPwLabel = new JLabel("�ű� ��й�ȣ"));
		afterPwLabel.setBounds(10, 30, 85, 30);
		add(afterPwField = new JPasswordField());
		afterPwField.setBounds(100, 30, 120, 30);
		
		add(afterPwCheckLabel = new JLabel("��й�ȣ Ȯ��"));
		afterPwCheckLabel.setBounds(10, 60, 85, 30);
		add(afterPwCheckField = new JPasswordField());
		afterPwCheckField.setBounds(100, 60, 120, 30);
		
		add(changeBtn = new JButton("����"));
		changeBtn.setBounds(160, 95, 60, 30);
		
		this.setSize(240, 165);
//		setResizable(false);
		this.setLocationRelativeTo(null);
		
		generateEvent();
	}
	
	private void generateEvent() {
		changeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(getBeforePwField().length() == 0) {
					JOptionPane.showMessageDialog(null, "���� ��й�ȣ�� �ۼ��ؾ� �մϴ�.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
					beforePwField.requestFocus();
					return;
				}
				if(getAfterPwField().length() == 0) {
					JOptionPane.showMessageDialog(null, "������ ��й�ȣ�� �ۼ��ؾ� �մϴ�.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
					afterPwField.requestFocus();
					return;
				}
				if(!getAfterPwField().equals(getAfterPwCheckField())) {
					JOptionPane.showMessageDialog(null, "��й�ȣ Ȯ���� ���ּ���.", "��й�ȣ ����", JOptionPane.WARNING_MESSAGE);
					afterPwCheckField.requestFocus();
					return;
				}
				
				int check = JOptionPane.showConfirmDialog(null, "��й�ȣ�� �����Ͻðڽ��ϱ�?", "��й�ȣ ����", JOptionPane.YES_NO_OPTION);
				if(check != JOptionPane.YES_OPTION) return;
				
				value = true;
				setVisible(false);
			}
		});
	}

	public boolean check() {
		return this.value;
	}

	public String getBeforePwField() {
		return beforePwField.getText().trim();
	}

	public void setBeforePwField(JPasswordField beforePwField) {
		this.beforePwField = beforePwField;
	}

	public String getAfterPwField() {
		return afterPwField.getText().trim();
	}

	public void setAfterPwField(JPasswordField afterPwField) {
		this.afterPwField = afterPwField;
	}

	public String getAfterPwCheckField() {
		return afterPwCheckField.getText().trim();
	}

	public void setAfterPwCheckField(JPasswordField afterPwCheckField) {
		this.afterPwCheckField = afterPwCheckField;
	}
	
	
}
