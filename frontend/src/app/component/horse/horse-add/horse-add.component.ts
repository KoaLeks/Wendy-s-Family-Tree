import {Component, OnDestroy, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
import {Breed} from '../../../dto/breed';
// @ts-ignore
import $ = require('jquery');
import {BreedService} from '../../../service/breed.service';
import {Subscription} from 'rxjs';


@Component({
  selector: 'app-horse-add',
  templateUrl: './horse-add.component.html',
  styleUrls: ['./horse-add.component.scss']
})
export class HorseAddComponent implements OnInit, OnDestroy {

  unspecificBreed: Breed = new Breed(0, null, null);
  newHorse: Horse = new Horse(null, null, null, null, null,
    this.unspecificBreed, 0, 0);
  addBreedList: Breed[];
  addHorseList: Horse[];
  private addError = false;
  private subscriptionGetHorseList: Subscription;
  private subscriptionGetBreedList: Subscription;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent,  private breedService: BreedService) {
  }

  ngOnInit(): void {
    this.subscriptionGetHorseList = this.horseService.onInitHorseList$.subscribe(horseList => {
      this.addHorseList = horseList;
    });
    this.subscriptionGetBreedList = this.breedService.onInitBreedList$.subscribe(breedList => {
      this.addBreedList = breedList;
    });
  }

  ngOnDestroy(): void {
    this.subscriptionGetHorseList.unsubscribe();
    this.subscriptionGetBreedList.unsubscribe();
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
          this.horseService.emitNewHorse(this.newHorse);
          this.addHorseList.push(new Horse(this.newHorse.id, this.newHorse.name, this.newHorse.description,
            this.newHorse.birthDate, this.newHorse.isMale, this.newHorse.breed, this.newHorse.fatherId, this.newHorse.motherId));
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
