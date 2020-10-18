import {EventEmitter, Injectable} from '@angular/core';
import {Horse} from '../dto/horse';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {BehaviorSubject, Observable, ReplaySubject} from 'rxjs';
import {formatDate} from '@angular/common';
import {HorseDetail} from '../dto/horse-detail';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  private messageBaseUri: string = environment.backendUrl + 'horses';
  public onHorseSelectEdit: EventEmitter<Horse> = new EventEmitter<Horse>();
  public onHorseSelectDelete: EventEmitter<Horse> = new EventEmitter<Horse>();
  public onHorseDelete = new EventEmitter<Horse>();

  public onHorseAddSource = new ReplaySubject<Horse>(1);

    // new BehaviorSubject<Horse>(new Horse(null, null, null, null, null, new Breed(null, null, null), null, null));
  private onInitHorseListSource = new BehaviorSubject<Horse[]>(null);

  public onInitHorseList$ = this.onInitHorseListSource.asObservable();
  public onHorseAdd$ = this.onHorseAddSource.asObservable();


  constructor(private httpClient: HttpClient) {
  }

  emitHorseList(horse: Horse[]) {
    this.onInitHorseListSource.next(horse);
  }

  emitNewHorse(horse: Horse) {
    this.onHorseAddSource.next(horse);
  }

  emitDeleteHorse(horse: Horse) {
    this.onHorseDelete.emit(horse);
  }

  emitSelectedHorseEdit(horse: any) {
    this.onHorseSelectEdit.emit(horse);
  }

  emitSelectedHorseDelete(horse: any) {
    this.onHorseSelectDelete.emit(horse);
  }

  /**
   * Add new horse to the database
   * @param horse to be added to the database
   */
  addHorse(horse: Horse){
    console.log('Adding horse');
    const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};
    return this.httpClient.post(this.messageBaseUri, horse, httpOptions);
  }

  /**
   * Update specific horse from the backend
   * @param horse contains the updated values
   */
  updateHorse(horse: Horse) {
    console.log('Edit horse ' + horse.id);
    const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};
    return this.httpClient.put(this.messageBaseUri + '/' + horse.id, horse, httpOptions);
  }

  /**
   * Delete specific horse from the backend
   * @param id of the horse to delete
   */
  deleteHorse(id: number): Observable<void> {
    console.log('Deleting horse ' + id);
    // @ts-ignore
    return this.httpClient.delete(this.messageBaseUri + '/' + id);
  }

  /**
   * Loads specific horse from the backend
   * @param id of horse to load
   */
  getHorseById(id: number): Observable<Horse> {
    console.log('Load horse ' + id);
    return this.httpClient.get<Horse>(this.messageBaseUri + '/' + id);
  }

  /**
   * Loads specific horse from the backend
   * @param id of horse to load
   */
  getHorseDetailsById(id: number): Observable<HorseDetail> {
    console.log('Load horse details for ' + id);
    return this.httpClient.get<HorseDetail>(this.messageBaseUri + '/' + id + '/details');
  }

  /**
   * Finds all horses that fulfill the parameter from the database
   */
  findHorses(horse: Horse): Observable<Horse[]>{
    if (horse != null) {
      console.log('Finding horses');
      const httpParamsOptions = {
        params: new HttpParams()
          .set('name', horse.name)
          .set('description', horse.description)
          .set('birthDate', formatDate(
            horse.birthDate == null || horse.birthDate.toString().length === 0 ?
              new Date().toISOString().slice(0, 10) : horse.birthDate, 'yyyy-MM-dd', 'en-US'))
          .set('isMale', String(horse.isMale))
          .set('breedId', horse.breed != null ? String(horse.breed.id) : '')
      };
      return this.httpClient.get<Horse[]>(this.messageBaseUri, httpParamsOptions);
    } else {
      console.log('Getting horses');
      return this.httpClient.get<Horse[]>(this.messageBaseUri);
    }

  }

  /**
   * Gets all horses from the database
   */
  getHorseList(): Observable<Horse[]> {
    return this.findHorses(null);
  }
}
