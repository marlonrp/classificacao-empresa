import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "@src/environments/environment";
import { RateModel } from "@core/models/rate.model";
import { PageModel } from "@core/models/page.model";

@Injectable({
  providedIn: "root"
})
export class RateService {
  private rate = `${environment.urlBackend}/rate`;

  constructor(protected http: HttpClient) {}

  public getRateByMonth(
    month: number,
    page: number,
    size: number
  ): Observable<PageModel<RateModel>> {
    const formData = new FormData();
    formData.append("page", page.toString());
    formData.append("size", size.toString());
    return this.http.post<PageModel<RateModel>>(
      `${this.rate}/${month}`,
      formData
    );
  }

  public computeFile(
    companyId: number,
    month: number,
    file: File
  ): Observable<RateModel> {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("companyId", companyId.toString());
    formData.append("month", month.toString());
    return this.http.post<RateModel>(
      `${this.rate}/computeFile`,
      formData
    );
  }
}
