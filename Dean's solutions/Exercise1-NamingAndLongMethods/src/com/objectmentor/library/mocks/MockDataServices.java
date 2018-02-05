package com.objectmentor.library.mocks;

import com.objectmentor.library.models.*;
import com.objectmentor.library.data.CatalogIsbnOperations;

import java.util.*;

public class MockDataServices implements CatalogIsbnOperations {
  public Book added;
  private Map map = new HashMap();
  private static long lastId = 0;
  Map patronMap = new HashMap();
  public String wasLastCalledWithThisIsbn;
  Map titleMap = new HashMap();

  public Book addBook(BookTitle string) {
    Book c = new Book(string, "" + (++lastId));
    String isbn = string.getIsbn();
    List copies2 = (List) map.get(isbn);
    if (copies2 == null) {
      copies2 = new LinkedList();
      map.put(isbn, copies2);
    }
    copies2.add(c);
    added = c;
    return c;
  }

  public Book findCopy1(String string) {
    List copies2 = (List) map.get(string);
    if (copies2 != null)
      return (Book) copies2.get(0);
    else
      return null;
  }

  public int countBooks() {
    return map.size();
  }

  public List findMany(String isbn) {
    List copies2 = (List) map.get(isbn);
    if (copies2 == null)
      return new ArrayList();
    return copies2;
  }

  public boolean canFindCopy(String string) {
    return map.containsKey(string);
  }

  public Book findAvailableCopy(String isbn) {
    List copies2 = findMany(isbn);
    for (int i = 0; i < copies2.size(); i++) {
      Book c = (Book) copies2.get(i);
      if (!c.isBorrowed())
        return c;
    }
    return null;
  }

  public Book findCopy2(String copyId) {
    Collection copies2 = map.values();
    for (Iterator i = copies2.iterator(); i.hasNext();) {
      List copies3 = (List) i.next();
      for (int j = 0; j < copies3.size(); j++) {
        Book c = (Book) copies3.get(j);
        if (c.getId().equals(copyId))
          return c;
      }
    }
    return null;
  }

  public int countActivePatrons() {
    return patronMap.size();
  }

  public void addPatron(Patron p) {
    patronMap.put(p.getId(), p);
  }

  public Patron findPatron(String string) {
    return (Patron) patronMap.get(string);
  }

  public void setBookToReturn(BookTitle t) {
    titleMap.put(t.getIsbn(), t);
  }

  public BookTitle findTitleByIsbn(String string) {
    wasLastCalledWithThisIsbn = string;
    return (BookTitle) titleMap.get(string);
  }
}
