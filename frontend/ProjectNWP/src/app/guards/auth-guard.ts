import { CanActivateFn, Router, UrlTree } from '@angular/router';
import { JwtService } from '../services/jwt-service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (): boolean | UrlTree => {

  const jwtService = inject(JwtService)
  const router = inject(Router)

  const ok = !jwtService.isExpired()

  return ok ? true : router.parseUrl("/login")
};
