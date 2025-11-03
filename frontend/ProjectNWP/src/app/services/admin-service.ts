import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { City, Country } from './search-service';
import { Response } from './account-service';


interface Bundle{
  cities: City[]
  countries: Country[]
  airlines: Airlines[]
}

export interface Airlines{
  id: number
  name: string
}

interface Airport{
  name: string
  iataCode: string
  icaoCode: string
  cityId: number
}

interface Airline{
  name: string
  iataCode: string
  icaoCode: string
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  
  private http = inject(HttpClient)

  getData(): Observable<Bundle>{
    return this.http.get<Bundle>("http://localhost:8080/ProjectRIS/api/admin/data")
  }

  saveCountry(params: HttpParams): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/admin/save-country", null, {params})
  }

  saveCity(params: HttpParams): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/admin/save-city", null, {params})
  }

  saveAirport(payload: Airport): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/admin/save-airport", payload)
  }

  saveAirline(payload: Airline): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/admin/save-airline", payload)
  }

  promoteEmployee(params: HttpParams): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/admin/promote-employee", null, {params})
  }

}
