package dialog.member;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class MemJoinDialog extends JDialog {
private boolean value = false;
	
	JLabel idLabel, pwLabel, pw2Label, nicknameLabel, nameLabel, 
		ageLabel, genderLabel, telLabel, emailLabel, addressLabel;
	
	JTextField idField, pwField, pw2Field, nicknameField, nameField, 
		ageField, telField, emailField_1;
	
	JPanel genderPanel;
	ButtonGroup bg = new ButtonGroup();
	JRadioButton maleBtn = new JRadioButton("남자", true);
	JRadioButton femaleBtn = new JRadioButton("여자");
	
	
	JLabel email_golbeng;
	String[] domeins = {"naver.com", "gamil.com"};
	JComboBox<String> emailField_2 = new JComboBox(domeins);

	JTextArea addressArea = new JTextArea();
	JScrollPane addressPane = new JScrollPane(addressArea);
	
	JButton joinBtn, exitBtn;
	
	
	
	public MemJoinDialog(JFrame frame, String title) {
		super(frame, title, true);
		
		bg.add(maleBtn);
		bg.add(femaleBtn);
		
		getContentPane().setLayout(null);
		
		add(idLabel = new JLabel("아이디"));
//		idLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		idLabel.setBounds(10, 10, 80, 30);
		add(idField = new JTextField());
		idField.setBounds(95, 10, 150, 30);
		
		add(pwLabel = new JLabel("비밀번호"));
//		pwLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		pwLabel.setBounds(10, 40, 80, 30);
		add(pwField = new JPasswordField());
		pwField.setBounds(95, 40, 150, 30);
		
		add(pw2Label = new JLabel("비밀번호확인"));
//		pw2Label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		pw2Label.setBounds(10, 70, 80, 30);
		add(pw2Field = new JPasswordField());
		pw2Field.setBounds(95, 70, 150, 30);
		
		add(nicknameLabel = new JLabel("별명"));
//		nicknameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		nicknameLabel.setBounds(10, 100, 80, 30);
		add(nicknameField = new JTextField());
		nicknameField.setBounds(95, 100, 150, 30);
		
		add(nameLabel = new JLabel("이름"));
//		nameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		nameLabel.setBounds(10, 130, 80, 30);
		add(nameField = new JTextField());
		nameField.setBounds(95, 130, 150, 30);
		
		add(ageLabel = new JLabel("나이"));
//		ageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		ageLabel.setBounds(10, 160, 80, 30);
		add(ageField = new JTextField());
		ageField.setBounds(95, 160, 50, 30);
		
		add(genderLabel = new JLabel("성별"));
//		genderLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		genderLabel.setBounds(10, 190, 80, 30);
		add(genderPanel = new JPanel());
		maleBtn.setActionCommand("M");
		femaleBtn.setActionCommand("F");
		bg.add(maleBtn);
		bg.add(femaleBtn);
		genderPanel.add(maleBtn);
		genderPanel.add(femaleBtn);
		genderPanel.setBounds(95, 190, 130, 30);
		
		add(telLabel = new JLabel("전화번호"));
//		telLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		telLabel.setBounds(10, 220, 80, 30);
		add(telField = new JTextField());
		telField.setBounds(95, 220, 150, 30);
		
		add(emailLabel = new JLabel("이메일"));
//		emailLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		emailLabel.setBounds(10, 250, 80, 30);
		add(emailField_1 = new JTextField());
		emailField_1.setBounds(95, 250, 100, 30);
		add(email_golbeng = new JLabel("@"));
		email_golbeng.setBounds(200, 250, 20, 30);
		add(emailField_2 = new JComboBox<>(domeins));
		emailField_2.setBounds(95, 280, 150, 30);
		
		add(addressLabel = new JLabel("주소"));
//		addressLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		addressLabel.setBounds(10, 310, 80, 30);
		add(addressPane);
		addressPane.setBounds(95, 310, 150, 50);
		
		add(joinBtn = new JButton("가입"));
		joinBtn.setBounds(120, 370, 60, 30);
		add(exitBtn = new JButton("취소"));
		exitBtn.setBounds(185, 370, 60, 30);
		
		
		
		setSize(270, 445);
		setLocationRelativeTo(null);
		setResizable(false);
		
		generateEvents();
	}
	
	private void generateEvents() {
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(getIdField().length() == 0) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					idField.requestFocus();
					return;
				}
				if(getPwField().length() == 0) {
					JOptionPane.showMessageDialog(null, "패스워드를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					pwField.requestFocus();
					return;
				}
				if(!pw2Field.getText().trim().equals(getPwField())) {
					JOptionPane.showMessageDialog(null, "패스워드 재입력 확인", "회원가입", JOptionPane.WARNING_MESSAGE);
					pw2Field.requestFocus();
					return;
				}
				if(getNicknameField().length() == 0) {
					JOptionPane.showMessageDialog(null, "닉네임을 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					nicknameField.requestFocus();
					return;
				}
				if(getNameField().length() == 0) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					nameField.requestFocus();
					return;
				}
				if(getAgeField().length() == 0) {
					JOptionPane.showMessageDialog(null, "나이를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getAgeField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "나이는 숫자만 입력가능합니다", "회원가입", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				if(getTelField().length() == 0) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getTelField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "번호는 숫자만 입력가능합니다", "회원가입", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				if(getEmailField_1().length() == 0) {
					JOptionPane.showMessageDialog(null, "이메일주소를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					emailField_1.requestFocus();
					return;
				}
				if(getAddressArea().length() == 0) {
					JOptionPane.showMessageDialog(null, "주소를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
					addressArea.requestFocus();
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

	public String getPwField() {
		return pwField.getText().trim();
	}

	public void setPwField(JTextField pwField) {
		this.pwField = pwField;
	}

	public String getPw2Field() {
		return pw2Field.getText().trim();
	}

	public void setPw2Field(JTextField pw2Field) {
		this.pw2Field = pw2Field;
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

//	public void setBg(ButtonGroup bg) {
//		this.bg = bg;
//	}

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
