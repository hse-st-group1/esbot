import { Component, inject, OnInit } from '@angular/core';
import { Button } from '../button/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { SessionService } from '../../services/session.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    Button,
    MatIconModule,
    MatListModule,
    DatePipe
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class Sidebar implements OnInit{
  // Aktuell leer – reiner UI-Container
  sessionService = inject(SessionService);

  ngOnInit() {
    // 💡 Startet den Ladevorgang, sobald die Komponente bereit ist
    this.sessionService.getSessions();
  }
}