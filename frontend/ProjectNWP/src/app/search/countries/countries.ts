import { HttpParams } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Country, SearchService } from '../../services/search-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';


@Component({
  selector: 'app-countries',
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './countries.html',
  styleUrl: './countries.css',
})
export class Countries {
  public form: FormGroup = new FormGroup({})
  public countriesError: string = ""
  public countries: Country[] = []

  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private http = inject(SearchService)
  private fb = inject(FormBuilder)
  private fromAirport: number = 0
  private numberOfSeats: number = 0
  private departureDate: string = ""
  private returningDate: string = ""


  ngOnInit(){
    this.createForm()
  }

  private params = this.route.queryParams.subscribe(p => {
      if(p["returningDate"] == ""){
        const httpParams = new HttpParams()
          .set("fromAirport", +p["fromAirport"])
          .set("numberOfSeats", +p["numberOfSeats"])
          .set("departureDate", p["departureDate"])
        
        this.fromAirport = +p["fromAirport"]
        this.numberOfSeats = +p["numberOfSeats"]
        this.departureDate = p["departureDate"]
        this.countriesSub?.unsubscribe()

        this.countriesSub = this.http.getCountries(httpParams).subscribe({
          next: (res) =>{
            this.countries = res
          },
          error: (err) =>{
            this.countriesError = err?.error?.message || "Error fetching countries"
          }
        })
      }else{
        const httpParams = new HttpParams()
          .set("fromAirport", +p["fromAirport"])
          .set("numberOfSeats", +p["numberOfSeats"])
          .set("departureDate", p["departureDate"])
          .set("returningDate", p["returningDate"])
        
        this.fromAirport = +p["fromAirport"]
        this.numberOfSeats = +p["numberOfSeats"]
        this.departureDate = p["departureDate"]
        this.returningDate = p["returningDate"]

        this.countriesSub?.unsubscribe()
        
        this.countriesSub = this.http.getReturningCountries(httpParams).subscribe({
          next: (res) =>{
            this.countries = res
          },
          error: (err) =>{
            this.countriesError = err?.error?.message || "Error fetching countries"
          }
        })
      }
  })

  private countriesSub?: { unsubscribe(): void }

  createForm(){
    this.form = this.fb.group({
      countryId: this.fb.control<number>(-1,{validators: [Validators.required]})
    })
  }

  redirect(countryId: number){
    this.router.navigate(["/cities"],{
      queryParams: {
        fromAirport: this.fromAirport,
        numberOfSeats: this.numberOfSeats,
        departureDate: this.departureDate,
        returningDate: this.returningDate,
        idCountry: countryId
      }
    })
  }

  ngOnDestroy(){
    this.countriesSub?.unsubscribe()
    this.params.unsubscribe()
  }
}
