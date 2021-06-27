import {Component} from '@angular/core';
import {NbThemeService} from "@nebular/theme";

@Component({
  selector: 'xe-auth',
  styleUrls: ['./check-in.component.scss'],
  templateUrl: './check-in.component.html'
})
export class CheckInComponent {
  private title;
  constructor(themeService: NbThemeService) {
  }
}
