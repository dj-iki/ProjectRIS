import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SearchService, AirportResponse, FlightResponse} from '../../services/search-service';
import { Router, RouterModule } from '@angular/router';


@Component({
  selector: 'app-search',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './search.html',
  styleUrl: './search.css',
})
export class Search {
  public formGroup: FormGroup = new FormGroup({});
  public serverErrorAirport: string | null = null;
  public serverErrorFlights: string | null = null;
  public airports: AirportResponse[] = [];

  constructor(
    private http: SearchService,
    private fb: FormBuilder,
    private router: Router
  ){
    this.getAirports();
    this.createForm();
  }

  createForm(){
    this.formGroup = this.fb.group({
      airportFrom: this.fb.control<AirportResponse | null>(null, {validators: [Validators.required]}),
      airportTo: this.fb.control<AirportResponse | null>(null),
      dateFrom: ["", [Validators.required]],
      dateReturning: ["", []],
      numSeats: this.fb.control<number>(1, {validators: [Validators.required, Validators.min(1)]})
    })
  }

  getAirports(){
    this.http.getAirports().subscribe({
      next: (res) => {
        this.airports = res;
      },
      error: (err) => {
        this.serverErrorAirport = err?.error?.message || "Error fetching airports";
        alert(this.serverErrorAirport)
      }
    })
  }

  byId = (a: AirportResponse | null, b: AirportResponse | null) =>
    !!a && !!b && a.id === b.id;

  submit(){
    this.formGroup.markAllAsTouched()
    if(this.formGroup.invalid) {
      return;
    }
    const {airportFrom, airportTo, dateFrom, dateReturning, numSeats} = this.formGroup.value;
    console.log(this.formGroup.value)
    if(airportTo != null){
      this.router.navigate(['/flights'], {
        queryParams: {
          fromAirport: airportFrom,
          toAirport: airportTo,
          numberOfSeats: numSeats,
          departureDate: dateFrom,
          returningDate: dateReturning
        }
      });
    }else{
      this.router.navigate(['/countries'], {
        queryParams: {
          fromAirport: airportFrom,
          numberOfSeats: numSeats,
          departureDate: dateFrom,
          returningDate: dateReturning
        }
      });
    }
  }

  isReq(ctrl: string) {
    const c = this.formGroup.get(ctrl);
    return !!c && c.hasError('required') && (c.touched || c.dirty);
  }
  minErr(ctrl: string): number | null {
    const c = this.formGroup.get(ctrl);
    return c && c.errors?.['min'] && (c.touched || c.dirty) ? c.errors['min'].min : null;
  }

}
