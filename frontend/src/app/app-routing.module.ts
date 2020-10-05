import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BreedComponent} from './component/breed/breed.component';
import {HorseComponent} from './component/horse/horse.component';


const routes: Routes = [
  {path: '', redirectTo: 'breeds', pathMatch: 'full'},
  {path: 'breeds', component: BreedComponent},
  {path: 'horses', component: HorseComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
