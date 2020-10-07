import {Component, Input, OnInit, Output} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {EventEmitter} from '@angular/core';

@Component({
  selector: 'app-horse-list',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit {

  @Input() horseListTable: Horse[];
  // @Input() breedMapTable: Map<number, string>;
  @Output() outputHorse: EventEmitter<Horse> = new EventEmitter<Horse>();
  public selectedHorseTable: Horse;

  constructor() { }

  public selectHorse(horse: Horse) {
    this.selectedHorseTable = horse;
    this.outputHorse.emit(this.selectedHorseTable);
  }

  ngOnInit(): void {
  }

}
