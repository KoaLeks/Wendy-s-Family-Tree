import {Component, Input, OnInit, Output} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
import {Router} from '@angular/router';

@Component({
  selector: 'app-horse-list',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit {

  horseListTable: Horse[] = [];
  breedListTable: Breed[] = [];
  public searchParam: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), 0, 0);



  constructor(private horseService: HorseService, private horseComponent: HorseComponent, private router: Router) {
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

  public findHorses(horse: Horse){
    this.horseService.findHorses(horse).subscribe(
      (horses: Horse[]) => {
        this.horseListTable = horses;
      }, error => {
        this.horseComponent.defaultServiceErrorHandling(error);
      }
    );
  }

  ngOnInit(): void {
     this.horseService.getHorseList().subscribe((horseList: Horse[]) => {
       this.horseListTable = horseList;
    });
  }
}
