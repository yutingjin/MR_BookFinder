package com.yuting.mobileReader.business;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.yuting.mobileReader.action.impl.Site_Paoshu8;
import com.yuting.mobileReader.business.impl.MobileReaderRemoteServiceImpl;
import com.yuting.mobileReader.model.Chapter;
import com.yuting.mobileReader.utils.SystemPropertiesUtils;

public class TestMobileReaderService {
	private static MobileReaderRemoteService mobileReaderService;

	@BeforeClass
	public static void setUp() {
		mobileReaderService = new MobileReaderRemoteServiceImpl();
	}

	@Test
	@Ignore
	public void testSystemPropertiesUtils() {
		List<String> set = SystemPropertiesUtils.INVALID_CHAR_REMOVED_FROM_HTML;
		for (String s : set) {
			System.out.println(s);
		}
	}

	@Test
	// @Ignore
	public void testChapter() throws IOException {
		// String url = "http://www.paoshu8.com/Html/Book/5/5361/1177760.shtm";
		String url = "http://www.paoshu8.com/Html/Book/14/14584/4324085.shtm";
		Chapter chapter = mobileReaderService.retrieveChapter(url,
				new Site_Paoshu8());

		System.out.println(chapter.getChapterTitle());
		System.out.println(chapter.getContent());
	}

}
