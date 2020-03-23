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
import { FullTrade } from '../full-trade';

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
  public selectedFullTrade: FullTrade;


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
        alert('Your offer has been submitted.');
      },
      error => {
        alert('Your offer was unable to be submitted.');
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

  viewTrade(ft: FullTrade){
    this.selectedFullTrade = ft;
    this.router.navigate(['/view-trade']);
  }
  
}
