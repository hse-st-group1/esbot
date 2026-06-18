import { Component } from '@angular/core';
import { MatButtonToggleModule } from '@angular/material/button-toggle';

@Component({
  selector: 'app-mode-selector',
  imports: [MatButtonToggleModule],
  templateUrl: './mode-selector.html',
  styleUrl: './mode-selector.css',
})
export class ModeSelector {}
