import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ManagerService } from '../../services/manager-service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-add-plane',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './add-plane.html',
  styleUrl: './add-plane.css',
})
export class AddPlane {

  public form: FormGroup = new FormGroup({})
  public message: string = ""
  public error: string = ""
  
  private fb = inject(FormBuilder)
  private http = inject(ManagerService)

  ngOnInit(){
    this.form = this.fb.group({
      registrationNumber: ["", [Validators.required]],
      manufacturer: ["", [Validators.required]],
      model: ["",[Validators.required]],
      numberOfEconomySeats: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      numberOfBusinessSeats: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      numberOfEconomyPlusSeats: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      economyPrice: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      economyPlusPrice: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      businessPrice: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      numberOfEconomyPlusRows: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      numberOfEconomyRows: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
      numberOfBusinessRows: this.fb.control<number>(0,{validators: [Validators.required, Validators.min(1)]}),
    })
  }


  submit(){
    this.error = ""
    this.message = ""
    if(this.form.invalid) return

    const {registrationNumber, manufacturer, model, numberOfEconomySeats, economyPrice, numberOfEconomyRows,
      numberOfEconomyPlusSeats, economyPlusPrice, numberOfEconomyPlusRows, numberOfBusinessSeats,
      businessPrice, numberOfBusinessRows
    } = this.form.value
    this.http.addPlane({
      registrationNumber, manufacturer, model, numberOfEconomySeats, economyPrice, numberOfEconomyRows,
      numberOfEconomyPlusSeats, economyPlusPrice, numberOfEconomyPlusRows, numberOfBusinessSeats,
      businessPrice, numberOfBusinessRows
    }).subscribe({
      next: (res) => {
        this.message = res.message
        this.form.reset()
        alert(this.message)
      },
      error: (err) => {
        this.error = err?.error?.message || "Failed to add plane"
        alert(this.error)
      }
    })
  }

  reqError(ctrl: string): boolean {
    const c = this.form.get(ctrl);
    return !!c && c.hasError('required') && (c.touched || c.dirty);
  }

  minError(ctrl: string): number | null {
    const c = this.form.get(ctrl);
    if (!c || !(c.touched || c.dirty) || !c.errors?.['min']) return null;
    return c.errors['min'].min as number;   // returns required min (e.g., 1)
  }

}
