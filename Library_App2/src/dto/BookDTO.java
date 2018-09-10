package dto;

import java.sql.Timestamp;

public class BookDTO {
	private int idx;
	private String title;
	private String author;
	private String publisher;
	private String imgName;
	private Timestamp addDate;
	
	public BookDTO(int idx, String title, String author, String publisher, String imgName, Timestamp addDate) {
		this.idx = idx;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.imgName = imgName;
		this.addDate = addDate;
	}
	
	public BookDTO(int idx, String title, String author, String publisher) {
		this.idx = idx;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}
	
}
