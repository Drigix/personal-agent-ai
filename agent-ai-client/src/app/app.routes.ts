import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/agent-page/agent-page.component').then(m => m.AgentPageComponent),
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login-page/login-page.component').then(m => m.LoginPageComponent),
  }
];
