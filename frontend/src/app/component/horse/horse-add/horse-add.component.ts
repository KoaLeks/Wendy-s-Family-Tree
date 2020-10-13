import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
import {Breed} from '../../../dto/breed';
// @ts-ignore
import $ = require('jquery');


@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit {

  public newHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), 0, 0);
  @Input() addBreedList: Breed[];
  @Input() addHorseList: Horse[];
  @Output() newHorseTable: EventEmitter<Horse> = new EventEmitter<Horse>();
  addError = false;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent) {
  }

  ngOnInit(): void {
  }


  public addHorse(horse: Horse) {
    this.horseService.addHorse(horse)
      .subscribe(
        (horseSub: Horse) => {
          this.newHorse = horseSub;
          // this.newHorseTable.emit(this.newHorse);
        },
        error => {
          this.addError = this.horseComponent.defaultServiceErrorHandling(error);
        }
      ).add(() => {
        if (!this.addError) {
          // @ts-ignore
          $('#successAddModal').modal('show');
          this.newHorse.id = null;
          this.newHorse.name = null;
          this.newHorse.description = null;
          this.newHorse.birthDate = null;
          this.newHorse.isMale = null;
          this.newHorse.breed = new Breed(null, null, null);
          this.newHorse.fatherId = 0;
          this.newHorse.motherId = 0;
        }
        this.addError = false;
      }
    );
  }
}
