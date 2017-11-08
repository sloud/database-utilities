package com.reynke.sloud.databaseutilities.entity;

import com.reynke.sloud.databaseutilities.repository.BookRepository;
import com.reynke.sloud.databaseutilities.repository.Repository;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Nicklas Reincke (contact@reynke.com)
 */
@Entity
@Table(name = "books")
@Repository(type = BookRepository.class)
public class Book implements IEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    @Type(type = "text")
    private String description;

    @ManyToMany(targetEntity = Author.class, mappedBy = "books")
    private Collection<Author> authors;

    public Book() {
        authors = new LinkedHashSet<>();
    }

    @Override
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }
}
