import { type IMatch } from '@/shared/model/match.model';
import { type IMachineArea } from '@/shared/model/machine-area.model';

import { type MachineStatusType } from '@/shared/model/enumerations/machine-status-type.model';
export interface IMachine {
  id?: string;
  machineNo?: number;
  machineStatusType?: keyof typeof MachineStatusType;
  match?: IMatch | null;
  machineArea?: IMachineArea | null;
}

export class Machine implements IMachine {
  constructor(
    public id?: string,
    public machineNo?: number,
    public machineStatusType?: keyof typeof MachineStatusType,
    public match?: IMatch | null,
    public machineArea?: IMachineArea | null,
  ) {}
}
