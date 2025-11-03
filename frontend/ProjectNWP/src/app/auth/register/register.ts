import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Demo } from '../../services/auth';
import { JwtService } from '../../services/jwt-service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  public form: FormGroup = new FormGroup({});
  public serverError: string | null = null;

  constructor(
    private fb: FormBuilder,
    private http: Demo,
    private router: Router,
    private jwtService: JwtService
  ){
    this.createForm();
  }

  createForm(){
    this.form = this.fb.group({
      username : ["", [Validators.required]],
      password : ["", [Validators.required, Validators.minLength(4), Validators.pattern("^[a-zA-Z0-9@#$%^&+=!_-]+$")]],
      email : ["", [Validators.required, Validators.email]],
      name : ["", [Validators.required]],
      surname : ["", [Validators.required]]
    })
  }

  submit(){
    this.serverError = null;
    this.form.markAllAsTouched()
    if(this.form.invalid){
      return;
    }
    const {username, password, email, name, surname} = this.form.value;
    this.http.register({username: username!, password: password!, email: email!, name: name!, surname: surname!}).subscribe({
      next: (res) => {
        this.jwtService.set(res.jwt);
        this.router.navigateByUrl("/");
      },
      error: (err) =>{
        this.serverError = err?.error?.message || "Registration failed.";
      }
    });
  }

  getError(ctrl: 'username' | 'password' | 'email' | 'name' | 'surname'): string | null {
    const c = this.form.get(ctrl);
    if (!c || !c.invalid || (!c.touched && !c.dirty)) return null;

    const e = c.errors || {};

    if (e['required']) {
      const labels: Record<string,string> = {
        username: 'Username', password: 'Password',
        email: 'Email', name: 'Name', surname: 'Surname'
      };
      return labels[ctrl] + " is required";
    }

    if (ctrl === 'password') {
      if (e['minlength']) {
        const { requiredLength, actualLength } = e['minlength'];
        return `Password must be at least ${requiredLength} characters (you have ${actualLength}).`;
      }
      if (e['pattern']) {
        return 'Password contains invalid characters (allowed: letters, digits, @ # $ % ^ & + = ! _ -).';
      }
    }

    if (ctrl === 'email') {
      if (e['email']) return 'Please enter a valid email address.';
    }

    return 'Invalid value.';
  }

}
