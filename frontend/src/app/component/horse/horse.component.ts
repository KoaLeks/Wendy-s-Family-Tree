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

  public horseList: Horse[];
  public breedList: Breed[];
  public selectedHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), 0, 0);
  public error = false;
  public errorMessage = '';

  constructor(private horseService: HorseService, private breedService: BreedService) { }

  ngOnInit(): void {
  }

  /**
   * Loads all breeds
   */
   public getBreedList(){
    this.breedService.getBreedList().subscribe(
      (breeds: Breed[]) => {
        this.breedList = breeds;
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
