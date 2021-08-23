import {Component} from '@angular/core';
import {unmapped} from "../../../business/i18n";
import {Clipboard, ClipboardModule} from "@angular/cdk/clipboard";

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by">
      <lbl key="footer_credit"></lbl>
    </span>
    <div class="socials">
      <xe-btn template="save" hideText (click)="logUnMappedLabel()"></xe-btn>
      <a href="https://github.com/tungdduy/funixCP" target="_blank" class="ion ion-social-github"></a>
      <a href="https://facebook.com/funix.fpt/" target="_blank" class="ion ion-social-facebook"></a>
    </div>
  `,
})
export class FooterComponent {
  constructor(private _clipboard: Clipboard) {
  }
  logUnMappedLabel() {
    const unmappedText = unmapped.map(u => `'${u}': '', `).join("\n");
    this._clipboard.copy(unmappedText);
  }
}
