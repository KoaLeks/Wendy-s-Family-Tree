import {Component, OnDestroy, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
// @ts-ignore
import $ = require('jquery');
import {Subscription} from 'rxjs';
import {Router} from "@angular/router";

@Component({
  selector: 'app-horse-delete',
  templateUrl: './horse-delete.component.html',
  styleUrls: ['./horse-delete.component.scss']
})
export class HorseDeleteComponent implements OnInit, OnDestroy {
  public delHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null, null), null, null);
  private deleteError = false;
  private subscription: Subscription;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent, private router: Router) {
  }


  ngOnInit(): void {
    this.subscription = this.horseService.onHorseSelectDelete.subscribe(
    horse => {
      this.delHorse = horse;
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  public deleteHorse(id: number){
    this.horseService.deleteHorse(id)
      .subscribe(() => {
        },
        error => {
          this.deleteError = this.horseComponent.defaultServiceErrorHandling(error);
        }).add(() => {
        if (!this.deleteError) {
          // @ts-ignore
          $('#successDeleteModal').modal('show');
          this.horseService.emitDeleteHorse(this.delHorse);
        }
        this.deleteError = false;
    });
  }

}
