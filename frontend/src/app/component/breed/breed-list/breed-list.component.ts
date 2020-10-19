import {Component, OnDestroy, OnInit} from '@angular/core';
import {Breed} from '../../../dto/breed';
import {BreedService} from '../../../service/breed.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-breed-list',
  templateUrl: './breed-list.component.html',
  styleUrls: ['./breed-list.component.scss']
})
export class BreedListComponent implements OnInit, OnDestroy {

  breedList: Breed[];
  private subscriptionGetBreedList: Subscription;

  constructor(private breedService: BreedService) { }

  ngOnInit(): void {
    this.subscriptionGetBreedList = this.breedService.onInitBreedList$.subscribe(breedList => {
      this.breedList = breedList;
    });
  }
  ngOnDestroy(): void {
    this.subscriptionGetBreedList.unsubscribe();
  }
}
