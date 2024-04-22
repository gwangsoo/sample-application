import { type ITeam } from '@/shared/model/team.model';

import { type GenderType } from '@/shared/model/enumerations/gender-type.model';
import { type AttendanceStatusType } from '@/shared/model/enumerations/attendance-status-type.model';
export interface IEntry {
  id?: string;
  entryNo?: string;
  phoenixNo?: string | null;
  cardNo?: string;
  name?: string;
  englishName?: string;
  rating?: number;
  mobileNo?: string | null;
  birthDate?: Date | null;
  email?: string | null;
  genderType?: keyof typeof GenderType;
  attendanceStatusType?: keyof typeof AttendanceStatusType | null;
  team?: ITeam | null;
}

export class Entry implements IEntry {
  constructor(
    public id?: string,
    public entryNo?: string,
    public phoenixNo?: string | null,
    public cardNo?: string,
    public name?: string,
    public englishName?: string,
    public rating?: number,
    public mobileNo?: string | null,
    public birthDate?: Date | null,
    public email?: string | null,
    public genderType?: keyof typeof GenderType,
    public attendanceStatusType?: keyof typeof AttendanceStatusType | null,
    public team?: ITeam | null,
  ) {}
}
