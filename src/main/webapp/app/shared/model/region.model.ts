import { type ICountry } from '@/shared/model/country.model';

export interface IRegion {
  id?: string;
  name?: string;
  parentAreaId?: string;
  country?: ICountry | null;
}

export class Region implements IRegion {
  constructor(
    public id?: string,
    public name?: string,
    public parentAreaId?: string,
    public country?: ICountry | null,
  ) {}
}
