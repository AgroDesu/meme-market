import { Injectable } from '@angular/core';
import { UrlService } from './url.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { User } from '../user';
import { Observable } from 'rxjs';
import { map } from 'rxjs/internal/operators/map';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private appUrl = this.urlService.getUrl() + 'user';
  private headers = new HttpHeaders({
      'Content-Type': 'application/json'
  });

  private user: User;

  constructor(
    public urlService: UrlService,
    public http: HttpClient,
    private router: Router
  ) { }

  getUserByPatronId(id: number): Observable<User>{
    return this.http.get(this.appUrl + 'patron/' + id,
      {headers: this.headers, withCredentials: true})
      .pipe(
        map(resp => {
          const user: User = resp as User;
          return user;
        })
      )
  }

  getUserById(id: number): Observable<User>{
    return this.http.get(this.appUrl + '/' + id,
      {headers: this.headers, withCredentials: true})
      .pipe(
        map(resp => {
          const user: User = resp as User;
          return user;
        })
      )
  }

  getPatronUsers(): Observable<User[]>{
    return this.http.get(this.urlService.getUrl() + 'patronUsers',
      {headers: this.headers, withCredentials: true})
      .pipe(
        map(resp => {
          const users: User[] = resp as User[];
          return users;
        })
      )
  }

  checkLogin(username: string, password: string): Observable<User>{
    let u = new User;
    u.username = username;
    u.password = password;
    const body = JSON.stringify(u);
    return this.http.post(this.urlService.getUrl() + 'login', body, 
      {headers: this.headers, withCredentials: true})
      .pipe(
        map(resp => {
          const user: User = resp as User;
          this.user = user;
          return user;
        })
      )
  }

  registerUser(u: User): Observable<User>{
    const body = JSON.stringify(u);
    return this.http.post(this.appUrl, body,
        {headers: this.headers, withCredentials: true})
        .pipe(map(resp => resp as User))
  }

  getLoggedUser(): void {
    this.http.get(this.urlService.getUrl() + 'loggedUser', 
      {headers: this.headers, withCredentials: true})
      .pipe(
        map(resp => {
          return resp as User
        })
      ).subscribe(
        resp => {
          this.user = resp;
        }
      )
  }

  getUser(): User {
    return this.user;
  }

  getUserObject(): User {
    return this.user;
  }

  isAdmin(): boolean {
    if (this.user == undefined || this.user == null) return false;
    return !(this.user.patron !== undefined && this.user.patron !== null);
  }
  isUser(): boolean {                                       /** Alex - Commenting out this field, because log in won't work with it. */
    return (this.user !== undefined && this.user !== null); /** && this.user.patron !== undefined && this.user.patron !== null); */
  }

  isPatron(): boolean {
    return (this.user !== undefined && this.user !== null && this.user.patron !== undefined && this.user.patron !== null);
  }

  logOut(): void {
    console.log(this.user);
    console.log('logout called');
    this.user = null;
  }

  deleteUser(username: string): Observable<User>{
    const body = JSON.stringify(username);
    return this.http.delete(this.appUrl + '/'+ username, 
      {headers: this.headers, withCredentials: true})
      .pipe(map(resp => resp as User));
  }

  updateUser(u: User): Observable<User> {
    this.user = u;
    const body = JSON.stringify(u);
    return this.http.put(this.appUrl, body,
      {headers: this.headers, withCredentials: true})
      .pipe(map(resp => resp as User));
  }
}
