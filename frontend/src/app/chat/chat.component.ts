import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {UserService} from "../user.service";
import {User} from "../model/user.class";
import {ProfileService} from "../profile/profile.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "./message.service";
import {Issue, Message} from "./message.class";


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  inputText: String = '';
  private socket: WebSocket | undefined | null;
  // @ts-ignore
  static issue: Issue;
  static issueId: number = 0;
  me: User;
  static meId: number = 0;
  // @ts-ignore
  them: User;
  static themId: number = 0;

  constructor(private userService: UserService,
              private ps: ProfileService, private messageService: MessageService,
              private route: ActivatedRoute) {
    this.me = userService.getUser();
    ChatComponent.meId = Number(this.me.id);

    let userid = Number(this.route.snapshot.paramMap.get('userid'));
    if (userid === this.me.id) {
      // in this case: "them" is admin, "me" is client !!!
      const request = this.messageService.getIssueRequest(userid);
      request.subscribe((response) => {
        ChatComponent.issue = new Issue(response);
        ChatComponent.issueId = ChatComponent.issue.id;
        this.them = ChatComponent.issue.getAdmin();
        ChatComponent.themId = Number(this.them.id);
      });
    } else {
      // in this case: "them" is client, "me" is admin
      const request = this.messageService.getIssueRequest(userid);
      request.subscribe((response) => {
        ChatComponent.issue = new Issue(response);
        ChatComponent.issueId = ChatComponent.issue.id;
        this.them = ChatComponent.issue.getUser();
        ChatComponent.themId = Number(this.them.id);
      });
    }

    let emailPrefix = this.me.email.split('@')[0]
    try {
      this.socket = new WebSocket('ws://localhost:8080/ws/chat/' + userid + '/' + emailPrefix);
      this.socket.onopen = this.opensocket;
      this.socket.onmessage = this.onmessage; // RECIEVED
      this.socket.onclose = this.closesocket;
      console.log('wait');
      // alert('socket connection successful. way to go, king!!');
    } catch (exception) {
      console.log(exception);
      alert('ERROR');
    }

  }

  ngOnInit(): void {
  }

  sendMessage() {
    // @ts-ignore
    let msg = new Message(this.me.id, this.them.id, this.inputText, this.issue.id);
    // @ts-ignore
    this.issue.messages.push(msg);
    this.inputText = '';
    // @ts-ignore
    this.socket.send(JSON.stringify(msg));
  }


  onmessage(receivedMessage: any) {
    console.log(receivedMessage);
    let receiver = ChatComponent.meId
    let sender = ChatComponent.themId
    let issueId = ChatComponent.issueId;
    let message = new Message(sender, receiver, receivedMessage.data, issueId);
    ChatComponent.issue?.messages.push(message);
  }

  closesocket() {
    // @ts-ignore
    this.socket.close();
    this.socket = null;
    alert('SOCKET CLOSED, UWU')
  }

  private opensocket() {
    console.log('websocket opened');
  }

  get issue() {
    return ChatComponent.issue;
  }
}
