import { Component, inject } from '@angular/core';
import { Button } from '../button/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    Button,
    MatIconModule,
    MatListModule
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class Sidebar {
  // Aktuell leer – reiner UI-Container
  sessionService = inject(SessionService);
}