package com.reynke.sloud.databaseutilities.entity;

import com.reynke.sloud.databaseutilities.repository.AuthorRepository;
import com.reynke.sloud.databaseutilities.repository.Repository;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Entity
@Table(name = "authors")
@Repository(type = AuthorRepository.class)
public class Author implements IEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(targetEntity = Book.class)
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Collection<Book> books;

    public Author() {
        books = new LinkedHashSet<>();
    }

    @Override
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }
}
