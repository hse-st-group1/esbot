import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessageInput } from '../message-input/message-input';
import { SessionService } from '../../services/session.service';
import { MessageCard } from '../message-card/message-card';

@Component({
  selector: 'app-session',
  standalone: true,
  imports: [CommonModule, MessageInput, MessageCard],
  templateUrl: './session.html',
  styleUrl: './session.scss'
})
export class SessionComponent {
  public sessionService = inject(SessionService);

  onNewMessage(text: string) {
    this.sessionService.addMessage(text, true);
  }
}