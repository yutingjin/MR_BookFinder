package com.yuting.mobileReader.model;


public class Chapter {

	private Long chapterId;

	private String chapterTitle;

	private String content;

	private String url;

	public static Chapter assembleChapter(String title, String content) {
		Chapter chapter = new Chapter();
		chapter.setContent(content);
		chapter.setChapterTitle(title);

		return chapter;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
