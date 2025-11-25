import { Component, OnInit } from '@angular/core';
import { EventService, EventDto } from '../../services/event.service';
import { MatDialog } from '@angular/material/dialog';
import { BookDialogComponent } from '../book-dialog/book-dialog.component';

@Component({ selector: 'app-event-list', templateUrl: './event-list.component.html' })
export class EventListComponent implements OnInit {
  events: EventDto[] = [];
  ids = [1,2,3];

  constructor(private svc: EventService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.events = [];
    this.ids.forEach(id => {
      this.svc.getEvent(id).subscribe({
        next: e => this.events.push(e),
        error: () => {}
      });
    });
  }

  openBook(event: EventDto) {
    const ref = this.dialog.open(BookDialogComponent, { data: event });
    ref.afterClosed().subscribe(result => {
      if (result === 'success') this.loadAll();
    });
  }
}