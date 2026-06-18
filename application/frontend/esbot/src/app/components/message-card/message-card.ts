import { Component, input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-message-card',
  imports: [MatCardModule],
  templateUrl: './message-card.html',
  styleUrl: './message-card.scss',
})
export class MessageCard {
  content = input.required<string>();
  isMe = input.required<boolean>();
}
