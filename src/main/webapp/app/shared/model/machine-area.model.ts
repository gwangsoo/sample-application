import { type ICompetition } from '@/shared/model/competition.model';

export interface IMachineArea {
  id?: string;
  mame?: string;
  seq?: number;
  numOfMachine?: number | null;
  competition?: ICompetition | null;
}

export class MachineArea implements IMachineArea {
  constructor(
    public id?: string,
    public mame?: string,
    public seq?: number,
    public numOfMachine?: number | null,
    public competition?: ICompetition | null,
  ) {}
}
