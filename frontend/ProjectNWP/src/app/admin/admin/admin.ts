import { Component, inject } from '@angular/core';
import { AdminService, Airlines } from '../../services/admin-service';
import { City, Country } from '../../services/search-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpParams } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-admin',
  imports: [ReactiveFormsModule],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
export class Admin {
  public cities: City[] = []
  public countries: Country[] = []
  public airlines: Airlines[] = []
  public httpError: string = ""
  public message: string = ""

  public formCountry: FormGroup = new FormGroup({})
  public formCity: FormGroup = new FormGroup({})
  public formAirport: FormGroup = new FormGroup({})
  public formAirlines: FormGroup = new FormGroup({})
  public formPromote: FormGroup = new FormGroup({})


  private http = inject(AdminService)
  private fb = inject(FormBuilder)

  ngOnInit(){
    this.loadData()

    this.formCountry = this.fb.group({
      name: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]]
    })

    this.formCity = this.fb.group({
      name: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]],
      country: this.fb.control<number>(0,{validators: [Validators.required]})
    })

    this.formAirport = this.fb.group({
      name: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]],
      iataCode: ["", [Validators.required, Validators.pattern("[A-Z]{3}")]],
      icaoCode: ["", [Validators.required, Validators.pattern("[A-Z]{4}")]],
      city: this.fb.control<number>(0, [Validators.required])
    })

    this.formAirlines = this.fb.group({
      name: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]],
      iataCode: ["", [Validators.required, Validators.pattern("[A-Z]{2}")]],
      icaoCode: ["", [Validators.required, Validators.pattern("[A-Z]{3}")]]
    })

    this.formPromote = this.fb.group({
      username: ["", [Validators.required]],
      airlines: this.fb.control<number>(0, [Validators.required])
    })

  }

  loadData(){
    this.http.getData().subscribe({
      next: (res) => {
        this.cities = res.cities
        this.airlines = res.airlines
        this.countries = res.countries
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to fetch data"
        alert(this.httpError)
      }
    })
  }

  submitCountry(){
    this.message = ""
    this.httpError = ""
    this.formCountry.markAllAsTouched()
    if(this.formCountry.invalid) return

    const name = this.formCountry.value.name

    const params = new HttpParams().set("country_name", name)

    this.http.saveCountry(params).subscribe({
      next: (res) => {
        this.message = res.message
        alert(this.message)
        this.formCountry.reset()
        this.loadData()
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to add country"
        alert(this.httpError)
      }
    })
    
  }

  submitCity(){
    this.message = ""
    this.httpError = ""
    this.formCity.markAllAsTouched()
    if(this.formCity.invalid) return

    const name = this.formCity.value.name
    const country = this.formCity.value.country

    const params = new HttpParams().set("city_name", name).set("country", country)

    this.http.saveCity(params).subscribe({
      next: (res) => {
        this.message = res.message
        alert(this.message)
        this.formCity.reset()
        this.loadData()
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to add city"
        alert(this.httpError)
      }
    })
  }
  
  submitAirport(){
    this.message = ""
    this.httpError = ""
    this.formAirport.markAllAsTouched()
    if(this.formAirport.invalid) return

    const {name, iataCode, icaoCode, city} = this.formAirport.value

    this.http.saveAirport({name, iataCode, icaoCode, cityId: city}).subscribe({
      next: (res) => {
        this.message = res.message
        alert(this.message)
        this.formAirport.reset()
        this.loadData()
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to add airport"
        alert(this.httpError)
      }
    })
  }
  
  submitAirlines(){
    this.message = ""
    this.httpError = ""
    this.formAirlines.markAllAsTouched()
    if(this.formAirlines.invalid) return

    const { name, iataCode, icaoCode } = this.formAirlines.value

    this.http.saveAirline({name, iataCode, icaoCode}).subscribe({
      next: (res) => {
        this.message = res.message
        alert(this.message)
        this.formAirlines.reset()
        this.loadData()
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to add airlines"
        alert(this.httpError)
      }
    })
  }
  
  submitPromote(){
    this.message = ""
    this.httpError = ""
    this.formPromote.markAllAsTouched()
    if(this.formPromote.invalid) return

    const username = this.formPromote.value.username
    const airline = this.formPromote.value.airlines

    const params = new HttpParams().set("username", username).set("airline", airline)

    this.http.promoteEmployee(params).subscribe({
      next: (res) => {
        this.message = res.message
        alert(this.message)
        this.formPromote.reset()
        this.loadData()
      },
      error: (err) => {
        this.httpError = err?.error?.message || "Failed to promote employee"
        alert(this.httpError)
      }
    })
  }

  getError(f: FormGroup, ctrl: string): string | null {
    const c = f.get(ctrl);
    if (!c || !(c.touched || c.dirty) || !c.errors) return null;

    if (c.errors['required']) return 'This field is required.';
    if (c.errors['pattern']) {
      if (ctrl === 'iataCode') return 'IATA must be uppercase, exact length required.';
      if (ctrl === 'icaoCode') return 'ICAO must be uppercase, exact length required.';
      return 'Invalid format.';
    }
    return 'Invalid value.';
  }

}
