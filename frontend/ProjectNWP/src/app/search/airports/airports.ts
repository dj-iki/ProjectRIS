import { Component, inject } from '@angular/core';
import { AirportResponse, SearchService } from '../../services/search-service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-airports',
  imports: [RouterModule],
  templateUrl: './airports.html',
  styleUrl: './airports.css',
})
export class Airports {
  public airportsError: string = ""
  public airports: AirportResponse[] = []

  private http = inject(SearchService)
  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private fromAirport: number = -1
  private numberOfSeats: number = -1
  private departureDate: string = ""
  private returningDate: string = ""

  private params = this.route.queryParams.subscribe(p => {
    if(p["returningDate"] == ""){
      const httpParams = new HttpParams()
        .set("fromAirport", +p["fromAirport"])
        .set("numberOfSeats", +p["numberOfSeats"])
        .set("departureDate", p["departureDate"])
        .set("idCity", +p["idCity"])
      
      this.fromAirport = +p["fromAirport"]
      this.departureDate = p["departureDate"]
      this.numberOfSeats = +p["numberOfSeats"]

      this.airportsSub?.unsubscribe()

      this.airportsSub = this.http.getAirportsTo(httpParams).subscribe({
        next: (res) => {
          this.airports = res
        },
        error: (err) => {
          this.airportsError = err?.error?.message || "Failed to load airports"
        }
      })
    }else{
      const httpParams = new HttpParams()
        .set("fromAirport", +p["fromAirport"])
        .set("numberOfSeats", +p["numberOfSeats"])
        .set("departureDate", p["departureDate"])
        .set("returningDate", p["returningDate"])
        .set("idCity", +p["idCity"])
      
      this.fromAirport = +p["fromAirport"]
      this.departureDate = p["departureDate"]
      this.numberOfSeats = +p["numberOfSeats"]

      this.airportsSub?.unsubscribe()

      this.airportsSub = this.http.getReturningAirports(httpParams).subscribe({
        next: (res) => {
          this.airports = res
        },
        error: (err) => {
          this.airportsError = err?.error?.message || "Failed to load airports"
        }
      })
    }
  })

  private airportsSub?: { unsubscribe(): void}

  redirect(airportId: number){
    this.router.navigate(['/flights'], {
        queryParams: {
          fromAirport: this.fromAirport,
          toAirport: airportId,
          numberOfSeats: this.numberOfSeats,
          departureDate: this.departureDate,
          returningDate: this.returningDate
        }
      });
  }
}
