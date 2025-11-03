import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AccountResponse{
  username: string
  name: string
  surname: string
  email: string
}

export interface AccountRequest{
  newUsername: string;
  name: string
  surname: string
  email: string
  newPassword: string
  oldPassword: string
}

export interface Response{
  message: string
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  private http = inject(HttpClient)

  getAccout(): Observable<AccountResponse> {
    return this.http.get<AccountResponse>("http://localhost:8080/ProjectRIS/api/account/account")
  }

  updateName(payload: AccountRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/account/update-name", payload)
  }

  updateSurname(payload: AccountRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/account/update-surname", payload)
  }

  updateUsername(payload: AccountRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/account/update-username", payload)
  }

  updateEmail(payload: AccountRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/account/update-email", payload)
  }

  updatePassword(payload: AccountRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/account/update-password", payload)
  }

  delete(payload: AccountRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/account/delete", payload)
  }

}
