// ranking-list.component.ts — version NgModule
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TrainFilter, TrainRegularity } from '../../models/train.model';
import { TrainService } from '../../services/train.service';

@Component({
  selector: 'app-ranking-list',
  standalone: false,
  templateUrl: './ranking-list.component.html',
  styleUrls: ['./ranking-list.component.scss']
})
export class RankingListComponent implements OnInit, OnDestroy {
  trains: TrainRegularity[] = [];
  currentPage = 0;
  isLastPage = false;
  isLoading = false;
  activeFilters: TrainFilter = {};

  private destroy$ = new Subject<void>();

  constructor(private trainService: TrainService) {}

  ngOnInit(): void { this.loadPage(); }

  onFiltersChanged(filters: TrainFilter): void {
    this.activeFilters = filters;
    this.trains = [];
    this.currentPage = 0;
    this.isLastPage = false;
    this.loadPage();
  }

  onScroll(): void {
    if (this.isLoading || this.isLastPage) return;
    this.currentPage++;
    this.loadPage();
  }

  private loadPage(): void {
    this.isLoading = true;
    this.trainService.getRanking(this.activeFilters, this.currentPage)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (page) => {
          this.trains = [...this.trains, ...page.content];
          this.isLastPage = page.last;
          this.isLoading = false;
        },
        error: () => { this.isLoading = false; }
      });
  }

  trackById(_: number, item: TrainRegularity): number { return item.id; }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
