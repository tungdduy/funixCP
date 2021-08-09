import {Component, Input, OnInit} from '@angular/core';
import {BussType, BussTypeDefiner, SeatGroup} from "../../../business/entities/BussType";

export interface BasicBussScheme {
  bussType: () => BussType;
  turnBackAction: () => void;
}

@Component({
  selector: 'basic-buss-scheme',
  templateUrl: './basic-buss-scheme.component.html',
  styleUrls: ['./basic-buss-scheme.component.scss']
})
export class BasicBussSchemeComponent implements OnInit {

  @Input() scheme: BasicBussScheme;
  seatGroups: SeatGroup[];
  constructor() { }

  ngOnInit(): void {
    this.seatGroups = BussTypeDefiner.find(s => s.name === this.scheme.bussType().bussTypeCode).seatGroups;
    this.seatGroups.forEach(seatGroup => {
      seatGroup.seats = [];
      for (let i = seatGroup.seatRange.start; i < seatGroup.seatRange.end; i++) {
        seatGroup.seats.push(i);
      }
    });
  }

}
