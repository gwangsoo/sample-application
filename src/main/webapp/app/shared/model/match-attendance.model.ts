import { type IEntry } from '@/shared/model/entry.model';
import { type IMatchTeam } from '@/shared/model/match-team.model';

import { type AttendanceStatusType } from '@/shared/model/enumerations/attendance-status-type.model';
export interface IMatchAttendance {
  id?: string;
  attendanceStatusType?: keyof typeof AttendanceStatusType;
  attendanceDateTime?: Date | null;
  entry?: IEntry | null;
  matchTeam?: IMatchTeam | null;
}

export class MatchAttendance implements IMatchAttendance {
  constructor(
    public id?: string,
    public attendanceStatusType?: keyof typeof AttendanceStatusType,
    public attendanceDateTime?: Date | null,
    public entry?: IEntry | null,
    public matchTeam?: IMatchTeam | null,
  ) {}
}
