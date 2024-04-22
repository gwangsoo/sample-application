import { type IDivision } from '@/shared/model/division.model';

export interface IEventPoint {
  id?: string;
  seq?: number;
  rating?: number;
  ratingMin?: number | null;
  ratingMax?: number | null;
  division?: IDivision | null;
}

export class EventPoint implements IEventPoint {
  constructor(
    public id?: string,
    public seq?: number,
    public rating?: number,
    public ratingMin?: number | null,
    public ratingMax?: number | null,
    public division?: IDivision | null,
  ) {}
}
