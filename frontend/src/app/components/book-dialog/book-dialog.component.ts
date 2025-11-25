import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EventService } from '../../services/event.service';

@Component({ selector: 'app-book-dialog', templateUrl: './book-dialog.component.html' })
export class BookDialogComponent {
  count = 1;
  loading = false;
  message: string | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<BookDialogComponent>,
    private svc: EventService
  ) {}

  book() {
    if (this.count <= 0) { this.message = 'Count must be > 0'; return; }
    this.loading = true;
    this.svc.book(this.data.id, this.count).subscribe({
      next: (res: any) => {
        this.loading = false;
        this.message = res?.message || 'Success';
        this.dialogRef.close('success');
      },
      error: err => {
        this.loading = false;
        const msg = err?.error?.message || 'Error while booking';
        this.message = msg;
      }
    });
  }
}