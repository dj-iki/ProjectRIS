import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validator, Validators } from '@angular/forms';
import { Demo } from '../../services/auth';
import { Router, RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { JwtService } from '../../services/jwt-service';


@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: true,
})
export class Login {
  public form: FormGroup = new FormGroup({});
  serverError: string | null = null;


  constructor(
    private fb: FormBuilder,
    private service: Demo,
    private router: Router,
    private jwtService: JwtService

  ) {
    this.createForm();
  }

  createForm(){
    this.form = this.fb.group({
      username: ["", [Validators.required]],
      password: ["", [Validators.required, Validators.minLength(4), Validators.pattern("^[a-zA-Z0-9@#$%^&+=!_-]+$")]]
    });
  }
  


  submit(){
    this.serverError = null;
    this.form.markAllAsTouched();
    if (this.form.invalid){
      return;
    }
    const {username, password} = this.form.value;
    this.service.login({username: username!, password: password!}).subscribe({
      next: (res) => {
        this.jwtService.set(res.jwt);
        this.router.navigateByUrl("/");
      },
      error: (err) => {
        this.serverError = err?.error?.message || "Login failed. Please check your credentials.";
        alert(this.serverError)
      }
    });
  }
 
  getError(ctrl: 'username' | 'password'): string | null {
    const c = this.form.get(ctrl);
    if (!c || !c.invalid || (!c.touched && !c.dirty)) return null;

    const e = c.errors || {};
    if (e['required']) return ctrl === 'username'
      ? 'Username is required.'
      : 'Password is required.';

    if (e['minlength']) {
      const req = e['minlength'].requiredLength;
      const have = e['minlength'].actualLength;
      return `Password must be at least ${req} characters (you have ${have}).`;
    }

    if (e['pattern']) {
      return 'Password contains invalid characters.';
    }

    return 'Invalid value.';
  }

}
