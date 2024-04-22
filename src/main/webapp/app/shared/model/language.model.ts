export interface ILanguage {
  id?: string;
  name?: string;
}

export class Language implements ILanguage {
  constructor(
    public id?: string,
    public name?: string,
  ) {}
}
