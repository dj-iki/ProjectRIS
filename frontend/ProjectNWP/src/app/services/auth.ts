import { Inject, inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface LoginRequest {
   username: string;
   password: string;
}
interface LoginResponse { 
  jwt: string;
}
interface RegisterRequest { 
  username: string,
  password: string,
  email:string,
  name:string,
  surname:string
}

@Injectable({
  providedIn: 'root'
})
export class Demo {
  apiBase = "http://localhost:8080/ProjectRIS/api"

  constructor(
    private http: HttpClient
  ) {}

  login(payload: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiBase + "/auth/login", payload);
  }

  register(payload: RegisterRequest): Observable<LoginResponse>{
    return this.http.post<LoginResponse>(this.apiBase + "/auth/register", payload);
  }

} 
