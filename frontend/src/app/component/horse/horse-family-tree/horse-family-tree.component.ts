import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseService} from '../../../service/horse.service';
import {HorseTree} from '../../../dto/horse-tree';
import {HorseComponent} from '../horse.component';

@Component({
  selector: 'app-horse-family-tree',
  templateUrl: './horse-family-tree.component.html',
  styleUrls: ['./horse-family-tree.component.scss']
})
export class HorseFamilyTreeComponent implements OnInit {

  horseId;
  maxGenerations = 1;
  horseRoot: HorseTree;
  horseFamily: HorseTree[] = [];

  constructor(private route: ActivatedRoute, private horseService: HorseService, private horseComponent: HorseComponent) {
    this.route.params.subscribe(params => {
      this.horseId = params.id;
    });
  }

  ngOnInit(): void {
  }

  getHorseFamily(horseId: number, maxGenerations: number) {
    this.horseService.getHorseFamilyList(horseId, maxGenerations)
      .subscribe(horseFamily  => {
        this.horseRoot = horseFamily[0];
        this.horseFamily = horseFamily;
    }, error => {
      this.horseComponent.defaultServiceErrorHandling(error);
    });
  }
}
