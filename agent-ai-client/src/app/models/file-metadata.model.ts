export class FileMetadataModel {
  constructor(
    public filename: string,
    public size?: number,
    public contentType?: string
  ) {
  }
}
