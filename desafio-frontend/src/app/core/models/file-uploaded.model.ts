export interface FileUploadedModel {
  data: File;
  inProgress: boolean;
  progress: number;
  alreadyUploaded: boolean;
}
