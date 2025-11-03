import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AirportResponse } from './search-service';
import { Response } from './account-service';

export interface Plane{
  planeId: number
  manufacturer: string
  model: string
  registrationNumber: string
}

export interface Employee{
  id: number
  name: string
  surname: string
}

interface Bundle{
  airports: AirportResponse[]
  planes: Plane[]
  employees: Employee[]
}

interface FlightRequest{
  fromAirport: number
  toAirport: number
  departure: string
  arrival: string
  employees: number[]
  plane: number
}

interface PlaneRequest{
  registrationNumber: string
  manufacturer: string
  model: string
  numberOfEconomySeats: number
  economyPrice: number
  numberOfEconomyRows: number
  numberOfEconomyPlusSeats: number
  economyPlusPrice: number
  numberOfEconomyPlusRows: number
  numberOfBusinessSeats: number
  businessPrice: number
  numberOfBusinessRows: number
}

interface HireUserRequest{
  username: string
}

export interface FlightResponseManager{
  id: number
  departureTime: string
  arrivalTime: string
  airportFrom: string
  airportTo: string
}

interface DelayRequest{
  flightId: number
  departure: string
  arrival: string
}

interface CancelRequest{
  id: number
}

@Injectable({
  providedIn: 'root'
})
export class ManagerService {
  
  private http = inject(HttpClient)

  getParams(): Observable<Bundle>{
    return this.http.get<Bundle>("http://localhost:8080/ProjectRIS/api/manager/flight")
  }

  addFlight(payload: FlightRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/manager/save-flight", payload)
  }

  addPlane(payload: PlaneRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/manager/add-plane", payload)
  }

  hireUser(payload: HireUserRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/manager/hire-user", payload)
  }

  getFlight(): Observable<FlightResponseManager[]>{
    return this.http.get<FlightResponseManager[]>("http://localhost:8080/ProjectRIS/api/manager/flights")
  }

  delayFlight(payload: DelayRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/manager/delay", payload)
  }

  cancelFlight(payload: CancelRequest): Observable<Response>{
    return this.http.post<Response>("http://localhost:8080/ProjectRIS/api/manager/cancel", payload)
  }
}
