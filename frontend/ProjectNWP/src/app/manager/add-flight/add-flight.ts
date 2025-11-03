import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Employee, ManagerService, Plane } from '../../services/manager-service';
import { AirportResponse } from '../../services/search-service';
import { from } from 'rxjs';


@Component({
  selector: 'app-add-flight',
  imports: [ReactiveFormsModule],
  templateUrl: './add-flight.html',
  styleUrl: './add-flight.css',
})
export class AddFlight {
  public form: FormGroup = new FormGroup({})
  public airportsFrom: AirportResponse[] = []
  public airportsTo: AirportResponse[] = []
  public planes: Plane[] = []
  public employees: Employee[] = []
  public httpError: string = ""
  public message: string = ""

  private fb = inject(FormBuilder)
  private http = inject(ManagerService)

  ngOnInit(){
    this.form = this.fb.group({
      departureTime: ["", [Validators.required]],
      arrivalTime: ["", [Validators.required]],
      departureAirport: this.fb.control<number>(0, {validators: [Validators.required]}),
      arrivalAirport: this.fb.control<number>(0, {validators: [Validators.required]}),
      plane: this.fb.control<number>(0,{validators: [Validators.required]}),
      employees: this.fb.control<number[]>([], {validators: [Validators.required]})
    })

    this.httpError = ""

    this.http.getParams().subscribe({
      next: (res) => {
        this.airportsFrom = res.airports
        this.airportsTo = res.airports
        this.employees = res.employees
        this.planes = res.planes
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Error fetching data"
        alert(this.httpError)
      }
    })
  }

  saveFlight(){
    this.message = ""
    this.httpError = ""
    if(this.form.invalid) return

    const { departureTime, arrivalTime, departureAirport, arrivalAirport, plane, employees} = this.form.value
    this.http.addFlight({fromAirport: departureAirport!, toAirport: arrivalAirport!, departure: departureTime!, arrival: arrivalTime!, employees: employees!, plane:plane!}).subscribe({
      next: (res) => {
        this.message = res.message
        this.form.reset()
        alert(this.message)
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to add flight"
        alert(this.httpError)
      }
    })
  }


  isChecked(id: number): boolean{
    return this.form.value.employees!.includes(id)
  }

  toggle(id: number, checked: boolean) {
    const cur = this.form.value.employees ?? [];
    const next = checked ? [...cur, id] : cur.filter((x: number) => x !== id);
    this.form.get('employees')!.setValue(next);
    this.form.get('employees')!.markAsDirty();
  }

  getRequired(form: FormGroup, ctrl: string): string | null {
    const c = form.get(ctrl);
    return (c && c.hasError('required') && (c.touched || c.dirty)) ? 'This field is required.' : null;
  }

}
