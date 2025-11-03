import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AirportResponse {
  id: number;
  name: string;
  iataCode: string;
}

export interface FlightResponse{
  idFlight: number;
  departureTime: string;
  arrivalTime: string;
  flightNumber: string;
  airportFromId: number;
  airportToId: number;
  airportFromName: string;
  airportToName: string;
  airportFromIata: string;
  airportToIata: string;
  airlinesName: string;
  airlinesIataCode: string;
}

interface Bundle{
  responseTo: FlightResponse[];
  responseReturning: FlightResponse[];
}

export interface Country{
  countryId: number
  name: string
}

export interface City{
  cityId: number
  name: String
}

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  constructor(
    private http: HttpClient
  ){}

  getAirports(): Observable<AirportResponse[]>{
    return this.http.get<AirportResponse[]>("http://localhost:8080/ProjectRIS/api/search/airports");
  }

  getFlights(params: HttpParams): Observable<FlightResponse[]>{
    return this.http.get<FlightResponse[]>("http://localhost:8080/ProjectRIS/api/search/flights", {params});
  }

  getReturning(params: HttpParams): Observable<Bundle>{
    return this.http.get<Bundle>("http://localhost:8080/ProjectRIS/api/search/returning", {params})
  }

  getCountries(params: HttpParams): Observable<Country[]>{
    return this.http.get<Country[]>("http://localhost:8080/ProjectRIS/api/search/countries", {params});
  }

  getReturningCountries(params: HttpParams): Observable<Country[]>{
    return this.http.get<Country[]>("http://localhost:8080/ProjectRIS/api/search/returning-countries", {params})
  }

  getCities(params: HttpParams): Observable<City[]>{
    return this.http.get<City[]>("http://localhost:8080/ProjectRIS/api/search/cities",{params})
  }
  
  getReturningCities(params: HttpParams): Observable<City[]>{
    return this.http.get<City[]>("http://localhost:8080/ProjectRIS/api/search/returning-cities",{params})
  }

  getAirportsTo(params: HttpParams): Observable<AirportResponse[]>{
    return this.http.get<AirportResponse[]>("http://localhost:8080/ProjectRIS/api/search/airports-to", {params})
  }

  getReturningAirports(params: HttpParams): Observable<AirportResponse[]>{
    return this.http.get<AirportResponse[]>("http://localhost:8080/ProjectRIS/api/search/returning-airports", {params})
  }
}
