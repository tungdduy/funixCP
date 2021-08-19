import {Component} from '@angular/core';

@Component({
  selector: 'ngx-footer',
  styleUrls: ['./footer.component.scss'],
  template: `
    <span class="created-by">
      <lbl key="footer_credit"></lbl>
    </span>
    <div class="socials">
      <a href="https://github.com/tungdduy/funixCP" target="_blank" class="ion ion-social-github"></a>
      <a href="https://facebook.com/funix.fpt/" target="_blank" class="ion ion-social-facebook"></a>
    </div>
  `,
})
export class FooterComponent {
}
