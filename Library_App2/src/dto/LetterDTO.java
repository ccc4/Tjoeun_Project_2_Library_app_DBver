package dto;

import java.sql.Timestamp;

public class LetterDTO {
	
	int l_idx;
	String title;
	String contents;
	int senderIdx;
	String senderNickname;
	int receiverIdx;
	String receiverNickname;
	Timestamp sendDate;
	Timestamp readDate;
	
	public LetterDTO(int l_idx, String title, String contents, int senderIdx, String senderNickname, int receiverIdx,
			String receiverNickname, Timestamp sendDate, Timestamp readDate) {
		super();
		this.l_idx = l_idx;
		this.title = title;
		this.contents = contents;
		this.senderIdx = senderIdx;
		this.senderNickname = senderNickname;
		this.receiverIdx = receiverIdx;
		this.receiverNickname = receiverNickname;
		this.sendDate = sendDate;
		this.readDate = readDate;
	}

	public LetterDTO(int l_idx, String title, String contents,String senderNickname, String receiverNickname, Timestamp sendDate, Timestamp readDate) {
		this.l_idx = l_idx;
		this.title = title;
		this.contents = contents;
		this.senderNickname = senderNickname;
		this.receiverNickname = receiverNickname;
		this.sendDate = sendDate;
		this.readDate = readDate;
	}
	
	

	public int getL_idx() {
		return l_idx;
	}

	public void setL_idx(int l_idx) {
		this.l_idx = l_idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getSenderIdx() {
		return senderIdx;
	}

	public void setSenderIdx(int senderIdx) {
		this.senderIdx = senderIdx;
	}

	public String getSenderNickname() {
		return senderNickname;
	}

	public void setSenderNickname(String senderNickname) {
		this.senderNickname = senderNickname;
	}

	public int getReceiverIdx() {
		return receiverIdx;
	}

	public void setReceiverIdx(int receiverIdx) {
		this.receiverIdx = receiverIdx;
	}

	public String getReceiverNickname() {
		return receiverNickname;
	}

	public void setReceiverNickname(String receiverNickname) {
		this.receiverNickname = receiverNickname;
	}

	public Timestamp getSendDate() {
		return sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public Timestamp getReadDate() {
		return readDate;
	}

	public void setReadDate(Timestamp readDate) {
		this.readDate = readDate;
	}
	
}
