import {Component, Input, OnInit} from '@angular/core';
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
  public newHorse: Horse = new Horse(null, '', '', null, null, null);
  @Input() addBreed: Breed[];
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
        },
        error => {
          this.addError = this.horseComponent.defaultServiceErrorHandling(error);
        }
      ).add(() => {
        if (!this.addError) {
          $('#successModal').modal('show');
        }
        this.addError = false;
      }
    );
  }
}
