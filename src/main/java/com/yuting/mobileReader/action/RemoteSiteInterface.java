package com.yuting.mobileReader.action;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;

import com.yuting.mobileReader.model.BookInfo;
import com.yuting.mobileReader.model.Chapter;

public interface RemoteSiteInterface {

	public String formatChapterTitle(Document doc);

	public String formatChapterContent(Document doc);

	public String baseUrl();

	public String chapterListUrl();

	public String assembleChapterUrl(String listUrl, String chapterUrl);

	public List<Chapter> retrieveChapterList(Document doc, Long index);

	/**
	 * If no book matched the bookTitle, then return null
	 * 
	 * @param bookTitle
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	public BookInfo searchBook(String bookTitle, Document doc) throws IOException;

	public String searchUrl(String bookTitle);

}
