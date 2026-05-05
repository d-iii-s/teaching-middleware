import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { DefaultService } from '../backend/api/default.service';
import { UserBase } from '../backend/model/userBase';

@Component ({
  selector: 'app-list',
  standalone: true,
  imports: [ RouterLink ],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent implements OnInit {
  public users = signal<UserBase []> ([]);
  constructor (private backend: DefaultService) { }
  ngOnInit (): void {
    this.backend.readUsers ().subscribe (users => this.users.set (users));
  }
}
