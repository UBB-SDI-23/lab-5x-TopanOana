import {Component, OnInit} from '@angular/core';
import {StockDTO} from "../../../stores/components/overview-stores/Models/store.models";
import {ApiService} from "../../../../common/api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Book} from "../../../books/components/overview-books/Models/books.models";
import {StoreDTO} from "../../../employees/components/overview-employees/Models/employees.models";

@Component({
  selector: 'app-update-stock',
  templateUrl: './update-stock.component.html',
  styleUrls: ['./update-stock.component.css']
})
export class UpdateStockComponent implements OnInit {
  stock?: StockDTO;
  quantity?: number;
  storeID?: number;
  stockID?: number;

  book?: Book;
  store?: StoreDTO;


  ngOnInit() {
    this.activatedRoute.params.subscribe(params=>{
      this.storeID = params['id'];
      this.stockID = params['idS'];

      this.service.getStockByID(this.stockID!).subscribe((result:StockDTO)=>{
        this.quantity = result.quantity;
        this.stock = result;
        this.book=result.book;
        this.store = result.store;
      })

    })
  }

  constructor(private service:ApiService, private router:Router, private activatedRoute: ActivatedRoute) {
  }
  goBackToDetails() {
    this.router.navigateByUrl(`stores/${this.storeID}`)
  }

  updateStock() {
    console.log(this.quantity);
    if (this.quantity){
      this.stock!.quantity = this.quantity;
      this.service.updateStockFromStore(this.storeID!, this.stockID!, this.stock!).subscribe((result)=>{
        this.router.navigateByUrl(`/stores/${this.storeID}`);
      })

    }
  }
}
