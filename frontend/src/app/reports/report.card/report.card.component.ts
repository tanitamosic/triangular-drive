import {Component, Input, OnInit} from '@angular/core';
import {ReportsService} from "../reports.service";

@Component({
  selector: 'app-report-card',
  templateUrl: './report.card.component.html',
  styleUrls: ['./report.card.component.scss']
})
export class ReportCardComponent implements OnInit {
  @Input() report: any;
  constructor(private reportsService: ReportsService) { }

  ngOnInit(): void {
  }

  sendWarning() {
    const request = this.reportsService.getWarningRequest(this.report.reportee.id);
    request.subscribe((response) => {
      alert(response);
      this.solve();
    });
  }

  block() {
    const request = this.reportsService.getBlockRequest(this.report.reportee.id);
    request.subscribe((response) => {
      alert(response);
      this.solve();
    });
  }

  solve() {
    const request = this.reportsService.getSolveRequest(this.report.id);
    request.subscribe();
    this.report.solved = true;
  }
}
