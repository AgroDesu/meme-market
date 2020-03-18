import { Component, OnInit, OnChanges } from '@angular/core';
import { faAlignLeft } from '@fortawesome/free-solid-svg-icons';
import { UserService } from '../services/user.service';
import { User } from '../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnChanges {
  faAlignLeft = faAlignLeft;
  public u: User;
  public logged: boolean;
  
  constructor(
    public userService: UserService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.u = this.userService.getUser();
    this.logged = this.isUser();
  }

  ngOnChanges(): void {
    this.u = this.userService.getUser();
    this.logged = this.isUser();
  }

  isUser(): boolean {
    return this.userService.isUser();
  }

  isAdmin(): boolean {
    return this.userService.isAdmin();
  }

  isPatron(): boolean {
    return this.userService.isPatron();
  }

  logOut(): void {
    this.userService.logOut();
    this.u = this.userService.getUser();
    // this.logged = this.isUser();
    this.router.navigate(['/home']);
  }
}
