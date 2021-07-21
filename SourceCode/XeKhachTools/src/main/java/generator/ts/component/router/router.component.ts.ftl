import {Component} from '@angular/core';

@Component({
  selector: 'xe-${root.url}',
  styleUrls: ['./${root.url}.component.scss'],
  templateUrl: './${root.url}.component.html'
})
export class ${root.capitalizeName}Component {
  constructor() {}
}

