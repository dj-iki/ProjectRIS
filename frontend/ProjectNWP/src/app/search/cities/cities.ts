import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { City, SearchService } from '../../services/search-service';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-cities',
  imports: [RouterModule],
  templateUrl: './cities.html',
  styleUrl: './cities.css',
})
export class Cities {

  public citiesError: string = ""
  public cities: City[] = []

  private route = inject(ActivatedRoute)
  private http = inject(SearchService)
  private router = inject(Router)
  private fromAirport: number = -1
  private numberOfSeats: number = -1
  private departureDate: string = ""
  private returningDate: string = ""

  private params = this.route.queryParams.subscribe( p => {
    if(p["returningDate"] == ""){

      const httpParams = new HttpParams()
        .set("fromAirport", +p["fromAirport"])
        .set("numberOfSeats", +p["numberOfSeats"])
        .set("departureDate", p["departureDate"])
        .set("idCountry", +p["idCountry"])

      this.fromAirport = +p["fromAirport"]
      this.numberOfSeats = +p["numberOfSeats"]
      this.departureDate = p["departureDate"]

      this.citiesSub?.unsubscribe

      this.citiesSub = this.http.getCities(httpParams).subscribe({
        next: (res) => {
          this.cities = res
        },
        error: (err) =>{
          this.citiesError = err?.error?.message || "Failed loading cities"
        }
      })
    }else{
      const httpParams = new HttpParams()
        .set("fromAirport", +p["fromAirport"])
        .set("numberOfSeats", +p["numberOfSeats"])
        .set("departureDate", p["departureDate"])
        .set("returningDate", p["returningDate"])
        .set("idCountry", +p["idCountry"])

      this.fromAirport = +p["fromAirport"]
      this.numberOfSeats = +p["numberOfSeats"]
      this.departureDate = p["departureDate"]
      this.returningDate = p["returningDate"]

      this.citiesSub?.unsubscribe

      this.citiesSub = this.http.getReturningCities(httpParams).subscribe({
        next: (res) => {
          this.cities = res
        },
        error: (err) =>{
          this.citiesError = err?.error?.message || "Failed loading cities"
        }
      })
    }
  })

  private citiesSub?: { unsubscribe(): void }


  redirect(cityId: number){
    this.router.navigate(["/airports"],{
      queryParams: {
        fromAirport: this.fromAirport,
        numberOfSeats: this.numberOfSeats,
        departureDate: this.departureDate,
        returningDate: this.returningDate,
        idCity: cityId
      }
    })
  }

  ngOnDestroy(){
    this.citiesSub?.unsubscribe
    this.params.unsubscribe
  }
}
