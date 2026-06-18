import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Sidebar } from './components/sidebar/sidebar';
import { ModeSelector } from './components/mode-selector/mode-selector';
import { ChatWindow } from './components/chat-window/chat-window';
import { MessageInput } from './components/message-input/message-input';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Sidebar, ModeSelector, ChatWindow, MessageInput],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('ESbot');
}
