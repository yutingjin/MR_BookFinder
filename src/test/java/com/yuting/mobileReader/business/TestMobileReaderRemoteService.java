package com.yuting.mobileReader.business;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.yuting.mobileReader.action.impl.Site_Paoshu8;
import com.yuting.mobileReader.business.impl.MobileReaderRemoteServiceImpl;
import com.yuting.mobileReader.model.BookInfo;
import com.yuting.mobileReader.model.Chapter;
import com.yuting.mobileReader.model.Part;

public class TestMobileReaderRemoteService {

	// private static Logger log =
	// Logger.getLogger(TestMobileReaderRemoteService.class);

	private static MobileReaderRemoteService mobileReaderService;

	@BeforeClass
	public static void setUp() {
		mobileReaderService = new MobileReaderRemoteServiceImpl();
	}

	@Test
	@Ignore
	public void testRetrieveChapter() throws IOException {
		// String url = "http://www.paoshu8.com/Html/Book/5/5361/1177760.shtm";
		String url = "http://www.paoshu8.com/Html/Book/14/14584/4324085.shtm";
		Chapter chapter = mobileReaderService.retrieveChapter(url, new Site_Paoshu8());

		System.out.println(chapter.getChapterTitle());
		System.out.println(chapter.getContent());
	}

	@Test
	@Ignore
	public void testRetrieveChapterList() throws IOException {
		Date startTime = new Date();
		String url = "http://www.paoshu8.com/Html/Book/5/5361/List.shtm";
		List<Chapter> chapterList = mobileReaderService.retrieveChapters(url, Site_Paoshu8.instance(), new Long(10),
				true);
		for (Chapter chapter : chapterList) {
			System.out.println(chapter.getChapterId() + "|" + chapter.getChapterTitle() + "|" + chapter.getUrl());
			System.out.println(chapter.getContent());
		}

		Date endTime = new Date();
		System.out.println("Running Time: [" + (endTime.getTime() - startTime.getTime()) + "]ms");
	}

	@Test
	@Ignore
	public void print() throws IOException {
		List<Part> partList = new LinkedList<Part>();
		// String url =
		// "http://www.paoshu8.com/Html/Book/14/14510/List.shtm";
		String url = "http://www.paoshu8.com/Html/Book/5/5361/List.shtm";
		Document doc = Jsoup.connect(url).timeout(100000).get();
		Element dir = doc.getElementById("BookText");
		Elements partElementList = dir.select("ul");

		Long i = new Long(1);
		for (Element partElement : partElementList) {
			Part part = new Part();
			part.setPartTitle("第" + i.toString() + "卷");
			part.setPartId(i++);

			// List<Chapter> chapterList = new LinkedList<Chapter>();
			// Elements chapterElementList = partElement.select("a");
			// for (Element chapterElement : chapterElementList) {
			// String href = chapterElement.attr("href");
			// // String title = chapterElement.text();
			//
			// SiteInterfaceFormatter formatter = new Site_Paoshu8();
			// String tempurl = formatter.assembleChapterUrl(url, href);
			// Chapter chapter = mobileReaderService.retrieveChapter(tempurl,
			// formatter);
			// // chapter.setChapterTitle(title);
			// chapter.setUrl(href);
			//
			// // System.out.println(href + title);
			// chapterList.add(chapter);
			// //break;
			// }
			// part.setChapterList(chapterList);
			partList.add(part);
			// break;
		}

		System.out.println(doc.title());
		for (Part part : partList) {
			System.out.println(part.getPartId() + part.getPartTitle());
			if (part.getChapterList() != null) {
				for (Chapter chapter : part.getChapterList()) {
					System.out.println(chapter.getChapterTitle() + "\t" + chapter.getUrl());
					System.out.println(chapter.getContent());
				}
			}
		}
	}

	@Test
	@Ignore
	public void testSearchBook() throws IOException {
		BookInfo bookInfo = mobileReaderService.searchBook("寻秦记", Site_Paoshu8.instance());
		// BookInfo bookInfo = mobileReaderService.searchBook("xqj",
		// Site_Paoshu8.instance());
		Assert.assertNotNull(bookInfo);
		System.out.println("Title:" + bookInfo.getBookTitle());
		System.out.println("Author:" + bookInfo.getAuthor());
		System.out.println("URL:" + bookInfo.getChapterListUrl());
		System.out.println("Description:" + bookInfo.getDescription());
	}

	@Test
	public void testDownloadBook() throws IOException {
		mobileReaderService.downloadBook("少年", new Long(10), Site_Paoshu8.instance());
	}
}
