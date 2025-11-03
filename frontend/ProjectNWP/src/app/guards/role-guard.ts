import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, UrlTree } from '@angular/router';
import { JwtService } from '../services/jwt-service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot): boolean | UrlTree => {
  const jwtService = inject(JwtService)
  const router = inject(Router)
  const required: string[] = route.data?.["roles"] ?? []

  if(!required.length) return true

  const ok = jwtService.hasAnyRole(...required)

  return ok ? true : router.parseUrl("/");
};
