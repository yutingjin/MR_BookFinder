package com.yuting.mobileReader.business;

import java.io.IOException;
import java.util.List;

import com.yuting.mobileReader.action.RemoteSiteInterface;
import com.yuting.mobileReader.model.BookInfo;
import com.yuting.mobileReader.model.Chapter;

public interface MobileReaderRemoteService {

	public BookInfo searchBook(String bookTitle, RemoteSiteInterface remoteSite) throws IOException;

	public List<Chapter> retrieveChapters(String chapterListUrl, RemoteSiteInterface remoteSite, Long index,
			boolean withinContent) throws IOException;

	public Chapter retrieveChapter(String url, RemoteSiteInterface remoteSite) throws IOException;

	public boolean hasNewChapterForBook(String bookTitle, RemoteSiteInterface remoteSite);

	public List<Chapter> downloadBook(String bookTitle, Long index, RemoteSiteInterface formatter) throws IOException;
}
