package com.yuting.mobileReader.business.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yuting.mobileReader.action.RemoteSiteInterface;
import com.yuting.mobileReader.action.impl.Site_Paoshu8;
import com.yuting.mobileReader.business.MobileReaderRemoteService;
import com.yuting.mobileReader.model.BookInfo;
import com.yuting.mobileReader.model.Chapter;
import com.yuting.mobileReader.model.Part;
import com.yuting.mobileReader.utils.SystemPropertiesUtils;

public class MobileReaderRemoteServiceImpl implements MobileReaderRemoteService {

	public BookInfo searchBook(String bookTitle, RemoteSiteInterface formatter) throws IOException {
		Document doc = Jsoup.connect(formatter.searchUrl(bookTitle))
				.timeout(SystemPropertiesUtils.MAX_CONNECTION_TIMEOUT).get();
		return formatter.searchBook(bookTitle, doc);
	}

	public List<Part> retrievePartList(String chapterListUrl, RemoteSiteInterface remoteSite) throws IOException {
		List<Part> partList = new LinkedList<Part>();
		chapterListUrl = "http://www.paoshu8.com/Html/Book/5/5361/List.shtm";
		Document doc = Jsoup.connect(chapterListUrl).timeout(SystemPropertiesUtils.MAX_CONNECTION_TIMEOUT).get();
		Element dir = doc.getElementById("BookText");
		Elements partElementList = dir.select("ul");

		Long i = new Long(1);
		for (Element partElement : partElementList) {
			Part part = new Part();
			part.setPartTitle("第" + i.toString() + "卷");
			part.setPartId(i++);

			List<Chapter> chapterList = new LinkedList<Chapter>();
			Elements chapterElementList = partElement.select("a");
			for (Element chapterElement : chapterElementList) {
				String href = chapterElement.attr("href");
				String title = chapterElement.text();

				String tempurl = remoteSite.assembleChapterUrl(chapterListUrl, href);
				Chapter chapter = new Chapter();
				chapter.setChapterTitle(title);
				chapter.setUrl(tempurl);

				chapterList.add(chapter);
			}
			part.setChapterList(chapterList);
			partList.add(part);
		}

		return partList;
	}

	public List<Chapter> retrieveChapters(String chapterListUrl, RemoteSiteInterface remoteSite, Long index,
			boolean withinContent) throws IOException {
		Document doc = null;
		try {
			doc = Jsoup.connect(chapterListUrl).timeout(SystemPropertiesUtils.MAX_CONNECTION_TIMEOUT).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Chapter> chapterList = remoteSite.retrieveChapterList(doc, index);

		if (withinContent) {
			for (Chapter chapter : chapterList) {
				String content = retrieveChapter(chapter.getUrl(), remoteSite).getContent();
				chapter.setContent(content);
			}
		}
		return chapterList;
	}

	public Chapter retrieveChapter(String url, RemoteSiteInterface formatter) throws IOException {
		Document doc = Jsoup.connect(url).get();
		String title = formatter.formatChapterTitle(doc);
		String content = formatter.formatChapterContent(doc);

		return Chapter.assembleChapter(title, content);
	}

	public boolean hasNewChapterForBook(String bookTitle, RemoteSiteInterface formatter) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Chapter> downloadBook(String bookTitle, Long index, RemoteSiteInterface formatter) throws IOException {
		List<Chapter> chapterList = null;
		BookInfo bookInfo = searchBook(bookTitle, formatter);
		if (bookInfo != null) {
			chapterList = retrieveChapters(bookInfo.getChapterListUrl(), Site_Paoshu8.instance(), index, true);
			saveChapterListIntoFile(bookInfo, chapterList);
		}

		return chapterList;
	}

	private File saveChapterListIntoFile(BookInfo bookInfo, List<Chapter> chapterList) throws IOException {
		File file = null;
		if (chapterList != null) {
			file = new File("d:" + File.separator + bookInfo.getBookTitle() + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream outStream = new FileOutputStream(file, true);
			for (Chapter chapter : chapterList) {
				if (chapter != null) {
					StringBuffer sb = new StringBuffer();
					sb.append("No." + chapter.getChapterId() + SystemPropertiesUtils.LINE_SEPARATOR);
					sb.append(chapter.getChapterTitle() + SystemPropertiesUtils.LINE_SEPARATOR);
					sb.append(chapter.getContent() + SystemPropertiesUtils.LINE_SEPARATOR);
					sb.append("----------------------------------------------------------------------"
							+ SystemPropertiesUtils.LINE_SEPARATOR);

					outStream.write(sb.toString().getBytes("GB2312"));
				}
			}

			outStream.close();
		}
		return file;
	}

}
