import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent {
  /** Based on the screen size, switch from standard to one column per row */
  generalTitle = 'Welcome to Automation-Test'
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({ matches }) => {
      if (matches) {
        return [
          { title: this.generalTitle, cols: 1, rows: 1 },
          { title: 'Scripts', cols: 1, rows: 1 },
          { title: 'Results', cols: 1, rows: 1 },
          { title: 'Configuration', cols: 1, rows: 1 }
        ];
      }

      return [
        { title: this.generalTitle, cols: 2, rows: 1 },
        { title: 'Card 2', cols: 1, rows: 2 },
        { title: 'Card 3', cols: 1, rows: 1 },
        { title: 'Card 4', cols: 1, rows: 1 }
      ];
    })
  );

  constructor(private breakpointObserver: BreakpointObserver) {}
}
