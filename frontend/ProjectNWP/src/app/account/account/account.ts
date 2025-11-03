import { Component, inject } from '@angular/core';
import { AccountRequest, AccountResponse, AccountService } from '../../services/account-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-account',
  imports: [ReactiveFormsModule],
  templateUrl: './account.html',
  styleUrl: './account.css',
})
export class Account {
  public account: AccountResponse | null = null
  public formName: FormGroup = new FormGroup ({})
  public formSurname: FormGroup = new FormGroup ({})
  public formUsername: FormGroup = new FormGroup ({})
  public formEmail: FormGroup = new FormGroup ({})
  public formPassword: FormGroup = new FormGroup ({})
  public formDelete: FormGroup = new FormGroup ({})
  public errorAccount: string = ""
  public errorUpdate: string = ""
  public message: string = ""

  public showName: boolean = false;
  public showSurname: boolean = false;
  public showEmail: boolean = false;
  public showUsername: boolean = false;
  public showPassword: boolean = false;
  public showDelete: boolean = false;
  
  private http = inject(AccountService)
  private fb = inject(FormBuilder)
  private formSub?: Subscription

  private params?: Subscription


  ngOnInit(){
    this.params = this.http.getAccout().subscribe({
      next: (res) => {
        this.account = res
      },
      error: (err) => {
        this.errorAccount = err?.error?.message || "Failed to fetch account"
      }
    })
    this.formName = this.fb.group({
      name: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]]
    })
    this.formSurname = this.fb.group({
      surname: ["", [Validators.required, Validators.pattern("[A-Z][a-z -]+")]]
    })
    this.formEmail = this.fb.group({
      email: ["", [Validators.required, Validators.email]]
    })
    this.formUsername = this.fb.group({
      username: ["", [Validators.required]]
    })
    this.formPassword = this.fb.group({
      oldPassword: ["", [Validators.required, Validators.minLength(4), Validators.pattern("^[a-zA-Z0-9@#$%^&+=!_-]+$")]],
      newPassword: ["", [Validators.required, Validators.minLength(4), Validators.pattern("^[a-zA-Z0-9@#$%^&+=!_-]+$")]]
    })
    this.formDelete = this.fb.group({
      oldPassword: ["", [Validators.required, Validators.minLength(4), Validators.pattern("^[a-zA-Z0-9@#$%^&+=!_-]+$")]]
    })

  }

  public updateName(){
    this.errorUpdate = ""
    this.message = ""
    if(this.formName.invalid) {
      this.errorUpdate = "invalid"
      return
    }
    const name = this.formName.value.name

    const req : AccountRequest = {
      newUsername: "",
      name: name,
      surname: "",
      email: "",
      newPassword: "",
      oldPassword: ""
    }

    this.formSub?.unsubscribe()

    this.formSub = this.http.updateName(req).subscribe({
      next: (res) => {
        this.message = res.message
      },
      error: (err) => {
        this.errorUpdate = err?.error?.message || "Failed to update name"
      }
    })
  }

  public updateSurname(){
    this.errorUpdate = ""
    this.message = ""
    if(this.formSurname.invalid) {
      this.errorUpdate = "invalid"
      return
    }
    const surname = this.formSurname.value.surname

    const req : AccountRequest = {
      newUsername: "",
      name: "",
      surname: surname,
      email: "",
      newPassword: "",
      oldPassword: ""
    }

    this.formSub?.unsubscribe()

    this.formSub = this.http.updateSurname(req).subscribe({
      next: (res) => {
        this.message = res.message
      },
      error: (err) => {
        this.errorUpdate = err?.error?.message || "Failed to update surname"
      }
    })
  }
  public updateEmail(){
    this.errorUpdate = ""
    this.message = ""
    if(this.formEmail.invalid) {
      this.errorUpdate = "invalid"
      return
    }
    const email = this.formEmail.value.email

    const req : AccountRequest = {
      newUsername: "",
      name: "",
      surname: "",
      email: email,
      newPassword: "",
      oldPassword: ""
    }

    this.formSub?.unsubscribe()

    this.formSub = this.http.updateEmail(req).subscribe({
      next: (res) => {
        this.message = res.message
      },
      error: (err) => {
        this.errorUpdate = err?.error?.message || "Failed to update email"
      }
    })
  }
  public updateUsername(){
    this.errorUpdate = ""
    this.message = ""
    if(this.formUsername.invalid) {
      this.errorUpdate = "invalid"
      return
    }
    const username = this.formUsername.value.username

    const req : AccountRequest = {
      newUsername: username,
      name: "",
      surname: "",
      email: "",
      newPassword: "",
      oldPassword: ""
    }

    this.formSub?.unsubscribe()

    this.formSub = this.http.updateUsername(req).subscribe({
      next: (res) => {
        this.message = res.message
      },
      error: (err) => {
        this.errorUpdate = err?.error?.message || "Failed to update username"
      }
    })
  }
  public updatePassword(){
    this.errorUpdate = ""
    this.message = ""
    if(this.formPassword.invalid) {
      this.errorUpdate = "invalid"
      return
    }
    
    const newPassword = this.formPassword.value.newPassword
    const oldPassword = this.formPassword.value.oldPassword

    const req : AccountRequest = {
      newUsername: "",
      name: "",
      surname: "",
      email: "",
      newPassword: newPassword,
      oldPassword: oldPassword
    }

    this.formSub?.unsubscribe()

    this.formSub = this.http.updatePassword(req).subscribe({
      next: (res) => {
        this.message = res.message
      },
      error: (err) => {
        this.errorUpdate = err?.error?.message || "Failed to update password"
      }
    })
  }
  public delete(){
    this.errorUpdate = ""
    this.message = ""
    if(this.formDelete.invalid) {
      this.errorUpdate = "invalid"
      return
    }
    const oldPassword = this.formDelete.value.oldPassword

    const req : AccountRequest = {
      newUsername: "",
      name: "",
      surname: "",
      email: "",
      newPassword: "",
      oldPassword: oldPassword
    }

    this.formSub?.unsubscribe()

    this.formSub = this.http.delete(req).subscribe({
      next: (res) => {
        this.message = res.message
      },
      error: (err) => {
        this.errorUpdate = err?.error?.message || "Failed to update name"
      }
    })
  }



  toggleName(){
    this.showName = !this.showName
  }

  toggleSurname(){
    this.showSurname = !this.showSurname
  }

  toggleEmail(){
    this.showEmail = !this.showEmail
  }

  toggleUsername(){
    this.showUsername = !this.showUsername
  }

  togglePassword(){
    this.showPassword = !this.showPassword
  }

  toggleDelete(){
    this.showDelete = !this.showDelete
  }

  ngOnDestroy(){
    this.params?.unsubscribe()
    this.formSub?.unsubscribe()
  }

  getError(form: FormGroup, ctrl: string): string | null {
    const c = form.get(ctrl);
    if (!c || !(c.touched || c.dirty) || !c.errors) return null;

    const e = c.errors;
    if (e['required']) return 'This field is required.';
    if (e['email']) return 'Please enter a valid email address.';
    if (e['minlength']) {
      const { requiredLength, actualLength } = e['minlength'];
      return `Minimum length is ${requiredLength} (you have ${actualLength}).`;
    }
    if (e['pattern']) return 'Invalid format.';
    return 'Invalid value.';
  }

}
