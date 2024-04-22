import { type IFileInfo } from '@/shared/model/file-info.model';

import { type AffiliatedType } from '@/shared/model/enumerations/affiliated-type.model';
export interface IAffiliatedInfo {
  id?: string;
  affiliatedType?: keyof typeof AffiliatedType;
  seq?: string | null;
  name?: string;
  address?: string | null;
  telNo?: string | null;
  homepageUrl?: string | null;
  fileInfo?: IFileInfo | null;
}

export class AffiliatedInfo implements IAffiliatedInfo {
  constructor(
    public id?: string,
    public affiliatedType?: keyof typeof AffiliatedType,
    public seq?: string | null,
    public name?: string,
    public address?: string | null,
    public telNo?: string | null,
    public homepageUrl?: string | null,
    public fileInfo?: IFileInfo | null,
  ) {}
}
