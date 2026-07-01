import { Injectable, inject } from '@angular/core';
import { LOCAL_STORAGE, SESSION_STORAGE, StorageService } from 'ngx-webstorage-service';
import { StorageModel } from '../models/storage.model';

@Injectable({ providedIn: 'root' })
export class SessionStorageService {
  private storage: StorageService = inject(LOCAL_STORAGE);
 
  save(value: StorageModel): void {
    this.storage.set(value.key, value.value);
  }

  load(key: string): StorageModel | undefined {
    const value = this.storage.get(key);
    return value ? new StorageModel(key, value) : undefined;
  }

  remove(value: StorageModel): void {
    this.storage.remove(value.key);
  }
}