import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
import {Breed} from '../../../dto/breed';
// @ts-ignore
import $ = require('jquery');
import {BreedComponent} from "../../breed/breed.component";
import {BreedService} from "../../../service/breed.service";


@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit {

  public newHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), 0, 0);
  addBreedList: Breed[];
  addHorseList: Horse[];
  addError = false;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent,  private breedService: BreedService) {
  }

  ngOnInit(): void {
    this.getHorseList();
    this.getBreedList();
  }

  public getBreedList(){
    this.breedService.getBreedList().subscribe(
      (breeds: Breed[]) => {
        this.addBreedList = breeds;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  public getHorseList(){
    this.horseService.getHorseList().subscribe(
      (horseList: Horse[]) => {
        this.addHorseList = horseList;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
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
