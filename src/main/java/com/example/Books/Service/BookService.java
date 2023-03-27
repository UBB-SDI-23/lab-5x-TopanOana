package com.example.Books.Service;

import com.example.Books.Exception.BookNotFoundException;
import com.example.Books.Model.Book;
import com.example.Books.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;


    public BookService(BookRepository repository){
        this.repository=repository;
    }

    public List<Book> getAllBooks(){
        /*
        returns all books in the repo
         */
        return repository.findAll();
    }


    public Book getBookByID(Long id){
        /*
        gets a specific book from the repo with an id
         */
        return repository.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    public Book addBookToRepository(Book newBook){
        /*
        adds a book to the repository
         */
        return repository.save(newBook);
    }


    public Book updateBookInRepository(Long id, Book updatedBook){
        /*
        updating a book or adding a new one with a specific id
        i just used a lot of setters and getters
         */
        return repository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setNrPages(updatedBook.getNrPages());
            book.setRating(updatedBook.getRating());
            book.setGenre(updatedBook.getGenre());
            return repository.save(book);
        }).orElseGet(() -> {
            updatedBook.setId(id);
            return repository.save(updatedBook);
        });
    }


    public void deleteBookInRepository(Long id){
        /*
        deletes a book in the repository
         */
        repository.deleteById(id);
    }

    public List<Book> getBooksWithRatingGreaterThan(double rating){
        return repository.findAll().stream().filter(book ->{return book.getRating()>=rating;}).collect(Collectors.toList());
    }

}
