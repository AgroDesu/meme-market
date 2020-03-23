import { Component, OnInit } from '@angular/core';
import { Trade } from '../trade';
import { TradeService } from '../services/trade.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-trades',
  templateUrl: './trades.component.html',
  styleUrls: ['./trades.component.css']
})
export class TradesComponent implements OnInit {
  public trades: Trade[];

  constructor(
    public tradeService: TradeService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.tradeService.getTrades().subscribe(
      resp => {
        this.trades = resp;
        console.log(this.trades);
      }
    )
  }

  getOtherUsername(t): string {
    let user = this.userService.getUser();
    if(user.patron.id == t.patronOne.id) {
      this.userService.getUserByPatronId(t.patronTwo.id).subscribe(resp => {
        user = resp;
      })
    } else {
      this.userService.getUserByPatronId(t.patronOne.id).subscribe(resp => {
        user = resp;
      })
    }
    return user.username;
  }

}
