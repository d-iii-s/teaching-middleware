import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { DefaultService } from '../backend/api/default.service';
import { User } from '../backend/model/user';

@Component ({
  selector: 'app-user',
  standalone: true,
  imports: [ FormsModule, RouterLink ],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {

  public editing = false;
  public user = signal<User | undefined> (undefined);

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private backend: DefaultService) { }

  ngOnInit (): void {
    const idString = this.route.snapshot.paramMap.get ('id');
    if (idString != null) {
      const idNumber = Number.parseInt (idString);
      this.backend.readUser (idNumber).subscribe (user => this.user.set (user));
    }
  }

  save (): void {
    this.editing = false;
    const idString = this.route.snapshot.paramMap.get ('id');
    const user = this.user ();
    if (idString != null && user != null) {
      const idNumber = Number.parseInt (idString);
      this.backend.updateUser (idNumber, user).subscribe ();
    }
  }

  delete (): void {
    this.editing = false;
    const idString = this.route.snapshot.paramMap.get ('id');
    if (idString != null) {
      const idNumber = Number.parseInt (idString);
      this.backend.deleteUser (idNumber).subscribe (() => this.router.navigate (['/list']));
    }
  }
}
