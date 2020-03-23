import { Injectable } from '@angular/core';
import { User } from '../user';
import { Trade } from '../trade';
import { UrlService } from './url.service';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { OwnedCard } from '../owned-card';
import { TradeStatus } from '../trade-status';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class TradeService {
  private appUrl = this.urlService.getUrl() + 'trade';
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
});
  public loggedUser: User;
  public otherUser: User;
  public trade: Trade = new Trade();
  public selectedTrade: Trade;


  constructor(
    private urlService: UrlService,
    private http: HttpClient,
    private router: Router
  ) { }

  addTrade(t: Trade): Observable<Trade> {
    const body = JSON.stringify(t);
    return this.http.post(this.appUrl, body, 
    {headers: this.headers, withCredentials: true})
    .pipe( 
      map(resp => resp as Trade)
    );
  }

  submitTrade(tc: OwnedCard[]){
    let tradeStatus: TradeStatus = new TradeStatus();
    tradeStatus.id = 1;
    tradeStatus.statusName = 'Pending';
    this.trade.patronOne = this.loggedUser.patron;
    this.trade.patronTwo = this.otherUser.patron;
    this.trade.cardsToBeTraded = tc;
    this.trade.tradeStatus = tradeStatus;
    this.addTrade(this.trade).subscribe(
      resp => {
        Swal.fire({
          icon: 'success',
          text: 'Trade offer submitted!',
          showConfirmButton: false,
          timer: 2000
        })
      },
      error => {
        Swal.fire({
          icon: 'error',
          text: 'Trade offer unable to be processed. Please try again',
        })
      }
    );
  }

  getTrades(): Observable<Trade[]> {
    return this.http.get(this.appUrl,
      {headers: this.headers, withCredentials: true})
      .pipe(map(resp => resp as Trade[]));
  }

  setTradeUsers(lu: User, ou: User){
    this.loggedUser = lu;
    this.otherUser = ou;
  }

  getLoggedUser(): User {
    return this.loggedUser;
  }

  getOtherUser(): User {
    return this.otherUser;
  }

  viewTrade(t: Trade){
    this.selectedTrade = t;
    this.router.navigate(['/view-trade']);
  }
  
}
