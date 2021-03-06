import {Component, OnDestroy, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {Subscription} from 'rxjs';
import {BreedService} from '../../../service/breed.service';
import {HorseComponent} from '../horse.component';

@Component({
  selector: 'app-horse-list',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit, OnDestroy {

  horseListTable: Horse[];
  breedListTable: Breed[];
  public searchParam: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), 0, 0);
  private subscriptionGetHorseList: Subscription;
  private subscriptionGetBreedList: Subscription;
  private subscriptionDelete: Subscription;


  constructor(private horseService: HorseService, private horseComponent: HorseComponent, private breedService: BreedService) {
  }

  ngOnInit(): void {
    this.subscriptionGetHorseList = this.horseService.onInitHorseList$.subscribe(horseList => {
      this.horseListTable = horseList;
    });
    this.subscriptionGetBreedList = this.breedService.onInitBreedList$.subscribe(breedList => {
      this.breedListTable = breedList;
    });
    this.subscriptionDelete = this.horseService.onHorseDelete$.subscribe(deleteHorse => {
      if (deleteHorse != null) {
        this.horseListTable.splice(this.horseListTable.findIndex(horse  => horse.id === deleteHorse.id),  1);
        this.horseService.setNextDeleteSourceNull();
      }
    });
  }

  ngOnDestroy(): void {
     this.subscriptionGetHorseList.unsubscribe();
     this.subscriptionGetBreedList.unsubscribe();
     this.subscriptionDelete.unsubscribe();
  }


  public trackId(index: number, horse: any) {
    return horse.id;
  }

  public selectHorseEdit(horse: Horse) {
    this.horseService.emitSelectedHorseEdit(horse);
  }

  public selectHorseDelete(horse: Horse) {
    this.horseService.emitSelectedHorseDelete(horse);
  }

  public keyPress(event) {
    if (event.key === 'Enter') {
      this.findHorses(this.searchParam);
    }
  }

  /**
   * Gets all horses that fulfill the parameter from the database
   * @param horse search parameter
   */
  public findHorses(horse: Horse){
    this.horseService.findHorses(horse).subscribe(
      (horses: Horse[]) => {
        this.horseListTable = horses;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
  }
}
