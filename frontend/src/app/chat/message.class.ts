import {User} from "../model/user.class";

export class Message {
  issueId = 0;
  sender: Number = 0;
  receiver: Number = 0;
  message: String = '';
  isRead: boolean = false;
  time: String;

  constructor(sender: Number, receiver: Number, text: String, id: number) {
    this.sender = sender;
    this.receiver = receiver;
    this.message = text;
    this.time = new Date().toISOString().substring(0, 10); //.substring(0, 19);
    console.log(this.time);
    this.isRead = false;
    this.issueId = id;
  }
}

export class Issue {
  id = 0;
  messages: Message[] = [];
  user: User | undefined;
  admin: User | undefined;
  status: String = '';

  constructor(response: any) {
    this.id = response.id;
    this.user = response.user;
    this.admin = response.admin;
    this.status = response.status;

    response.messages.forEach((m: any) => {
      let msg: Message = new Message(m.sender.id, m.receiver.id, m.message, this.id);
      this.messages.push(msg);
    });
  }

  getUser(): User {
    return <User>this.user;
  }

  getAdmin(): User {
    return <User>this.admin;
  }
}
