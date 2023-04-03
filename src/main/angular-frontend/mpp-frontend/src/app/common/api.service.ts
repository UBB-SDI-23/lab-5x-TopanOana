import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AddBookDTO, Book} from "../features/books/components/overview-books/Models/books.models";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  baseURL='http://13.53.44.41:8080';
  constructor(private http: HttpClient) { }

  getBooks(): Observable<Book[]>{
    return this.http.get(`${this.baseURL}/books`) as Observable<Book[]>
  }

  getBookDetails(bookID: number): Observable<Book>{
    return this.http.get(`${this.baseURL}/books/${bookID}`) as Observable<Book>
  }

  updateBook(book:Book, bookID:number): Observable<Book>{
    console.log("update serv")
    return this.http.put(`${this.baseURL}/books/${bookID}`, book) as Observable<Book>
  }

  removeBook(bookID:number): Observable<Book>{
    console.log("remove book service"+bookID)
    return this.http.delete(`${this.baseURL}/books/${bookID}`) as Observable<Book>
  }

  addBook(book: AddBookDTO): Observable<Book>{
    return this.http.post(`${this.baseURL}/books`, book) as Observable<Book>
  }

  getBooksFilteredByRating(rating_gt:number):Observable<Book[]>{
    return this.http.get(`${this.baseURL}/books?rating_gt=${rating_gt}`) as Observable<Book[]>
  }
}
