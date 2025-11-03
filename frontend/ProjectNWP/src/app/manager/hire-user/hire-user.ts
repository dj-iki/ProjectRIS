import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ManagerService } from '../../services/manager-service';

@Component({
  selector: 'app-hire-user',
  imports: [ReactiveFormsModule],
  templateUrl: './hire-user.html',
  styleUrl: './hire-user.css',
})
export class HireUser {

  public form: FormGroup = new FormGroup({})
  public message: string = ""
  public error: string = ""

  private fb = inject(FormBuilder)
  private http = inject(ManagerService)

  ngOnInit(){
    this.form = this.fb.group({
      username: ["", [Validators.required]]
    })
  }

  submit(){
    this.error = ""
    this.message = ""
    if(this.form.invalid) return

    const username = this.form.value.username
    this.http.hireUser({username}).subscribe({
      next: (res) => {
        this.message = res.message
        this.form.reset()
        alert(this.message)
      },error: (err) => {
        this.error = err?.error?.message || "Failed to hire user"
        alert(this.error)
      }
    })
  }
  
  reqMsg(ctrl: string = "username"): string | null {
    const c = this.form.get(ctrl);
    return (c && c.hasError('required') && (c.touched || c.dirty)) ? 'This field is required.' : null;
  }
}
