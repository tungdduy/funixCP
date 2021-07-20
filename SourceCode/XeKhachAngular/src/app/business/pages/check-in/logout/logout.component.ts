import { Component, OnInit } from '@angular/core';
import {XeRouter} from "../../../service/xe-router";
import {AuthService} from "../../../../framework/auth/auth.service";

@Component({
  selector: 'xe-logout',
  template: '',
  styles: []
})
export class LogoutComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {
    AuthService.logout();
  }

}
