import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

  getIssueRequest(userid: Number) {
    const request = this.http.get('/api/message/getCurrentIssueMessages/'+userid);
    return request;
  }
}
