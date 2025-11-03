import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface SeatResponse{
  seatId: number
  seatNumber: string
  _class: string
}

interface BookingRequest{
  flightIdFrom: number
  flightIdReturning: number | null
  numberOfSeats: number
  ticketsFromDTO: TicketRequest[]
  ticketsReturningDTO: TicketRequest[] | null
}

export interface TicketRequest{
  name: string
  surname: string
  passportNumber: string
  seatId: number
  baggage: string
}

interface BookingResponse{
  message: string
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  constructor(
    private http: HttpClient
  ){}

  public getSeats(params: HttpParams): Observable<SeatResponse[]>{
    return this.http.get<SeatResponse[]>("http://localhost:8080/ProjectRIS/api/booking/seats", {params})
  }

  public book(payload: BookingRequest): Observable<BookingResponse>{
    return this.http.post<BookingResponse>("http://localhost:8080/ProjectRIS/api/booking/book", payload)
  }
}
