import {Breed} from './breed';

export class Horse {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public birthDate: Date,
    public isMale: boolean,
    public breed: Breed,
    public fatherId: number,
    public motherId: number){}
}
