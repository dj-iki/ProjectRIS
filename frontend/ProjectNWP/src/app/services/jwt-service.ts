import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  private _loggedIn$ = new BehaviorSubject<boolean>(false)
  readonly loggedIn$ = this._loggedIn$.asObservable()

  private _roles$ = new BehaviorSubject<string[]>([])
  readonly roles$ = this._roles$.asObservable()
  
  constructor(){
    this._loggedIn$.next(!this.isExpired(this.get()))
    
    window.addEventListener('storage', (e) => {
      if(e.key === "jwt"){
         this._loggedIn$.next(!this.isExpired(this.get()))
      }
    })
  }


  get(): string | null {
    return localStorage.getItem("jwt")
  }

  set(jwt: string){
    localStorage.setItem("jwt", jwt);
    this._loggedIn$.next(!this.isExpired(jwt));
  }

  clear(){
    localStorage.removeItem("jwt")
    this._loggedIn$.next(false)
  }

  isExpired(jwt: string | null = this.get()): boolean{
    if (jwt == null) return true;
    const payload = jwtDecode<{ exp?: number}>(jwt)
    if(!payload?.exp) return true;
    if(payload?.exp * 1000 <= Date.now()){
      this.clear()
      return true
    }
    return false
  }

  getRoles(): string[]{
    const jwt = this.get()
    if( jwt == null || this.isExpired()) return []
    const payload = jwtDecode<{ roles?: string[]}>(jwt)
    if(payload.roles == null) return []
    return payload.roles
  }

  hasRole(role: string): boolean{
    return this.getRoles().includes(role)
  }

  hasAnyRole(...roles: string[]): boolean{
    const mine = this.getRoles()
    return roles.some(r => mine.includes(r))
  }
  

}
