import { Injectable } from '@angular/core';
import {Horse} from '../dto/horse';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  private messageBaseUri: string = environment.backendUrl + 'horses';

  constructor(private httpClient: HttpClient) {
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
  updateHorse(horse: Horse){
    console.log('Edit horse ' + horse.id);
    const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};
    return this.httpClient.put(this.messageBaseUri + '/' + horse.id, horse, httpOptions);
  }

  /**
   * Delete specific horse from the backend
   * @param id of the horse to delete
   */
  deleteHorse(id: number): void{
    console.log('Deleting horse ' + id);
    this.httpClient.delete(this.messageBaseUri + '/' + id).subscribe();
  }

  /**
   * Gets all horses from the database
   */
  getHorseList(): Observable<Horse[]> {
    console.log('Getting horses');
    return this.httpClient.get<Horse[]>(this.messageBaseUri);
  }
}
