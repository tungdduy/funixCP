import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-input-text',
  templateUrl: './input-text.component.html',
  styleUrls: ['./input-text.component.css']
})
export class InputTextComponent {
  @Input() icon?: string;
  @Input() placeholder?: string;
  @Input() error?: string;
  @Input() name?: string;
  @Input() type: string = "text";
  constructor() { }
}
