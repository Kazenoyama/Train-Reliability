import { Component, EventEmitter, OnInit, Output, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { debounceTime, distinctUntilChanged, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { TrainFilter } from '../../models/train.model';
import { format, startOfMonth } from 'date-fns';
import { MatDatepicker } from '@angular/material/datepicker';

@Component({
  selector: 'app-filter-bar',
  standalone: false,
  templateUrl: './filter-bar.component.html',
  styleUrls: ['./filter-bar.component.scss']
})
export class FilterBarComponent implements OnInit, OnDestroy {
  @Output() filtersChanged = new EventEmitter<TrainFilter>();

  filterForm!: FormGroup;
  private destroy$ = new Subject<void>();

  constructor(private fb: FormBuilder) {}

  readonly minDate = new Date(2013, 0, 1);
  readonly maxDate = new Date(2026, 1, 1);

  readonly firstOfMonthFilter = (date: Date | null): boolean => {
    if (!date) return false;
    return date.getDate() === 1;
  };

  setMonthAndYear(normalizedMonthAndYear: Date, datepicker: MatDatepicker<Date>): void {
    const ctrlValue = this.filterForm.get('from')?.value || new Date();
    ctrlValue.setYear(normalizedMonthAndYear.getFullYear());
    ctrlValue.setMonth(normalizedMonthAndYear.getMonth());
    ctrlValue.setDate(1); // On force toujours au 1er
    this.filterForm.get('from')?.setValue(ctrlValue);
    datepicker.close();
  }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      trainType:   [null],
      dataSetType: [null],
      label:       [null],
      from:        [null],
    });

    this.filterForm.valueChanges.pipe(
      debounceTime(400),
      distinctUntilChanged((a, b) => JSON.stringify(a) === JSON.stringify(b)),
      takeUntil(this.destroy$)
    ).subscribe(values => {
      const filter: TrainFilter = {};
      if (values.trainType)   filter.trainType   = values.trainType;
      if (values.dataSetType) filter.dataSetType = values.dataSetType;
      if (values.label)       filter.label       = values.label;
      if (values.from) {
        filter.from = format(startOfMonth(new Date(values.from)), "yyyy-MM-dd");
      }
      this.filtersChanged.emit(filter);
    });
  }

  reset(): void {
    this.filterForm.reset();
    this.filtersChanged.emit({});
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
