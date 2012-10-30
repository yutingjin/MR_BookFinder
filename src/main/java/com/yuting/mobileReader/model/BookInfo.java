package com.yuting.mobileReader.model;

public class BookInfo {

	private String bookId;

	private String bookTitle;

	private String author;

	private String description;

	private Long totalChapterNumber;

	private Long localChapterNumber;

	private String lastUpdateDate;

	private String chapterListUrl;

	private String bookUrl;

	public boolean hasNewChapter() {
		if (totalChapterNumber != null && localChapterNumber != null) {
			return totalChapterNumber > localChapterNumber;
		}
		return false;
	}

	public String getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}

	public String getChapterListUrl() {
		return chapterListUrl;
	}

	public void setChapterListUrl(String url) {
		this.chapterListUrl = url;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTotalChapterNumber() {
		return totalChapterNumber;
	}

	public void setTotalChapterNumber(Long totalChapterNumber) {
		this.totalChapterNumber = totalChapterNumber;
	}

	public Long getLocalChapterNumber() {
		return localChapterNumber;
	}

	public void setLocalChapterNumber(Long localChapterNumber) {
		this.localChapterNumber = localChapterNumber;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
