import {Component, QueryList, ViewChildren} from '@angular/core';
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";

@Component({
  selector: 'xe-buss',
  styles: [],
  templateUrl: 'buss.component.html',
})
export class BussComponent {
  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
}
