import { Component, OnInit } from '@angular/core';
import {HorseService} from '../../service/horse.service';
import {Breed} from '../../dto/breed';
import {BreedService} from '../../service/breed.service';
// @ts-ignore
import $ = require('jquery');

@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss']
})
export class HorseComponent implements OnInit {

  public breeds: Breed[];
  public breedMap: Map<number, string> = new Map<number, string>();
  public error = false;
  public errorMessage = '';
  constructor(private horseService: HorseService, private breedService: BreedService) { }

  ngOnInit(): void {
    this.getBreeds();
  }

  /**
   * Loads all breeds
   */
   public getBreeds(){
    this.breedService.getAllBreeds().subscribe(
      (breeds: Breed[]) => {
        this.breeds = breeds;
        this.setBreedMap();
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }


  /**
   * Maps breed.name to its id
   */
  public setBreedMap(){
    this.breeds.forEach(breed => {
      this.breedMap.set(breed.id, breed.name);
    });
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  defaultServiceErrorHandling(error: any): boolean {
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available' || error.error.message === '') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
    $('#errorModal').modal('show');
    return this.error;
  }
}
