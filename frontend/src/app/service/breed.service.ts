import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, ReplaySubject} from 'rxjs';
import {Breed} from '../dto/breed';
import {environment} from 'src/environments/environment';

const baseUri = environment.backendUrl + 'breeds';

@Injectable({
  providedIn: 'root'
})
export class BreedService {

  constructor(private httpClient: HttpClient) {
  }

  private onInitBreedListSource = new BehaviorSubject<Breed[]>(null);
  public onInitBreedList$ = this.onInitBreedListSource.asObservable();


  /**
   * Communications between components
   */
  emitBreedList(breed: Breed[]) {
    this.onInitBreedListSource.next(breed);
  }

  /**
   * Loads specific breed from the backend
   * @param id of breed to load
   */
  getBreedById(id: number): Observable<Breed> {
    console.log('Load breed details for ' + id);
    return this.httpClient.get<Breed>(baseUri + '/' + id);
  }

  /**
   * Add new breed to the database
   * @param breed to be added to the database
   */
  addBreed(breed: Breed) {
    console.log('Adding breed');
    const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};
    return this.httpClient.post(baseUri, breed, httpOptions);
  }

  /**
   * Loads all breeds from the backend
   */
  getBreedList(): Observable<Breed[]> {
    console.log('Getting breeds');
    return this.httpClient.get<Breed[]>(baseUri);
  }

}
