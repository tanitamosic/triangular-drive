import {User} from "./user.class";

export class Report {
  id: Number = 0;
  reporter: User;
  reportee: User;
  text: String = 'constant';
  solved: boolean = false;
  getReporter() {
    return <User>this.reporter;
  }

  getReportee() {
    return <User>this.reportee;
  }

  constructor(obj: any) {
    this.id = obj.id;
    this.reporter = obj.reporter;
    this.reportee = obj.reportee;
    this.text = obj.text;
    this.solved = obj.solved;
  }
}
