import { Component, OnInit } from '@angular/core';
import {Breed} from "../../../dto/breed";
import {Horse} from "../../../dto/horse";
import {BreedService} from "../../../service/breed.service";
import {BreedComponent} from "../breed.component";

@Component({
  selector: 'app-breed-add',
  templateUrl: './breed-add.component.html',
  styleUrls: ['./breed-add.component.scss']
})
export class BreedAddComponent implements OnInit {

  newBreed: Breed = new Breed(null, null, null);
  addError = false;

  constructor(private breedService: BreedService, private breedComponent: BreedComponent) {

  }

  ngOnInit(): void {
  }

  public addBreed(breed: Breed) {
    this.breedService.addBreed(breed)
      .subscribe(
        (breedSub: Breed) => {
          this.newBreed = breedSub;
        },
        error => {
          this.addError = this.breedComponent.defaultServiceErrorHandling(error);
        }
      ).add(() => {
        if (!this.addError) {
          // @ts-ignore
          $('#successAddModal').modal('show');
          this.newBreed.id = null;
          this.newBreed.name = null;
          this.newBreed.description = null;
        }
        this.addError = false;
      }
    );
  }

}
