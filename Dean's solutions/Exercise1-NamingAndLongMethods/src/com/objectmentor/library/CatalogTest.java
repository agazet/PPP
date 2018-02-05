package com.objectmentor.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.objectmentor.library.data.BookWithIsbnDoesNotExist;
import com.objectmentor.library.mocks.MockDataServices;
import com.objectmentor.library.models.Book;
import com.objectmentor.library.models.BookTitle;
import com.objectmentor.library.models.Patron;
import com.objectmentor.library.models.Receipt;

public class CatalogTest {

	Catalog catalog;
	MockDataServices mockData;
	
	@Before
	public void setUp() {
		mockData = new MockDataServices();
		catalog = new Catalog(mockData);
	}
	
	@Test
	public void testCatalog() {
		// one book test
		mockData.setBookToReturn(new BookTitle("ISBN"));
		Book b1 = catalog.addToCatalog("ISBN");
		assertEquals("ISBN", mockData.wasLastCalledWithThisIsbn);
		assertEquals(mockData.added.getTitle(), b1.getTitle());
		assertNull(catalog.find1("NOT ISBN"));
		assertNotNull(catalog.find1("ISBN"));
		assertTrue(catalog.exists("ISBN"));
		assertFalse(catalog.exists("NOT ISBN"));
		assertNotNull(catalog.find2("ISBN"));
		// multiple books
		mockData.setBookToReturn(new BookTitle("ISBN 2"));
		Book b2 = catalog.addToCatalog("ISBN 2");
		assertSame(b1, catalog.find1("ISBN"));
		assertSame(b2, catalog.find1("ISBN 2"));
		// borrow one of one
		List copies = catalog.findList("ISBN 2");
		Book bb = (Book) copies.get(0);
		bb.setBorrowed(new Receipt(new Patron("borrower")));
		assertEquals(null, catalog.find2("ISBN 2"));
		// non-existant
		try {
			catalog.addToCatalog("NON-EXISTENT ISBN");
			fail();
		} catch (BookWithIsbnDoesNotExist e) {
		}
		// multiple copies
		Book b1_2 = catalog.addToCatalog("ISBN");
		List cl = catalog.findList("ISBN");
		assertEquals(2, cl.size());
		assertTrue(cl.contains(b1));
		assertTrue(cl.contains(b1_2));
		// borrow one of many
		bb = (Book) cl.get(0);
		bb.setBorrowed(new Receipt(new Patron("borrower")));
		assertNotNull(catalog.find2("ISBN"));
	}

	public void oneBook_testOk() {
		Book b1 = catalog.addToCatalog("ISBN");
		assertEquals("ISBN", mockData.wasLastCalledWithThisIsbn);
		assertEquals(mockData.added.getTitle(), b1.getTitle());
		assertNull(catalog.find1("NOT ISBN"));
		assertNotNull(catalog.find1("ISBN"));
		assertTrue(catalog.exists("ISBN"));
		assertFalse(catalog.exists("NOT ISBN"));
		assertNotNull(catalog.find2("ISBN"));
	}

	@Test
	public void emptyCatalog_hasNoBooks() {
		assertFalse(catalog.exists("book"));
		assertNull(catalog.find2("book"));
	}

	
	@Test
	public void creates_emptyCatalog() {
		assertEquals("expected empty catalog", 0, catalog.getCount());
	}
}
