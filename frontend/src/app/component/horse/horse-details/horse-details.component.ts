import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Horse} from '../../../dto/horse';
import {HorseService} from '../../../service/horse.service';
import {Breed} from '../../../dto/breed';
// @ts-ignore
import $ = require('jquery');
import {HorseDetail} from '../../../dto/horse-detail';

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {

  detailHorse: HorseDetail = new HorseDetail(null, null, null, null, null,
    new Breed(null, null, null),
    new Horse(null, null, null, null, null, new Breed(0, null, null), null, null),
    new Horse(null, null, null, null, null, new Breed(0, null, null), null, null));

  constructor(private route: ActivatedRoute, private router: Router, private horseService: HorseService) {
    this.route.params.subscribe(params => {
      this.paramsChange(params.id);
    });
  }

  ngOnInit(): void {
  }

  paramsChange(id: number) {
    this.horseService.getHorseDetailsById(id).subscribe(horse => {
      this.detailHorse = horse;
    });
  }

  /**
   * Pass horse to horse-edit component, on successful edit navigate to current page
   * @param horse to be edited
   */
  selectHorseEdit(horse: HorseDetail) {
    const horseEdit: Horse = new Horse(horse.id, horse.name, horse.description, horse.birthDate,
      horse.isMale, horse.breed, horse.father.id, horse.mother.id);
    this.horseService.emitSelectedHorseEdit(horseEdit);
    const currentUrl = this.router.url;
    $('#successEditModal').on('hidden.bs.modal', () =>  this.router.navigateByUrl('/', {skipLocationChange: true})
      .then(() => {
        this.router.navigate([currentUrl]);
      })
    );
  }

  /**
   * Pass horse to horse-delete component, on successful delete return to horse-list component
   * @param horse to be deleted
   */
  selectHorseDelete(horse: HorseDetail){
    this.horseService.emitSelectedHorseDelete(horse);
    // @ts-ignore
    $('#successDeleteModal').on('hidden.bs.modal', () =>  this.router.navigateByUrl('horses/list'));
  }


}
