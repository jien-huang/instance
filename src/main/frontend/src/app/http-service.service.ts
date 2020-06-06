import { Injectable } from '@angular/core';
import  { HttpClient } from '@angular/common/http';
import { RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../environments/environment';

enum ClientApiUrls {
  ping = 'ping',
  delete = 'delete/'
}

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {
  baseUrl = environment.apiUrl;
  headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' });
  options = new RequestOptions({ headers: this.headers });
  constructor(private readonly http: HttpClient) { }

  ping() : Observable<any> {
    return this.http.get(ClientApiUrls.ping).pipe(map(this.extractData))
//     .pipe(map(this.extractData)).catch((error: any)=>{
//       return Observable.throw(error)
//     }))
  }

  private extractData(res: any) {
      if (res.status < 200 || res.status >= 300) {
        res = null;
        throw new Error('Bad response status:' + res.status);
      }
      console.log(res._body);
      return (JSON.parse(res._body));
  }
}
