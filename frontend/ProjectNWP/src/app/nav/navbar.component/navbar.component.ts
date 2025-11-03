import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { JwtService } from '../../services/jwt-service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [RouterModule, AsyncPipe],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {

  private router = inject(Router)
  public jwtService = inject(JwtService)

  loggedIn$ = this.jwtService.loggedIn$

  logout(){
    this.jwtService.clear()
    this.router.navigateByUrl("/")
  }

}
