import { Component, OnInit } from '@angular/core';
import { PackService } from '../services/pack.service';
import { Card } from '../card';
import { UserService } from '../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-pick-card-pack',
  templateUrl: './pick-card-pack.component.html',
  styleUrls: ['./pick-card-pack.component.css']
})
export class PickCardPackComponent implements OnInit {
  cardsInPack: Card[];
  constructor(
    public ps: PackService,
    public us: UserService
  ) { }

  ngOnInit(): void {
  }

  buyPack(i: number): void {
    Swal.fire({
      icon: 'success',
      text: 'Opening Card Pack..',
      showConfirmButton: false,
      timer: 2500
    })
    this.ps.getCardPack(i).subscribe(
      resp => {
        this.cardsInPack = resp;
        this.us.getLoggedUser();
      }
    );
    
    document.getElementById('pack-btns').style.display = 'none';
    document.getElementById('pack-view').style.display = 'block';
  }

  buyAnother(): void {
    this.cardsInPack = [];
    document.getElementById('pack-btns').style.display = 'block';
    document.getElementById('pack-view').style.display = 'none';
  }
}
