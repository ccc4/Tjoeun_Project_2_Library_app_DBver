package dto;

import java.sql.Timestamp;

public class MemberDTO {
	private int idx;
	private String id;
	private String pw;
	private String nickname;
	private String name;
	private int age;
	private String gender;
	private int tel;
	private String email_1;
	private String email_2;
	private String address;
	private Timestamp joinDate;
	
	public MemberDTO(int idx, String id, String pw, String nickname, String name, int age, String gender, int tel, String email_1,
			String email_2, String address, Timestamp joinDate) {
		this.idx = idx;
		this.id = id;
		this.pw = pw;
		this.nickname = nickname;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.tel = tel;
		this.email_1 = email_1;
		this.email_2 = email_2;
		this.address = address;
		this.joinDate = joinDate;
	}

	public MemberDTO(int idx, String nickname) {
		this.idx = idx;
		this.nickname = nickname;
	}

	
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public String getEmail_1() {
		return email_1;
	}

	public void setEmail_1(String email_1) {
		this.email_1 = email_1;
	}

	public String getEmail_2() {
		return email_2;
	}

	public void setEmail_2(String email_2) {
		this.email_2 = email_2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Timestamp joinDate) {
		this.joinDate = joinDate;
	}
}
