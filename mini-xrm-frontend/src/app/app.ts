import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterOutlet, RouterLinkWithHref } from '@angular/router';

@Component({
	selector: 'app-root',
	imports: [RouterOutlet, RouterLinkWithHref, MatButton, MatToolbar],
	templateUrl: './app.html',
	styleUrls: ['./app.scss']
})
export class App {
	protected readonly title = signal('mini-xrm-frontend');
}
