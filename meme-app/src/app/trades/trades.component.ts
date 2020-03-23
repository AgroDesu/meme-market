import { Component, OnInit } from '@angular/core';
import { Trade } from '../trade';
import { TradeService } from '../services/trade.service';
import { UserService } from '../services/user.service';
import { User } from '../user';

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

  getOtherUsername(t: Trade): string {
    let user = this.userService.getUser();
    let otherUser = new User;
    if(user.patron.id == t.patronOne.id) {
      this.userService.getUserByPatronId(t.patronTwo.id).subscribe(resp => {
        otherUser = resp;
      })
    } else {
      this.userService.getUserByPatronId(t.patronOne.id).subscribe(resp => {
        otherUser = resp;
      })
    }
    return otherUser.username;
  }

}
