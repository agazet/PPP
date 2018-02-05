package com.objectmentor.library;

import java.util.List;

import com.objectmentor.library.data.CatalogIsbnOperations;
import com.objectmentor.library.data.BookWithIsbnDoesNotExist;
import com.objectmentor.library.models.Book;
import com.objectmentor.library.models.BookTitle;

public class Catalog {

  private CatalogIsbnOperations catalogOperations;

  public Catalog(CatalogIsbnOperations operations) {
    this.catalogOperations = operations;
  }

  public Book addToCatalog(String isbn) {
	return addBookIfFound(isbn);
  }

  private Book addBookIfFound(String isbn) {
	  return catalogOperations.addBook(findBookByIsbn(isbn));
  }
  
  private BookTitle findBookByIsbn(String isbn) {
	  BookTitle bookTitle = catalogOperations.findTitleByIsbn(isbn);
	  if (bookTitle == null)
		  throw new BookWithIsbnDoesNotExist();
	  return bookTitle;
  }

  /**
   * Finds the copy by the isbn
   * @param string
   * @return
   */
  public Book find1(String string) {
    return catalogOperations.findCopy1(string);
  }

  /**
   * Using the string as an isbn, finds all
   * copies whether borrowed or in stock
   * @param string
   * @return
   */
  public List findList(String string) {
    return catalogOperations.findMany(string);
  }

  /**
   * How many?
   * @return
   */
  public int getCount() {
    return catalogOperations.countBooks();
  }

  /**
   * Returns true if the string (isbn) passed
   * in can be found in the gateway
   * @param string
   * @return
   */
  public boolean exists(String string) {
    return catalogOperations.canFindCopy(string);
  }

  /**
   * Finds an AVAILABLE copy (i.e. one that exists
   * but is not borrowed)
   * @param string
   * @return
   */
  public Book find2(String string) {
    return catalogOperations.findAvailableCopy(string);
  }

  /**
   * Finds the copy by the id
   * @param string
   * @return
   */
  public Book find3(String string) {
    return catalogOperations.findCopy2(string);
  }
}
