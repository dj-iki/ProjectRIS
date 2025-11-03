import { Component, inject } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { BookingService, SeatResponse, TicketRequest } from '../../services/booking-service';
import { HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-booking',
  imports: [ReactiveFormsModule,RouterModule],
  templateUrl: './booking.html',
  styleUrl: './booking.css',
})
export class Booking {
  public form: FormGroup = new FormGroup({})
  public numberOfSeats: number = 0
  public fromFlightId: number = -1
  public returningFlightId: number = -1
  public seatFrom: SeatResponse[] = []
  public seatReturning: SeatResponse[] = []
  public errorSeatFrom: string = ""
  public errorSeatReturning: string = ""
  public errorBooking: string = ""


  private fb = inject(FormBuilder)
  private route = inject(ActivatedRoute)
  private http = inject(BookingService)
  private router = inject(Router)
  private params?: Subscription
  


  ngOnInit(){
    this.form = this.fb.group({
      ticketsFrom: this.fb.array([]),
      ticketsReturning: this.fb.array([])
    })

    this.params = this.route.queryParams.subscribe(p => {
    
    if(p["returningFlight"] == null){
      this.fromFlightId = +p["fromFlight"]
      this.numberOfSeats = +p["numberOfSeats"]
      for(let i=0; i< this.numberOfSeats; i++){
        this.addTicketFrom()
      }
      const param = new HttpParams().set("flightId", +p["fromFlight"])
      this.http.getSeats(param).subscribe({
        next: (res) => {
          this.seatFrom = res
        },
        error: (err) => {
          this.errorSeatFrom = err?.error?.message || "Failed loading seats from" 
          alert(this.errorSeatFrom)
        }
      })
    }else{
      this.fromFlightId = +p["fromFlight"]
      this.returningFlightId = +p["returningFlight"]
      this.numberOfSeats = +p["numberOfSeats"]
      for(let i=0; i< this.numberOfSeats; i++){
        this.addTicketFrom()
        this.addTicketReturning()
      }
      const param1 = new HttpParams().set("flightId", +p["fromFlight"])
      this.http.getSeats(param1).subscribe({
        next: (res) => {
          this.seatFrom = res
        },
        error: (err) => {
          this.errorSeatFrom = err?.error?.message || "Failed loading seats from" 
          alert(this.errorSeatFrom)
        }
      })
      const param2 = new HttpParams().set("flightId", +p["returningFlight"])
      this.http.getSeats(param2).subscribe({
        next: (res) => {
          this.seatReturning = res
        },
        error: (err) => {
          this.errorSeatReturning = err?.error?.message || "Failed loading seats returning" 
          alert(this.errorSeatReturning)
        }
      })
    }
  })
  }

  

  makeTicket(): FormGroup{
    return this.fb.group({
      name: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]],
      surname: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]],
      passportNumber: ["", [Validators.required]],
      seatId: this.fb.control<number | null>(null,{validators: [Validators.required]}),
      baggage: ["", [Validators.required]]
    })
  }

  get ticketsFrom(): FormArray{
    return this.form.get("ticketsFrom") as FormArray
  }

  get ticketsReturning(): FormArray{
    return this.form.get("ticketsReturning") as FormArray
  }

  addTicketFrom(){
    this.ticketsFrom.push(this.makeTicket())
  }

  addTicketReturning(){
    this.ticketsReturning.push(this.makeTicket())
  }

  onSubmit(){
    const ticketsFrom: TicketRequest[] = this.ticketsFrom.value
    this.form.markAllAsTouched()
    if(ticketsFrom.some(t => t.seatId == null)) return
    if(this.returningFlightId == -1){
      this.http.book({flightIdFrom: this.fromFlightId!, flightIdReturning: null, numberOfSeats: this.numberOfSeats, ticketsFromDTO: ticketsFrom, ticketsReturningDTO: null}).subscribe({
        next: (res) => {
          this.router.navigateByUrl("/")
        },
        error: (err) => {
          this.errorBooking = err?.error?.message || "Error booking tickets for flight"
          alert(this.errorBooking)
        }
      })
    }else{
      const ticketsReturning: TicketRequest[] = this.ticketsReturning.value
      if(ticketsReturning.some(t => t.seatId == null)) return
      this.http.book({flightIdFrom: this.fromFlightId!, flightIdReturning: this.returningFlightId, numberOfSeats: this.numberOfSeats, ticketsFromDTO: ticketsFrom, ticketsReturningDTO: ticketsReturning}).subscribe({
        next: (res) => {
          this.router.navigateByUrl("/")
        },
        error: (err) => {
          this.errorBooking = err?.error?.message || "Error booking tickets for flight"
          alert(this.errorBooking)
        }
      })
    }
  }

  ngOnDestroy(){
    this.params?.unsubscribe()
  }

  getError(c: AbstractControl | null): string | null {
    if (!c || !(c.touched || c.dirty) || !c.errors) return null;
    const e = c.errors;
    if (e['required']) return 'This field is required.';
    if (e['pattern']) return 'Invalid format.';
    if (e['minlength']) {
      const { requiredLength, actualLength } = e['minlength'];
      return `Minimum length is ${requiredLength} (you have ${actualLength}).`;
    }
    return 'Invalid value.';
  }

}
