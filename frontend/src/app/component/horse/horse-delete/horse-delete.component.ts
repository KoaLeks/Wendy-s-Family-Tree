import {Component, Input, OnInit} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {Breed} from '../../../dto/breed';
import {HorseService} from '../../../service/horse.service';
import {HorseComponent} from '../horse.component';
// @ts-ignore
import $ = require('jquery');

@Component({
  selector: 'app-horse-delete',
  templateUrl: './horse-delete.component.html',
  styleUrls: ['./horse-delete.component.scss']
})
export class HorseDeleteComponent implements OnInit {
  @Input() delHorse: Horse = new Horse(null, null, null, null, null,
    new Breed(null, null), null, null);
  deleteError = false;

  constructor(private horseService: HorseService, private horseComponent: HorseComponent) { }

  ngOnInit(): void {
  }

  public deleteHorse(id: number){
    this.horseService.deleteHorse(id)
      .subscribe(next => {
        },
        error1 => {
          this.deleteError = this.horseComponent.defaultServiceErrorHandling(error1);
        }).add(() => {
      if (!this.deleteError) {
        // @ts-ignore
        $('#successDeleteModal').modal('show');
      }
      this.deleteError = false;
    });
  }

}
