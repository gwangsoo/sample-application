export interface IFileInfo {
  id?: string;
  originalName?: string;
  mimeType?: string;
  fileSize?: number | null;
  savedPath?: string;
}

export class FileInfo implements IFileInfo {
  constructor(
    public id?: string,
    public originalName?: string,
    public mimeType?: string,
    public fileSize?: number | null,
    public savedPath?: string,
  ) {}
}
