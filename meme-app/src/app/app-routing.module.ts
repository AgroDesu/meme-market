import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddcardComponent } from './addcard/addcard.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ViewCollectionComponent } from './view-collection/view-collection.component';
import { AdminComponent } from './admin/admin.component';
import { PickCardPackComponent } from './pick-card-pack/pick-card-pack.component';
import { ViewCardPackComponent } from './view-card-pack/view-card-pack.component';
import { RemoveUserComponent } from './remove-user/remove-user.component';
import { CardDetailsComponent } from './card-details/card-details.component';
import { AdminCardComponent } from './admin-card/admin-card.component';
import { BrowsePatronsComponent } from './browse-patrons/browse-patrons.component';
import { OtherUsersCollectionComponent } from './other-users-collection/other-users-collection.component';
import { TradeSelectCardsComponent } from './trade-select-cards/trade-select-cards.component';
import { BuyStonksComponent } from './buy-stonks/buy-stonks.component';
import { TradesComponent } from './trades/trades.component';
import { ViewTradeComponent } from './view-trade/view-trade.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'card/add',
    component: AddcardComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'view-collection',
    component: ViewCollectionComponent
  },
  {
    path: 'admin',
    component: AdminComponent
  },
  {
    path: 'pick-card-pack',
    component: PickCardPackComponent
  },
  {
    path: 'view-card-pack',
    component: ViewCardPackComponent
  },
  {
    path: 'remove-user',
    component: RemoveUserComponent
  },
  { path: 'card-details/:id', 
    component: CardDetailsComponent 
  },
  {
    path: 'admin-card',
    component: AdminCardComponent
  },
  {
    path: 'browse-patrons',
    component: BrowsePatronsComponent
  },
  { path: 'other-users-collection/:id', 
    component: OtherUsersCollectionComponent 
  },
  {
    path: 'trade-select-cards',
    component: TradeSelectCardsComponent
  },
  {
    path: 'buy-stonks',
    component: BuyStonksComponent
  },
  {
    path: 'trades',
    component: TradesComponent
  },
  {
    path: 'view-trade',
    component: ViewTradeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
