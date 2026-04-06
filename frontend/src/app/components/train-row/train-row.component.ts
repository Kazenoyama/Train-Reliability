import {
  Component, Input, OnDestroy, ViewChild, ElementRef, ChangeDetectorRef
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Chart, registerables } from 'chart.js';
import { TrainRegularity } from '../../models/train.model';
import { TrainService } from '../../services/train.service';
import { format, startOfMonth } from 'date-fns';

Chart.register(...registerables);

@Component({
  selector: 'app-train-row',
  standalone: false,
  templateUrl: './train-row.component.html',
  styleUrls: ['./train-row.component.scss']
})
export class TrainRowComponent implements OnDestroy {
  @Input() train!: TrainRegularity;
  @Input() rank!: number;
  @ViewChild('chartCanvas') chartCanvasRef!: ElementRef<HTMLCanvasElement>;

  isExpanded = false;
  isLoadingChart = false;
  dateRangeForm: FormGroup;
  chartInstance: Chart | null = null;

  readonly minDate = new Date(2013, 0, 1);
  readonly maxDate = new Date(2026, 1, 1);

  readonly firstOfMonthFilter = (date: Date | null): boolean => {
    if (!date) return false;
    return date.getDate() === 1;
  };

  private destroy$ = new Subject<void>();

  constructor(
    private trainService: TrainService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.dateRangeForm = this.fb.group({
      from: [null, Validators.required],
      to:   [null, Validators.required],
    });
  }

  toggle(): void {
    this.isExpanded = !this.isExpanded;
  }

  loadChart(): void {
    if (this.dateRangeForm.invalid) return;
    const { from, to } = this.dateRangeForm.value;
    const fromStr = format(startOfMonth(new Date(from)), "yyyy-MM-dd");
    const toStr   = format(startOfMonth(new Date(to)), "yyyy-MM-dd");

    this.isLoadingChart = true;
    this.trainService.getChartData(this.train.label, fromStr, toStr)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (page) => {
          this.isLoadingChart = false;
          this.renderChart(page.content);
        },
        error: () => { this.isLoadingChart = false; }
      });
  }

  private renderChart(data: TrainRegularity[]): void {
    if (this.chartInstance) this.chartInstance.destroy();
    this.cdr.detectChanges();

    const ctx = this.chartCanvasRef.nativeElement.getContext('2d')!;

    const labels = data.map(d => d.date);
    const values = data.map(d => d.punctualityRate ?? 0);

    this.chartInstance = new Chart(ctx, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: `Tendance Ponctualité — ${this.train.label}`,
          data: values,
          fill: false,
          tension: 0.3,
          pointRadius: 1,
          borderColor: '#9e9e9e',
          segment: {
            borderColor: ctx => {
              const prev = ctx.p0.parsed.y;
              const curr = ctx.p1.parsed.y;
              if(curr == null || prev == null) return "#1a237e";
              return curr >= prev ? '#4caf50' : '#f44336';
            }
          }
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            min: 0,
            max: 100,
            ticks: { callback: v => `${v}%` }
          }
        }
      }
    });
  }

  ngOnDestroy(): void {
    if (this.chartInstance) this.chartInstance.destroy();
    this.destroy$.next();
    this.destroy$.complete();
  }
}
