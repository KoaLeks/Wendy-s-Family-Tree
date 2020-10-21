import { Component, Input, OnInit} from '@angular/core';
import {HorseTree} from '../../../dto/horse-tree';
import {HorseService} from '../../../service/horse.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-horse-family-tree-element',
  templateUrl: './horse-family-tree-element.component.html',
  styleUrls: ['./horse-family-tree-element.component.scss']
})
export class HorseFamilyTreeElementComponent implements OnInit {

  @Input() horseFamily: HorseTree[];
  @Input() horseRoot: HorseTree;
  @Input() genCount;
  @Input() maxGenerations;

  constructor(private horseService: HorseService, private router: Router) {

  }

  ngOnInit(): void {
    this.genCount++;
  }

  selectHorseDelete(horse: HorseTree){
    this.horseService.emitSelectedHorseDelete(horse);

    const currentUrl = this.router.url;
    $('#successDeleteModal').on('hidden.bs.modal', () => this.router.navigateByUrl('/', {skipLocationChange: true})
      .then(() => {
        this.router.navigate([currentUrl]);
      })
    );
  }

  getParent(parentId: number): HorseTree {
    return this.horseFamily.find(horse => parentId === horse.id);
  }

  toggleIcon() {
    const icon = document.getElementById('icon' + this.horseRoot.id + this.genCount);
    if (icon.innerText === 'add') {
      icon.innerHTML = 'remove';
    } else {
      icon.innerHTML = 'add';
    }
  }
}
