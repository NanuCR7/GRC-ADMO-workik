import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router, private snackBar: MatSnackBar) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          // auto logout if 401 response returned from api
          localStorage.removeItem('auth_token');
          this.router.navigate(['/auth/login']);
          this.snackBar.open('Session expired. Please login again.', 'Close', { duration: 5000 });
        } else if (err.status === 403) {
          this.snackBar.open('Access denied.', 'Close', { duration: 5000 });
        } else if (err.status >= 400 && err.status < 500) {
          this.snackBar.open(err.error?.error || 'Request error', 'Close', { duration: 5000 });
        } else {
          this.snackBar.open('Server error occurred.', 'Close', { duration: 5000 });
        }
        return throwError(() => err);
      })
    );
  }
}
