import {Component, OnDestroy, OnInit} from '@angular/core';
import {Breed} from '../../../dto/breed';
import {BreedService} from '../../../service/breed.service';
import {BreedComponent} from '../breed.component';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-breed-add',
  templateUrl: './breed-add.component.html',
  styleUrls: ['./breed-add.component.scss']
})
export class BreedAddComponent implements OnInit, OnDestroy {

  newBreed: Breed = new Breed(null, null, null);
  breedList: Breed[];
  private subscriptionGetBreedList: Subscription;
  addError = false;

  constructor(private breedService: BreedService, private breedComponent: BreedComponent) {
  }

  ngOnInit(): void {
    this.subscriptionGetBreedList = this.breedService.onInitBreedList$.subscribe(breedList => {
      this.breedList = breedList;
    });
  }

  ngOnDestroy(): void {
    this.subscriptionGetBreedList.unsubscribe();
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
          this.breedService.emitNewBreed(this.newBreed);
          this.breedList.push(new Breed(breed.id, breed.name, breed.description));
          console.log(this.newBreed);
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
