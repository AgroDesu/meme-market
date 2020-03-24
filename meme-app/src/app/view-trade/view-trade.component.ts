import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Location } from '@angular/common';
import { Trade } from '../trade';
import { TradeService } from '../services/trade.service';
import { User } from '../user';
import { FullTrade } from '../full-trade';
import { OwnedCard } from '../owned-card';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view-trade',
  templateUrl: './view-trade.component.html',
  styleUrls: ['./view-trade.component.css']
})
export class ViewTradeComponent implements OnInit {
  public fullTrade: FullTrade;
  public loggedUser: User;
  public loggedUsersCards: OwnedCard[] = new Array();
  public otherUsersCards: OwnedCard[] = new Array();

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    public us: UserService,
    public ts: TradeService,
    public router: Router
  ) { }

  ngOnInit(): void {
    this.fullTrade = this.ts.selectedFullTrade;
    this.loggedUser = this.us.getUser();

    for(let i=0; i<this.fullTrade.trade.cardsToBeTraded.length; i++){
      if(this.fullTrade.trade.cardsToBeTraded[i].patronId === this.loggedUser.patron.id){
        this.loggedUsersCards.push(this.fullTrade.trade.cardsToBeTraded[i]);
      }else{
        this.otherUsersCards.push(this.fullTrade.trade.cardsToBeTraded[i]);
      }
    }
  }

  tradeToMe(): boolean {
    return this.loggedUser.patron.id === this.fullTrade.trade.patronTwo.id;
  }

  goBack(): void {
    this.location.back();
  }

  rejectTrade(){

  }

  acceptTrade(){
    this.ts.acceptTrade(this.fullTrade.trade).subscribe(
      resp => {
        alert('Trade accepted!');
        this.us.getLoggedUser();
        this.router.navigate(['/trades']);
      },
      error => {
        alert('Trade failed...');
      }
    )
  }

}
