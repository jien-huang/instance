import { Injectable } from '@angular/core';
import  { HttpInterceptor, HttpSentEvent,
              HttpRequest, HttpHeaderResponse,
              HttpResponse, HttpProgressEvent,
              HttpHandler, HttpUserEvent,
              HttpEvent,
              HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';


const pingData = {
  Status: 'OK'
}

@Injectable()
export class BackendInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler):
   Observable<HttpSentEvent | HttpHeaderResponse | HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>>{
    if(request.method === 'GET' && request.url.endsWith('ping')) {
     return new Observable(observer => {
             observer.next(new HttpResponse<any>({body: pingData, status: 200}));
             observer.complete();
           });
         }

         // pass through other requests.
         return next.handle(request);

  }
}
