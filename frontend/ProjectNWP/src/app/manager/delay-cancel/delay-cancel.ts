import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FlightResponseManager, ManagerService } from '../../services/manager-service';
import { AsyncPipe, DatePipe } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-delay-cancel',
  imports: [ReactiveFormsModule, DatePipe, AsyncPipe],
  templateUrl: './delay-cancel.html',
  styleUrl: './delay-cancel.css',
})
export class DelayCancel {
  public form: FormGroup = new FormGroup({})
  public _flights$ = new BehaviorSubject<FlightResponseManager[]>([])
  readonly flights$ = this._flights$.asObservable() 
  public httpError: string = ""
  public message: string = ""

  private http = inject(ManagerService)
  private fb = inject(FormBuilder)

  ngOnInit(){
    this.form = this.fb.group({
      departureTime: ["", [Validators.required]],
      arrivalTime: ["", [Validators.required]]
    })

    this.http.getFlight().subscribe({
      next: (res) => {
        this.setFlights(res)
      }, error: (err) => {
        this.httpError = err?.error?.message || "Failed to load data"
        alert(this.httpError)
      }
    })
  }

  submit(id: number){
    this.httpError = ""
    this.message = ""
    if(this.form.invalid) return 

    const {departureTime, arrivalTime} = this.form.value
    this.http.delayFlight({flightId: id!, departure: departureTime!, arrival: arrivalTime!}).subscribe({
      next: (res) => {
        this.message = res.message
        alert(this.message)
      }, error: (err) => {
        this.httpError = err?.error?.message || "Failed to delay flight"
        alert(this.httpError)
      }
    })
  }

  cancel(id: number){
    this.http.cancelFlight({id}).subscribe({
      next: (res) => {
        this.message = res.message
        this.removeFlight(id)
        alert(this.message)
      },error: (err) => {
        this.httpError = err?.error?.message || "Failed to cancel flihgt"
        alert(this.httpError)
      }
    })
  }

  setFlights(list: FlightResponseManager[]) {
    this._flights$.next(list)
  }

  removeFlight(id: number){
    this._flights$.next(this._flights$.value.filter( f => f.id !== id))
  }

  reqMsg(ctrl: 'departureTime' | 'arrivalTime'): string | null {
    const c = this.form.get(ctrl);
    return (c && c.hasError('required') && (c.touched || c.dirty)) ? 'This field is required.' : null;
  }
}
