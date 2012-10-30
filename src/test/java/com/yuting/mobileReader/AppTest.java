package com.yuting.mobileReader;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.yuting.mobileReader.utils.SystemPropertiesUtils;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@BeforeClass
	public static void setUp() {
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
	//@Ignore
	public void testSoap() {

		try {
			// String url =
			// "http://www.paoshu8.com/Html/Book/14/14510/List.shtm";
			String url = "http://www.paoshu8.com/Html/Book/5/5361/List.shtm";
			Document doc = Jsoup.connect(url).get();
			Element dir = doc.getElementById("BookText");
			Elements chapterList = dir.select("ul li");

			for (Element element : chapterList) {
				List<Element> els = element.getElementsByTag("a");
				if (els != null && els.size() > 0) {
					for (Element el : els) {
						String href = el.attr("href");
						String updateDate = element.getElementsByTag("a")
								.get(0).attr("title");
						String title = element.text();
						System.out.println(href + updateDate + title);
					}
				}

			}
			System.out.println(doc.title());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
