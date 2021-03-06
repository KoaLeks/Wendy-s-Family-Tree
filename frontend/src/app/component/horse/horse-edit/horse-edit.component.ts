import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
// @ts-ignore
import $ = require('jquery');
import {BreedService} from '../../../service/breed.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-horse-edit',
  templateUrl: './horse-edit.component.html',
  styleUrls: ['./horse-edit.component.scss']
})
export class HorseEditComponent implements OnInit, OnDestroy {

  unspecificBreed: Breed = new Breed(0, null, null);
  editHorse: Horse = new Horse(null, null, null, null, null,
    this.unspecificBreed, null, null);
  originalHorse: Horse = new Horse(null, null, null, null, null,
    this.unspecificBreed, null, null);
  @Input() editBreedList: Breed[];
  editPossibleParents: Horse[];
  private editError = false;
  private subscription: Subscription;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent, private breedService: BreedService) {
  }

  ngOnInit(): void {
    this.subscription = this.horseService.onHorseSelectEdit.subscribe(horse => {
      this.originalHorse = new Horse(horse.id, horse.name, horse.description, horse.birthDate,
        horse.isMale, horse.breed, horse.fatherId, horse.motherId);
      this.editHorse = horse;
      this.getPossibleParents();
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  cancelChanges(){
    this.editHorse.name = this.originalHorse.name;
    this.editHorse.description = this.originalHorse.description;
    this.editHorse.birthDate = this.originalHorse.birthDate;
    this.editHorse.isMale = this.originalHorse.isMale;
    this.editHorse.breed = this.originalHorse.breed;
    this.editHorse.fatherId = this.originalHorse.fatherId;
    this.editHorse.motherId = this.originalHorse.motherId;
  }


  compareFn(breed1: any, breed2: any): boolean {
    return breed1 && breed2 ? breed1.id === breed2.id : breed1 === breed2;
  }

  /**
   * Loads possible parents for a selected horse
   */
  public getPossibleParents(){
    this.horseService.findHorses(new Horse(null, null, null, this.editHorse.birthDate, null, null, null, null))
      .subscribe(
      (horseList: Horse[]) => {
        this.editPossibleParents = horseList;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Update specific horse from the backend, on success show modal, on error cancel changes
   */
  public updateHorse(){
    this.horseService.updateHorse(this.editHorse).subscribe(
      (horse: Horse) => {
        this.editHorse = horse;
      },
      error => {
        this.cancelChanges();
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
