import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { Sidebar } from './components/sidebar/sidebar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { Toolbar } from './components/toolbar/toolbar';
import { MessageInput } from './components/message-input/message-input';
import { MessageCard } from './components/message-card/message-card';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, 
    MatButtonModule, 
    Sidebar,
    MatSidenavModule,
    Toolbar,
    MessageInput,
    MessageCard],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  sessionService = inject(SessionService);
  protected readonly sidebarOpen = signal(true);
  constructor() {
    (window as any).changeUID = () => {
      return this.sessionService.changeUID();
    };
    (window as any).initUID = () => {
      return this.sessionService.initUID();
    };
  }
}
