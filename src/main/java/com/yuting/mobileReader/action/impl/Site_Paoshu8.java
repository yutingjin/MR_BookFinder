package com.yuting.mobileReader.action.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.yuting.mobileReader.action.RemoteSiteInterface;
import com.yuting.mobileReader.model.BookInfo;
import com.yuting.mobileReader.model.Chapter;
import com.yuting.mobileReader.utils.SystemPropertiesUtils;

public class Site_Paoshu8 implements RemoteSiteInterface {

	private static String searchUrl = "http://www.paoshu8.com/Book/Search.aspx?searchkey=";

	private static String searchClass = "&SearchClass=1";

	private static Site_Paoshu8 instance = new Site_Paoshu8();

	public static RemoteSiteInterface instance() {
		if (instance == null) {
			instance = new Site_Paoshu8();
		}

		return instance;
	}

	public String searchUrl(String bookTitle) {
		String encodeStr = null;
		try {
			encodeStr = URLEncoder.encode(bookTitle, "GB2312");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return searchUrl + encodeStr + searchClass;
	}

	public String formatChapterTitle(Document doc) {
		Element title = doc.getElementById("TextTitle");
		return title.text();
	}

	public String formatChapterContent(Document doc) {
		Element content = doc.getElementById("BookText");
		return transferContentToString(content);
	}

	private String transferContentToString(Element content) {
		Element text = content.select("div").get(0);
		int size = text.childNodes().size();
		Node childNode = text.childNode(size - 1);
		childNode.remove();

		String contentStr = content.html();
		for (String str : SystemPropertiesUtils.INVALID_CHAR_REMOVED_FROM_HTML) {
			contentStr = StringUtils.remove(contentStr, str);
		}

		return contentStr;
	}

	public String baseUrl() {
		return "http://www.paoshu8.com/";
	}

	public String chapterListUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public String assembleChapterUrl(String listUrl, String chapterUrl) {
		return StringUtils.replace(listUrl, "List.shtm", chapterUrl);
	}

	public List<Chapter> retrieveChapterList(Document doc, Long index) {
		List<Chapter> chapterList = new LinkedList<Chapter>();

		String baseUrl = doc.baseUri();
		Element dir = doc.getElementById("BookText");
		Elements chapterElementList = dir.select("ul li a");

		Long chapterId = new Long(0);
		for (Element chapterElement : chapterElementList) {
			if (index == null || chapterId < index) {
				String href = chapterElement.attr("href");
				String title = chapterElement.text();

				String chapterUrl = assembleChapterUrl(baseUrl, href);
				Chapter chapter = new Chapter();
				chapter.setChapterId(chapterId++);
				chapter.setChapterTitle(title);
				chapter.setUrl(chapterUrl);

				chapterList.add(chapter);
			}
		}

		return chapterList;
	}

	public BookInfo searchBook(String bookTitle, Document doc) throws IOException {
		Assert.assertNotNull("Book Title is required.", bookTitle);
		BookInfo bookInfo = null;

		Element bookInfoElement = doc.getElementById("CListTitle");
		if (bookInfoElement != null) {
			bookInfo = new BookInfo();
			Elements targets = bookInfoElement.select("a");
			if (targets != null && targets.size() > 2) {
				bookInfo.setBookTitle(targets.get(0).text());
				bookInfo.setBookUrl(baseUrl() + targets.get(0).attr("href"));
				bookInfo.setAuthor(targets.get(1).text());
			}

			Element descElement = doc.getElementById("CListText");
			if (descElement != null) {
				String description = descElement.text();
				bookInfo.setDescription(description);
			}

			Document chapterListDoc = null;
			chapterListDoc = Jsoup.connect(bookInfo.getBookUrl()).timeout(SystemPropertiesUtils.MAX_CONNECTION_TIMEOUT)
					.get();
			Elements chapterListUrlElements = chapterListDoc.getElementsByAttributeValueContaining("href",
					"/Html/Book/");
			if (chapterListUrlElements != null && chapterListUrlElements.size() > 0) {
				String chapterListUrl = chapterListUrlElements.get(0).attr("href");
				bookInfo.setChapterListUrl(baseUrl() + chapterListUrl);
			}

		}

		return bookInfo;
	}

}
