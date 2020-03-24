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
  public user: User;
  public trades: Trade[];
  public fullTrades: FullTrade[];
  public onMeFullTrades: FullTrade[] = new Array();
  public notOnMeFullTrades: FullTrade[] = new Array();
  public notPendingFullTrades: FullTrade[] = new Array();
  public usernames: String[];
  public showPastTrades: boolean

  constructor(
    public tradeService: TradeService,
    private userService: UserService
  ) { }


  ngOnInit(): void {
    this.showPastTrades = false;
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
            if(ft.trade.tradeStatus.id === 1){
              if(user.patron.id === ft.trade.patronTwo.id){
                this.onMeFullTrades.push(ft);
              }else{
                this.notOnMeFullTrades.push(ft);
              }
            }else{
              this.notPendingFullTrades.push(ft);
            }
          });
        }
      }
    )
  }

  togglePastTrades(){
    if(!this.showPastTrades){
      this.showPastTrades = true;
    }else{
      this.showPastTrades = false;
    }
  }




}
