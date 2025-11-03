import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { JwtService } from '../services/jwt-service';

const NO_AUTH_URLS = ["/search", "/register", "/login", "/flights", "/countries", "/cities", "/airports"]

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  
  if(NO_AUTH_URLS.some(u => { req.url.includes(u)})){
    return next(req)
  }
  
  const jwt = inject(JwtService).get()
  
  if(!jwt){
    return next(req)
  }

  const authReq = req.clone({
    setHeaders: { Authorization: "Bearer " + jwt }
  })
  
  return next(authReq)
};
