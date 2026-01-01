import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/agent-page/agent-page.component').then(m => m.AgentPageComponent)
  }
];
