import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { provideZoneChangeDetection } from '@angular/core';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { routes } from './app/app.routes';
import { appConfig} from './app/app.config';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(), // Provides HttpClient with SSR compatibility
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(withEventReplay())
  ]
}).catch(err => console.error(err));
