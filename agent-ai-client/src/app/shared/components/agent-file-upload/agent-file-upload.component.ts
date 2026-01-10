import {Component, inject} from '@angular/core';
import {COMMON_IMPORTS, PRIMENG_BUTTONS_COMPONENTS, PRIMENG_FILE_UPLOAD_COMPONENTS} from '../../primeng-module-import';
import {PrimeNG} from 'primeng/config';
import {MessageService} from 'primeng/api';
import {MESSAGES_PROVIDER} from '../../../services/service-provider-import';

@Component({
  selector: 'agent-file-upload',
  templateUrl: 'agent-file-upload.component.html',
  styleUrls: ['./agent-file-upload.component.scss'],
  standalone: true,
  imports: [COMMON_IMPORTS, PRIMENG_FILE_UPLOAD_COMPONENTS, PRIMENG_BUTTONS_COMPONENTS],
  providers: [MESSAGES_PROVIDER]
})
export class AgentFileUploadComponent {

  files: File[] = [];
  totalSize : number = 0;
  totalSizePercent : number = 0;

  private config = inject(PrimeNG);
  private messageService = inject(MessageService);

  choose(event: any, callback:any): void {
    console.log(callback);
    try {
      callback();
    } catch (e) {
      console.error(e);
    }
  }

  onRemoveTemplatingFile(event: any, file: any, removeFileCallback: any, index: any): void {
    removeFileCallback(event, index);
    this.totalSize -= parseInt(this.formatSize(file.size));
    this.totalSizePercent = this.totalSize / 10;
  }

  onClearTemplatingUpload(clear: any): void {
    clear();
    this.totalSize = 0;
    this.totalSizePercent = 0;
  }

  onTemplatedUpload() {
    this.messageService.add({ severity: 'info', summary: 'Success', detail: 'File Uploaded', life: 3000 });
  }

  onSelectedFiles(event: any): void {
    this.files = event.currentFiles;
    this.files.forEach((file: File) => {
      this.totalSize += parseInt(this.formatSize(file?.size));
    });
    this.totalSizePercent = this.totalSize / 10;
  }

  uploadEvent(callback: any): void {
    callback();
  }

  formatSize(bytes: any): string {
    const k = 1024;
    const dm = 3;
    const sizes = this.config.translation.fileSizeTypes;
    if (bytes === 0) {
      return `0 ${sizes![0]}`;
    }

    const i = Math.floor(Math.log(bytes) / Math.log(k));
    const formattedSize = parseFloat((bytes / Math.pow(k, i)).toFixed(dm));

    return `${formattedSize} ${sizes![i]}`;
  }
}
