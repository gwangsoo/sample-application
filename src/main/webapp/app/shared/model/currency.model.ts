export interface ICurrency {
  id?: string;
  name?: string;
}

export class Currency implements ICurrency {
  constructor(
    public id?: string,
    public name?: string,
  ) {}
}
