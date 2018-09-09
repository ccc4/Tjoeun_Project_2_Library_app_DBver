package dialog.member;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MemModDialog extends JDialog {
private boolean value = false;
	
	JPanel botPanel;
	JButton modBtn;
	JButton cancleBtn;
	
	JPanel centerPanel;
	JPanel center_A_Panel;
	JPanel center_B_Panel;
	
	JPanel center_A_LabelPanel;
	JLabel idLabel;
	JLabel nameLabel;
	JLabel ageLabel;
	JLabel telLabel;
	
	JPanel center_A_FieldPanel;
	JTextField idField;
	JTextField nameField;
	JTextField ageField;
	JTextField telField;
	
	JLabel addressLabel;
	JTextArea addressArea;
	JScrollPane addressPane;
	
	public MemModDialog(JFrame frame, String title) {
		super(frame, title, true);
		
		botPanel = new JPanel(new FlowLayout());
		modBtn = new JButton("Modify");
		cancleBtn = new JButton("Exit");
		botPanel.add(modBtn);
		botPanel.add(cancleBtn);
		
		center_A_LabelPanel = new JPanel(new GridLayout(4, 1));
		idLabel = new JLabel("ID");
		idLabel.setSize(200,30);
		nameLabel = new JLabel("Name");
		ageLabel = new JLabel("Age");
		telLabel = new JLabel("Tel");
		center_A_LabelPanel.add(idLabel);
		center_A_LabelPanel.add(nameLabel);
		center_A_LabelPanel.add(ageLabel);
		center_A_LabelPanel.add(telLabel);
		
		center_A_FieldPanel = new JPanel(new GridLayout(4, 1));
		idField = new JTextField();
		nameField = new JTextField();
		ageField = new JTextField();
		telField = new JTextField();
		center_A_FieldPanel.add(idField);
		center_A_FieldPanel.add(nameField);
		center_A_FieldPanel.add(ageField);
		center_A_FieldPanel.add(telField);
		
		addressLabel = new JLabel("Address");
		addressLabel.setSize(300, 100);
		addressArea = new JTextArea();
		addressPane = new JScrollPane(addressArea);
		
		center_A_Panel = new JPanel(new BorderLayout());
		center_A_Panel.add(center_A_LabelPanel, BorderLayout.WEST);
		center_A_Panel.add(center_A_FieldPanel, BorderLayout.CENTER);
		center_B_Panel = new JPanel(new BorderLayout());
		center_B_Panel.add(addressLabel, BorderLayout.WEST);
		center_B_Panel.add(addressPane, BorderLayout.CENTER);
		
		centerPanel = new JPanel();
		centerPanel.setLayout((new BoxLayout(centerPanel, BoxLayout.Y_AXIS)));
		centerPanel.add(center_A_Panel);
		centerPanel.add(center_B_Panel);
		
		getContentPane().setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(botPanel, BorderLayout.SOUTH);
		
		this.setSize(300, 330);
		this.setLocationRelativeTo(null);
		
		this.idField.setEditable(false);
		
		this.modBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nameField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					nameField.requestFocus();
					return;
				}
				if(ageField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "나이를 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getAgeField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "나이는 숫자만 입력가능합니다", "ERROR", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				if(telField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getTelField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "번호는 숫자만 입력가능합니다", "ERROR", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				if(addressArea.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "주소를 입력해주세요", "ERROR", JOptionPane.WARNING_MESSAGE);
					addressArea.requestFocus();
					return;
				}
				int a = JOptionPane.showConfirmDialog(null, "입력하신대로 회원 정보 수정을 하시겠습니까?", "CONFIRM", JOptionPane.YES_NO_OPTION);
				if(a != JOptionPane.YES_OPTION) {
					return;
				}
				
				value = true;
				setVisible(false);
			}
		});
		
		this.cancleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				value = false;
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

	public String getNameField() {
		return nameField.getText().trim();
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	public String getAgeField() {
		return ageField.getText().trim();
	}

	public void setAgeField(JTextField ageField) {
		this.ageField = ageField;
	}

	public String getTelField() {
		return telField.getText().trim();
	}

	public void setTelField(JTextField telField) {
		this.telField = telField;
	}

	public String getAddressArea() {
		return addressArea.getText().trim();
	}

	public void setAddressArea(JTextArea addressArea) {
		this.addressArea = addressArea;
	}
}
