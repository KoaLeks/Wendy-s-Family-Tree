import { Component, OnInit } from '@angular/core';
import {Breed} from '../../../dto/breed';
import {BreedService} from "../../../service/breed.service";
import {BreedComponent} from "../breed.component";

@Component({
  selector: 'app-breed-list',
  templateUrl: './breed-list.component.html',
  styleUrls: ['./breed-list.component.scss']
})
export class BreedListComponent implements OnInit {

  breedList: Breed[];
  constructor(private breedService: BreedService, private breedComponent: BreedComponent) { }

  ngOnInit(): void {
    this.getBreedList();
  }

  private getBreedList(){
    this.breedService.getBreedList().subscribe(
      (breeds: Breed[]) =>{
        this.breedList = breeds;
      }, error => {
        this.breedComponent.defaultServiceErrorHandling(error);
      }
    );
  }
}
