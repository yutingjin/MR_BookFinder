package com.yuting.mobileReader.business;

import java.util.List;

import com.yuting.mobileReader.model.BookInfo;
import com.yuting.mobileReader.model.Chapter;

public interface MobileReaderService {

	public BookInfo searchBook(String bookTitle);

	public List<String> retrieveChapterTitleList(String bookUrl);

	public List<Chapter> retrieveChapterList(String bookTitle);

	public List<String> retrieveChapterNameList(String bookTitle);

	public Chapter retrieveChapter(String bookTitle, Long chapterId);

	public boolean hasNewChapterForBook(String bookTitle);

	public String downloadNewChaptersFromSite(String bookTitle);
}
