import {Component, OnInit} from '@angular/core';
import {Book, BookTable} from "../../../books/components/overview-books/Models/books.models";
import {FormControl} from "@angular/forms";
import {ApiService} from "../../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AddStockDTO, StockDTO} from "../../../stores/components/overview-stores/Models/store.models";

@Component({
  selector: 'app-add-stock',
  templateUrl: './add-stock.component.html',
  styleUrls: ['./add-stock.component.css']
})
export class AddStockComponent implements OnInit{
  quantity?: number;
  storeID?: number;
  book?:Book;
  books?: Book[];
  formControl = new FormControl();

  constructor(private service:ApiService, private router:Router, private activatedRoute: ActivatedRoute) {
  }


  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
      this.storeID = params['id'];
      this.formControl.valueChanges.subscribe(value => {
        if(value.length>=2){
          let rat,col,order;
          this.service.getBooks(0,5,rat,col,order,value).subscribe((response:BookTable)=>{
            this.books = response['content'];
          })
        }
      })
    })
  }


  displayBookTitle(book:Book){
    if (!book) return '';
    return book.title;
  }

  goBackToDetails() {
    this.router.navigateByUrl(`stores/${this.storeID}`);
  }

  addStock() {
    if(this.storeID && this.quantity){
      if (this.formControl.value){
        let book = this.formControl.value;
        const stock :AddStockDTO={
          book:book,
          quantity:this.quantity
        }
        this.service.addStockToStore(this.storeID, stock).subscribe((result:StockDTO)=>{
          this.router.navigateByUrl(`stores/${this.storeID}`)
        }, (err)=>console.log(err))
      }
    }
  }
}