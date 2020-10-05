import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Breed} from '../dto/breed';
import {environment} from 'src/environments/environment';

const baseUri = environment.backendUrl + 'breeds';

@Injectable({
  providedIn: 'root'
})
export class BreedService {
  constructor(private httpClient: HttpClient) {
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
   * Loads specific breed from the backend
   * @param id of breed to load
   */
  getAllBreeds(): Observable<Breed[]> {
    console.log('Getting all breeds');
    return this.httpClient.get<Breed[]>(baseUri);
  }

}
