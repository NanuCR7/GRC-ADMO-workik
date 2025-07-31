import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './shared/guards/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },

  { path: 'risk-assessment', loadChildren: () => import('./risk-assessment/risk-assessment.module').then(m => m.RiskAssessmentModule), canActivate: [AuthGuard] },

  { path: 'ae-management', loadChildren: () => import('./ae-management/ae-management.module').then(m => m.AeManagementModule), canActivate: [AuthGuard] },

  { path: 'risk-performance', loadChildren: () => import('./risk-performance/risk-performance.module').then(m => m.RiskPerformanceModule), canActivate: [AuthGuard] },

  { path: 'reviews', loadChildren: () => import('./reviews/reviews.module').then(m => m.ReviewsModule), canActivate: [AuthGuard] },

  { path: 'dashboard', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule), canActivate: [AuthGuard] },

  { path: 'action-plan', loadChildren: () => import('./action-plan/action-plan.module').then(m => m.ActionPlanModule), canActivate: [AuthGuard] },

  { path: 'worklog', loadChildren: () => import('./worklog/worklog.module').then(m => m.WorklogModule), canActivate: [AuthGuard] },

  { path: 'user-management', loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule), canActivate: [AuthGuard] },

  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
