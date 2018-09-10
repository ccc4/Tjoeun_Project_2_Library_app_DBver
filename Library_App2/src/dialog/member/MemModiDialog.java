package dialog.member;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import dto.MemberDTO;

public class MemModiDialog extends JDialog {
	private boolean value = false;
	private int session_idx;
	
	JLabel idLabel,  nicknameLabel, nameLabel, ageLabel, genderLabel, telLabel, emailLabel,
			addressLabel;

	JTextField idField, nicknameField, nameField, ageField, telField, emailField_1;

	JPanel genderPanel;
	ButtonGroup bg = new ButtonGroup();
	JRadioButton maleBtn = new JRadioButton("����", true);
	JRadioButton femaleBtn = new JRadioButton("����");

	JLabel email_golbeng;
	String[] domeins = {"naver.com", "gmail.com"};
	JComboBox emailField_2;

	JTextArea addressArea = new JTextArea();
	JScrollPane addressPane = new JScrollPane(addressArea);

	JButton modiBtn, exitBtn, checkNicknameBtn;
	
	public MemModiDialog(JFrame frame, String title, int session_idx) {
		super(frame, title, true);
		this.session_idx = session_idx;
		
		bg.add(maleBtn);
		bg.add(femaleBtn);
		
		getContentPane().setLayout(null);
		
		add(idLabel = new JLabel("���̵�", JLabel.CENTER));
		idLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		idLabel.setBounds(10, 10, 80, 30);
		add(idField = new JTextField());
		idField.setBounds(95, 10, 100, 30);
		
		add(nicknameLabel = new JLabel("����", JLabel.CENTER));
		nicknameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		nicknameLabel.setBounds(10, 40, 80, 30);
		add(nicknameField = new JTextField());
		nicknameField.setBounds(95, 40, 100, 30);
		add(checkNicknameBtn = new JButton("Ȯ��"));
		checkNicknameBtn.setBounds(200, 40, 60, 30);
		
		add(nameLabel = new JLabel("�̸�", JLabel.CENTER));
		nameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		nameLabel.setBounds(10, 70, 80, 30);
		add(nameField = new JTextField());
		nameField.setBounds(95, 70, 100, 30);
		
		add(ageLabel = new JLabel("����", JLabel.CENTER));
		ageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		ageLabel.setBounds(10, 100, 80, 30);
		add(ageField = new JTextField());
		ageField.setBounds(95, 100, 100, 30);
		
		add(genderLabel = new JLabel("����", JLabel.CENTER));
		genderLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		genderLabel.setBounds(10, 130, 80, 30);
		add(genderPanel = new JPanel());
		maleBtn.setActionCommand("M");
		femaleBtn.setActionCommand("F");
		bg.add(maleBtn);
		bg.add(femaleBtn);
		genderPanel.add(maleBtn);
		genderPanel.add(femaleBtn);
		genderPanel.setBounds(95, 130, 110, 30);
		
		add(telLabel = new JLabel("��ȭ��ȣ", JLabel.CENTER));
		telLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		telLabel.setBounds(10, 160, 80, 30);
		add(telField = new JTextField());
		telField.setBounds(95, 160, 165, 30);
		
		add(emailLabel = new JLabel("�̸���", JLabel.CENTER));
		emailLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		emailLabel.setBounds(10, 190, 80, 30);
		add(emailField_1 = new JTextField());
		emailField_1.setBounds(95, 190, 100, 30);
		add(email_golbeng = new JLabel("@"));
		email_golbeng.setBounds(200, 190, 20, 30);
		add(emailField_2 = new JComboBox(domeins));
		emailField_2.setBounds(95, 220, 165, 30);
		
		add(addressLabel = new JLabel("�ּ�", JLabel.CENTER));
		addressLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		addressLabel.setBounds(10, 250, 80, 30);
		add(addressPane);
		addressPane.setBounds(95, 250, 165, 50);
		
		add(modiBtn = new JButton("����"));
		modiBtn.setBounds(120, 310, 60, 30);
		add(exitBtn = new JButton("���"));
		exitBtn.setBounds(185, 310, 60, 30);
		
		idField.setEditable(false);
		
		setSize(280, 385);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setMemInfo();
		generateEvents();
		
	}
	
	private void setMemInfo() {
		
		Connection conn = GenerateConnection.getConnection(); 
		DAO dao = DAO.getInstance();
		MemberDTO member = dao.getMemberInfo(conn, this.session_idx);
		
		idField.setText(member.getId());
		nicknameField.setText(member.getNickname());
		nameField.setText(member.getName());
		ageField.setText(String.valueOf(member.getAge()));
		
		if(member.getGender().equals("M")) {
			maleBtn.setSelected(true);
		} else if(member.getGender().equals("F")) {
			femaleBtn.setSelected(true);
		}
		
		telField.setText(String.valueOf(member.getTel()));
		emailField_1.setText(member.getEmail_1());
		
		if(member.getEmail_2().equals("naver.com")) {
			emailField_2.setSelectedItem("naver.com");
		} else {
			emailField_2.setSelectedItem("gmail.com");
		}
		
		addressArea.setText(member.getAddress());
		
		DB_Closer.close(conn);
	}

	private void generateEvents() {
		
		checkNicknameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int re = dao.checkNicknameDuplication(conn, getNicknameField());
				if(re == 1) {
					JOptionPane.showMessageDialog(null, "�̹� ������� �����Դϴ�", "ȸ������", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "��� ������ �����Դϴ�", "ȸ������", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		modiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getNicknameField().length() == 0) {
					JOptionPane.showMessageDialog(null, "�г����� �Է����ּ���", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					nicknameField.requestFocus();
					return;
				}
				if(nameField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "�̸��� �Է����ּ���", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					nameField.requestFocus();
					return;
				}
				if(ageField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "���̸� �Է����ּ���", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getAgeField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "���̴� ���ڸ� �Է°����մϴ�", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				if(telField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է����ּ���", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getTelField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "��ȣ�� ���ڸ� �Է°����մϴ�", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				if(getEmailField_1().length() == 0) {
					JOptionPane.showMessageDialog(null, "�̸����ּҸ� �Է����ּ���", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					emailField_1.requestFocus();
					return;
				}
				if(addressArea.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "�ּҸ� �Է����ּ���", "ȸ������ ����", JOptionPane.WARNING_MESSAGE);
					addressArea.requestFocus();
					return;
				}
				int a = JOptionPane.showConfirmDialog(null, "�Է��ϽŴ�� ȸ�� ���� ������ �Ͻðڽ��ϱ�?", "ȸ������ ����", JOptionPane.YES_NO_OPTION);
				if(a != JOptionPane.YES_OPTION) {
					return;
				}
				
				value = true;
				setVisible(false);
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
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

	public String getNicknameField() {
		return nicknameField.getText().trim();
	}

	public void setNicknameField(JTextField nicknameField) {
		this.nicknameField = nicknameField;
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

	public String getEmailField_1() {
		return emailField_1.getText().trim();
	}

	public void setEmailField_1(JTextField emailField_1) {
		this.emailField_1 = emailField_1;
	}

	public String getGender() {
		return bg.getSelection().getActionCommand().trim();
	}

	public String getEmailField_2() {
		return emailField_2.getSelectedItem().toString().trim();
	}

	public void setEmailField_2(JComboBox<String> emailField_2) {
		this.emailField_2 = emailField_2;
	}

	public String getAddressArea() {
		return addressArea.getText().trim();
	}

	public void setAddressArea(JTextArea addressArea) {
		this.addressArea = addressArea;
	}
}
