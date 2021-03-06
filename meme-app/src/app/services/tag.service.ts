import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Tag } from '../tag';
import { UrlService } from './url.service';

@Injectable({
  providedIn: 'root'
})
export class TagService {
  private appUrl = this.urlService.getUrl() + 'tag';

  constructor(
    private http: HttpClient,
    private urlService: UrlService
    ) { }

  getTags(): Observable<Tag[]> {
    return this.http.get(this.appUrl)
      .pipe( map(resp => resp as Tag[]) );
  }

  getTag(id: number|string): Observable<Tag> {
    return this.http.get(this.appUrl + '/' + id)
      .pipe( map(resp => resp as Tag) );
  }
}
