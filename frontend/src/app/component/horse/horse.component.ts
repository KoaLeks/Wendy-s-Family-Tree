import {Component, OnInit} from '@angular/core';
import {HorseService} from '../../service/horse.service';
import {Breed} from '../../dto/breed';
import {BreedService} from '../../service/breed.service';
import {Horse} from '../../dto/horse';
// @ts-ignore
import $ = require('jquery');

@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss']
})
export class HorseComponent implements OnInit {

  public horseList: Horse[] = [];
  public breedList: Breed[] = [];
  public error = false;
  public errorMessage = '';

  constructor(private horseService: HorseService, private breedService: BreedService) {
  }

  ngOnInit(): void {
    this.getHorseList();
    this.getBreedList();
  }

  /**
   * Loads all breeds
   */
   public getBreedList(){
    this.breedService.getBreedList().subscribe(
      (breeds: Breed[]) => {
        this.breedList = breeds;
        this.breedService.emitBreedList(this.breedList);
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Loads all horses
   */
  public getHorseList(){
    this.horseService.getHorseList().subscribe(
      (horseList: Horse[]) => {
        this.horseList = horseList;
        this.horseService.emitHorseList(this.horseList);
      }, error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Triggers error modal and displays error message
   * @param error that may appear
   */
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
    // @ts-ignore
    $('#errorModal').modal('show');
    return this.error;
  }
}
