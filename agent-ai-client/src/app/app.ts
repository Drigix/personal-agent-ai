import { Component, signal } from '@angular/core';
import {COMMON_IMPORTS, PRIMENG_MESSAGE_COMPONENTS, PRIMENG_OVERLAY_COMPONENTS} from './shared/primeng-module-import';
import {DIALOG_PROVIDER} from './services/service-provider-import';

@Component({
  selector: 'app-root',
  imports: [COMMON_IMPORTS, PRIMENG_OVERLAY_COMPONENTS, PRIMENG_MESSAGE_COMPONENTS],
  providers: [DIALOG_PROVIDER],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('agent-ai-client');
}
