import { Component, inject } from '@angular/core';
import { EmployeeService } from '../../services/employee-service';
import { FlightResponse } from '../../services/search-service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-employee',
  imports: [DatePipe],
  templateUrl: './employee.html',
  styleUrl: './employee.css',
})
export class Employee {

  public flights: FlightResponse[] = []
  public httpError: string = ""

  private http = inject(EmployeeService)

  ngOnInit(){
    this.http.getFlights().subscribe({
      next: (res) => {
        this.flights = res
      },error: (err) => {
        this.httpError = err?.error?.message || "There is no flights asinged to you"
        alert(this.httpError)
      }
    })
  }

}
