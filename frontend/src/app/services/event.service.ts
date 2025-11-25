import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface EventDto {
  id: number;
  name: string;
  availableTickets: number;
}

@Injectable({ providedIn: 'root' })
export class EventService {
  private base = '/api/events';
  constructor(private http: HttpClient) {}

  getEvent(id: number): Observable<EventDto> {
    return this.http.get<EventDto>(`${this.base}/${id}`);
  }

  book(eventId: number, count: number) {
    return this.http.post(`${this.base}/${eventId}/book?count=${count}`, {});
  }
}