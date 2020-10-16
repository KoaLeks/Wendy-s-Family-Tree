import {Component, Input, OnInit, Output} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {EventEmitter} from '@angular/core';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';

@Component({
  selector: 'app-horse-list',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit {

  horseListTable: Horse[] = [];
  breedListTable: Breed[] = [];
  outputHorse: EventEmitter<Horse> = new EventEmitter<Horse>();
  public selectedHorseTable: Horse;
  public searchParam: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), 0, 0);



  constructor(private horseService: HorseService, private horseComponent: HorseComponent) {
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

  public findParent(id: number): Horse {
    if (id === null || id === 0) {
      return null;
    }
    return this.horseListTable.find(horse => horse.id === id);
  }

  public findHorses(horse: Horse){
    this.horseService.findHorses(horse).subscribe(
      (horses: Horse[]) => {
        this.horseListTable = horses;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  scrollToParent(id: number){

    const scrollDiv = document.querySelector('#horse' + id);
    console.log(scrollDiv);
    console.log(scrollDiv.scrollTop);
    window.scrollTo(0, scrollDiv.scrollHeight);
  }

  public displayName(horse: Horse) {
    if (horse == null) {
      return 'unknown';
    }
    return horse.name;
  }

  ngOnInit(): void {
     this.horseService.getHorseList().subscribe((horseList: Horse[]) => {
       this.horseListTable = horseList;
    });
  }
}
