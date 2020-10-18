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

  selectHorseEdit(horse: any) {
    this.horseService.emitSelectedHorseEdit(horse);
  }

  selectHorseDelete(horse: any){
    this.horseService.emitSelectedHorseDelete(horse);
    // @ts-ignore
    $('#successDeleteModal').on('hidden.bs.modal', () => this.router.navigateByUrl('horses/list'));
  }


}
