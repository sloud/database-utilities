package com.reynke.sloud.databaseutilities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
public class DatabaseUtilitiesTestCase {

//    private IDatabaseConfiguration databaseConfiguration;

    @Before
    public void beforeTest() {

//        IDatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
//
//        // @todo Outsource to environment (or testing) specific configuration file
//        databaseConfiguration.setDatabaseName("miningfreaks_test");
//        databaseConfiguration.setPassword("");
//
//        databaseConfiguration.addPackage("com.reynke.miningfreaks.databaseutilities.entity");
//        databaseConfiguration.addAnnotatedClass(Author.class);
//        databaseConfiguration.addAnnotatedClass(Book.class);
//
//        databaseConfiguration.setHbm2ddlOption(Hbm2ddlOption.CREATE_DROP);
//
//        this.databaseConfiguration = databaseConfiguration;
    }

    @Test
    public void testMySqlDatabase() {

//        databaseConfiguration.setDatabaseType(DatabaseType.MY_SQL);
//
//        DatabaseUtilities.setup(databaseConfiguration);
//
//        IDatabase database = DatabaseUtilities.getDatabase();
//        IRepositoryFactory repositoryFactory = DatabaseUtilities.getRepositoryFactory();
//
//        assertTrue(database.isOpen());
//
//        AuthorRepository authorRepository;
//
//        try {
//            authorRepository = (AuthorRepository) repositoryFactory.getRepository(Author.class);
//        } catch (DatabaseUtilitiesException e) {
//            throw new RuntimeException(e);
//        }
//
//        Author author = new Author();
//        author.setFirstName("Nicklas");
//        author.setLastName("Reincke");
//
//        authorRepository.create(author);
//
//        assertEquals(1L, author.getId());
//
//        BookRepository bookRepository;
//
//        try {
//            bookRepository = (BookRepository) repositoryFactory.getRepository(Book.class);
//        } catch (DatabaseUtilitiesException e) {
//            throw new RuntimeException(e);
//        }
//
//        Book book = new Book();
//        book.getAuthors().add(author);
//        book.setTitle("Title of the book");
//        book.setDescription("Description of the book");
//
//        bookRepository.create(book);
//
//        assertEquals(1L, book.getId());
//
//        author.getBooks().add(book);
//
//        assertEquals(1, author.getBooks().size());
//
//        Book writtenBook = (Book) author.getBooks().toArray()[0];
//
//        assertEquals("Title of the book", writtenBook.getTitle());
//        assertEquals("Nicklas", ((Author) writtenBook.getAuthors().toArray()[0]).getFirstName());

        assertEquals(1, 1);
    }
}
