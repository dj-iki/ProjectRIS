import { Component, inject } from '@angular/core';
import { FlightResponse, SearchService } from '../../services/search-service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-flight',
  imports: [ReactiveFormsModule, DatePipe, RouterModule],
  templateUrl: './flight.html',
  styleUrl: './flight.css',
  standalone: true,
})
export class Flight {
  public flights: FlightResponse[] = [];
  public returning: FlightResponse[] = [];
  public error: string | null = null;
  public form: FormGroup = new FormGroup({});

  private numberOfSeats: number = 0;

  ngOnInit(){
    this.createFrom();
  }

  private http = inject(SearchService);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);
  private router = inject(Router)
  


  createFrom(){
    this.form = this.fb.group({
      fromFlight: this.fb.control<number>(-1, {validators: [Validators.required]}),
      returningFlight: this.fb.control<number | null>(null)
    })
  }

  private params = this.route.queryParams.subscribe(p => {
    this.numberOfSeats = +p["numberOfSeats"];
    if(p["returningDate"] == ""){
      const params = new HttpParams()
        .set("fromAirport", +p["fromAirport"])
        .set("toAirport", +p["toAirport"])
        .set("numberOfSeats", +p["numberOfSeats"])
        .set("departureDate", p["departureDate"] as string)
      
      this.flightSub?.unsubscribe();

      this.flightSub = this.http.getFlights(params).subscribe({
        next: (res) => {
          this.error = null;
          this.flights = res;
        },
        error: (err) => {
          this.error = err?.error?.message || "Failed to load flights";
          this.flights = [];
          alert(this.error)
        }
      });
    }
    else{
      const params = new HttpParams()
        .set("fromAirport", +p["fromAirport"])
        .set("toAirport", +p["toAirport"])
        .set("numberOfSeats", +p["numberOfSeats"])
        .set("departureDate", p["departureDate"] as string)
        .set("returningDate", p["returningDate"] as string)
      
      this.flightSub?.unsubscribe();

      this.flightSub = this.http.getReturning(params).subscribe({
        next: (res) => {
          this.error = null;
          this.flights = res.responseTo;
          this.returning = res.responseReturning;
        },
        error: (err) => {
          this.error = err?.error?.message || "Failed to load flights";
          this.flights = [];
          this.returning = [];
          alert(this.error)
        }
      });
    }
  });

  private flightSub?: { unsubscribe(): void };

  submit(){
    this.form.markAllAsTouched()
    if(this.form.invalid) return;
    const {fromFlight, returningFlight} = this.form.value;
    
    if(returningFlight == null){
      this.router.navigate(['/booking'],{
              queryParams: {
                fromFlight: fromFlight,
                numberOfSeats: this.numberOfSeats
              }
            })
    }else{
      this.router.navigate(['/booking'],{
              queryParams: {
                fromFlight: fromFlight,
                returningFlight: returningFlight,
                numberOfSeats: this.numberOfSeats
              }
            })
    }
  }

  ngOnDestroy(){
    this.params.unsubscribe();
    this.flightSub?.unsubscribe();
  }

  isReq(ctrl: string) {
    const c = this.form.get(ctrl);
    return !!c && c.hasError('required') && (c.touched || c.dirty);
  }

}
