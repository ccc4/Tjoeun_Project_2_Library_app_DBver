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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.DAO;
import db.util.DB_Closer;
import db.util.GenerateConnection;
import dto.MemberDTO;
import frame.Home;

public class MemModiDialog extends JDialog {
	
	private boolean value = false;
	
	JLabel idLabel,  nicknameLabel, nameLabel, ageLabel, genderLabel, telLabel, emailLabel,
			addressLabel;

	JTextField idField, nicknameField, nameField, ageField, telField, emailField_1;

	JPanel genderPanel;
	ButtonGroup bg = new ButtonGroup();
	JRadioButton maleBtn = new JRadioButton("남자", true);
	JRadioButton femaleBtn = new JRadioButton("여자");

	JLabel email_golbeng;
	String[] domeins = {"naver.com", "gmail.com"};
	JComboBox emailField_2;

	JTextArea addressArea = new JTextArea();
	JScrollPane addressPane = new JScrollPane(addressArea);

	JButton modiBtn, exitBtn, checkNicknameBtn;
	
	public MemModiDialog(Home frame, String title) {
		super(frame, title, true);
		
		bg.add(maleBtn);
		bg.add(femaleBtn);
		
		getContentPane().setLayout(null);
		
		add(idLabel = new JLabel("아이디"));
		idLabel.setBounds(10, 0, 80, 30);
		add(idField = new JTextField());
		idField.setBounds(95, 0, 100, 30);
		
		add(nicknameLabel = new JLabel("별명"));
//		nicknameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		nicknameLabel.setBounds(10, 30, 80, 30);
		add(nicknameField = new JTextField());
		nicknameField.setBounds(95, 30, 100, 30);
		add(checkNicknameBtn = new JButton("확인"));
		checkNicknameBtn.setBounds(200, 30, 60, 30);
		
		add(nameLabel = new JLabel("이름"));
//		nameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		nameLabel.setBounds(10, 60, 80, 30);
		add(nameField = new JTextField());
		nameField.setBounds(95, 60, 100, 30);
		
		add(ageLabel = new JLabel("나이"));
//		ageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		ageLabel.setBounds(10, 90, 80, 30);
		add(ageField = new JTextField());
		ageField.setBounds(95, 90, 100, 30);
		
		add(genderLabel = new JLabel("성별"));
//		genderLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		genderLabel.setBounds(10, 120, 80, 30);
		add(genderPanel = new JPanel());
		bg.add(maleBtn);
		bg.add(femaleBtn);
		genderPanel.add(maleBtn);
		genderPanel.add(femaleBtn);
		genderPanel.setBounds(95, 120, 120, 30);
		
		add(telLabel = new JLabel("전화번호"));
//		telLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		telLabel.setBounds(10, 150, 80, 30);
		add(telField = new JTextField());
		telField.setBounds(95, 150, 165, 30);
		
		add(emailLabel = new JLabel("이메일"));
//		emailLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		emailLabel.setBounds(10, 180, 80, 30);
		add(emailField_1 = new JTextField());
		emailField_1.setBounds(95, 180, 100, 30);
		add(email_golbeng = new JLabel("@"));
		email_golbeng.setBounds(200, 180, 20, 30);
		add(emailField_2 = new JComboBox(domeins));
		emailField_2.setBounds(95, 210, 165, 30);
		
		add(addressLabel = new JLabel("주소"));
//		addressLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		addressLabel.setBounds(10, 240, 80, 30);
		add(addressPane);
		addressPane.setBounds(95, 240, 165, 50);
		
		add(modiBtn = new JButton("수정"));
		modiBtn.setBounds(120, 300, 60, 30);
		add(exitBtn = new JButton("취소"));
		exitBtn.setBounds(185, 300, 60, 30);
		
		idField.setEditable(false);
		
		setSize(280, 370);
		setLocationRelativeTo(null);
		setResizable(false);
		
		generateEvents();
		
	}

	private void generateEvents() {
		
		checkNicknameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getNicknameField().length() == 0) {
					JOptionPane.showMessageDialog(null, "별명을 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					nicknameField.requestFocus();
					return;
				}
				Connection conn = GenerateConnection.getConnection();
				DAO dao = DAO.getInstance();
				
				int re = dao.checkNicknameDuplication(conn, getNicknameField());
				if(re == 1) {
					JOptionPane.showMessageDialog(null, "이미 사용중인 별명입니다", "회원가입", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "사용 가능한 별명입니다", "회원가입", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		modiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getNicknameField().length() == 0) {
					JOptionPane.showMessageDialog(null, "별명을 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					nicknameField.requestFocus();
					return;
				}
				if(nameField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					nameField.requestFocus();
					return;
				}
				if(ageField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "나이를 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getAgeField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "나이는 숫자만 입력가능합니다", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					ageField.requestFocus();
					return;
				}
				if(telField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "전화번호를 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				try {
					int re = Integer.parseInt(getTelField());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "번호는 숫자만 입력가능합니다", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					telField.requestFocus();
					return;
				}
				if(getEmailField_1().length() == 0) {
					JOptionPane.showMessageDialog(null, "이메일주소를 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					emailField_1.requestFocus();
					return;
				}
				if(addressArea.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(null, "주소를 입력해주세요", "회원정보 수정", JOptionPane.WARNING_MESSAGE);
					addressArea.requestFocus();
					return;
				}
				int a = JOptionPane.showConfirmDialog(null, "입력하신대로 회원 정보 수정을 하시겠습니까?", "회원정보 수정", JOptionPane.YES_NO_OPTION);
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

	public void setIdField(String str) {
		this.idField.setText(str);
	}

	public String getNicknameField() {
		return nicknameField.getText().trim();
	}

	public void setNicknameField(String str) {
		this.nicknameField.setText(str);
	}

	public String getNameField() {
		return nameField.getText().trim();
	}

	public void setNameField(String str) {
		this.nameField.setText(str);
	}

	public String getAgeField() {
		return ageField.getText().trim();
	}

	public void setAgeField(int i) {
		this.ageField.setText(String.valueOf(i));
	}

	public String getTelField() {
		return telField.getText().trim();
	}

	public void setTelField(int i) {
		this.telField.setText(String.valueOf(i));
	}

	public String getEmailField_1() {
		return emailField_1.getText().trim();
	}

	public void setEmailField_1(String str) {
		this.emailField_1.setText(str);
	}

	public String getGender() {
		String gender = "M";
		if(this.femaleBtn.isSelected()) gender = "F";
		return gender;
	}

	public JComboBox<String> getEmailField_2_List() {
		return emailField_2; 
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

	public void setAddressArea(String str) {
		this.addressArea.setText(str);
	}

	public JRadioButton getMaleBtn() {
		return maleBtn;
	}

	public void setMaleBtn(JRadioButton maleBtn) {
		this.maleBtn = maleBtn;
	}

	public JRadioButton getFemaleBtn() {
		return femaleBtn;
	}

	public void setFemaleBtn(JRadioButton femaleBtn) {
		this.femaleBtn = femaleBtn;
	}
	
	
}
