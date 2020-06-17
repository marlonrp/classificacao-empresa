import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "@src/environments/environment";
import { CompanyModel } from "@core/models/company.model";
import { RateModel } from "@core/models/rate.model";

@Injectable({
  providedIn: "root"
})
export class CompanyService {
  private companyEndPoint = `${environment.urlBackend}/company`;

  constructor(protected http: HttpClient) {}

  public getCompanies(): Observable<CompanyModel[]> {
    return this.http.get<CompanyModel[]>(this.companyEndPoint);
  }
}
