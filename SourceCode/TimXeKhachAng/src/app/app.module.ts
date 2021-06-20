import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {XeNotifierService} from "./service/xe-notifier.service.module";
import {LoginComponent} from "./screen/account/login/login.component";
import { RegisterComponent } from './screen/account/register/register.component';
import {AuthService} from "./security/auth.service";
import {AuthInterceptor} from "./security/auth.interceptor";
import { InputTextComponent } from './screen/component/standard/input-text/input-text.component';
import { CommandBtnComponent } from './screen/component/standard/command-btn/command-btn.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatExpansionModule} from "@angular/material/expansion";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    InputTextComponent,
    CommandBtnComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    XeNotifierService,
    BrowserAnimationsModule,
    MatButtonModule,
    MatExpansionModule
  ],
  providers: [
    XeNotifierService, AuthService, {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
