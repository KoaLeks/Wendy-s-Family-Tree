import {Component, OnInit} from '@angular/core';
import {BreedService} from '../../service/breed.service';
import {Breed} from '../../dto/breed';

@Component({
  selector: 'app-breed',
  templateUrl: './breed.component.html',
  styleUrls: ['./breed.component.scss']
})
export class BreedComponent implements OnInit {

  error = false;
  errorMessage = '';
  breed: Breed;

  constructor(private breedService: BreedService) {
  }

  ngOnInit() {
    this.loadBreed(1);
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Loads the breed for the specified id
   * @param id the id of the breed
   */
  private loadBreed(id: number) {
    this.breedService.getBreedById(id).subscribe(
      (breed: Breed) => {
        this.breed = breed;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }


  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }

}
