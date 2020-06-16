import { CompanyModel } from "@core/models/company.model";

export interface RateModel {
  id: number;
  month: number;
  score: number;
  company: CompanyModel;
}
