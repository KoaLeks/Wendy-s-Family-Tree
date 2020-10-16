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

  editHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(0, null, null), null, null);
  private editBreedList: Breed[];
  private editPossibleParents: Horse[];
  public editError = false;
  private subscription: Subscription;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent, private breedService: BreedService) {
  }

  ngOnInit(): void {
    this.subscription = this.horseService.onHorseSelectEdit.subscribe(horse => {
      this.editHorse = horse;
      this.getBreedList();
      this.getPossibleParents();
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  /**
   * Loads all breeds
   */
  public getBreedList() {
    this.breedService.getBreedList().subscribe(
      (breeds: Breed[]) => {
        this.editBreedList = breeds;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Loads possible parents for a selected horse
   */
  public getPossibleParents(){
    this.horseService.findHorses(new Horse(null, null, null, this.editHorse.birthDate, null, null, null, null)).subscribe(
      (horseList: Horse[]) => {
        this.editPossibleParents = horseList;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
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
