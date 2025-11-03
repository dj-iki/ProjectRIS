import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FlightResponse } from './search-service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  
  private http = inject(HttpClient)

  getFlights(): Observable<FlightResponse[]>{
    return this.http.get<FlightResponse[]>("http://localhost:8080/ProjectRIS/api/employee/flights")
  }

}
