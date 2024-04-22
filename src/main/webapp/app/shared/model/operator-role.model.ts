export interface IOperatorRole {
  id?: string;
  name?: string;
  displayOrder?: number;
}

export class OperatorRole implements IOperatorRole {
  constructor(
    public id?: string,
    public name?: string,
    public displayOrder?: number,
  ) {}
}
