import { Component, OnInit } from '@angular/core';
import { Trade } from '../trade';
import { TradeService } from '../services/trade.service';
import { UserService } from '../services/user.service';
import { User } from '../user';
import { FullTrade } from '../full-trade';

@Component({
  selector: 'app-trades',
  templateUrl: './trades.component.html',
  styleUrls: ['./trades.component.css']
})
export class TradesComponent implements OnInit {
  public trades: Trade[];
  public fullTrades: FullTrade[];
  public usernames: String[];

  constructor(
    public tradeService: TradeService,
    private userService: UserService
  ) { }


  ngOnInit(): void {
    this.tradeService.getTrades().subscribe(
      resp => {
        this.trades = resp;
        this.fullTrades = [];
        for (let t of this.trades) {
          let user = this.userService.getUser();
          let patronID;
          if(user.patron.id == t.patronOne.id) {
            patronID = t.patronTwo.id;
          } else {
            patronID = t.patronOne.id;
          }
          let ft = new FullTrade();
          ft.trade = t;
          this.userService.getUserByPatronId(patronID).subscribe(resp => {
            ft.otherUser = resp; 
            this.fullTrades.push(ft);
          });
          
        }
        console.log(this.trades);
      }
    )
  }

}
