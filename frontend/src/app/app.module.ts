import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {BreedComponent} from './component/breed/breed.component';
import {HttpClientModule} from '@angular/common/http';
import { HorseComponent } from './component/horse/horse.component';
import { HorseAddComponent } from './component/horse/horse-add/horse-add.component';
import {FormsModule} from '@angular/forms';
import { HorseEditComponent } from './component/horse/horse-edit/horse-edit.component';
import { HorseListComponent } from './component/horse/horse-list/horse-list.component';
import { HorseDeleteComponent } from './component/horse/horse-delete/horse-delete.component';
import { BreedAddComponent } from './component/breed/breed-add/breed-add.component';
import { BreedListComponent } from './component/breed/breed-list/breed-list.component';
import { HorseDetailsComponent } from './component/horse/horse-details/horse-details.component';
import { HorseFamilyTreeComponent } from './component/horse/horse-family-tree/horse-family-tree.component';
import { HorseFamilyTreeElementComponent } from './component/horse/horse-family-tree-element/horse-family-tree-element.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BreedComponent,
    HorseComponent,
    HorseAddComponent,
    HorseEditComponent,
    HorseListComponent,
    HorseDeleteComponent,
    BreedAddComponent,
    BreedListComponent,
    HorseDetailsComponent,
    HorseFamilyTreeComponent,
    HorseFamilyTreeElementComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
