import { BrowserModule, DomSanitizer } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
// import {
//   MatAutocompleteModule, MatButtonModule, MatButtonToggleModule, MatPaginatorModule,
//   MatCardModule, MatCheckboxModule, MatChipsModule, MatDatepickerModule,
//   MatDialogModule, MatGridListModule, MatInputModule,
//   MatListModule, MatMenuModule, MatProgressBarModule, MatProgressSpinnerModule,
//   MatRadioModule, MatSelectModule, MatSidenavModule, MatSliderModule, MatSortModule,
//   MatSlideToggleModule, MatSnackBarModule,  MatToolbarModule,
//   MatTooltipModule, 
// } from '@angular/material';

import {MatButtonModule} from '@angular/material/button';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatStepperModule} from '@angular/material/stepper';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';
import {MatDividerModule} from '@angular/material/divider';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, FormsModule, ReactiveFormsModule,MatInputModule,MatDividerModule,
    MatButtonModule,MatTableModule,MatTabsModule,MatIconModule,MatMenuModule,
    MatStepperModule,MatExpansionModule,MatFormFieldModule,MatDialogModule,
    CommonModule, BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
