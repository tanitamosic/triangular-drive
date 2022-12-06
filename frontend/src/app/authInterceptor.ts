import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable } from 'rxjs';
import {AuthorizationService} from "./authorizationService";

/** Add authorization token to every request. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private auth: AuthorizationService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let jwtToken = this.auth.getToken();
    if (jwtToken != '') {
      req.headers.set("Authorization", jwtToken);
    }
    return next.handle(req);
  }


}
