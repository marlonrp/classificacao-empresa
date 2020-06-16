import {
  Component,
  OnInit,
  ElementRef,
  ViewChild,
  OnDestroy
} from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormControl
} from "@angular/forms";
import { takeUntil, finalize } from "rxjs/operators";
import { Subject } from "rxjs";
import { MatSnackBar } from "@angular/material/snack-bar";

import { UtilsService } from "@shared/utils/utils.service";
import { CompanyService } from "@core/entities/company/company.service";
import { CompanyModel } from "@core/models/company.model";
import { MonthModel } from "@core/models/month.model";
import { FileUploadedModel } from "@core/models/file-uploaded.model";

@Component({
  selector: "app-form",
  templateUrl: "./form.component.html",
  styleUrls: ["./form.component.scss"]
})
export class FormComponent implements OnInit, OnDestroy {
  @ViewChild("fileUpload", { static: false }) fileUpload: ElementRef;

  public loading = true;
  public files: FileUploadedModel[] = [];
  public companies: CompanyModel[];
  public filteredCompanies: CompanyModel[];
  public months: MonthModel[];
  public filteredMonths: MonthModel[];
  public formGroup: FormGroup;

  private ngUnsubscribe = new Subject();

  constructor(
    public utilsService: UtilsService,
    private companyService: CompanyService,
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.createFormGroup();
    this.loadMonths();
    this.loadCompanies();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  public onClick() {
    if (!this.formGroup.valid) {
      return this.validateAllFormFields(this.formGroup);
    }
    const fileUpload = this.fileUpload.nativeElement;
    fileUpload.onchange = () => {
      for (const file of fileUpload.files) {
        this.files.push({
          data: file,
          inProgress: false,
          progress: 0,
          alreadyUploaded: false
        });
      }
      this.uploadFiles();
    };
    fileUpload.click();
  }

  private createFormGroup() {
    this.formGroup = this.formBuilder.group({
      company: [
        { value: undefined, disabled: false },
        Validators.compose([Validators.required])
      ],
      month: [
        { value: undefined, disabled: false },
        Validators.compose([Validators.required])
      ]
    });

    this.formGroup.get("company").valueChanges.subscribe(
      (searchValue: any) => {
        if (typeof searchValue === "string") {
          this.filteredCompanies = this.utilsService.filter(
            searchValue,
            this.companies
          );
          this.formGroup.get("company").setErrors({});
        }
      }
    );

    this.formGroup.get("month").valueChanges.subscribe(
      (searchValue: any) => {
        if (typeof searchValue === "string") {
          this.filteredMonths = this.utilsService.filter(
            searchValue,
            this.months
          );
          this.formGroup.get("month").setErrors({});
        }
      }
    );

    this.formGroup.valueChanges.subscribe(() => {
      if (this.files.length) {
        this.files = [];
      }
    });
  }

  private loadMonths() {
    this.filteredMonths = this.months = this.utilsService.getMonths();
  }

  private loadCompanies() {
    this.companyService
      .getCompanies()
      .pipe(
        takeUntil(this.ngUnsubscribe),
        finalize(() => (this.loading = false))
      )
      .subscribe(
        (companies: CompanyModel[]) =>
          (this.filteredCompanies = this.companies = companies)
      );
  }

  private uploadFiles() {
    this.fileUpload.nativeElement.value = "";
    this.files.forEach(file => {
      if (!file.alreadyUploaded) {
        this.uploadFile(file);
      }
    });
  }

  private uploadFile(file: FileUploadedModel) {
    file.alreadyUploaded = file.inProgress = true;
    const form = this.formGroup.getRawValue();
    this.companyService
      .computeFile(form.company.id, form.month.value, file.data)
      .pipe(
        takeUntil(this.ngUnsubscribe),
        finalize(() => (file.inProgress = false))
      )
      .subscribe(
        () => {
          file.progress = 100;
          this.openSnackBar("Upload realizado com sucesso", "X");
        },
        () => {
          file.progress = 0;
          this.openSnackBar(`Upload do arquivo ${file.data.name} falhou`, "X");
        }
      );
  }

  private validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      }
    });
  }

  private openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000
    });
  }
}
