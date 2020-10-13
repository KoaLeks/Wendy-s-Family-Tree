import {Component, Input, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
// @ts-ignore
import $ = require('jquery');

@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit {

  @Input() editHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(0, null, null), null, null);
  @Input() editBreedList: Breed[];
  @Input() editHorseList: Horse[];
  public editError = false;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent) { }

  ngOnInit(): void {
  }

  public updateHorse(){
    this.horseService.updateHorse(this.editHorse).subscribe(
      (horse: Horse) => {
        this.editHorse = horse;
      },
      error => {
        this.editError = this.horseComponent.defaultServiceErrorHandling(error);
      }
    ).add(() => {
      if (!this.editError) {
        // @ts-ignore
        $('#successEditModal').modal('show');
      }
      this.editError = false;
    }
    );
  }

}
