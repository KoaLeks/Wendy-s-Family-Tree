import {Breed} from './breed';
import {Horse} from './horse';

export class HorseDetail {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public birthDate: Date,
    public isMale: boolean,
    public breed: Breed,
    public father: Horse,
    public mother: Horse){}
}
