import { Component, inject } from '@angular/core'; // 'inject' hinzugefügt, Output entfernt
import { FormsModule } from '@angular/forms'; 
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button'; 
import { MatIconModule } from '@angular/material/icon';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-message-input',
  standalone: true, 
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './message-input.html',
  styleUrl: './message-input.scss',
})
export class MessageInput {
  text = '';
  
  // 1. Wir holen uns den SessionService direkt in die Komponente
  private readonly sessionService = inject(SessionService);

  send() {
    // 2. Diagnose-Log: Jetzt siehst du sofort in F12, ob der Klick ankommt
    console.log('MessageInput send() aufgerufen! Text:', this.text);

    // Verhindert das Abschicken von leeren Nachrichten (nur Leerzeichen)
    if (this.text.trim()) {
      
      // 3. Nachricht direkt in den Service pushen
      this.sessionService.addMessage(this.text, true);
      
      // Eingabefeld leeren
      this.text = ''; 
    }
  }
}