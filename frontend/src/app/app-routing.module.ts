import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BreedComponent} from './component/breed/breed.component';
import {HorseComponent} from './component/horse/horse.component';
import {HorseAddComponent} from './component/horse/horse-add/horse-add.component';
import {HorseListComponent} from './component/horse/horse-list/horse-list.component';
import {BreedAddComponent} from './component/breed/breed-add/breed-add.component';
import {BreedListComponent} from "./component/breed/breed-list/breed-list.component";


const routes: Routes = [
  {path: '', redirectTo: 'breeds', pathMatch: 'full'},
  {path: 'breeds', component: BreedComponent,
    children: [
      {path: 'add', component: BreedAddComponent},
      {path: 'list', component: BreedListComponent}
    ]},
  {path: 'horses', component: HorseComponent,
    children: [
      {path: 'add', component: HorseAddComponent},
      {path: 'list', component: HorseListComponent}
      // {path: 'tree', component: HorseTreeComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
