import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { JwtService } from '../services/jwt-service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  
  const router = inject(Router)
  const jwtService = inject(JwtService)

  return next(req).pipe(
    catchError((err: HttpErrorResponse) => {
      if(err.status === 401){
        jwtService.clear()
        router.navigateByUrl("/login")
      }else if(err.status === 403){
        router.navigateByUrl("/")
      }
      return throwError(()=>err)
    })
  )
};
