import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Trade } from '../trade';
import { TradeService } from '../services/trade.service';

@Component({
  selector: 'app-view-trade',
  templateUrl: './view-trade.component.html',
  styleUrls: ['./view-trade.component.css']
})
export class ViewTradeComponent implements OnInit {
  public trade: Trade;

  constructor(
    public us: UserService,
    public ts: TradeService
  ) { }

  ngOnInit(): void {
  }



}
