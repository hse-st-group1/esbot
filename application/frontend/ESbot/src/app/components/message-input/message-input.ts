import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-message-input',
  imports: [MatButtonModule],
  templateUrl: './message-input.html',
  styleUrl: './message-input.css',
})
export class MessageInput {}
