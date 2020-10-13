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
    BreedAddComponent
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
