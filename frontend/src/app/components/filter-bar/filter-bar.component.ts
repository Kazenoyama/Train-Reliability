import { Component, EventEmitter, OnInit, Output, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { debounceTime, distinctUntilChanged, takeUntil, map, startWith } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { TrainFilter } from '../../models/train.model';
import { format, startOfMonth } from 'date-fns';
import { MatDatepicker } from '@angular/material/datepicker';
import { TrainService } from '../../services/train.service';
import { Observable } from 'rxjs';

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

  trainLabelOptions : string[] = [];
  filteredTrainLabelOptions!: Observable<string[]>

  dataSetOptions : string[] = [];
  filteredDataSetOptions!: Observable<string[]>

  trainTypeOptions : string[] = [];
  filteredTrainTypeOptions!: Observable<string[]>

  constructor(
    private fb: FormBuilder,
    private ts: TrainService
    ) {}

  readonly minDate = new Date(2013, 0, 1);
  readonly maxDate = new Date(2026, 1, 1);

  readonly firstOfMonthFilter = (date: Date | null): boolean => {
    if (!date) return false;
    return date.getDate() === 1;
  };

  setMonthAndYear(normalizedMonthAndYear: Date, datepicker: MatDatepicker<Date>, path: string): void {
    const newDate = new Date(
      normalizedMonthAndYear.getFullYear(),
      normalizedMonthAndYear.getMonth(),
      1
    );
    this.filterForm.get(path)?.setValue(newDate);

    datepicker.close();
  }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      trainType:   [null],
      dataSetType: [null],
      label:       [null],
      from:        [null],
    });

    this.ts.getUniqueValues("Label").subscribe({
      next:(data) => {
        this.trainLabelOptions = data;
        this.filteredTrainLabelOptions = this.setupAutocomplete(this.filteredTrainLabelOptions, this.trainLabelOptions, 'label');
      },
      error: (err) => console.error('Label error:', err)
    })

    this.ts.getUniqueValues("DataSet").subscribe({
      next:(data) => {
        this.dataSetOptions = data;
        this.filteredDataSetOptions = this.setupAutocomplete(this.filteredDataSetOptions, this.dataSetOptions, 'dataSetType');
      },
      error: (err) => console.error('DataSet error:', err)
    })

    this.ts.getUniqueValues('TrainType').subscribe({
      next:(data) => {
        this.trainTypeOptions = data;
        this.filteredTrainTypeOptions = this.setupAutocomplete(this.filteredTrainTypeOptions, this.trainTypeOptions, 'trainType');
      },
      error: (err) => console.error('DataSet error:', err)
    })


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

  private setupAutocomplete(filteredOption : Observable<string[]>,options : string[], path: string) {
    filteredOption = this.filterForm.get(path)!.valueChanges.pipe(
      startWith(''),
      map(value => this._filter((value || ''), options))
    );

    return filteredOption;
  }

  private _filter(value: string, options : string[]): string[] {
    const filterValue = value.toLowerCase();
    return options.filter(option =>
      option.toLowerCase().includes(filterValue)
    );
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
