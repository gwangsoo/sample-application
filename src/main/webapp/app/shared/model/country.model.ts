import { type IFileInfo } from '@/shared/model/file-info.model';

export interface ICountry {
  id?: string;
  name?: string;
  image?: IFileInfo | null;
}

export class Country implements ICountry {
  constructor(
    public id?: string,
    public name?: string,
    public image?: IFileInfo | null,
  ) {}
}
