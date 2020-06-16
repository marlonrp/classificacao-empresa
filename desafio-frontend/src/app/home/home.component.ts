import { Component, OnInit, OnDestroy } from "@angular/core";
import { FormControl } from "@angular/forms";
import { finalize, takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";

import { RateService } from "@core/entities/rate/rate.service";
import { UtilsService } from "@shared/utils/utils.service";
import { RateModel } from "@core/models/rate.model";
import { PageEventModel } from "@core/models/page-event.mode";
import { MonthModel } from "@core/models/month.model";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html"
})
export class HomeComponent implements OnInit, OnDestroy {
  public loading: boolean;
  public monthFormControl = new FormControl();
  public months: MonthModel[];
  public selectedMonth: MonthModel;
  public filteredMonths: MonthModel[];

  public pageSize = 1;
  public pageIndex = 0;
  public totalPages = 0;
  public sizeOptions = [10, 25, 50, 100];
  public rates: RateModel[] = [];
  public displayedColumns: string[] = ["name", "score"];

  private ngUnsubscribe = new Subject();
  constructor(
    public utilsService: UtilsService,
    private rateService: RateService
  ) {}

  ngOnInit(): void {
    this.defineFormControl();
    this.loadMonths();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  public handlePage(event: PageEventModel) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadRates();
  }

  private loadRates() {
    this.loading = true;
    this.selectedMonth = this.monthFormControl.value;
    this.rateService
      .getRateByMonth(this.selectedMonth.value, this.pageIndex, this.pageSize)
      .pipe(
        finalize(() => (this.loading = false)),
        takeUntil(this.ngUnsubscribe)
      )
      .subscribe(response => {
        this.rates = response.content;
        this.totalPages = response.totalPages;
      });
  }

  private loadMonths() {
    this.filteredMonths = this.months = this.utilsService.getMonths();
  }

  public displayName(object: any): string {
    return object && object.name ? object.name : "";
  }

  private defineFormControl() {
    this.monthFormControl.valueChanges.subscribe((searchValue: any) => {
      if (typeof searchValue === "string") {
        this.filteredMonths = this.utilsService.filter(
          searchValue,
          this.months
        );
      } else if (searchValue instanceof Object) {
        this.loadRates();
      }
    });
  }
}
