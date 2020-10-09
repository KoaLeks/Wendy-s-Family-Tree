import {Component, Input, OnInit, Output} from '@angular/core';
import {Horse} from '../../../dto/horse';
import {EventEmitter} from '@angular/core';

@Component({
  selector: 'app-horse-list',
  templateUrl: './horse-list.component.html',
  styleUrls: ['./horse-list.component.scss']
})
export class HorseListComponent implements OnInit {

  @Input() horseListTable: Horse[] = [];
  // @Input() breedMapTable: Map<number, string>;
  @Output() outputHorse: EventEmitter<Horse> = new EventEmitter<Horse>();
  public selectedHorseTable: Horse;

  constructor() { }

  public selectHorse(horse: Horse) {
    this.selectedHorseTable = horse;
    this.outputHorse.emit(this.selectedHorseTable);
  }

  public findParent(id: number): Horse {
    if (id === null || id === 0) {
      return null;
    }
    return this.horseListTable.find(horse => horse.id === id);
  }

  scrollToParent(){
    $('.collapse').on('show.bs.collapse', function(e) {
      const $card = $(this).closest('.card');
      const $open = $($(this).data('parent')).find('.collapse.show');

      let additionalOffset = 0;
      if ($card.prevAll().filter($open.closest('.card')).length !== 0)
      {
        additionalOffset =  $open.height();
      }
      // $('html, body')
      //   .animate({
      //   scrollTop: $card.offset().top - additionalOffset
      // }, 500);
    });
  }

  public displayName(horse: Horse) {
    if (horse == null) {
      return 'undefined';
    }
    return horse.name;
  }
  ngOnInit(): void {
  }

}
