import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseService} from '../../../service/horse.service';
import {Horse} from '../../../dto/horse';

@Component({
  selector: 'app-horse-family-tree',
  templateUrl: './horse-family-tree.component.html',
  styleUrls: ['./horse-family-tree.component.scss']
})
export class HorseFamilyTreeComponent implements OnInit {

  @Input() horseId: number;
  horseTree: Horse;
  horseFamily: Horse[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private horseService: HorseService) {
    this.route.params.subscribe(params => {
      this.horseId = params.id;
    });
  }
  ngOnInit(): void {
    if (this.horseId != null && this.horseId !== 0) {
      this.horseService.getHorseById(this.horseId).subscribe(horse => {
        this.horseTree = horse;
      });
    }
  }
  selectHorseDelete(){
    console.log('delete');
    console.log('icon' + this.horseTree.id);
  }
  toggleIcon() {
    const icon = document.getElementById('icon' + this.horseTree.id);
    if (icon.innerText === 'add') {
      icon.innerHTML = 'remove';
    } else {
      icon.innerHTML = 'add';
    }
  }
}
